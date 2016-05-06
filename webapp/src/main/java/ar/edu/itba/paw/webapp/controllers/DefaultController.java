package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "*/**" })
public class DefaultController extends RaptorController {

	private static final String PAGE_NOT_FOUND = "pageNotFound";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView pageNotFound() {

		return new ModelAndView(PAGE_NOT_FOUND);
	}
}
