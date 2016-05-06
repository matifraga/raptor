package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface FollowerService {

	/**
	 * Register a following action.
	 *
	 * @param follower
	 * @param following
	 */
	void follow(final User follower, final User following);

	/**
	 * Returns whether a user follows another or not.
	 *
	 * @param follower
	 * @param following
	 * @return true if the user follows the other, false if not.
	 */

	Boolean isFollower(final User follower, final User following); //no se si va a hacer falta despues

	/**
	 * Stop a following action.
	 *
	 * @param follower  the userID from the follower.
	 * @param following the userID from the user not longer being followed.
	 */
	void unfollow(final User follower, final User following);

	/**
	 * Get the amount of followers for a user.
	 *
	 * @param user The user.
	 * @return how many followers does the user have.
	 */
	Integer countFollowers(final User user);

	/**
	 * Get the amount of following for a user.
	 *
	 * @param user The user.
	 * @return The amount of following the user has.
	 */
	Integer countFollowing(final User user);
}
