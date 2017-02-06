package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TweetDTO {
    private String id;
    @XmlElement(name = "created_time")
    private long createdTime;
    private String status;
    @XmlElement(name = "user_has_liked")
    private Boolean userHasLiked;
    @XmlElement(name = "user_has_rerawred")
    private Boolean userHasRerawred;
    private TweetCountsDTO counts;
    private UserDTO owner;
    private RerawrDTO rerawr;

    /* default */ TweetDTO() {}

    public TweetDTO(String id, long createdTime, String status, Boolean userHasLiked, Boolean userHasRerawred, TweetCountsDTO counts, UserDTO owner, RerawrDTO rerawr) {
        this.id = id;
        this.createdTime = createdTime;
        this.status = status;
        this.userHasLiked = userHasLiked;
        this.userHasRerawred = userHasRerawred;
        this.counts = counts;
        this.owner = owner;
        this.rerawr = rerawr;
    }

    /*
     * 
     * Getters & Setters.
     * 
     * */

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getUserHasLiked() {
        return userHasLiked;
    }

    public void setUserHasLiked(Boolean userHasLiked) {
        this.userHasLiked = userHasLiked;
    }

    public Boolean getUserHasRerawred() {
        return userHasRerawred;
    }

    public void setUserHasRerawred(Boolean userHasRerawred) {
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

    public RerawrDTO getRerawr() {
        return rerawr;
    }

    public void setRerawr(RerawrDTO rerawr) {
        this.rerawr = rerawr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TweetDTO)) return false;

        TweetDTO tweetDTO = (TweetDTO) o;

        if (createdTime != tweetDTO.createdTime) return false;
        if (id != null ? !id.equals(tweetDTO.id) : tweetDTO.id != null) return false;
        if (status != null ? !status.equals(tweetDTO.status) : tweetDTO.status != null) return false;
        if (userHasLiked != null ? !userHasLiked.equals(tweetDTO.userHasLiked) : tweetDTO.userHasLiked != null)
            return false;
        if (userHasRerawred != null ? !userHasRerawred.equals(tweetDTO.userHasRerawred) : tweetDTO.userHasRerawred != null)
            return false;
        if (counts != null ? !counts.equals(tweetDTO.counts) : tweetDTO.counts != null) return false;
        return (owner != null ? owner.equals(tweetDTO.owner) : tweetDTO.owner == null) && (rerawr != null ? rerawr.equals(tweetDTO.rerawr) : tweetDTO.rerawr == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (createdTime ^ (createdTime >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (userHasLiked != null ? userHasLiked.hashCode() : 0);
        result = 31 * result + (userHasRerawred != null ? userHasRerawred.hashCode() : 0);
        result = 31 * result + (counts != null ? counts.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (rerawr != null ? rerawr.hashCode() : 0);
        return result;
    }
}
