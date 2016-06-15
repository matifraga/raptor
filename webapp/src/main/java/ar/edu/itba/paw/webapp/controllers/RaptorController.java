package ar.edu.itba.paw.webapp.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.paw.services.NotificationService;
import ar.edu.itba.paw.webapp.viewmodels.NotificationViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewmodels.UserViewModel;

public abstract class RaptorController {

	private static final String REDIRECT = "redirect:";

	private static final String REFERER = "Referer";

	private static final String ANONYMOUS_USER = "anonymousUser";

	@Autowired
	MessageSource messageSource;

	@Autowired
	protected UserService userService;

	@Autowired
	protected NotificationService notificationService;

	@ModelAttribute("sessionUser")
    public User sessionUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.getName().equals(ANONYMOUS_USER))
				return null;
			User user = (User)auth.getPrincipal();
            return user;
    }
	
	@ModelAttribute("navbarViewUser")
	public UserViewModel navbarUser() {
		
		User u = sessionUser();
		if(u == null) return null;
		
		return new UserViewModel(u, 50);
	}

	@ModelAttribute("notifications")
	public List<NotificationViewModel> notifications(){
		List<NotificationViewModel> noti = NotificationViewModel.transform(notificationService.getNotifications(sessionUser(), 5, 1));
		return noti;
	}

	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
	   return Optional.ofNullable(request.getHeader(REFERER)).map(requestUrl -> REDIRECT + requestUrl);
	}
}
