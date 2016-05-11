package ar.edu.itba.paw.persistence;

public interface FavoriteDAO {

    /**
     * Register a favorite action.
     *
     * @param tweetID The tweet's ID.
     * @param userID  The user's ID.
     */
    void favorite(final String tweetID, final String userID);

    /**
     * Returns whether a user favorited a tweet or not.
     *
     * @param tweetID The tweet's ID.
     * @param userID  The user's ID.
     * @return True if the user favorited the tweet, false if not.
     */
    Boolean isFavorited(final String tweetID, final String userID); 

    /**
     * Stop a favorite action.
     *
     * @param tweetID The tweet's ID.
     * @param userID  The user's ID.
     */
    void unfavorite(final String tweetID, final String userID);
}
