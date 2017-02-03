package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;

public interface FollowerDAO {

    /**
     * Register a following action.
     *
     * @param follower  	The follower.
     * @param following 	The user being followed.
     */
    void follow(final User follower, final User following);

    /**
     * Returns whether a user follows another or not.
     *
     * @param follower  	The follower.
     * @param following 	The user being followed.
     * @return 				True if the user follows the other, false if not.
     */
    Boolean isFollower(final User follower, final User following); 

    /**
     * Stop a following action.
     *
     * @param follower  	The follower.
     * @param following 	The user not longer being followed.
     */
    void unfollow(final User follower, final User following);

    /**
     * Get the amount of followers for a user.
     *
     * @param user 			The user.
     * @return 				The amount of followers the user has.
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
