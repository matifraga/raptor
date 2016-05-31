package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.HashtagService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;

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
@RequestMapping(value = { "/" })
public class FeedController extends TweetListController {

	private static final String CUSTOM = "custom";

	private static final String GLOBAL = "global";

	private static final String ACTIVE = "active";

	private static final String LINK = "link";

	private static final String TITLE_CODE = "titleCode";

	private final static String FEED = "feed";

	private static final String PAGE = "page";

	private static final int TIMELINE_SIZE = 10;

	private static final String GLOBAL_FEED = "globalfeed";

	private static final String TWEET_LIST = "tweetList";
	private static final String TRENDS_LIST = "trendsList";
	private static final String HEADER = "header";

	private static final String PAGE_INFO = "pageInfo";

	private static final int TRENDING_TOPIC_LIMIT = 5;

	@Autowired
	private HashtagService hashtagService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView feed(@PathVariable Map<Object, String> pathVariables,
							 @RequestParam(value = PAGE, defaultValue = "1") String pageValue) {

		Integer page = null;

		try {
			page = new Integer(pageValue);
		} catch (Exception e) {
			page = 1;
		}

		final ModelAndView mav = new ModelAndView(FEED);
		
		List<String> trendsList = hashtagService.getTrendingTopics(TRENDING_TOPIC_LIMIT);
		mav.addObject(TRENDS_LIST, trendsList);
		
		List<TweetViewModel> tweetList;
		User sessionUser = sessionUser();
		String active;

		if (sessionUser == null) {
			tweetList = TweetViewModel.transform(tweetService.globalFeed(TIMELINE_SIZE, page, null));
			active = GLOBAL;
		} else {
			tweetList = TweetViewModel.transform(tweetService.currentSessionFeed(sessionUser, TIMELINE_SIZE, page));
			active = CUSTOM;
		}

		List<Map<String, Object>> header = createHeader(sessionUser, active);
		mav.addObject(HEADER, header);

		mav.addObject(TWEET_LIST, tweetList);

		Map<String, Object> pageInfo = buildPageInfo(page, TIMELINE_SIZE, tweetList.size(), "./");
		mav.addObject(PAGE_INFO, pageInfo);

		return mav;
	}

	@RequestMapping(value={GLOBAL_FEED}, method = RequestMethod.GET)
	public ModelAndView globalFeed(@PathVariable Map<String, String> pathVariables,
								   @RequestParam(value = PAGE, defaultValue = "1") String pageValue) {

		Integer page = null;

		try {
			 page = new Integer(pageValue);
		} catch (Exception e) {
			 page = 1;
		}

		if(page<1) page = 1;

		final ModelAndView mav = new ModelAndView(FEED);

		List<String> trendsList = hashtagService.getTrendingTopics(TRENDING_TOPIC_LIMIT);
		mav.addObject(TRENDS_LIST, trendsList);

		List<TweetViewModel> tweetList;
		User sessionUser = sessionUser();

		tweetList = TweetViewModel.transform(tweetService.globalFeed(TIMELINE_SIZE, page, sessionUser));
		List<Map<String, Object>> header = createHeader(sessionUser, GLOBAL);
		mav.addObject(HEADER, header);

		mav.addObject(TWEET_LIST, tweetList);

		Map<String, Object> pageInfo = buildPageInfo(page, TIMELINE_SIZE, tweetList.size(), "./globalfeed");
		mav.addObject(PAGE_INFO, pageInfo);

		return mav;
	}

	private List<Map<String, Object>> createHeader(User u, String active) {

		List<Map<String, Object>> header = new ArrayList<>();

		HashMap<String, Object> global = new HashMap<>();
		global.put(TITLE_CODE, "feed.title.globalFeed");
		global.put(LINK, "globalfeed");
		global.put(ACTIVE, (active.equals(GLOBAL)?Boolean.TRUE:Boolean.FALSE));

		if(u != null) {
			HashMap<String, Object> custom = new HashMap<>();
			custom.put(TITLE_CODE, "feed.title.customFeed");
			custom.put(LINK, "");
			custom.put(ACTIVE, (active.equals(CUSTOM)?Boolean.TRUE:Boolean.FALSE));
			header.add(custom);
		}

		header.add(global);

		return header;
	}
}
