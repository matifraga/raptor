package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by luis on 1/26/17.
 */
@XmlRootElement
public class NotificationsDTO {
    private List<NotificationDTO> notifications;

    public NotificationsDTO() {
    }

    public NotificationsDTO(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }
}
