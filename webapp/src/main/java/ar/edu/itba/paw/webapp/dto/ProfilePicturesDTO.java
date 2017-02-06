package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProfilePicturesDTO {

    private String small;
    private String medium;
    private String large;

    /* default */ ProfilePicturesDTO() {}

    public ProfilePicturesDTO(final String small, final String medium, final String large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfilePicturesDTO)) return false;

        ProfilePicturesDTO that = (ProfilePicturesDTO) o;

        if (small != null ? !small.equals(that.small) : that.small != null) return false;
        return (medium != null ? medium.equals(that.medium) : that.medium == null) && (large != null ? large.equals(that.large) : that.large == null);
    }

    @Override
    public int hashCode() {
        int result = small != null ? small.hashCode() : 0;
        result = 31 * result + (medium != null ? medium.hashCode() : 0);
        result = 31 * result + (large != null ? large.hashCode() : 0);
        return result;
    }
}