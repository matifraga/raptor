package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by luis on 2/2/17.
 */
@XmlRootElement
public class RerawrDTO {
    private String id;
    @XmlElement(name="created_time")
    private long createdTime;
    private UserDTO owner;

    /*default*/ RerawrDTO() {
    }

    public RerawrDTO(String id, long createdTime, UserDTO owner) {
        this.id = id;
        this.createdTime = createdTime;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RerawrDTO)) return false;

        RerawrDTO rerawrDTO = (RerawrDTO) o;

        if (createdTime != rerawrDTO.createdTime) return false;
        if (id != null ? !id.equals(rerawrDTO.id) : rerawrDTO.id != null) return false;
        return owner != null ? owner.equals(rerawrDTO.owner) : rerawrDTO.owner == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (createdTime ^ (createdTime >>> 32));
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
