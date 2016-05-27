package ar.edu.itba.paw.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {

	@Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweets_tweetid_seq") //TODO what to do with randomID 
//  @SequenceGenerator(sequenceName = "tweets_tweetid_seq", name = "tweets_tweetid_seq", allocationSize = 1) //idem 
	@GenericGenerator(name = "random_id", strategy = "idgenerators.RandomIdGenerator")
    @GeneratedValue(generator = "random_id")
    @Column(name = "userID", length = 12, nullable = false)
    private String id;
	
	@Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

	@Column(name = "email", length = 100, nullable = false)
    private String email;

	@Column(name = "firstName", length = 100, nullable = false)
    private String firstName;

	@Column(name = "lastName", length = 100, nullable = false)
    private String lastName;

	@Column(name = "verified", nullable = false)
    private Boolean verified;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> followers;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> followings;
	
    private String miniBio = null; //TODO use!
    
    /* package */ User() {
        // Just for Hibernate.
    }

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
    
	public Set<User> getFollowers() {
		return followers;
	}

	public Set<User> getFollowings() {
		return followings;
	}

	public String getMiniBio() {
		return miniBio;
	}    
    
    /*
     * 
     * Setters (just for hibernate)
     * 
     * */



	public void setId(String id) {
		this.id = id;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public void setFollowings(Set<User> followings) {
		this.followings = followings;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public void setMiniBio(String miniBio) {
		this.miniBio = miniBio;
	}
    
}
