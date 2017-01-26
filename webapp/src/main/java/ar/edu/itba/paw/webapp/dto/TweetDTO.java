package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by luis on 1/25/17.
 */
@XmlRootElement
public class TweetDTO {
    private String id;
    private String createdTime;
    private String status;
    private boolean userHasLiked;
    private boolean userHasRerawred;
    private TweetCountsDTO counts;
    private UserDTO owner;

    public TweetDTO() {
    }

    public TweetDTO(String id, String createdTime, String status, boolean userHasLiked, boolean userHasRerawred, TweetCountsDTO counts, UserDTO owner) {
        this.id = id;
        this.createdTime = createdTime;
        this.status = status;
        this.userHasLiked = userHasLiked;
        this.userHasRerawred = userHasRerawred;
        this.counts = counts;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isUserHasLiked() {
        return userHasLiked;
    }

    public void setUserHasLiked(boolean userHasLiked) {
        this.userHasLiked = userHasLiked;
    }

    public boolean isUserHasRerawred() {
        return userHasRerawred;
    }

    public void setUserHasRerawred(boolean userHasRerawred) {
        this.userHasRerawred = userHasRerawred;
    }

    public TweetCountsDTO getCounts() {
        return counts;
    }

    public void setCounts(TweetCountsDTO counts) {
        this.counts = counts;
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
        if (!(o instanceof TweetDTO)) return false;

        TweetDTO tweetDTO = (TweetDTO) o;

        if (userHasLiked != tweetDTO.userHasLiked) return false;
        if (userHasRerawred != tweetDTO.userHasRerawred) return false;
        if (id != null ? !id.equals(tweetDTO.id) : tweetDTO.id != null) return false;
        if (createdTime != null ? !createdTime.equals(tweetDTO.createdTime) : tweetDTO.createdTime != null)
            return false;
        if (status != null ? !status.equals(tweetDTO.status) : tweetDTO.status != null) return false;
        if (counts != null ? !counts.equals(tweetDTO.counts) : tweetDTO.counts != null) return false;
        return owner != null ? owner.equals(tweetDTO.owner) : tweetDTO.owner == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (userHasLiked ? 1 : 0);
        result = 31 * result + (userHasRerawred ? 1 : 0);
        result = 31 * result + (counts != null ? counts.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
