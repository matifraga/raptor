package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;


public class SignupForm {
	
	@NotBlank @Email @Length(max=100)
    private String email;

    @NotBlank @Length(max=100) @Pattern(regexp ="[a-zA-Z ]*$")
    private String firstName;

    @NotBlank @Length(max=100) @Pattern(regexp ="[a-zA-Z ]*$")
    private String lastName;

    @Length(min=6,max=12) @Pattern(regexp = "[a-zA-Z0-9]*")
    private String password;
    
	@AssertTrue()
	private Boolean terms;
	
	@NotBlank @Length(max=100) @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
		this.password = password;
	}

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
		this.terms = terms;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
		this.username = username;
	}

    @Override
    public String toString() {
        return new StringBuilder("SignupForm{").append("email='").append(email).append('\'').append(", firstName='").append(firstName)
                .append('\'').append(", lastName='").append(lastName).append('\'')
        		.append(", username='").append(username).append('\'').append('}').toString();
    }
}
