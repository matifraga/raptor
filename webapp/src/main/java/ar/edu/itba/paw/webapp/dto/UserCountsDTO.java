package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by luis on 1/25/17.
 */

@XmlRootElement
public class UserCountsDTO {
    private Integer rawrs;
    private Integer followers;
    private Integer following;

    public UserCountsDTO(Integer rawrs, Integer followers, Integer following) {
        this.rawrs = rawrs;
        this.followers = followers;
        this.following = following;
    }

    public UserCountsDTO() {
    }

    public Integer getRawrs() {
        return rawrs;
    }

    public void setRawrs(Integer rawrs) {
        this.rawrs = rawrs;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCountsDTO)) return false;

        UserCountsDTO that = (UserCountsDTO) o;

        if (rawrs != null ? !rawrs.equals(that.rawrs) : that.rawrs != null) return false;
        if (followers != null ? !followers.equals(that.followers) : that.followers != null) return false;
        return following != null ? following.equals(that.following) : that.following == null;
    }

    @Override
    public int hashCode() {
        int result = rawrs != null ? rawrs.hashCode() : 0;
        result = 31 * result + (followers != null ? followers.hashCode() : 0);
        result = 31 * result + (following != null ? following.hashCode() : 0);
        return result;
    }
}