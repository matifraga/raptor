package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController extends RaptorController{

	private final static String FEED = "/";

	@RequestMapping
	public String logoutAction(HttpServletRequest request) {
		
	    session.removeAttribute(USER);
	    return getPreviousPageByRequest(request).orElse(FEED);
	}
}
