package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.forms.SignupForm;

@Controller
@RequestMapping("/signup")
public class SignupController extends RaptorController{

	private final static String SIGNUP = "signup";
	private final static String REDIRECT = "redirect:";

	private static final String USERNAME = "username";

	@Autowired
	private UserService userService;

	@Autowired
	protected AuthenticationProvider authenticationProvider;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView signUp(Model model) {
		final ModelAndView mav = new ModelAndView(SIGNUP);
		model.addAttribute("signUpForm", new SignupForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerAction(@Valid @ModelAttribute("signUpForm") SignupForm form, BindingResult results, HttpServletRequest request){

		if (results.hasErrors()) {
			return SIGNUP;
		 
		} else {
		 
			User user = userService.register(form.getUsername(), form.getPassword(), 
					form.getEmail(), form.getFirstName(), form.getLastName());
			if(user==null){
				results.rejectValue(USERNAME, "form.username.exists");
				return SIGNUP;
			}

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());

	        token.setDetails(new WebAuthenticationDetails(request));

			Authentication authenticatedUser = authenticationProvider.authenticate(token);

	        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

			return REDIRECT;
		}
	}
}