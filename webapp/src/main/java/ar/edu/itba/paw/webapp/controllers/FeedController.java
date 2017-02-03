package ar.edu.itba.paw.webapp.controllers;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.TweetService;

@Path("feed")
@Component
public class FeedController {

	@Autowired
    private TweetService ts;
	
	@Autowired
    private TweetDTOBuilder tweetDTOBuilder;
	
	@Context
    private UriInfo uriInfo;
	
	@GET
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getGlobalFeed(@QueryParam("limit") final int limit, @QueryParam("max_position") final long maxPosition, @QueryParam("min_position") final long minPosition) {
		//El checkeo de parametros se hace en service?
		//if(page < 1 || limit < 1)
		//return Response.status(Response.Status.BAD_REQUEST).build();
		List<Tweet> tweets = ts.globalFeed(limit, page);
		User user = SessionHandler.sessionUser();
		return Response.ok(tweetDTOBuilder.buildList(tweets, user)).build();
	}
}
