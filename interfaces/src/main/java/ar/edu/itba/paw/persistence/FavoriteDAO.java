package ar.edu.itba.paw.persistence;

public interface FavoriteDAO {

	/**
	 * Register a favorite action.
	 * 
	 * @param tweetID the tweet's ID.
	 * @param userID the user's ID.
	 */
	void favorite(final String tweetID, final String userID);
	
	/**
	 * Returns whether a user favorited a tweet or not.
	 * 
	 * @param tweetID the tweet's ID.
	 * @param userID the user's ID.
	 * @return true if the user favorited the tweet, false if not.
	 */
	
	Boolean isFavorite (final String tweetID, final String userID); //no se si va a hacer falta despues
	
	/**
	 * Stop a favorite action.
	 * 
	 * @param tweetID the tweet's ID.
	 * @param userID the user's ID.
	 */
	void unfavorite(final String tweetID, final String userID);
	
	/**
	 * Get the amount of favorites for a tweet.
	 * 
	 * @param tweetID The tweet's ID.
	 * @return The amount of favorites the tweet has.
	 */
	Integer countFavorites(final String tweetID);
}
