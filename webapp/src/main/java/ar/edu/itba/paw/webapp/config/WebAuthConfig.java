//@BEAN PROBLEM WITH USER DETAILS SERVICE.
/*
package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.edu.itba.paw.webapp.services.PawUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ADMIN";
    
	private static final String USERNAME = "j_username";
	private static final String PASSWORD = "j_password";
	private static final String REMEMBERME = "j_rememberme";
	private static final String KEY = "mysupersecretketthatnobodyknowsabout";
    
	private static final String ADMIN = "/admin/**";
	private static final String ALL = "/**";
	private static final String LOGOUT = "/logout";
	private static final String LOGIN = "/login";
	
	private static final String FORBIDDEN = "/403";
	private static final String FAVICON = "/favicon.ico";
	private static final String IMG = "/img/**";
	private static final String JS = "/js/**";
	private static final String CSS = "/css/**";
	
	@Autowired
    private PawUserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
            .sessionManagement()
                .invalidSessionUrl(LOGIN)
            .and().authorizeRequests()
                .antMatchers(LOGIN).anonymous()
                .antMatchers(ADMIN).hasRole(ADMIN_ROLE)
                .antMatchers(ALL).authenticated()
            .and().formLogin()
                .usernameParameter(USERNAME)
                .passwordParameter(PASSWORD)
                .defaultSuccessUrl("/", false)
                .loginPage(LOGIN)
            .and().rememberMe()
                .rememberMeParameter(REMEMBERME)
                .userDetailsService(userDetailsService)
                .key(KEY)
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
            .and().logout()
                .logoutUrl(LOGOUT)
                .logoutSuccessUrl(LOGIN)
            .and().exceptionHandling()
                .accessDeniedPage(FORBIDDEN)
            .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(CSS, JS, IMG, FAVICON, FORBIDDEN);
    }
}
*/