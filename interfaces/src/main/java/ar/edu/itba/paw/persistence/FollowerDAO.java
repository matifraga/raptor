package ar.edu.itba.paw.persistence;

public interface FollowerDAO {

    /**
     * Register a following action.
     *
     * @param followerID  the userID from the follower.
     * @param followingID the userID from the user being followed.
     */
    void follow(final String followerID, final String followingID);

    /**
     * Returns whether a user follows another or not.
     *
     * @param followerID  the userID from the follower.
     * @param followingID the userID from the user being followed.
     * @return true if the user follows the other, false if not.
     */

    Boolean isFollower(final String followerID, final String followingID); //no se si va a hacer falta despues

    /**
     * Stop a following action.
     *
     * @param followerID  the userID from the follower.
     * @param followingID the userID from the user not longer being followed.
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
