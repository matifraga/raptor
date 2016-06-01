package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.HashtagService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;
import ar.edu.itba.paw.webapp.viewmodels.UserViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/user")
public class TimelineController extends TweetListController {
	
	private static final String TWEETS_COUNT = "tweets_count";
	private static final String FOLLOWING_COUNT = "following_count";
	private static final String FOLLOWERS_COUNT = "followers_count";
	private static final String ACTIVE = "active";
	private static final String LINK = "link";
	private static final String TITLE_CODE = "titleCode";
	private static final int TIMELINE_PIC_SIZE = 200;
	private static final String USERNAME = "username";
	private static final String PAGE = "page";
	private static final String FOLLOW = "follow";
	private static final String UNFOLLOW = "unfollow";
	private static final String MENTIONS = "mentions";
	private static final String FAVORITES = "favorites";
	private static final String MAP_USERS = "{" + USERNAME + "}";

	private static final String MAP_USER_MENTIONS = MAP_USERS +"/" + MENTIONS;
	private static final String MAP_USER_FAVORITES = MAP_USERS + "/" + FAVORITES;
	private static final String MAP_USER_FOLLOW = MAP_USERS +"/" + FOLLOW;
	private static final String MAP_USER_UNFOLLOW = MAP_USERS +"/" + UNFOLLOW;

	private static final int 	TIMELINE_SIZE = 10;
	private static final String TIMELINE = "timeline";
	private static final String USER = "user";
	private static final String TWEET_LIST = "tweetList";
	private static final String TRENDS_LIST = "trendsList";
	private static final String USER_INFO = "userInfo";
	private static final String HEADER = "header";
	private static final String PAGE_INFO = "pageInfo";
	private final static String REDIRECT = "redirect:";
	
	private static final int TRENDING_TOPIC_LIMIT = 5;

	@Autowired
	private FollowerService followerService;

	@Autowired
	private HashtagService hashtagService;

	@Autowired
	private UserService userService;

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
	

	@RequestMapping(value={MAP_USERS}, method= RequestMethod.GET)
	public ModelAndView timeline(@PathVariable Map<String, String> pathVariables,
								 @RequestParam(value = PAGE, defaultValue = "1") String pageValue){

		Integer page = null;

		try {
			page = new Integer(pageValue);
		} catch (Exception e) {
			page = 1;
		}

		String username = pathVariables.get(USERNAME);

		User u = userService.getUserWithUsername(username);
		List<Tweet> tweetList = null;

		if(u != null) {
			tweetList = tweetService.getTimeline(u.getId(), TIMELINE_SIZE, page, (sessionUser()==null)?null:sessionUser().getId());
		}

		return buildMav(tweetList, u, page, "timeline", "");
	}

	@RequestMapping(value={MAP_USER_MENTIONS}, method= RequestMethod.GET)
	public ModelAndView mentions(@PathVariable Map<String, String> pathVariables,
								 @RequestParam(value = PAGE, defaultValue = "1") String pageValue){

		Integer page = null;

		try {
			page = new Integer(pageValue);
		} catch (Exception e) {
			page = 1;
		}

		String username = pathVariables.get(USERNAME);

		User u = userService.getUserWithUsername(username);
		List<Tweet> tweetList = null;

		if(u != null) {
			tweetList = tweetService.getMentions(u.getId(), TIMELINE_SIZE, page, (sessionUser()==null)?null:sessionUser().getId());
		}

		return buildMav(tweetList, u, page, "mentions", MENTIONS);
	}

	@RequestMapping(value={MAP_USER_FAVORITES}, method= RequestMethod.GET)
	public ModelAndView favorites(@PathVariable Map<String, String> pathVariables,
								  @RequestParam(value = PAGE, defaultValue = "1") String pageValue){

		Integer page = null;

		try {
			page = new Integer(pageValue);
		} catch (Exception e) {
			page = 1;
		}

		String username = pathVariables.get(USERNAME);

		User u = userService.getUserWithUsername(username);
		List<Tweet> tweetList = null;

		if(u != null) {
			tweetList = tweetService.getFavorites(u.getId(), TIMELINE_SIZE, page, (sessionUser()==null)?null:sessionUser().getId());
		}

		return buildMav(tweetList, u, page, "favorites", FAVORITES);
	}

	private ModelAndView buildMav(List<Tweet> tweetList, User user, Integer page, String headerType, String pageBase) {
		final ModelAndView mav = new ModelAndView(TIMELINE);

		if(user != null){
			List<String> trendsList = hashtagService.getTrendingTopics(TRENDING_TOPIC_LIMIT);
			List<TweetViewModel> tweetViewList = transform(tweetList);

			Map<String, Integer> userInfo = new HashMap<>();
			userInfo.put(FOLLOWERS_COUNT, followerService.countFollowers(user));
			userInfo.put(FOLLOWING_COUNT, followerService.countFollowing(user));
			userInfo.put(TWEETS_COUNT, tweetService.countTweets(user));

			UserViewModel uvm = new UserViewModel(user, TIMELINE_PIC_SIZE);
			uvm.setFollowersCount(followerService.countFollowers(user));
			uvm.setFollowingCount(followerService.countFollowing(user));
			uvm.setTweetsCount(tweetService.countTweets(user));
			if (sessionUser() != null) {
				uvm.setFollowing(followerService.isFollower(sessionUser(), user));
			}
			mav.addObject(USER, uvm);

			mav.addObject(TWEET_LIST, tweetViewList);
			mav.addObject(TRENDS_LIST, trendsList);
			mav.addObject(USER_INFO, userInfo);

			List<Map<String, Object>> header = createHeader(user, headerType);
			mav.addObject(HEADER, header);

			Map<String, Object> pageInfo = buildPageInfo(page, TIMELINE_SIZE, tweetViewList.size(), "user/" + user.getUsername() + "/" + pageBase);
			mav.addObject(PAGE_INFO, pageInfo);
		}
		return mav;
	}

	private List<Map<String, Object>> createHeader(User u, String active) {

		List<Map<String, Object>> header = new ArrayList<>();

		HashMap<String, Object> timeline = new HashMap<>();
		timeline.put(TITLE_CODE, "timeline.timelineListTitle");
		timeline.put(LINK, "user/" + u.getUsername());
		timeline.put(ACTIVE, (active.equals("timeline")?Boolean.TRUE:Boolean.FALSE));

		HashMap<String, Object> mentions = new HashMap<>();
		mentions.put(TITLE_CODE, "timeline.mentionsListTitle");
		mentions.put(LINK, "user/" + u.getUsername() + "/mentions");
		mentions.put(ACTIVE, (active.equals("mentions")?Boolean.TRUE:Boolean.FALSE));

		HashMap<String, Object> favorites = new HashMap<>();
		favorites.put(TITLE_CODE, "timeline.favoritesListTitle");
		favorites.put(LINK, "user/" + u.getUsername() + "/favorites");
		favorites.put(ACTIVE, (active.equals("favorites")?Boolean.TRUE:Boolean.FALSE));

		header.add(timeline);
		header.add(mentions);
		header.add(favorites);

		return header;
	}
}