package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public interface FavoriteService {

	/**
	 * Register a favorite action.
	 * 
	 * @param tweet
	 * @param user
	 */
	void favorite(final Tweet tweet, final User user);
	
	/**
	 * Returns whether a user favorited a tweet or not.
	 * 
	 * @param tweet
	 * @param user
	 * @return true if the user favorited the tweet, false if not.
	 */
	
	Boolean isFavorite (final Tweet tweet, final User user); //no se si va a hacer falta despues
	
	/**
	 * Stop a favorite action.
	 * 
	 * @param tweet
	 * @param user
	 */
	void unfavorite(final Tweet tweet, final User user);
	
	/**
	 * Get the amount of favorites for a tweet.
	 * 
	 * @param tweet
	 * @return The amount of favorites the tweet has.
	 */
	Integer countFavorites(final Tweet tweet);
}
