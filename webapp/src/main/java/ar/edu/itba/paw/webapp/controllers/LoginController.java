package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.forms.LoginForm;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends RaptorController{
	
	private final static String FEED = "/";
	
	@Autowired
	UserService userService;

	/*private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@ModelAttribute
    public User loggedUser() {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final User user = userService.getUserWithUsername("pepe");
            LOGGER.debug("Logged user is {}", user);
            return user;
    }*/
	
	@RequestMapping(method = RequestMethod.POST)
	public String loginAction(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult results, HttpServletRequest request, RedirectAttributes attr) {
		
	    attr.addFlashAttribute("loginForm", form);
		
		if (results.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.loginForm", results);
			return getPreviousPageByRequest(request).orElse(FEED);
		} else {
			User user = userService.logInUser(form.getUsername(),form.getPassword());
			if(user != null){
				session.setAttribute(USER, user);
				return getPreviousPageByRequest(request).orElse(FEED);
			} else {
				results.rejectValue("password", "login.error", null);
				attr.addFlashAttribute("org.springframework.validation.BindingResult.loginForm", results);
				return getPreviousPageByRequest(request).orElse(FEED);
			}
		}
	}
}
