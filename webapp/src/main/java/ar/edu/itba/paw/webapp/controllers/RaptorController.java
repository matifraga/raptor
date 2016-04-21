package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.webapp.forms.LoginForm;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.models.User;

public abstract class RaptorController {

	@Autowired
	private HttpSession session;

	@Autowired
	MessageSource messageSource;
	
	@ModelAttribute("sessionUser")
	public User sessionUser() {
		return (User) session.getAttribute("user");
	}

	@ModelAttribute("loginForm")
	public LoginForm loginForm() { return new LoginForm();}

}
