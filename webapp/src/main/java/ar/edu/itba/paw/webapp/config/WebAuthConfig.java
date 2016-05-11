package ar.edu.itba.paw.webapp.config;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
            	.antMatchers("/login").anonymous()
            	.antMatchers("/signup").anonymous()
            	.antMatchers("**").permitAll()
        	.and().sessionManagement()
        		.invalidSessionUrl("/")
            .and().formLogin()
            	.loginPage("/login")
	            .usernameParameter("j_username")
	            .passwordParameter("j_password")
	            .successHandler(successHandler)
				.failureHandler(failureHandler)
	        .and().logout()
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/")
	        .and().exceptionHandling().
            	accessDeniedPage("/403")
            .and().csrf().disable();
	}

	@Component
	class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
		
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
			return request.getHeader("Referer") + "?error=true";
		}

	}

	@Component
	class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
		
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
		
		private String getSuccessUrl(HttpServletRequest request){
			return request.getHeader("Referer");
		}
	}
	
}
