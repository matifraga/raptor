package ar.edu.itba.paw.services;

import java.util.List;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public interface TweetService {

	/**
	 * Store a new Tweet.
	 * 
	 * @param msg The tweet's message. 
	 * @param owner The user wrote this tweet.
	 * @return The registered tweet.
	 */
	public Tweet register(final String msg, final User owner);
	
	/**
	 * Reweets a previous tweet.
	 * 
	 * @param tweetID The old tweet's id.
	 * @param owner The user wrote this tweet.
	 * @return The new tweet.
	 */
	public Tweet retweet(final String tweetID, final User owner);
	
	
	/**
	 * Get a user's list of tweets.
	 * 
	 * @param id The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page Number of page needed.
	 * @return The recovered tweets.
	 */
	public List<Tweet> getTimeline(final String id, final int resultsPerPage, final int page);
	
	/**
	 * Get a user's feed tweets.
	 * 
	 * @param id The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page Number of page needed.
	 * @return The recovered feed.
	 */
	public List<Tweet> getFeed(final String id, final int resultsPerPage, final int page);
	
	/**
	 * Get a user's mentions.
	 * 
	 * @param id The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page Number of page needed.
	 * @return The recovered mentions.
	 */
	public List<Tweet> getMentions(final String id, final int resultsPerPage, final int page);
	
	/**
	 * Get a user's favourites.
	 * 
	 * @param id The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page Number of page needed.
	 * @return The recovered favourites.
	 */
	public List<Tweet> getFavourites(final String id, final int resultsPerPage, final int page);
	
	/**
	 * Get a list of tweets with a hashtag.
	 * 
	 * @param hashtag The key hashtag.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page Number of page needed.
	 * @return The recovered list.
	 */
	public List<Tweet> getHashtag(final String hashtag, final int resultsPerPage, final int page);
	
	/**
	 * Get a list of tweets containing the search.
	 * 
	 * @param text The text searched.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page Number of page needed.
	 * @return The recovered list.
	 */
	public List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page);

	/**
	 * Get a list of the latest tweets in the whole network
	 *
	 * @param resultsPerPage limit number of tweets per page
	 * @param page number of page needed
     * @return
     */
	public List<Tweet> globalFeed(final int resultsPerPage, final int page);
	
	/**
	 * Get a list of the latest tweets from the logged in user.
	 *
	 * @param userID the id of the current user.
	 * @param resultsPerPage limit number of tweets per page
	 * @param page number of page needed
     * @return
     */
	public List<Tweet> currentSessionFeed(final String userID, final int resultsPerPage, final int page);
}
