package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotificationDTO {
    private long id;
    private String type;
    private boolean read;
    private long created;
    private UserDTO user;
    private String statusId;

    public NotificationDTO() {
    }

    public NotificationDTO(long id, String type, boolean read, long created, UserDTO user, String statusId) {
        this.id = id;
        this.type = type;
        this.read = read;
        this.created = created;
        this.user = user;
        this.statusId = statusId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}