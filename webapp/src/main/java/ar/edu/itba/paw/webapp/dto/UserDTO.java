package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {
    private String id;
    private String username;
    @XmlElement(name = "first_name")
    private String firstName;
    @XmlElement(name = "last_name")
    private String lastName;
    @XmlElement(name = "profile_pictures")
    private ProfilePicturesDTO pics;
    private Boolean verified;
    private UserCountsDTO counts;
    @XmlElement(name = "user_follows")
    private Boolean userFollows;

    /* default */ UserDTO() {}

	public UserDTO(String id, String username, String firstName, String lastName, ProfilePicturesDTO pics, Boolean verified, UserCountsDTO userCountsDTO, Boolean userFollows) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pics = pics;
		this.verified = verified;
		this.counts = userCountsDTO;
		this.userFollows = userFollows;
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

	public UserCountsDTO getCounts() {
		return counts;
	}

	public void setCounts(UserCountsDTO userCountsDTO) {
		this.counts = userCountsDTO;
	}

	public Boolean getUserFollows() {
		return userFollows;
	}

	public void setUserFollows(Boolean userFollows) {
		this.userFollows = userFollows;
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
		if (counts != null ? !counts.equals(userDTO.counts) : userDTO.counts != null)
			return false;
		return userFollows != null ? userFollows.equals(userDTO.userFollows) : userDTO.userFollows == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (pics != null ? pics.hashCode() : 0);
		result = 31 * result + (verified != null ? verified.hashCode() : 0);
		result = 31 * result + (counts != null ? counts.hashCode() : 0);
		result = 31 * result + (userFollows != null ? userFollows.hashCode() : 0);
		return result;
	}
}