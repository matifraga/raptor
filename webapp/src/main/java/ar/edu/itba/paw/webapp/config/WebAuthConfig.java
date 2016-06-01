package ar.edu.itba.paw.webapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableWebSecurity
@ComponentScan({ "ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	private static final String EVERYTHING = "**";
	private static final String ERROR_PAGE = "/403";
	private static final String LOGOUT = "/logout";
	private static final String PASSWORD = "j_password";
	private static final String USERNAME = "j_username";
	private static final String FEED = "/";
	private static final String SIGNUP = "/signup";
	private static final String LOGIN = "/login";

	@Autowired
	private AuthenticationProvider authProv;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private AuthenticationFailureHandler failureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {

		http.authenticationProvider(authProv)
            .authorizeRequests()
            	.antMatchers(LOGIN).anonymous()
            	.antMatchers(SIGNUP).anonymous()
            	.antMatchers(EVERYTHING).permitAll()
        	.and().sessionManagement()
        		.invalidSessionUrl(FEED)
            .and().formLogin()
            	.loginPage(LOGIN)
	            .usernameParameter(USERNAME)
	            .passwordParameter(PASSWORD)
	            .successHandler(successHandler)
				.failureHandler(failureHandler)
	        .and().logout()
            	.logoutUrl(LOGOUT)
            	.logoutSuccessUrl(FEED)
	        .and().exceptionHandling().
            	accessDeniedPage(ERROR_PAGE)
            .and().csrf().disable();
	}

	@Component
	class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
		
		private static final String ERROR_PARAM_TRUE = "?error=true";
		private static final String REFERER = "Referer";

		@Autowired
		public FailureHandler(){
			super();
		}
		
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			setDefaultFailureUrl(getFailureUrl(request));
			super.onAuthenticationFailure(request, response, exception);
		}
		
		private String getFailureUrl(HttpServletRequest request){
			String ref  = request.getHeader(REFERER); 
			int index = ref.indexOf("?");
			return ref.substring(0, index==-1?ref.length():index) + ERROR_PARAM_TRUE;
		}

	}

	@Component
	class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
		
		private static final String REFERER = "Referer";

		@Autowired
		public SuccessHandler(){
			super();
		}
		
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			setDefaultTargetUrl(getSuccessUrl(request));
			super.onAuthenticationSuccess(request, response, authentication);
		}
		
		private String getSuccessUrl(HttpServletRequest request){ //TODO why does split not work?
			String ref  = request.getHeader(REFERER); 
			int index = ref.indexOf("?");
			return ref.substring(0, index==-1?ref.length():index);
		}
	}
	
}
