package ar.edu.itba.paw.webapp.controllers;


import java.util.Date;
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
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getGlobalFeed(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("page") final String p) {

		Date from = null, to =   null;
		int limit, page = 1;
		try {
			limit = Integer.parseInt(lim);
			if (maxPosition != null)
				to = new Date(Long.parseLong(maxPosition));
			if (minPosition != null)
				from = new Date(Long.parseLong(minPosition));
			if (p != null)
				page = Integer.parseInt(p);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		List<Tweet> tweets = ts.globalFeed(limit, from, to, page);
		User user = SessionHandler.sessionUser();
		return Response.ok(tweetDTOBuilder.buildList(tweets, user)).build();
	}
}
