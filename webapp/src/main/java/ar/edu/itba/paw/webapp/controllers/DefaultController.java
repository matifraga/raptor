package ar.edu.itba.paw.webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;

@Controller
@RequestMapping(value = { "*/**" })
public class DefaultController extends RaptorController {

	private static final String SEARCH = "searchResults";
	private static final String SEARCH_TYPE = "searchType";
	private static final String TWEET_SEARCH = "tweetSearch";
	private static final String RESULT = "resultList";
	private static final String NUMBER_OF_RESULTS = "number";

	private static final int TWEET_RESULTS_PER_PAGE = 10;

	private static final String SEARCH_TEXT = "searchText";

	@Autowired
	private UserService userService;

	@Autowired
	private TweetService tweetService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search() {

		final ModelAndView mav = new ModelAndView(SEARCH);

		String text = "#funciona";
		
		mav.addObject(SEARCH_TEXT, text);

		mav.addObject(SEARCH_TYPE, TWEET_SEARCH);
		List<TweetViewModel> hashtags = TweetViewModel.transform(tweetService
				.getHashtag(text.substring(1), TWEET_RESULTS_PER_PAGE, 1));
		mav.addObject(NUMBER_OF_RESULTS, hashtags.size());
		mav.addObject(RESULT, hashtags);

		return mav;
	}
}
