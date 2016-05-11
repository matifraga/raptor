package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface FavoriteService {

	/**
	 * Register a favorite action.
	 *
	 * @param tweetID The tweet's ID.
	 * @param user The user who favorite.
	 */
	void favorite(final String tweetID, final User user);

	/**
	 * Returns whether a user favorited a tweet or not.
	 *
	 * @param tweetID The tweet's ID.
	 * @param user The user who sees the tweet.
	 * @return True if the user favorited the tweet, false if not.
	 */
	Boolean isFavorited(final String tweetID, final User user);

	/**
	 * Stop a favorite action.
	 *
	 * @param tweetID The tweet's ID.
	 * @param user The user who stop the favorite action.
	 */
	void unfavorite(final String tweetID, final User user);
}
