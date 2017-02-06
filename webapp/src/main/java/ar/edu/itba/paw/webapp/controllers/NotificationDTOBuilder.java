package ar.edu.itba.paw.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.models.Notification;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.NotificationDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;

import javax.ws.rs.core.GenericEntity;

public class NotificationDTOBuilder {
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationDTOBuilder.class);

    @Autowired
    private UserDTOBuilder userDTOBuilder;

    public NotificationDTO build(Notification n) {
        LOGGER.debug("building notification");
        String type = n.getType().toString();
        LOGGER.debug("user " + n.getFrom().getUsername());
        UserDTO user = userDTOBuilder.buildSimpleUser(n.getFrom());
        Tweet t = n.getTweet();
        String id = null;
        if (t != null)
            id = t.getId();

        return new NotificationDTO(n.getId(), type.toLowerCase(), !n.getSeen(), n.getTimestamp().getTime(), user, id);
    }

    public GenericEntity<List<NotificationDTO>> buildList(List<Notification> notifications) {
        return new GenericEntity<List<NotificationDTO>>(
        		notifications.stream()
        		.map(this::build).collect(Collectors.toList())
        ) {};
    }
}
