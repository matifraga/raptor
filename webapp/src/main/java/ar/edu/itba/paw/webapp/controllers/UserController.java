package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FollowerService;
import ar.edu.itba.paw.services.NotificationService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.NotificationsDTO;
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
    NotificationService ns;

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    @Autowired
    private TweetDTOBuilder tweetDTOBuilder;

    @Autowired
    private NotificationDTOBuilder notificationDTOBuilder;

    @Context
    private UriInfo uriInfo;




    @GET
    @Path("/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUser() {
        User loggedUser = SessionHandler.sessionUser();
        System.out.println(loggedUser);
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok(userDTOBuilder.build(loggedUser)).build();
    }

    @GET
    @Path("/feed")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getFeed(@QueryParam("page") final int page, @QueryParam("limit") final int limit) {
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser != null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Tweet> feed = ts.globalFeed(limit,page);
        return Response.ok(tweetDTOBuilder.buildList(feed,loggedUser)).build();
    }

    @GET
    @Path("/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotifications(@QueryParam("page") final int page, @QueryParam("limit") final int limit) {
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Notification> notifications = ns.getNotifications(loggedUser, limit, page);
        return Response.ok(notificationDTOBuilder.buildList(notifications)).build();
    }


    //hay que poder buscar una notificacion por id
    /*@POST
    @Path("/notifications/read")
    public Response readNotifications(NotificationsDTO toRead){
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

    }*/


  /*  @GET
    @Path("/nottifications/count")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotificationCount() {
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        //int count = ns.getUnreadNotifications(user);
        return Response.ok(count).build()
    }*/



}
