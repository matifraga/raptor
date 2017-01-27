package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private ProfilePicturesDTO pics;
    private Boolean verified;
    private UserCountsDTO userCountsDTO;

    public UserDTO(String id, String username, String firstName, String lastName, ProfilePicturesDTO pics, Boolean verified, UserCountsDTO userCountsDTO) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pics = pics;
        this.verified = verified;
        this.userCountsDTO = userCountsDTO;
    }

    public UserDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProfilePicturesDTO getPics() {
        return pics;
    }

    public void setPics(ProfilePicturesDTO pics) {
        this.pics = pics;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public UserCountsDTO getUserCountsDTO() {
        return userCountsDTO;
    }

    public void setUserCountsDTO(UserCountsDTO userCountsDTO) {
        this.userCountsDTO = userCountsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != null ? !id.equals(userDTO.id) : userDTO.id != null) return false;
        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;
        if (firstName != null ? !firstName.equals(userDTO.firstName) : userDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDTO.lastName) : userDTO.lastName != null) return false;
        if (pics != null ? !pics.equals(userDTO.pics) : userDTO.pics != null) return false;
        if (verified != null ? !verified.equals(userDTO.verified) : userDTO.verified != null) return false;
        return userCountsDTO != null ? userCountsDTO.equals(userDTO.userCountsDTO) : userDTO.userCountsDTO == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (pics != null ? pics.hashCode() : 0);
        result = 31 * result + (verified != null ? verified.hashCode() : 0);
        result = 31 * result + (userCountsDTO != null ? userCountsDTO.hashCode() : 0);
        return result;
    }
}
