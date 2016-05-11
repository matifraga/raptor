package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserService {

	/**
	 * Registers a new user.
	 *
	 * @param username  The new user's username.
	 * @param password  The new user's password.
	 * @param email     The new user's email.
	 * @param firstName The new user's first name.
	 * @param lastName  The new user's last name.
	 * @return The newly created user.
	 */
	User register(final String username, final String password, final String email, final String firstName, final String lastName);

	/**
	 * Checks if user exists and password is correct.
	 *
	 * @param username The user's username.
	 * @param password The user's password.
	 * @return The user.
	 */
	User authenticateUser(final String username, final String password);

	/**
	 * Get a user with a given ID.
	 *
	 * @param userId The user's ID.
	 * @return The user with the given identifier.
	 */
	User getUserWithId(final String userId);

	/**
	 * Get a user with a given username.
	 *
	 * @param username The user's username.
	 * @return The user with the given username.
	 */
	User getUserWithUsername(final String username);

	/**
	 * Get a list of users with usernames containing the search.
	 *
	 * @param text           The text searched.
	 * @param resultsPerPage Limit number of users per page.
	 * @param page           Number of page needed.
	 * @return The recovered list.
	 */
	List<User> searchUsers(final String text, final int resultsPerPage, final int page);

	/**
	 * Get a list of followers for a user.
	 *
	 * @param userId         The user's id.
	 * @param resultsPerPage Limit number of users per page.
	 * @param page           Number of page needed.
	 * @return The recovered list.
	 */
	List<User> getFollowers(final String userId, final int resultsPerPage, final int page);

	/**
	 * Get a list of users being followed by a user.
	 *
	 * @param userId         The user's id.
	 * @param resultsPerPage Limit number of users per page.
	 * @param page           Number of page needed.
	 * @return The recovered list.
	 */
	List<User> getFollowing(final String userId, final int resultsPerPage, final int page);
}
