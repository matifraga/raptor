package ar.edu.itba.paw.webapp.services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;

@Service
@Component
public class PawUserDetailsService implements UserDetailsService { 

    private static final String USER_MESSAGE = "No user found by the name ";
	private static final String ADMIN_ROLE = "ROLE_ADMIN";
	
	@Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final User user = us.getUserWithUsername(username);
        if (user != null) {
            final Collection<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(ADMIN_ROLE));
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(), null/*password*/, true, true,
                true, true, authorities);
        }

        throw new UsernameNotFoundException(USER_MESSAGE + username); //TODO i18n
    }

}