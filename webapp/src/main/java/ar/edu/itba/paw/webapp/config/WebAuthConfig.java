package ar.edu.itba.paw.webapp.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.User;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@ComponentScan({ "ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	/*private static final String EVERYTHING = "**";
	private static final String ERROR_PAGE = "/403";
	private static final String LOGOUT = "/logout";
	private static final String PASSWORD = "j_password";
	private static final String USERNAME = "j_username";
	private static final String FEED = "/";
	private static final String SIGNUP = "/signup";
	private static final String LOGIN = "/login";

	@Autowired
	private AuthenticationProvider authProv;

//	@Autowired
//	private WebApplicationContext context;

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
			if (ref == null)
				return "/";
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
			if (ref == null)
				return "/";
			int index = ref.indexOf("?");
			return ref.substring(0, index==-1?ref.length():index);
		}
	}*/


	private static final String LOGIN_PATH = "/auth/login";
	private static final String LOGOUT_PATH = "/auth/logout";
	private static final String PASSWORD = "j_password";
	private static final String USERNAME = "j_username";
	private static final String EVERYTHING = "**";
	private static final String USER = "/user/?*";
	private static final String USERS = "/users/**";
	private static final String RAWRS = "/rawrs/**";
    private static final String FEED = "/feed";
    private static final String SEARCH = "/search/**";
    private static final String TRENDING = "/trending";
    private static final String SIGNUP = "/signup";
    private static final String TOKEN = "/auth/token";


	@Autowired
	private AuthenticationProvider authProv;
	@Autowired
	private HttpAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	@Autowired
	private AuthFailureHandler authFailureHandler;
	@Autowired
	private HttpLogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.requestMatchers(new AntPathRequestMatcher(SIGNUP, "POST")).anonymous()
				.requestMatchers(new AntPathRequestMatcher(LOGIN_PATH,"POST")).anonymous()
				.requestMatchers(new AntPathRequestMatcher(LOGOUT_PATH,"POST")).authenticated()
				.requestMatchers(new AntPathRequestMatcher(USERS,"GET")).permitAll()
				.requestMatchers(new AntPathRequestMatcher(RAWRS,"GET")).permitAll()
				.antMatchers(FEED).permitAll()
				.antMatchers(SEARCH).permitAll()
				.antMatchers(TRENDING).permitAll()
				.antMatchers(TOKEN).permitAll()
				.anyRequest().authenticated();



		http.csrf().csrfTokenRepository(new  HttpSessionCsrfTokenRepository())
				.ignoringAntMatchers(LOGIN_PATH)
				.ignoringAntMatchers(SIGNUP)
				.and()
				.authenticationProvider(authProv)
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
				.formLogin()
				.loginProcessingUrl(LOGIN_PATH)
				.usernameParameter(USERNAME)
				.passwordParameter(PASSWORD)
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH, "POST"))
				.logoutSuccessHandler(logoutSuccessHandler);
/*				.sessionManagement()
				.maximumSessions(1);*/

		//http.authorizeRequests().anyRequest().authenticated();


	}

	@Component
	public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

	    @Autowired
	    public HttpAuthenticationEntryPoint() {
            super();
        }

        @Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
							 AuthenticationException authException) throws IOException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		}
	}

	@Component
	public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
		private final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);


		@Autowired
		public AuthSuccessHandler(){
			super();
		}

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
											Authentication authentication) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_OK);

			User user = (User) authentication.getPrincipal();
			LOGGER.info(user.getUsername() + " is connected ");
            CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
            LOGGER.info("token: " + token);
            response.setHeader("X-CSRF-HEADER",token.getHeaderName());
            response.setHeader("X-CSRF-PARAM", token.getParameterName());
            response.setHeader("X-CSRF-TOKEN", token.getToken());
			response.getWriter().flush();

		}
	}

	@Component
	public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

        @Autowired
        public AuthFailureHandler(){
            super();
        }

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
											AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().flush();
		}
	}

	@Component
	public class HttpLogoutSuccessHandler implements LogoutSuccessHandler {

	    @Autowired
        public HttpLogoutSuccessHandler() {
            super();
        }

        @Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
				throws IOException {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().flush();
		}
	}
}
