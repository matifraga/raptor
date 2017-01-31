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
    private boolean userFollows; 

    /* default */ UserDTO() {}

	public UserDTO(final String id, final String username, final String firstName, final String lastName, final ProfilePicturesDTO pics,
			final Boolean verified, final UserCountsDTO userCountsDTO, final boolean userFollows) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pics = pics;
		this.verified = verified;
		this.userCountsDTO = userCountsDTO;
		this.userFollows = userFollows;
	}
	
	/*
	 * 
	 * Getters & Setters.
	 * 
	 * */

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (pics == null) {
			if (other.pics != null)
				return false;
		} else if (!pics.equals(other.pics))
			return false;
		if (userCountsDTO == null) {
			if (other.userCountsDTO != null)
				return false;
		} else if (!userCountsDTO.equals(other.userCountsDTO))
			return false;
		if (userFollows != other.userFollows)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (verified == null) {
			if (other.verified != null)
				return false;
		} else if (!verified.equals(other.verified))
			return false;
		return true;
	}

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfilePicturesDTO getPics() {
        return pics;
    }

    public UserCountsDTO getUserCountsDTO() {
        return userCountsDTO;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getVerified() {
        return verified;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((pics == null) ? 0 : pics.hashCode());
		result = prime * result + ((userCountsDTO == null) ? 0 : userCountsDTO.hashCode());
		result = prime * result + (userFollows ? 1231 : 1237);
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((verified == null) ? 0 : verified.hashCode());
		return result;
	}

    public boolean isUserFollows() {
		return userFollows;
	}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPics(ProfilePicturesDTO pics) {
        this.pics = pics;
    }

	public void setUserCountsDTO(UserCountsDTO userCountsDTO) {
        this.userCountsDTO = userCountsDTO;
    }

	public void setUserFollows(boolean userFollows) {
		this.userFollows = userFollows;
	}

	public void setUsername(String username) {
        this.username = username;
    }

	public void setVerified(Boolean verified) {
        this.verified = verified;
    }	
}
