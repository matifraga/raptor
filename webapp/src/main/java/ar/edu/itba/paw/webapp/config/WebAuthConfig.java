package ar.edu.itba.paw.webapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@ComponentScan({ "ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProvider authProv;
	
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
            	.antMatchers("/**").permitAll()
        	.and().sessionManagement()
        		.invalidSessionUrl("/")
            .and().formLogin()
            	.loginPage("/login")
	            .usernameParameter("j_username")
	            .passwordParameter("j_password")
	            .failureHandler(failureHandler)
	            .successHandler(successHandler)
	        .and().logout()
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/login")
	        .and().exceptionHandling().
            	accessDeniedPage("/403")
            .and().csrf().disable();
	}
	
	@Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }
	
	@Bean			//for spring security
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
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
			return "/login?error";
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
