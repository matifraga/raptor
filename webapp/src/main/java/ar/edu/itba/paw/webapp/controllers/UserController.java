package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by luis on 1/25/17.
 */

@Path("user")
@Component
public class UserController {


    @Autowired
    private UserService us;

    @Autowired
    private FollowerService fs;

    @Autowired
    private TweetService ts;

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    @Autowired
    private TweetDTOBuilder tweetDTOBuilder;

    @Context
    private UriInfo uriInfo;




    @GET
    @Path("/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUser() {
        User loggedUser = SessionHandler.sessionUser();
        System.out.println(loggedUser);
        if (loggedUser == null)
            return Response.ok().build();

        return Response.ok(userDTOBuilder.build(loggedUser)).build();
    }

    @GET
    @Path("/feed")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getFeed(@QueryParam("page") final int page, @QueryParam("limit") final int limit) {
        User loggedUser = SessionHandler.sessionUser();
        List<Tweet> feed;
        if (loggedUser != null)
            feed = ts.currentSessionFeed(loggedUser,limit,page);
        else
            feed = ts.globalFeed(limit,page);

        return Response.ok(tweetDTOBuilder.buildList(feed,loggedUser)).build();
    }

}
