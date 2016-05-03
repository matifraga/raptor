package ar.edu.itba.paw.webapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.HashtagService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;
import ar.edu.itba.paw.webapp.viewmodels.UserViewModel;

@Controller
@RequestMapping(value="/user")
public class TimelineController extends RaptorController{
	
	private static final int TIMELINE_PIC_SIZE = 200;
	private static final String USERNAME = "username";
	private static final String PAGE = "page";
	private static final String FOLLOW = "follow";
	private static final String UNFOLLOW = "unfollow";
	private static final String MENTIONS = "mentions";
	private static final String MAP_USERS = "{" + USERNAME + "}";
	private static final String MAP_USERS_WITH_PAGING = MAP_USERS + "/{" + PAGE + ":[1-9][0-9]*}";

	private static final String MAP_USER_MENTIONS = MAP_USERS +"/" + MENTIONS;
	private static final String MAP_USER_MENTIONS_WITH_PAGING = MAP_USER_MENTIONS + "/{" + PAGE + ":[1-9][0-9]*}";

	private static final String MAP_USER_FOLLOW = MAP_USERS +"/" + FOLLOW;
	private static final String MAP_USER_UNFOLLOW = MAP_USERS +"/" + UNFOLLOW;

	private static final int 	TIMELINE_SIZE = 10;

	private static final String TIMELINE = "timeline";
	
	private static final String USER = "user";
	
	private static final String TWEET_LIST = "tweetList";
	private static final String TRENDS_LIST = "trendsList";
	private static final String USER_INFO = "userInfo";
	private static final String HEADER = "header";
	private static final String FOLLOWING = "following";

	private final static String REDIRECT = "redirect:";
	
	private static final int TRENDING_TOPIC_LIMIT = 5;
	
	@Autowired
	private UserService userService;

	@Autowired
	private TweetService tweetService;

	@Autowired
	private HashtagService hashtagService;

	@Autowired
	private FollowerService followerService;

	@RequestMapping(value={MAP_USER_FOLLOW}, method= RequestMethod.POST)
	public String follow(@PathVariable Map<String, String> pathVariables) {
		String currentProfileUsername = pathVariables.get(USERNAME);
		User currentProfileUser = userService.getUserWithUsername(currentProfileUsername);

		if(currentProfileUser != null) {
			if(sessionUser() == null);
			else if (sessionUser().getUsername().equals(currentProfileUsername));
			else if (!(followerService.isFollower(sessionUser(), currentProfileUser))) {
				followerService.follow(sessionUser(), currentProfileUser);
			}
		}

		return REDIRECT + "/user/" + currentProfileUsername;
	}

	@RequestMapping(value={MAP_USER_UNFOLLOW}, method= RequestMethod.POST)
	public String unfollow(@PathVariable Map<String, String> pathVariables) {
		String currentProfileUsername = pathVariables.get(USERNAME);
		User currentProfileUser = userService.getUserWithUsername(currentProfileUsername);

		if(currentProfileUser != null) {
			if(sessionUser() == null);
			else if (sessionUser().getUsername().equals(currentProfileUsername));
			else if (followerService.isFollower(sessionUser(), currentProfileUser)) {
				followerService.unfollow(sessionUser(), currentProfileUser);
			}
		}

		return REDIRECT + "/user/" + currentProfileUsername;
	}
	

	@RequestMapping(value={MAP_USERS, MAP_USERS_WITH_PAGING}, method= RequestMethod.GET)
	public ModelAndView timeline(@PathVariable Map<String, String> pathVariables){
	//(value=USERNAME) String username) {
		String username = pathVariables.get(USERNAME);
		int page = Integer.valueOf(pathVariables.getOrDefault(PAGE, "1"));
		final ModelAndView mav = new ModelAndView(TIMELINE);
		User u = userService.getUserWithUsername(username);

		if(u != null){
			mav.addObject(USER, new UserViewModel(u, TIMELINE_PIC_SIZE));

			List<TweetViewModel> tweetList = TweetViewModel.transform(tweetService.getTimeline(u.getId(), TIMELINE_SIZE, page));
			List<String> trendsList = hashtagService.getTrendingTopics(TRENDING_TOPIC_LIMIT);

			Map<String, Integer> userInfo = new HashMap<String, Integer>();
			userInfo.put("followers_count", followerService.countFollowers(u));
			userInfo.put("following_count", followerService.countFollowing(u));
			userInfo.put("tweets_count", tweetService.countTweets(u));

			mav.addObject(TWEET_LIST, tweetList);
			mav.addObject(TRENDS_LIST, trendsList);
			mav.addObject(USER_INFO, userInfo);

			if(sessionUser() == null) {
				mav.addObject(FOLLOWING, -1);
			} else if(sessionUser().getUsername().equals(username)) {
				mav.addObject(FOLLOWING, 2);
			} else {
				mav.addObject(FOLLOWING, (followerService.isFollower(sessionUser(), u)? 1 : 0));
			}

			List<Map<String, Object>> header = createHeader(u, "timeline");

			mav.addObject(HEADER, header);
		}
		return mav;
	}

	@RequestMapping(value={MAP_USER_MENTIONS, MAP_USER_MENTIONS_WITH_PAGING}, method= RequestMethod.GET)
	public ModelAndView mentions(@PathVariable Map<String, String> pathVariables){
		//(value=USERNAME) String username) {
		String username = pathVariables.get(USERNAME);
		int page = Integer.valueOf(pathVariables.getOrDefault(PAGE, "1"));
		final ModelAndView mav = new ModelAndView(TIMELINE);
		User u = userService.getUserWithUsername(username);

		if(u != null){
			mav.addObject(USER, new UserViewModel(u, TIMELINE_PIC_SIZE));

			List<TweetViewModel> mentionList = TweetViewModel.transform(tweetService.getMentions(u.getId(), TIMELINE_SIZE, page));
			List<String> trendsList = hashtagService.getTrendingTopics(TRENDING_TOPIC_LIMIT);

			Map<String, Integer> userInfo = new HashMap<String, Integer>();
			userInfo.put("followers_count", followerService.countFollowers(u));
			userInfo.put("following_count", followerService.countFollowing(u));
			userInfo.put("tweets_count", tweetService.countTweets(u));

			mav.addObject(TWEET_LIST, mentionList);
			mav.addObject(TRENDS_LIST, trendsList);
			mav.addObject(USER_INFO, userInfo);

			if(sessionUser() == null) {
				mav.addObject(FOLLOWING, -1);
			} else if(sessionUser().getUsername().equals(username)) {
				mav.addObject(FOLLOWING, 2);
			} else {
				mav.addObject(FOLLOWING, (followerService.isFollower(sessionUser(), u)? 1 : 0));
			}

			List<Map<String, Object>> header = createHeader(u, "mentions");

			mav.addObject(HEADER, header);
		}
		return mav;
	}

	private List<Map<String, Object>> createHeader(User u, String active) {

		List<Map<String, Object>> header = new ArrayList<Map<String, Object>>();

		HashMap<String, Object> timeline = new HashMap<String, Object>();
		timeline.put("title", messageSource.getMessage("timeline.timelineListTitle", null, null, null));
		timeline.put("link", "/user/" + u.getUsername());
		timeline.put("active", (active.equals("timeline")?Boolean.TRUE:Boolean.FALSE));

		HashMap<String, Object> mentions = new HashMap<String, Object>();
		mentions.put("title", messageSource.getMessage("timeline.mentionsListTitle", null, null, null));
		mentions.put("link", "/user/" + u.getUsername() + "/mentions");
		mentions.put("active", (active.equals("mentions")?Boolean.TRUE:Boolean.FALSE));

		header.add(timeline);
		header.add(mentions);

		return header;
	}
}