package ar.edu.itba.paw.webapp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.FeedDTO;
import ar.edu.itba.paw.webapp.dto.UsersDTO;

@Path("search")
@Component
public class SearchController {
	private final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private TweetService ts;

	@Autowired
	private UserService us;
	
	@Autowired
	private TweetDTOBuilder tweetDTOBuilder;

	@Autowired
	private UserDTOBuilder userDTOBuilder;
	
	@GET
	@Path("/users")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchUsers(@QueryParam("page") final int page, @QueryParam("limit") final int limit, @QueryParam("term") final String term) {
		if(page < 1 || limit < 1 || term.length() == 0)
        	return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.info("searching users with term: " + term.substring(1));
		User loggedUser = SessionHandler.sessionUser();
		UsersDTO users = userDTOBuilder.buildList(us.searchUsers(term.substring(1), limit, page),loggedUser);
		return Response.ok(users).build();
	}
	
	@GET
	@Path("/rawrs")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchRawrs(@QueryParam("page") final int page, @QueryParam("limit") final int limit, @QueryParam("term") final String term) {
		if(page < 1 || limit < 1 || term.length() == 0)
        	return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.info("searching rawrs with term: " + term);
		User loggedUser = SessionHandler.sessionUser();
		FeedDTO rawrs = tweetDTOBuilder.buildList(ts.searchTweets(term, limit, page), loggedUser);
		return Response.ok(rawrs).build();
	}
	
	@GET
	@Path("/hashtags")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchHashtags(@QueryParam("page") final int page, @QueryParam("limit") final int limit, @QueryParam("term") final String term) {
		if(page < 1 || limit < 1 || term.length() == 0)
        	return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.info("searching hashtags with term: " + term.substring(1	));
		User loggedUser = SessionHandler.sessionUser();
		FeedDTO hashtags = tweetDTOBuilder.buildList(ts.getHashtag(term.substring(1), limit, page), loggedUser);
		return Response.ok(hashtags).build();
	}
}
