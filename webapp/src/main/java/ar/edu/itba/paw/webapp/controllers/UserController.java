package ar.edu.itba.paw.webapp.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.NotificationService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.NotificationIDsDTO;

@Path("user")
@Component
public class UserController {

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

        return Response.ok(userDTOBuilder.build(loggedUser, null)).build();
    }

    @GET
    @Path("/feed")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getFeed(@QueryParam("limit") final int limit, @QueryParam("max_position") final long maxPosition, @QueryParam("min_position") final long minPosition) {
        if(page < 1 || limit < 1)
        	return Response.status(Response.Status.BAD_REQUEST).build();
    	User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Tweet> feed = ts.currentSessionFeed(loggedUser, limit, page);
        return Response.ok(tweetDTOBuilder.buildList(feed, loggedUser)).build();
    }

    @GET
    @Path("/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotifications(@QueryParam("limit") final int limit, @QueryParam("max_position") final long maxPosition, @QueryParam("min_position") final long minPosition) {
    	if(page < 1 || limit < 1)
        	return Response.status(Response.Status.BAD_REQUEST).build();
    	User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Notification> notifications = ns.getNotifications(loggedUser, limit, page);
        return Response.ok(notificationDTOBuilder.buildList(notifications, loggedUser)).build();
    }

    //hay que poder buscar una notificacion por id
    @POST
    @Path("/notifications/read")
    public Response readNotifications(NotificationIDsDTO toRead){
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        for(Long id : toRead.getNotificationIDs()){
        	ns.seen(ns.getNotificationByID(id));
        }
        return Response.ok().build();
    }

    @GET
    @Path("/notifications/count")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotificationCount() {
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        int count = ns.getUnreadNotificationsCount(loggedUser);
        return Response.ok("{\"count\":" + count +"}" ).build();
    }
}
