package ar.edu.itba.paw.webapp.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsersDTO{
    private List<UserDTO> users;

    /* default */ UsersDTO() {}

    public UsersDTO(List<UserDTO> users) {
        this.users = users;
    }
   
    /*
     * 
     * Getters & Setters.
     * 
     * */

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UsersDTO))
			return false;
		UsersDTO other = (UsersDTO) obj;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
}