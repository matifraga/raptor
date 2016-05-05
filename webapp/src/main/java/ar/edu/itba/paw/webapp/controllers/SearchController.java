package ar.edu.itba.paw.webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;
import ar.edu.itba.paw.webapp.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = { "/search", "/search/{page:[1-9][0-9]*}" })
public class SearchController extends RaptorController{
	
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@RequestMapping(method= RequestMethod.GET)
    public ModelAndView search(@RequestParam(value=SEARCH_TEXT, required=true) String text) {
		
        final ModelAndView mav = new ModelAndView(SEARCH);
        
        mav.addObject(SEARCH_TEXT, text);

        if(text.length()==0)
        	return mav;

        switch(text.charAt(0)){
        	case '#':   mav.addObject(SEARCH_TYPE, TWEET_SEARCH);
						List<TweetViewModel> hashtags = TweetViewModel.transform(tweetService.getHashtag(text.substring(1),TWEET_RESULTS_PER_PAGE,1));
						mav.addObject(NUMBER_OF_RESULTS, hashtags.size());
						mav.addObject(RESULT, hashtags);
						break;
        	case '@':   mav.addObject(SEARCH_TYPE, USER_SEARCH);
        				List<UserViewModel> users = UserViewModel.transform(userService.searchUsers(text.substring(1),USER_RESULTS_PER_PAGE,1), SEARCH_PIC_SIZE);
        				mav.addObject(NUMBER_OF_RESULTS, users.size());
        				mav.addObject(RESULT, users);
        				break;
        	default:	mav.addObject(SEARCH_TYPE, TWEET_SEARCH);
						List<TweetViewModel> tweets = TweetViewModel.transform(tweetService.searchTweets(text,TWEET_RESULTS_PER_PAGE,1));
						mav.addObject(NUMBER_OF_RESULTS, tweets.size());
						mav.addObject(RESULT, tweets);
						break;
        }
        
        return mav;
    }
}