package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.NotificationDTO;
import ar.edu.itba.paw.webapp.dto.NotificationsDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luis on 1/26/17.
 */
public class NotificationDTOBuilder {


    @Autowired
    private UserDTOBuilder userDTOBuilder;

    public NotificationDTO build(Notification n) {

        String type = n.getType().toString();
        UserDTO user = userDTOBuilder.build(n.getFrom());
        return new NotificationDTO(n.getId(), type, n.getSeen(), n.getTimestamp().getTime(), user, n.getTweet().getId() );
    }

    public NotificationsDTO buildList(List<Notification> notifications) {
        return new NotificationsDTO(notifications.stream().map(this::build).collect(Collectors.toList()));
    }

}
