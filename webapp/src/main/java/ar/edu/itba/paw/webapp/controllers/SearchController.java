package ar.edu.itba.paw.webapp.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;
import ar.edu.itba.paw.webapp.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = { "/search", "/search/{page:[1-9][0-9]*}" })
public class SearchController extends TweetListController {
	
	private static final String SEARCH = "searchResults";
	private static final String SEARCH_TYPE = "searchType";
	private static final String USER_SEARCH = "userSearch";
	private static final String TWEET_SEARCH = "tweetSearch";	
	private static final String RESULT = "resultList";
	private static final String NUMBER_OF_RESULTS = "number";
	
	private static final int USER_RESULTS_PER_PAGE = 10;
	private static final int TWEET_RESULTS_PER_PAGE = 10;
	
	private static final String SEARCH_TEXT = "searchText";
	private static final int SEARCH_PIC_SIZE = 150;

	private static final String PAGE_INFO = "pageInfo";
	private static final String PAGE = "page";

	@Autowired
	private UserService userService;
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = SEARCH_TEXT) String text,
							   @RequestParam(value = PAGE, defaultValue = "1") String pageValue,
							   HttpServletRequest request) {
		
        final ModelAndView mav = new ModelAndView(SEARCH);
        mav.addObject(SEARCH_TEXT, text);

		String baseURL = (String) request.getContextPath() + "/search";

		baseURL = TweetListController.addParameter(baseURL, SEARCH_TEXT, text);

		Integer page = null;

		try {
			page = new Integer(pageValue);
		} catch (Exception e) {
			page = 1;
		}

        if(text.length()==0)
        	return mav;

		Map<String, Object> pageInfo;

		User sessionUser = sessionUser();
        switch(text.charAt(0)){
        	case '#':   mav.addObject(SEARCH_TYPE, TWEET_SEARCH);
						List<TweetViewModel> hashtags = TweetViewModel.transform(tweetService.getHashtag(text.substring(1),TWEET_RESULTS_PER_PAGE, page, (sessionUser==null)?null:sessionUser.getId()));
						mav.addObject(NUMBER_OF_RESULTS, hashtags.size());
						mav.addObject(RESULT, hashtags);
						pageInfo = buildPageInfo(page, TWEET_RESULTS_PER_PAGE, hashtags.size(), baseURL);
						mav.addObject(PAGE_INFO, pageInfo);
						break;
        	case '@':   mav.addObject(SEARCH_TYPE, USER_SEARCH);
        				List<UserViewModel> users = UserViewModel.transform(userService.searchUsers(text.substring(1),USER_RESULTS_PER_PAGE,1), SEARCH_PIC_SIZE);
        				mav.addObject(NUMBER_OF_RESULTS, users.size());
        				mav.addObject(RESULT, users);
        				break;
        	default:	mav.addObject(SEARCH_TYPE, TWEET_SEARCH);
						List<TweetViewModel> tweets = TweetViewModel.transform(tweetService.searchTweets(text,TWEET_RESULTS_PER_PAGE, page, (sessionUser==null)?null:sessionUser.getId()));
						mav.addObject(NUMBER_OF_RESULTS, tweets.size());
						mav.addObject(RESULT, tweets);
						pageInfo = buildPageInfo(page, TWEET_RESULTS_PER_PAGE, tweets.size(), baseURL);
						mav.addObject(PAGE_INFO, pageInfo);
						break;
        }
        
        return mav;
    }
}