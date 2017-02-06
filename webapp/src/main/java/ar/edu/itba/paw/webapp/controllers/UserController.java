package ar.edu.itba.paw.webapp.controllers;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.NotificationService;
import ar.edu.itba.paw.services.TweetService;
import ar.edu.itba.paw.webapp.dto.NotificationIDsDTO;

@Path("user")
@Component
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
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

        return Response.ok(userDTOBuilder.buildFullUser(loggedUser, null)).build();
    }

    @GET
    @Path("/feed")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getFeed(@QueryParam("limit") final String lim, @QueryParam("max_position") final String maxPosition, @QueryParam("min_position") final String minPosition, @QueryParam("page") final String p) {
        Date from = null, to = null;
        int limit, page = 1;
        try {
            limit = Integer.parseInt(lim);
            if (maxPosition != null)
                to = new Date(Long.parseLong(maxPosition));
            if (minPosition != null)
                from = new Date(Long.parseLong(minPosition));
            if (p != null)
                page = Integer.parseInt(p);
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
        Date from = null, to = null;
        int limit, page = 1;
        try {
            limit = Integer.parseInt(lim);
            if (maxPosition != null)
                to = new Date(Long.parseLong(maxPosition));
            if (minPosition != null)
                from = new Date(Long.parseLong(minPosition));
            if (p != null)
                page = Integer.parseInt(p);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User loggedUser = SessionHandler.sessionUser();
        LOGGER.info(loggedUser.getUsername());
        List<Notification> notifications = ns.getNotifications(loggedUser, limit, from, to, page);
        return Response.ok(notificationDTOBuilder.buildList(notifications)).build();
    }

    @POST
    @Path("/notifications/read")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response readNotifications(final NotificationIDsDTO toRead) {
        /*
        Nunca nos fijamos que las notificaciones correspondan al usuario correcto
         */

        User loggedUser = SessionHandler.sessionUser();
        Notification notification;
        for (Long id : toRead.getNotificationIDs()) {
            notification = ns.getNotificationByID(id);
            if (notification == null || !loggedUser.equals(notification.getTo()))
                return Response.status(Response.Status.NOT_FOUND).build();
        }
        toRead.getNotificationIDs().forEach(id -> ns.seen(ns.getNotificationByID(id)));

        /*
        //   CAMBIAR PARA RECIBIR UN NOTIFICATION ID NO UNA LISTA
           Notification notification notification = ns.getNotificationByID(id);;
           if (notification != null || loggedUser.equals(notification.gettTo())
                return Response.status(Response.Status.NOT_FOUND).build();
           ns.seen(notification);
         */

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
        return Response.ok("{\"count\":" + count + "}").build();
    }
}
