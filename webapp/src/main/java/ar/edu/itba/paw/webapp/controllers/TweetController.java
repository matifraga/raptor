package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FavoriteService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;

@Controller
public class TweetController extends RaptorController{

	private static final String SUCCESS_0 = "{\"success\":0}";
	private static final String SUCCESS_1 = "{\"success\":1}";
	private static final String REDIRECT = "redirect:";
	private static final String MESSAGE = "message";
	private static final String TWEETID = "tweetId";

	private static final String ACTIONS = "/actions/";
	private static final String POST = "post";
	private static final String RETWEET = "retweet";
	private static final String FAVORITE = "favorite";

	@Autowired
	private UserService userService;

	@Autowired
	private TweetService tweetService;

	@Autowired
	private FavoriteService favoriteService;

	@RequestMapping(value =  {ACTIONS + POST}, method = RequestMethod.POST)
	public String postTweetAction(
			@RequestParam(value = MESSAGE) String message, HttpServletRequest request) {

		if(sessionUser() == null) {
			return REDIRECT + "/";
		}

		tweetService.register(message,
				userService.getUserWithUsername(sessionUser().getUsername()));

	    return getPreviousPageByRequest(request).orElse("/user/" + sessionUser().getUsername());
	}

	@RequestMapping(value = {ACTIONS + RETWEET}, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String retweetAction(@RequestParam(value = TWEETID) String tweetId,
								@RequestParam(value = RETWEET) Boolean retweet) {

		User sessionUser = sessionUser();
		Tweet tweet = tweetService.getTweet(tweetId, sessionUser);
		if(sessionUser != null) {
			if (retweet) {
				tweetService.retweet(tweet, sessionUser);
			} else {
				tweetService.unretweet(tweet, sessionUser);
			}
			return SUCCESS_1;
		}

		return SUCCESS_0;
	}

	@RequestMapping(value = {ACTIONS + FAVORITE}, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String favoriteTweetAction(@RequestParam(value = TWEETID) String tweetId,
									  @RequestParam(value = FAVORITE) Boolean favorite) {

		User sessionUser = sessionUser();
		Tweet tweet = tweetService.getTweet(tweetId, sessionUser);
		if(sessionUser != null) {
			if (favorite) {
				favoriteService.favorite(tweet, sessionUser);
			} else {
				favoriteService.unfavorite(tweet, sessionUser);
			}
			return SUCCESS_1;
		}

		return SUCCESS_0;
	}

}
