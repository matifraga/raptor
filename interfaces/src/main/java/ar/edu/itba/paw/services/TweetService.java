package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface TweetService {

	/**
	 * Store a new Tweet.
	 *
	 * @param msg   The tweet's message.
	 * @param owner The user who wrote this tweet.
	 */
	void register(final String msg, final User owner);

	/**
	 * Reweets a previous tweet.
	 *
	 * @param tweet The old tweet.
	 * @param owner   The user who retweeted.
	 */
	void retweet(final Tweet tweet, final User owner);

	/**
	 * Get a user's list of tweets.
	 *
	 * @param id             The user.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser      The user that is logged in.
	 * @return The recovered tweets.
	 */
	List<Tweet> getTimeline(final User user, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a user's mentions.
	 *
	 * @param user           The user.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser      The user that is logged in.
	 * @return The recovered mentions.
	 */
	List<Tweet> getMentions(final User user, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a user's favorites.
	 *
	 * @param user             The user.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser      The user that is logged in.
	 * @return The recovered favorites.
	 */
	List<Tweet> getFavorites(final User user, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of tweets with a hashtag.
	 *
	 * @param hashtag        The hashtag to be searched.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser      The user that is logged in.
	 * @return The recovered list.
	 */
	List<Tweet> getHashtag(final String hashtag, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of tweets containing the search.
	 *
	 * @param text           The text searched.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser      The user that is logged in.
	 * @return The recovered list.
	 */
	List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of the latest tweets in the whole network.
	 *
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser     The user that is logged in.
	 * @return The recovered List.
     */
	List<Tweet> globalFeed(final int resultsPerPage, final int page, final User sessionUser);
	
	/**
	 * Get a list of the latest tweets from users followed by the current user.
	 *
	 * @param user         The the current user.
	 * @param resultsPerPage Limit number of tweets per page
	 * @param page           Number of page needed.
	 * @return The recovered List.
     */
	List<Tweet> currentSessionFeed(final User user, final int resultsPerPage, final int page);

	/**
	 * Get the amount of tweets the user has.
	 *
	 * @param user The user.
	 * @return The amount of tweets the user has.
	 */
	Integer countTweets(final User user);

	/**
	 * Reflect favorite action on the tweet's favorite counter.
	 *
	 * @param tweet The tweet.
	 */
	void increaseFavoriteCount(final Tweet tweet);

	/**
	 * Reflect unfavorite action on the tweet's favorite counter.
	 *
	 * @param tweet The tweet.
	 */
	void decreaseFavoriteCount(final Tweet tweet);

	/**
	 * Reflect retweet action on the tweet's retweet counter.
	 *
	 * @param tweet The tweet.
	 */
	void increaseRetweetCount(final Tweet tweet);

	/**
	 * Reflect unretweet action on the tweet's retweet counter.
	 *
	 * @param tweet The tweet.
	 */
	void decreaseRetweetCount(final Tweet tweet);

	/**
	 * Get a tweet.
	 *
	 * @param tweetID   The tweet's ID.
	 * @param sessionUser The user that is logged in.
	 * @return The recovered tweet.
	 */
	Tweet getTweet(final String tweetID, final User sessionUser);

	/**
	 * Returns whether a user retweeted a tweet or not.
	 *
	 * @param tweet The tweet.
	 * @param user The user who retweeted.
	 * @return True if the user retweeted the tweet, false if not.
	 */

	Boolean isRetweeted(final Tweet tweet, final User user);

	/**
	 * Stop a retweet action.
	 *
	 * @param tweet The tweet.
	 * @param user The user who stop retweeting.
	 */
	void unretweet(final Tweet tweet, final User user);
}
