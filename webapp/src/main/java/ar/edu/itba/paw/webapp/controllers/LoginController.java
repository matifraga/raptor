package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.forms.LoginForm;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends RaptorController{

	private final static String REDIRECT = "redirect:";
	private final static String FEED = "/";

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public String loginAction(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult results) {
		
		if (results.hasErrors()) {
			return "feed";
		} else {
			User user = userService.logInUser(form.getUsername(),form.getPassword());
			if(user != null){
				session.setAttribute(USER, user);
				return REDIRECT + FEED;
			} else {
				results.rejectValue("password", "login.error", null);
				return "feed";
			}
		}
	}
}
