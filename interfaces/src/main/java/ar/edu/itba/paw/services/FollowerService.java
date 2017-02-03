package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface FollowerService {

	/**
	 * Register a following action.
	 *
	 * @param follower  	The user who is being followed.
	 * @param following 	The user who follows.
	 */
	void follow(final User follower, final User following);

	/**
	 * Returns whether a user follows another or not.
	 *
	 * @param follower  	The user who is being followed.
	 * @param following 	The user who follows.
	 * @return 				True if the user follows the other, false if not.
	 */
	Boolean isFollower(final User follower, final User following); 

	/**
	 * Stop a following action.
	 *
	 * @param follower  	The user who is being followed.
	 * @param following 	The user who follows.
	 */
	void unfollow(final User follower, final User following);

	/**
	 * Get the amount of followers for a user.
	 *
	 * @param user 			The user.
	 * @return 				How many followers does the user have.
	 */
	Integer countFollowers(final User user);

	/**
	 * Get the amount of following for a user.
	 *
	 * @param user 			The user.
	 * @return 				The amount of following the user has.
	 */
	Integer countFollowing(final User user);
}
