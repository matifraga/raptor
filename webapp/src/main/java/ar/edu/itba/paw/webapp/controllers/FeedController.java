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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.TweetService;

@Path("feed")
@Component
public class FeedController {
	private final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	@Autowired
    private TweetService ts;
	
	@Autowired
    private TweetDTOBuilder tweetDTOBuilder;
	
	@Context
    private UriInfo uriInfo;
	
	@GET
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getGlobalFeed(@QueryParam("limit") final String limit, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition) {

		Date from = null, to =   null;
		Integer lim =  null;
		try {
			lim = Integer.valueOf(limit);
			to = new Date(Long.valueOf(maxPosition));
			from = new Date(Long.valueOf(minPosition));
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		List<Tweet> tweets = ts.globalFeed(lim, from, to);
		User user = SessionHandler.sessionUser();
		return Response.ok(tweetDTOBuilder.buildList(tweets, user)).build();
	}
}
