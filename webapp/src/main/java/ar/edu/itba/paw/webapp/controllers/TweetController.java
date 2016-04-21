package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.services.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ar.edu.itba.paw.services.UserService;

@Controller
@RequestMapping("/tweetAction")
public class TweetController extends RaptorController{

	private static final String USERNAME = "username";
	private static final String MAP_USER = "/user/";

	private static final String REDIRECT = "redirect:";
	private static final String MESSAGE = "message";

	@Autowired
	private UserService userService;

	@Autowired
	private TweetService tweetService;

	@RequestMapping(method = RequestMethod.POST)
	public String registerAction(
			@RequestParam(value = MESSAGE, required = true) String message,
			@RequestParam(value = USERNAME, required = true) String username) {

		tweetService.register(message,
				userService.getUserWithUsername(username));

		return REDIRECT + MAP_USER + username;
	}
}
