package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.services.FavoriteService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TweetController extends RaptorController{

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

		//return REDIRECT +  "/user/" + sessionUser().getUsername();
	}

	@RequestMapping(value = {ACTIONS + RETWEET}, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String retweetAction(@RequestParam(value = TWEETID) String tweetId,
								@RequestParam(value = RETWEET) Boolean retweet) {

		if(sessionUser() != null) {
			if (retweet) {
				tweetService.retweet(tweetId, sessionUser());
			} else {
				tweetService.unretweet(tweetId, sessionUser());
			}
			return "{\"success\":1}";
		}

		return "{\"success\":0}";
	}

	@RequestMapping(value = {ACTIONS + FAVORITE}, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String favoriteTweetAction(@RequestParam(value = TWEETID) String tweetId,
									  @RequestParam(value = FAVORITE) Boolean favorite) {

		if(sessionUser() != null) {
			if (favorite) {
				favoriteService.favorite(tweetId, sessionUser());
			} else {
				favoriteService.unfavorite(tweetId, sessionUser());
			}
			return "{\"success\":1}";
		}

		return "{\"success\":0}";
	}

}
