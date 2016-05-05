package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface FavoriteService {

	/**
	 * Register a favorite action.
	 * 
	 * @param tweet
	 * @param user
	 */
	void favorite(final String tweetID, final User user);
	
	/**
	 * Returns whether a user favorited a tweet or not.
	 * 
	 * @param tweet
	 * @param user
	 * @return true if the user favorited the tweet, false if not.
	 */
	
	Boolean isFavorite (final String tweetID, final User user); 
	
	/**
	 * Stop a favorite action.
	 * 
	 * @param tweetID
	 * @param user
	 */
	void unfavorite(final String tweetID, final User user);
	
	/**
	 * Get the amount of favorites for a tweet.
	 * 
	 * @param tweetID 
	 * @return The amount of favorites the tweet has.
	 */
	Integer countFavorites(final String tweetID);
}
