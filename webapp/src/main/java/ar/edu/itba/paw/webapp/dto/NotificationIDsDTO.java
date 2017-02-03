package ar.edu.itba.paw.webapp.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotificationIDsDTO {

    @XmlElement(name = "notification_ids")
    private List<Long> notificationIDs;

    /* default */ NotificationIDsDTO() {}

    public NotificationIDsDTO(List<Long> notificationIDs) {
        this.notificationIDs = notificationIDs;
    }

    public List<Long> getNotificationIDs() {
        return notificationIDs;
    }

    public void setNotificationIDs(List<Long> notificationIDs) {
        this.notificationIDs = notificationIDs;
    }

}
