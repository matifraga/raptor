package ar.edu.itba.paw.webapp.dto;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by luis on 1/31/17.
 */
@XmlRootElement
public class SignupDTO {
    @XmlElement(name = "first_name")
    private String firstName;
    @XmlElement(name = "last_name")
    private String lastName;
    private String username;
    private String email;
    private String password;

    /*default*/ SignupDTO() {
    }

    public SignupDTO(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignupDTO)) return false;

        SignupDTO signupDTO = (SignupDTO) o;

        if (firstName != null ? !firstName.equals(signupDTO.firstName) : signupDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(signupDTO.lastName) : signupDTO.lastName != null) return false;
        if (username != null ? !username.equals(signupDTO.username) : signupDTO.username != null) return false;
        if (email != null ? !email.equals(signupDTO.email) : signupDTO.email != null) return false;
        return password != null ? password.equals(signupDTO.password) : signupDTO.password == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
