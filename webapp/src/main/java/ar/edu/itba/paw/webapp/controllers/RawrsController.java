package ar.edu.itba.paw.webapp.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FavoriteService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.StatusDTO;

@Path("rawrs")
@Component
public class RawrsController {

	@Autowired
	private TweetService ts;

	@Autowired
	private FavoriteService fs;
	
	@Autowired
	TweetDTOBuilder tweetDTOBuilder;
	
	@POST
	@Path("/")
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response postRawr(StatusDTO status){
		User loggedUser = SessionHandler.sessionUser();
		if(loggedUser == null)
			Response.status(Status.UNAUTHORIZED).build();
		Tweet t = ts.register(status.getStatus(), loggedUser);
		return t == null ? Response.status(Status.BAD_REQUEST).build() : Response.status(Status.CREATED).build();
	}
	
	@GET
	@Path("/{rawr_id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getRawr(@PathParam("rawr_id") final String id){
		Tweet t = ts.getTweet(id);
		User loggedUser = SessionHandler.sessionUser();
		return t == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(tweetDTOBuilder.build(t, loggedUser)).build();
	}
	
	@POST
	@Path("/{rawr_id}/like")
	public Response like(@PathParam("rawr_id") final String id){
		User loggedUser = SessionHandler.sessionUser();
		if(loggedUser == null)
			Response.status(Status.UNAUTHORIZED).build();
		
		Tweet t = ts.getTweet(id);
		if(t == null)
			Response.status(Status.NOT_FOUND).build();
		
		fs.favorite(t, loggedUser,loggedUser);
		return Response.ok().build();
	}
	
	
	@POST
	@Path("/{rawr_id}/unlike")
	public Response unlike(@PathParam("rawr_id") final String id){
		User loggedUser = SessionHandler.sessionUser();
		if(loggedUser == null)
			Response.status(Status.UNAUTHORIZED).build();
		
		Tweet t = ts.getTweet(id);
		if(t == null)
			Response.status(Status.NOT_FOUND).build();
		
		fs.unfavorite(t, loggedUser);
		return Response.ok().build();
	}
	
	@POST
	@Path("/{rawr_id}/rerawr")
	public Response rerawr(@PathParam("rawr_id") final String id){
		User loggedUser = SessionHandler.sessionUser();
		if(loggedUser == null)
			Response.status(Status.UNAUTHORIZED).build();
		
		Tweet t = ts.getTweet(id);
		if(t == null)
			Response.status(Status.NOT_FOUND).build();
		
		ts.retweet(t, loggedUser);
		return Response.ok().build();
	}

	@POST
	@Path("/{rawr_id}/unrerawr")
	public Response unrerawr(@PathParam("rawr_id") final String id){
		User loggedUser = SessionHandler.sessionUser();
		if(loggedUser == null)
			Response.status(Status.UNAUTHORIZED).build();
		
		Tweet t = ts.getTweet(id);
		if(t == null)
			Response.status(Status.NOT_FOUND).build();
		
		ts.unretweet(t, loggedUser);
		return Response.ok().build();
	}
	
	
}
