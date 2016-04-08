package ar.edu.itba.paw.persistence;

import java.util.List;

import ar.edu.itba.paw.models.User;

public interface UserDAO {
	
    /**
     * Create a new user.
     * 
     * @param username The name of the user.
     * @param password The user's password.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param email The user's email.
     * @param id The user's id.
     * @return The created user.
     */
    User create(final String username, final String password, final String email, final String firstName, final String lastName);
    
    /**
     * Get a user with a given username.
     * 
     * @param username the searched username
     * @return the user
     */
    User getByUsername(final String username);
    
    /**
     * Search for users.
     * 
     * @param text the searched text.
	 * @param resultsPerPage limit number of users per page.
	 * @param page number of page needed.
     * @return the list of users.
     */
    List<User> searchUsers(final String text, int resultsPerPage, int page);
}
