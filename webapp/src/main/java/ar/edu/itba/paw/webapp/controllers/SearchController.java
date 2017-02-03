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
import ar.edu.itba.paw.webapp.dto.RawrsDTO;
import ar.edu.itba.paw.webapp.dto.UsersDTO;

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
		Integer limit =  null;
		Integer page = null;
		try {
			limit = Integer.valueOf(lim);
			page = Integer.valueOf(p);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (term == null || term.length() == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.debug("searching users with term: " + term.substring(1));
		User loggedUser = SessionHandler.sessionUser();
		UsersDTO users = userDTOBuilder.buildList(us.searchUsers(term.substring(1), limit, page),loggedUser);
		return Response.ok(users).build();
	}
	
	@GET
	@Path("/rawrs")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchRawrs(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("term") final String term, @QueryParam("page") final String p) {
		Date from = null, to = null;
		Integer limit = null, page = null;
		try {
			limit = Integer.valueOf(lim);
			to = new Date(Long.valueOf(maxPosition));
			from = new Date(Long.valueOf(minPosition));
			page = Integer.valueOf(p);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (term == null || term.length() == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.debug("searching rawrs with term: " + term);
		User loggedUser = SessionHandler.sessionUser();
		RawrsDTO rawrs = tweetDTOBuilder.buildList(ts.searchTweets(term, limit, from, to,  page), loggedUser);
		return Response.ok(rawrs).build();
	}
	
	@GET
	@Path("/hashtags")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response searchHashtags(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("term") final String term, @QueryParam("page") final String p) {
		Date from = null, to = null;
		Integer limit = null, page = null;
		try {
			limit = Integer.valueOf(lim);
			to = new Date(Long.valueOf(maxPosition));
			from = new Date(Long.valueOf(minPosition));
			page = Integer.valueOf(p);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (term == null || term.length() == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		LOGGER.debug("searching hashtags with term: " + term.substring(1	));
		User loggedUser = SessionHandler.sessionUser();
		RawrsDTO hashtags = tweetDTOBuilder.buildList(ts.getHashtag(term.substring(1), limit, from, to, page), loggedUser);
		return Response.ok(hashtags).build();
	}
}
