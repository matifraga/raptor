package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.services.HashtagService;
import ar.edu.itba.paw.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "/", "/{page}" })
public class FeedController extends RaptorController {

	private final static String FEED = "feed";

	private static final String PAGE = "page";

	private static final int TIMELINE_SIZE = 10;

	private static final String TWEET_LIST = "tweetList";
	private static final String TRENDS_LIST = "trendsList";

	private static final int TRENDING_TOPIC_LIMIT = 5;

	@Autowired
	private TweetService tweetService;

	@Autowired
	private HashtagService hashtagService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView feed(@PathVariable Map<String, String> pathVariables) {

		int page = Integer.valueOf(pathVariables.getOrDefault(PAGE, "1"));
		final ModelAndView mav = new ModelAndView(FEED);
		
		List<String> trendsList = hashtagService.getTrendingTopics(TRENDING_TOPIC_LIMIT);
		mav.addObject(TRENDS_LIST, trendsList);
		
		List<Tweet> tweetList;
		
		if (sessionUser() == null) {
			tweetList = tweetService.globalFeed(TIMELINE_SIZE, page);
		} else {
			tweetList = tweetService.currentSessionFeed(sessionUser().getId(), TIMELINE_SIZE, page);
		}

		mav.addObject(TWEET_LIST, tweetList);

		return mav;
	}
}
