package ar.edu.itba.paw.webapp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
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
	public LoginForm loginForm(Model model) { 
		if(!model.containsAttribute("loginForm")){
			return new LoginForm();
		} else{
			return (LoginForm) model.asMap().get("loginForm");
		}
	}

	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
	   return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
}
