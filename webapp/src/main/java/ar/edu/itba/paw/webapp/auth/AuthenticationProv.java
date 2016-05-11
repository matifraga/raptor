package ar.edu.itba.paw.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;

@Component
class AuthenticationProv implements AuthenticationProvider{

	@Autowired
    private UserService userService;
	
	public AuthenticationProv() {
	}
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	String username = authentication.getName();
        String password = (String) authentication.getCredentials();
 
        User user = userService.authenticateUser(username, password);
        //User user = userService.getUserWithUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Username or password is incorrect.");
        }

 
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
 
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
