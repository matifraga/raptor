package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface TweetDAO {

	/**
	 * Create a new tweet.
	 *
	 * @param msg   The tweet's message.
	 * @param owner The user who wrote this tweet.
	 * @return The registered tweet.
	 */
	Tweet create(final String msg, final User owner);

	/**
	 * Retweet a previous tweet.
	 *
	 * @param tweet The previous tweet.
	 * @param user    The owner of the reTweet.
	 * @return The new tweet.
	 */
	Tweet retweet(final Tweet tweet, final User user);

	/**
	 * Get a list of user's tweets.
	 *
	 * @param user           The user.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param page           Number of page needed.
	 * @param sessionUser    The user that is logged in.
	 * @return The user's list of tweets.
	 */
	List<Tweet> getTweetsForUser(final User user, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of Tweets with a hashtag.
	 *
	 * @param hashtag		 The searched hashtag.
	 * @param resultsPerPage Limit number of tweets per page.
	 * @param sessionUser    The user that is logged in.
	 * @param page           Number of page needed.
	 * @return 				 The list of tweets containing the hashtag.
	 */
	List<Tweet> getTweetsByHashtag(final String hashtag, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of Tweets mentioning a user.
	 *
	 * @param user         		The user.
	 * @param resultsPerPage 	Limit number of tweets per page.
	 * @param sessionUser      	The user that is logged in.
	 * @param page           	Number of page needed.
	 * @return 					the list of tweets containing the mention.
	 */
	List<Tweet> getTweetsByMention(final User user, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Search for tweets.
	 *
	 * @param text           	The searched text.
	 * @param resultsPerPage 	Limit number of tweets per page.
	 * @param page           	Number of page needed.
	 * @param sessionUser       The user that is logged in.
	 * @return the list of tweets containing the text.
     */
    List<Tweet> searchTweets(final String text, final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of the latest tweets in the whole network.
	 *
	 * @param resultsPerPage 	Limit number of tweets per page.
	 * @param sessionUser       The user that is logged in.
	 * @param page           	Number of page needed.
	 * @return The list of tweets.
     */
	List<Tweet> getGlobalFeed(final int resultsPerPage, final int page, final User sessionUser);

	/**
	 * Get a list of the latest tweets from the users you follow.
	 *
	 * @param user         		The user.
	 * @param resultsPerPage 	Limit number of tweets per page.
	 * @param page           	Number of page needed.
	 * @return The list of tweets.
     */
	List<Tweet> getLogedInFeed(final User user, final int resultsPerPage, final int page);

	/**
	 * Get the amount of tweets the user has.
	 *
	 * @param user the user.
	 * @return the amount of tweets the user has.
	 */
	Integer countTweets(final User user);

	/**
	 * Reflect favorite action on the tweet's favorite counter.
	 *
	 * @param tweet the tweet.
	 */
	void increaseFavoriteCount(final Tweet tweet);

	/**
	 * Reflect unfavorite action on the tweet's favorite counter.
	 *
	 * @param tweet the tweet.
	 */
	void decreaseFavoriteCount(final Tweet tweet);

	/**
	 * Reflect retweet action on the tweet's retweet counter.
	 *
	 * @param tweet the tweet.
	 */
	void increaseRetweetCount(final Tweet tweet);

	/**
	 * Reflect unretweet action on the tweet's retweet counter.
	 *
	 * @param tweet the tweet.
	 */
	void decreaseRetweetCount(final Tweet tweet);

	/**
	 * Get a tweet.
	 *
	 * @param tweetID   The tweet's ID.
	 * @param sessionUser The user that is logged in.
	 * @return The recovered tweet.
	 */
	Tweet getTweetById(final String tweetID, final User sessionUser);

	/**
	 * Returns whether a user retweeted a tweet or not.
	 *
	 * @param tweet The tweet.
	 * @param user  The user.
	 * @return True if the user retweeted the tweet, false if not.
	 */
	Boolean isRetweeted(final Tweet tweet, final User user);

	/**
	 * Stop a retweet action.
	 *
	 * @param tweet The tweet.
	 * @param user  The user.
	 */
	void unretweet(final Tweet tweet, final User user);

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

}
