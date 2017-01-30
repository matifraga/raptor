package ar.edu.itba.paw.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.NotificationDTO;
import ar.edu.itba.paw.webapp.dto.NotificationsDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;

public class NotificationDTOBuilder {

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    public NotificationDTO build(Notification n, User viewer) {
        String type = n.getType().toString();
        UserDTO user = userDTOBuilder.build(n.getFrom(), viewer);
        return new NotificationDTO(n.getId(), type, n.getSeen(), n.getTimestamp().getTime(), user, n.getTweet().getId() );
    }

    public NotificationsDTO buildList(List<Notification> notifications, User viewer) {
        return new NotificationsDTO(
        		notifications.stream()
        		.map(n -> this.build(n,viewer))
        				.collect(Collectors.toList()));
    }
}
