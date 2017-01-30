package ar.edu.itba.paw.webapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.LoginDTO;

@Path("auth")
@Component
public class AuthController {

	@Autowired
	UserService us;

	@Autowired
	protected AuthenticationProvider authenticationProvider;
	
	@Context
    private UriInfo uriInfo;
	
	@POST
	@Path("/login")
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response login(final LoginDTO loginDTO, @Context final HttpServletRequest request){
	
		User isLogged = SessionHandler.sessionUser();
		if (isLogged != null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		String username = loginDTO.getUsername();
		String password = loginDTO.getPassword();
		User loggedUser = us.authenticateUser(username, password);
		if(loggedUser == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
	    token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationProvider.authenticate(token);
	    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		
	    return Response.ok().build();
	}
	
//	@POST
//	@Path("/logout")
//	public Response logout(@Context final HttpServletRequest request){
//	
//		User loggedUser = SessionHandler.sessionUser();
//		if(loggedUser == null)
//			return Response.status(Response.Status.BAD_REQUEST).build();
//		
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//	    token.setDetails(new WebAuthenticationDetails(request));
//		Authentication authenticatedUser = authenticationProvider.authenticate(token);
//	    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//		
//	    return Response.ok().build();
//	}
}