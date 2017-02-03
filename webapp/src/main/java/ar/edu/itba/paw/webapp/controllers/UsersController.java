package ar.edu.itba.paw.webapp.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;

import java.util.Date;

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
    public Response getTimeline(@PathParam("username") final String username, @QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition) {
        Date from = null, to = null;
        Integer limit =  null;
        try {
            limit = Integer.valueOf(lim);
            to = new Date(Long.valueOf(maxPosition));
            from = new Date(Long.valueOf(minPosition));
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        User viewer = SessionHandler.sessionUser();
        return Response.ok(tweetDTOBuilder.buildList(ts.getTimeline(user,limit,from,to), viewer)).build();
    }

    @GET
    @Path("/{username}/mentions")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getMentions(@PathParam("username") final String username, @QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition) {
        Date from = null, to = null;
        Integer limit =  null;
        try {
            limit = Integer.valueOf(lim);
            to = new Date(Long.valueOf(maxPosition));
            from = new Date(Long.valueOf(minPosition));
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    	User loggedUser = SessionHandler.sessionUser();
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(tweetDTOBuilder.buildList(ts.getMentions(user, limit, from, to),loggedUser)).build();
    }

    @GET
    @Path("/{username}/likes")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getLikes(@PathParam("username") final String username, @QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition) {
        Date from = null, to = null;
        Integer limit =  null;
        try {
            limit = Integer.valueOf(lim);
            to = new Date(Long.valueOf(maxPosition));
            from = new Date(Long.valueOf(minPosition));
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    	User loggedUser = SessionHandler.sessionUser();
        User user = us.getUserWithUsername(username);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(tweetDTOBuilder.buildList(ts.getFavorites(user, limit, from, to),loggedUser)).build();
    }


    @POST
    @Path("/{username}/follow")
    public Response followUser(@PathParam("username") final String username) {
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null) {
            //nunca deberia entrar por spring security
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        User user = us.getUserWithUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        fs.follow(loggedUser,user);
        return Response.ok().build();

    }

    @POST
    @Path("/{username}/unfollow")
    public Response unfollowUser(@PathParam("username") final String username) {
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null) {
            //nunca deberia entrar por spring security
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        User user = us.getUserWithUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        fs.unfollow(loggedUser,user);
        return Response.ok().build();

    }

}
