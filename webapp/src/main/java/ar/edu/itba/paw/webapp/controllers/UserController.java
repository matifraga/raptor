package ar.edu.itba.paw.webapp.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
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
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok(userDTOBuilder.build(loggedUser, null)).build();
    }

    @GET
    @Path("/feed")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getFeed(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("page") final String p) {
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
    	User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Tweet> feed = ts.currentSessionFeed(loggedUser, limit, from, to, page);
        return Response.ok(tweetDTOBuilder.buildList(feed, loggedUser)).build();
    }

    @GET
    @Path("/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotifications(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("page") final String p) {
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
    	User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Notification> notifications = ns.getNotifications(loggedUser, limit, from, to, page);
        return Response.ok(notificationDTOBuilder.buildList(notifications, loggedUser)).build();
    }

    @POST
    @Path("/notifications/read")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response readNotifications(final NotificationIDsDTO toRead){
        User loggedUser = SessionHandler.sessionUser();
        if (loggedUser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
       /* List<Long> toRead;
        try {
            toRead = Arrays.stream(toReadS).map(Long::parseLong).collect(Collectors.toList());
        }catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }*/
       Notification notification;
        for(Long id : toRead.getNotificationIDs()){
            notification = ns.getNotificationByID(id);
            if (notification != null)
        	    ns.seen(notification);
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
