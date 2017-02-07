package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserDAO {

    /**
     * Create a new user.
     *
     * @param username  		The name of the user.
     * @param password  		The user's password.
     * @param firstName 		The user's first name.
     * @param lastName  		The user's last name.
     * @param email     		The user's email.
     * @return 					The created user.
     */
    User create(final String username, final String password, final String email, final String firstName, final String lastName);

    /**
     * Get a user with a given username.
     *
     * @param username 			The searched username.
     * @return 					The user
     */
    User getByUsername(final String username);

    /**
     * Search for users.
     *
     * @param text           	The searched text.
     * @param resultsPerPage 	Limit number of users per page.
     * @param page           	Number of page needed.
     * @return 				 	The list of users.
     */
    List<User> searchUsers(final String text, final int resultsPerPage, final int page);

    /**
     * Checks if a certain username is available.
     * 
     * @param username 		 	The tested username.
     * @return 					True if username can be used by new user, false if not.
     */
    Boolean isUsernameAvailable(final String username);
    
    /**
     * Checks if a certain email is available.
     * 
     * @param username 			The tested email.
     * @return 					True if email can be used by new user, false if not.
     */
    Boolean isEmailAvailable(final String email);

    /**
     * Check if user exists and the password is correct.
     *
     * @param username 			The user's username.
     * @param password 			The user's password.
     * @return 					The user in case the user can log in, if not null.
     */
    User authenticateUser(final String username, final String password);

    /**
     * Get a list of followers for a user.
     *
     * @param user         		The user.
     * @param resultsPerPage 	Limit number of users per page.
     * @param page           	Number of page needed.
     * @return 					The recovered list.
     */
    List<User> getFollowers(final User user, final int resultsPerPage, final int page);

    /**
     * Get a list of users being followed by a user.
     *
     * @param user         		The user.
     * @param resultsPerPage 	Limit number of users per page.
     * @param page           	Number of page needed.
     * @return The recovered 	list.
     */
    List<User> getFollowing(final User user, final int resultsPerPage, final int page);
    
    void hashEverything(); //TODO erase it
}
