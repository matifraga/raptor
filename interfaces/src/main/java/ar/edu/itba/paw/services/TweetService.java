package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface TweetService {

	/**
	 * Store a new Tweet.
	 *
	 * @param msg   The tweet's message.
	 * @param owner The user wrote this tweet.
	 */
	void register(final String msg, final User owner);

	/**
	 * Reweets a previous tweet.
	 *
	 * @param tweetID The old tweet's ID.
	 * @param owner   The user who retweeted.
	 */
	void retweet(final String tweetID, final User owner);

	/**
	 * Get a user's list of tweets.
	 *
	 * @param id             The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionID      The id of the user that is logged in.
	 * @return The recovered tweets.
	 */
	List<Tweet> getTimeline(final String id, final int resultsPerPage, final int page, final String sessionID);

	/**
	 * Get a user's mentions.
	 *
	 * @param id             The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionID      The id of the user that is logged in.
	 * @return The recovered mentions.
	 */
	List<Tweet> getMentions(final String id, final int resultsPerPage, final int page, final String sessionID);

	/**
	 * Get a user's favorites.
	 *
	 * @param id             The user's ID.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionID      The id of the user that is logged in.
	 * @return The recovered favorites.
	 */
	List<Tweet> getFavorites(final String id, final int resultsPerPage, final int page, final String sessionID);

	/**
	 * Get a list of tweets with a hashtag.
	 *
	 * @param hashtag        The key hashtag.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionID      The id of the user that is logged in.
	 * @return The recovered list.
	 */
	List<Tweet> getHashtag(final String hashtag, final int resultsPerPage, final int page, final String sessionID);

	/**
	 * Get a list of tweets containing the search.
	 *
	 * @param text           The text searched.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionID      The id of the user that is logged in.
	 * @return The recovered list.
	 */
	List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page, final String sessionID);

	/**
	 * Get a list of the latest tweets in the whole network
	 *
	 * @param resultsPerPage limit number of tweets per page
	 * @param page           number of page needed
	 * @param sessionID      The id of the user that is logged in.
	 * @return
     */
	List<Tweet> globalFeed(final int resultsPerPage, final int page, final String sessionID);
	
	/**
	 * Get a list of the latest tweets from users followed by the current user.
	 *
	 * @param userID         the id of the current user.
	 * @param resultsPerPage limit number of tweets per page
	 * @param page           number of page needed
	 * @return
     */
	List<Tweet> currentSessionFeed(final String userID, final int resultsPerPage, final int page);

	/**
	 * Get the amount of tweets the user has
	 *
	 * @param user the user.
	 * @return the amount of tweets the user has.
	 */
	Integer countTweets(final User user);

	/**
	 * Reflect favorite action on the tweet's favorite counter.
	 *
	 * @param tweetID
	 */
	void increaseFavoriteCount(final String tweetID);

	/**
	 * Reflect unfavorite action on the tweet's favorite counter.
	 *
	 * @param tweetID
	 */
	void decreaseFavoriteCount(final String tweetID);

	/**
	 * Reflect retweet action on the tweet's retweet counter.
	 *
	 * @param tweetID
	 */
	void increaseRetweetCount(final String tweetID);

	/**
	 * Reflect unretweet action on the tweet's retweet counter.
	 *
	 * @param tweetID
	 */
	void decreaseRetweetCount(final String tweetID);

	/**
	 * Get a tweet.
	 *
	 * @param tweetID   the tweet's ID.
	 * @param sessionID The id of the user that is logged in.
	 * @return the tweet.
	 */
	Tweet getTweet(final String tweetID, final String sessionID);

	/**
	 * Returns whether a user retweeted a tweet or not.
	 *
	 * @param tweetID
	 * @param user
	 * @return true if the user retweeted the tweet, false if not.
	 */

	Boolean isRetweeted(final String tweetID, final User user);

	/**
	 * Stop a retweet action.
	 *
	 * @param tweetID
	 * @param user
	 */
	void unretweet(final String tweetID, final User user);
}
