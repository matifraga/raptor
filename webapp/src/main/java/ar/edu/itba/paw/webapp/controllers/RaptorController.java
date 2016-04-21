package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.models.User;

public abstract class RaptorController {

	protected final static String USER = "user";
	
	@Autowired
	protected HttpSession session;
	
	@ModelAttribute("sessionUser")
	public User sessionUser() {
		return (User) session.getAttribute(USER);
	}

}
