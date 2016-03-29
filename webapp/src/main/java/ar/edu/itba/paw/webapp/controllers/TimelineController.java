package ar.edu.itba.paw.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.UserService;

@Controller
public class TimelineController {
	
	@Autowired
	private UserService userService;
	  
	@RequestMapping("/")
	public ModelAndView timeline() {
		final ModelAndView mav = new ModelAndView("timeline");
		mav.addObject("greeting", "Rawrrr!");
		mav.addObject("imageLink", "https://s-media-cache-ak0.pinimg.com/736x/cf/7e/7d/cf7e7db68d7926e96ca586411c1bf9ca.jpg");
		return mav;
	}
}
