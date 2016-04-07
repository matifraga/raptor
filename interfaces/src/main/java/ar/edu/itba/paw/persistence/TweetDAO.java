package ar.edu.itba.paw.persistence;

import java.util.List;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public interface TweetDAO {
	
	/**
	 * Create a new tweet.
	 * 
	 * @param msg The tweet's message. 
	 * @param owner The user who wrote this tweet.
	 * @return The registered tweet.
	 */
	Tweet create(final String msg, final User owner);
	
	/**
	 * Get a list of user's tweets.
	 * 
	 * @param id The user's ID.
	 * @return The user's list of tweets.
	 */
	List<Tweet> getTweetsByUserID(final String id);
	
	/**
	 * Get a list of Tweets with a hashtag.
	 * 
	 * @param hashtag
	 * @return the list
	 */
	List<Tweet> getTweetsByHashtag(final String hashtag);

	 /**
     * Search for tweets.
     * 
     * @param text the searched text.
     * @return the list of tweets.
     */
    List<Tweet> searchTweets(final String text);
}
