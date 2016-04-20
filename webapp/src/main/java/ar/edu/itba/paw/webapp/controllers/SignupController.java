package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.forms.SignupForm;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/signup")
public class SignupController {

	private final static String SIGNUP = "signup";

	private final static String MAP_SIGNUP = "/signup";
	private final static String MAP_USER = "/user/";
	private final static String REDIRECT = "redirect:";

//	private static final String USERNAME = "username";
//	private static final String PASSWORD = "password";
//	private static final String EMAIL = "email";
//	private static final String FIRSTNAME = "firstName";
//	private static final String LASTNAME = "lastName";

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView signUp(Model model) {
		final ModelAndView mav = new ModelAndView(SIGNUP);
		model.addAttribute("signUpForm", new SignupForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerAction(@Valid @ModelAttribute("signUpForm") SignupForm form, BindingResult results){
//			@RequestParam(value=PASSWORD, required=true) String password,
//								 @RequestParam(value=USERNAME, required=true) String username,
//								 @RequestParam(value=FIRSTNAME, required=true) String firstName,
//								 @RequestParam(value=LASTNAME, required=true) String lastName,
//								 @RequestParam(value=EMAIL, required=true) String email) {

		if (results.hasErrors()) {
			return SIGNUP;
		 
		} else {
		 
			User user = userService.register(form.getUsername(), form.getPassword(), 
					form.getEmail(), form.getFirstName(), form.getLastName());
			return REDIRECT + MAP_USER + user.getUsername();

		}
	}
}