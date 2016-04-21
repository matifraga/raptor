package ar.edu.itba.paw.webapp.controllers;

import java.lang.reflect.Array;
import java.util.ArrayDeque;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.forms.LoginForm;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private final static String REDIRECT = "redirect:";
	private final static String FEED = "/";

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public String loginAction(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult results, HttpSession session) {
		
		if (results.hasErrors()) {
			return REDIRECT + FEED;
		} else {
			//User user = userService.getUserWithUsername(form.getUsername());
			User user = userService.logInUser(form.getUsername(),form.getPassword());
			if(user != null){
				session.setAttribute("user", user);
				return REDIRECT + FEED;
			} else {
				results.rejectValue("password", "login.error", null);
				return REDIRECT + FEED;
			}
		}
	}
}
