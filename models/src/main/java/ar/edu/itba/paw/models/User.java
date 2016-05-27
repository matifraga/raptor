package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @SuppressWarnings("unused")
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
}