package ar.edu.itba.paw.persistence;

public interface FollowerDAO {

    /**
     * Register a following action.
     *
     * @param followerID  The userID from the follower.
     * @param followingID The userID from the user being followed.
     */
    void follow(final String followerID, final String followingID);

    /**
     * Returns whether a user follows another or not.
     *
     * @param followerID  The userID from the follower.
     * @param followingID The userID from the user being followed.
     * @return True if the user follows the other, false if not.
     */
    Boolean isFollower(final String followerID, final String followingID); 

    /**
     * Stop a following action.
     *
     * @param followerID  The userID from the follower.
     * @param followingID The userID from the user not longer being followed.
     */
    void unfollow(final String followerID, final String followingID);

    /**
     * Get the amount of followers for a user.
     *
     * @param userID The user's ID.
     * @return The amount of followers the user has.
     */
    Integer countFollowers(final String userID);

    /**
     * Get the amount of following for a user.
     *
     * @param userID The user's ID.
     * @return The amount of following the user has.
     */
    Integer countFollowing(final String userID);
}
