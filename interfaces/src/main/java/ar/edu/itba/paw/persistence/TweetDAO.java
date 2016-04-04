package ar.edu.itba.paw.persistence;

import java.util.List;

import ar.edu.itba.paw.models.Tweet;

public interface TweetDAO {
	
	/**
	 * Create a new tweet.
	 * 
	 * @param msg The tweet's message. 
	 * @param id The tweet's ID.
	 * @param userID The user's ID.
	 * @return The registered tweet.
	 */
	Tweet create(final String msg, final int id, final int userID);
	
	/**
	 * Get a list of user's tweets.
	 * 
	 * @param id The user's ID.
	 * @return The user's list of tweets.
	 */
	List<Tweet> getTweetsByUserID(final int id);

}
