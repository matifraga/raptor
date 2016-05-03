package ar.edu.itba.paw.models;

public class User {

	private final String id;
	private final String username;
	private final String email;
	private final String firstName;
	private final String lastName;
	private final Boolean verified;
	
	@SuppressWarnings("unused")	private final String miniBio = null; //TODO use!

	public User(String username, String email,
			String firstName, String lastName, String id, Boolean verified) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.verified = verified;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * 
	 * Getters
	 * 
	 */

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public Boolean getVerified() {
		return verified;
	}
}
