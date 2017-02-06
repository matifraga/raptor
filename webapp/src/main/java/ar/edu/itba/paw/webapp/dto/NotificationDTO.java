package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotificationDTO {
    private long id;
    private String type;
    @XmlElement(name = "new")
    private boolean read;
    @XmlElement(name = "created_time")
    private long created;
    private UserDTO user;
    @XmlElement(name = "status_id")
    private String statusId;

    /* default */ NotificationDTO() {}

    public NotificationDTO(final long id, final String type, final boolean read, final long created, final UserDTO user, final String statusId) {
        this.id = id;
        this.type = type;
        this.read = read;
        this.created = created;
        this.user = user;
        this.statusId = statusId;
    }

    /*
     * 
     * Getters & Setters.
     * 
     * */
    
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
