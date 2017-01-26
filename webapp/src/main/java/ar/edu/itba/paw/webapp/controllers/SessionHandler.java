package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by luis on 1/26/17.
 */
public class SessionHandler {


    private static final String ANONYMOUS_USER = "anonymousUser";

    public static User sessionUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals(ANONYMOUS_USER))
            return null;
        User user = (User) auth.getPrincipal();
        return user;
    }

}
