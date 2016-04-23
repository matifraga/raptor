package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

import java.util.List;

import ar.edu.itba.paw.webapp.viewmodels.TweetViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/search")
public class SearchController extends RaptorController{
	
	private final static String SEARCH = "searchResults";
	private final static String SEARCH_TYPE = "searchType";
	private final static String USER_SEARCH = "userSearch";
	private final static String TWEET_SEARCH = "tweetSearch";	
	private final static String RESULT = "resultList";
	private final static String NUMBER_OF_RESULTS = "number";
	
	private final static int USER_RESULTS_PER_PAGE = 10;
	private final static int TWEET_RESULTS_PER_PAGE = 10;
	
	private final static String SEARCH_TEXT = "searchText";
	
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

        //TODO check no input stream
        switch(text.charAt(0)){
        	case '#':   mav.addObject(SEARCH_TYPE, TWEET_SEARCH);
						List<TweetViewModel> hashtags = TweetViewModel.transform(tweetService.getHashtag(text.substring(1),TWEET_RESULTS_PER_PAGE,1));
						mav.addObject(NUMBER_OF_RESULTS, hashtags.size());
						mav.addObject(RESULT, hashtags);
						break;
        	case '@':   mav.addObject(SEARCH_TYPE, USER_SEARCH);
        				List<User> users = userService.searchUsers(text.substring(1),USER_RESULTS_PER_PAGE,1);
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