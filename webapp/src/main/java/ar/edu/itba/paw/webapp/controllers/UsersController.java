package ar.edu.itba.paw.webapp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;

@Path("users")
@Component
public class UsersController {

    @Autowired
    private UserService us;

    @Autowired
    private TweetService ts;

    @Autowired
    private FollowerService fs;

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    @Autowired TweetDTOBuilder tweetDTOBuilder;

    @GET
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") final String username) {
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        User loggedUser = SessionHandler.sessionUser();
        return Response.ok(userDTOBuilder.build(user,loggedUser)).build();
    }

    @GET
    @Path("/{username}/timeline")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getTimeline(@PathParam("username") final String username, @QueryParam("page") final int page,
    @QueryParam("limit") final int limit) {
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        User viewer = SessionHandler.sessionUser();
        return Response.ok(tweetDTOBuilder.buildList(ts.getTimeline(user,limit,page), viewer)).build();
    }

    @GET
    @Path("/{username}/mentions")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getMentions(@PathParam("username") final String username, @QueryParam("page") final int page,  @QueryParam("limit") final int limit) {
        User loggedUser = SessionHandler.sessionUser();
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(tweetDTOBuilder.buildList(ts.getMentions(user, limit, page),loggedUser)).build();
    }

    @GET
    @Path("/{username}/likes")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getLikes(@PathParam("username") final String username, @QueryParam("page") final int page, @QueryParam("limit") final int limit) {
        User loggedUser = SessionHandler.sessionUser();
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(tweetDTOBuilder.buildList(ts.getFavorites(user, limit, page),loggedUser)).build();
    }
}
