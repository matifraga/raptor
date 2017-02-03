package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public interface FavoriteDAO {

    /**
     * Register a favorite action.
     *
     * @param tweet 	The tweet.
     * @param user  	The user.
     */
    void favorite(final Tweet tweet, final User user);

    /**
     * Returns whether a user favorited a tweet or not.
     *
     * @param tweet 	The tweet.
     * @param user  	The user.
     * @return 			True if the user favorited the tweet, false if not.
     */
    Boolean isFavorited(final Tweet tweet, final User user); 

    /**
     * Stop a favorite action.
     *
     * @param tweet 	The tweet.
     * @param user  	The user.
     */
    void unfavorite(final Tweet tweet, final User user);
}
