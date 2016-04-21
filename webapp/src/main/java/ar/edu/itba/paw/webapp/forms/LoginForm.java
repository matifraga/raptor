package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class LoginForm {

    @Length(min=6,max=12)
    private String password;
    
	@NotBlank @Length(max=100)
    private String username;
	
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
