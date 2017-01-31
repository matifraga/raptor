package ar.edu.itba.paw.webapp.dto;

/**
 * Created by luis on 1/31/17.
 */
public class SignupDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    /* default */ SignupDTO() {
    }

    public SignupDTO(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
