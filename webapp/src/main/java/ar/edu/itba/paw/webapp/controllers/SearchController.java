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

import java.util.Date;

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
	public Response searchUsers(@QueryParam("page") final String p, @QueryParam("limit") final String lim, @QueryParam("term") final String term) {
		Integer limit = null;
		int page = 1;
		try {
			if (lim != null) {
				page = Integer.valueOf(p);
				limit = Integer.valueOf(lim);
			}
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (limit == null || term == null || term.length() == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.info("searching users with term: " + term);
		User loggedUser = SessionHandler.sessionUser();
		return Response.ok(userDTOBuilder.buildList(us.searchUsers(term, limit, page),loggedUser)).build();
	}
	
	@GET
	@Path("/rawrs")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchRawrs(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("term") final String term, @QueryParam("page") final String p) {
		Date from = null, to =   null;
		int limit, page = 1;
		try {
			limit = Integer.valueOf(lim);
			if (maxPosition != null)
				to = new Date(Long.valueOf(maxPosition));
			if (minPosition != null)
				from = new Date(Long.valueOf(minPosition));
			if (p != null)
				page = Integer.valueOf(p);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (term == null || term.length() == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.debug("searching rawrs with term: " + term);
		User loggedUser = SessionHandler.sessionUser();
		return Response.ok(tweetDTOBuilder.buildList(ts.searchTweets(term, limit, from, to,  page), loggedUser)).build();
	}
	
	@GET
	@Path("/hashtags")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchHashtags(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("term") final String term, @QueryParam("page") final String p) {
		Date from = null, to =   null;
		int limit, page = 1;
		try {
			limit = Integer.valueOf(lim);
			if (maxPosition != null)
				to = new Date(Long.valueOf(maxPosition));
			if (minPosition != null)
				from = new Date(Long.valueOf(minPosition));
			if (p != null)
				page = Integer.valueOf(p);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (term == null || term.length() == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.info("searching hashtags with term: " + term);
		User loggedUser = SessionHandler.sessionUser();
		return Response.ok(tweetDTOBuilder.buildList(ts.getHashtag(term, limit, from, to, page), loggedUser)).build();
	}
}
