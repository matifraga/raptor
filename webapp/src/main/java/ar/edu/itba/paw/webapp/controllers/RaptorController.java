package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.LoginForm;

public abstract class RaptorController {

	protected final static String USER = "user";

	@Autowired
	MessageSource messageSource;

	@Autowired
	protected HttpSession session;
	
	@ModelAttribute("sessionUser")
	public User sessionUser() {
		return (User) session.getAttribute(USER);
	}

	@ModelAttribute("loginForm")
	public LoginForm loginForm() { return new LoginForm();}

}
