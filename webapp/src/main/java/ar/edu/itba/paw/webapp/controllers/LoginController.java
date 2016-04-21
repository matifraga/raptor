package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpSession;
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
public class LoginController {

	public static final String LOGIN = "login";
	private final static String REDIRECT = "redirect:";
	private final static String FEED = "/";

	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(Model model) {
		final ModelAndView mav = new ModelAndView(LOGIN);
		model.addAttribute("loginForm", new LoginForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String loginAction(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult results, HttpSession session) {
		
		if (results.hasErrors()) {
			return LOGIN;
		} else {
			//User user = userService.getUserWithUsername(form.getUsername());
			User user = userService.logInUser(form.getUsername(),form.getPassword());
			if(user != null){
				session.setAttribute("user", user);
				return REDIRECT + FEED;
			} else {
				results.rejectValue("password", "login.error", null);
				return LOGIN;
			}
		}
	}
}
