package ar.edu.itba.paw.persistence;


import static ar.edu.itba.paw.persistence.FavoriteJDBC.FAVORITES;
import static ar.edu.itba.paw.persistence.FavoriteJDBC.FAVORITE_ID;
import static ar.edu.itba.paw.persistence.HashtagJDBC.HASHTAG;
import static ar.edu.itba.paw.persistence.HashtagJDBC.HASHTAGS;
import static ar.edu.itba.paw.persistence.MentionJDBC.MENTIONS;
import static ar.edu.itba.paw.persistence.UserJDBC.EMAIL;
import static ar.edu.itba.paw.persistence.UserJDBC.FIRST_NAME;
import static ar.edu.itba.paw.persistence.UserJDBC.LAST_NAME;
import static ar.edu.itba.paw.persistence.UserJDBC.USERNAME;
import static ar.edu.itba.paw.persistence.UserJDBC.USERS;
import static ar.edu.itba.paw.persistence.UserJDBC.VERIFIED;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

/**
 * 
 * Testing model
 *
 */
@Repository
public class TweetJDBC implements TweetDAO {
	
	static final String TWEET_ID = "tweetID";
	static final String MESSAGE = "message";
	static final String USER_ID = "userID";
	static final String TIMESTAMP = "timestamp";
	static final String TWEETS = "tweets";
	static final String RETWEET_FROM = "retweetFrom";
	static final String REPLY_FROM = "replyFrom";	
	static final String REPLY_TO = "replyTo";
	static final String COUNT_FAVORITES = "countFavorites";
	static final String COUNT_RETWEETS = "countRetweets";
	private static final String IS_RETWEETED = "isRetweeted";
	private static final String IS_FAVORITED = "isFavorited";
	
	static final int	TWEET_ID_LENGTH = 12;
	
	private static final String MQ_SELECT_FROM = "select distinct tweets.tweetID, tweets.message, users2.userID, tweets.timestamp, tweets.retweetFrom, "
			+ "tweets.countRetweets, tweets.countFavorites, users2.username, users2.email, users2.firstname, users2.lastname, users2.verified, "
			+ "max((CASE when favoriteID = users.userID and favoriteID =? and tweets.tweetID = favorites.tweetID then 1 else 0 end)) as isFavorited, "
			+ "max((CASE when tweets2.retweetFrom = tweets.tweetID and users.userID =? and tweets2.userID = users.userID then 1 else 0 end)) as isRetweeted "
			+ "from tweets, users,favorites, tweets as tweets2, users as users2, followers";
	private static final String MQ_GROUP_BY = " group by tweets.tweetID, users2.userID order by tweets.timestamp desc";
	private static final String MQ_JOIN_TWEET_USER2 = " where users2.userID = tweets.userID";
	private static final String MQ_GET_TWEETS = MQ_SELECT_FROM + MQ_JOIN_TWEET_USER2 + " and users2.userID = ?" + MQ_GROUP_BY;
//			"select distinct tweets.tweetID, tweets.message, users2.userID, tweets.timestamp, tweets.retweetFrom, "
//			+ "tweets.countRetweets, tweets.countFavorites, users2.username, users2.email, users2.firstname, users2.lastname, users2.verified, "
//			+ "max((CASE when favoriteID = users.userID and favoriteID =? and tweets.tweetID = favorites.tweetID then 1 else 0 end)) as isFav, "
//			+ "max((CASE when tweets2.retweetFrom = tweets.tweetID and users.userID =? and tweets2.userID = users.userID then 1 else 0 end)) as isRet "
//			+ "from tweets, users,favorites, tweets as tweets2, users as users2, followers where users2.userID = tweets.userID "
//			+ "and (users2.userID in (select followingID from followers where followerID = ?) or users2.userID = ? ) "
//			+ "group by tweets.tweetID, users2.userID order by tweets.timestamp desc";
	
	private static final String MQ_LOGGED_IN_FEED = MQ_SELECT_FROM + MQ_JOIN_TWEET_USER2 + " and (users2.userID in (select followingID from followers where followerID = ?) or users2.userID = ? )" + MQ_GROUP_BY;
	
	private static final String MQ_GET_TWEETS_WITH_HASHTAG = MQ_SELECT_FROM + ", hashtags " + MQ_JOIN_TWEET_USER2 + " and " + HASHTAGS + "." + TWEET_ID + " = " + TWEETS + "." + TWEET_ID + " and UPPER(" + HASHTAG + ") = ?" + MQ_GROUP_BY;
	
	
//	private static final String TWEET_SELECT = TWEETS + "." + TWEET_ID + ", " + MESSAGE + ", " + TWEETS + "." + USER_ID
//						+ " AS " + USER_ID + ", " + TIMESTAMP + ", " + USERNAME + ", " + FIRST_NAME 
//						+ ", " + LAST_NAME + ", " + EMAIL + ", " + VERIFIED + ", " + RETWEET_FROM + ", " + REPLY_TO + ", " + REPLY_FROM
//						+ ", " + COUNT_FAVORITES + ", " + COUNT_RETWEETS;
		
	private static final String SQL_GET_TWEETS = "select * from " + TWEETS + ", " 
						+ USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID + 
						" AND " + USERS + "." + USER_ID + " = ? ORDER BY " 
						+ TIMESTAMP + " DESC";

//	private static final String SQL_GET_TWEETS_WITH_HASHTAG = "select " + TWEET_SELECT + " from " + TWEETS + ", " 
//						+ HASHTAGS + ", " + USERS + " where " + HASHTAGS + "." + TWEET_ID + " = " + TWEETS + "." + TWEET_ID + 
//						" AND " + TWEETS + "." + USER_ID + " = " + USERS + "." + USER_ID + " AND UPPER(" + HASHTAG + ") = ? ORDER BY " 
//						+ TIMESTAMP + " DESC";
	
	private static final String MQ_GET_TWEETS_WITH_MENTION = MQ_SELECT_FROM + ", mentions " + MQ_JOIN_TWEET_USER2 + " and " + MENTIONS + "." + TWEET_ID + " = " + TWEETS + "." + TWEET_ID + 
			" AND " + MENTIONS + "." + USER_ID + " = ?" + MQ_GROUP_BY;
	
//	private static final String SQL_GET_TWEETS_WITH_MENTION = "select " + TWEET_SELECT + " from " + TWEETS + ", " 
//						+ MENTIONS + ", " + USERS + " where " + MENTIONS + "." + TWEET_ID + " = " + TWEETS + "." + TWEET_ID + 
//						" AND " + TWEETS + "." + USER_ID + " = " + USERS + "." + USER_ID + 
//						" AND " + MENTIONS + "." + USER_ID + " = ? ORDER BY " 
//						+ TIMESTAMP + " DESC";
	
	private static final String MQ_GET_TWEETS_CONTAINING = MQ_SELECT_FROM + MQ_JOIN_TWEET_USER2 + " AND UPPER(" + TWEETS + "." + MESSAGE + ") LIKE ('%' || ? || '%')" + MQ_GROUP_BY;
	
//	private static final String SQL_GET_TWEETS_CONTAINING = "select " + TWEET_SELECT + " from " + TWEETS 
//						+ ", " + USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID + 
//						" AND UPPER(" + MESSAGE + ") LIKE ('%' || ? || '%') ORDER BY " + TIMESTAMP + " DESC";

	private static final String MQ_GET_GLOBAL_FEED = MQ_SELECT_FROM + MQ_JOIN_TWEET_USER2 + MQ_GROUP_BY;
	
//	private static final String SQL_GET_GLOBAL_FEED = "select " + TWEET_SELECT + " from " + TWEETS + ", "
//						+ USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID
//						+ " ORDER BY " + TIMESTAMP + " DESC";
		
//	private static final String SQL_GET_LOGED_IN_FEED = "select " + TWEET_SELECT + " from " + TWEETS + ", "
//			+ USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID
//			+ " AND (" + USERS + "." + USER_ID + " IN (" + SQL_GET_FOLLOWING_IDS + ") OR " + USERS + "." + USER_ID + "= ?)" 
//			+ " ORDER BY " + TIMESTAMP + " DESC";
	
	private static final String SQL_COUNT_TWEETS = "SELECT COUNT(aux) FROM (" + SQL_GET_TWEETS + ") as aux";
	private static final String SQL_INCREASE_FAVORITES = "UPDATE " + TWEETS + " SET " + COUNT_FAVORITES + " = " + COUNT_FAVORITES + "+1 WHERE " + TWEET_ID + "=?";
	private static final String SQL_DECREASE_FAVORITES = "UPDATE " + TWEETS + " SET " + COUNT_FAVORITES + " = " + COUNT_FAVORITES + "-1 WHERE " + TWEET_ID + "=?";
	private static final String SQL_INCREASE_RETWEETS = "UPDATE " + TWEETS + " SET " + COUNT_RETWEETS + " = " + COUNT_RETWEETS + "+1 WHERE " + TWEET_ID + "=?";
	private static final String SQL_DECREASE_RETWEETS = "UPDATE " + TWEETS + " SET " + COUNT_RETWEETS + " = " + COUNT_RETWEETS + "-1 WHERE " + TWEET_ID + "=?";
	
//	private static final String SQL_GET_BY_ID = "SELECT "+ TWEET_SELECT +" FROM " + TWEETS + ", " + USERS
//			+ " WHERE " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID + " AND " + TWEET_ID + " = ?";

	private static final String MQ_GET_BY_ID = MQ_SELECT_FROM + MQ_JOIN_TWEET_USER2 + " AND " + TWEETS + "." + TWEET_ID + " = ?" + MQ_GROUP_BY;
	
	
	private static final String SQL_IS_RETWEETED = "SELECT EXISTS( SELECT * FROM " + TWEETS + " WHERE " + RETWEET_FROM
			+ " = ? AND " + USER_ID + " = ?)";
	
	private static final String SQL_UNRETWEET = "DELETE FROM " + TWEETS + " WHERE " + RETWEET_FROM + " = ? AND "
			+ USER_ID + " = ?";
	
//	private static final String SQL_GET_FAVORITES = "SELECT " + TWEET_SELECT + " FROM " + TWEETS + ", " + USERS + ", " + FAVORITES 
//			+ " WHERE " + FAVORITE_ID + " = ?" + " AND " + TWEETS + "." + TWEET_ID + " = " + FAVORITES + "." + TWEET_ID 
//			+ " AND " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID;
	
	private static final String MQ_GET_FAVORITES = MQ_SELECT_FROM + MQ_JOIN_TWEET_USER2 + " and " + FAVORITE_ID + " = ?" + " AND " + TWEETS + "." + TWEET_ID + " = " + FAVORITES + "." + TWEET_ID + MQ_GROUP_BY;
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final TweetRowMapper tweetRowMapper;

	@Autowired
	public TweetJDBC(final DataSource ds) {
		tweetRowMapper = new TweetRowMapper();
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TWEETS);
	}

	@Override
	public Tweet create(final String msg, final User owner) {
		final Map<String, Object> args = new HashMap<String, Object>();
		Tweet ans;
		String id = randomTweetId();
		Timestamp thisMoment = new Timestamp(new Date().getTime());
		try {
			ans = new Tweet(msg, id, owner, thisMoment);
		} catch (IllegalArgumentException e) { return null; }
		args.put(TWEET_ID, id);
		args.put(MESSAGE, msg);
		args.put(USER_ID, owner.getId());
		args.put(TIMESTAMP, thisMoment);
		args.put(COUNT_FAVORITES, 0);
		args.put(COUNT_RETWEETS, 0);
		args.put(REPLY_FROM, null);
		args.put(REPLY_TO, null);
		args.put(RETWEET_FROM, null);
		jdbcInsert.execute(args);
		return ans;
	}

	@Override
	public List<Tweet> getTweetsByUserID(final String id, final int resultsPerPage, final int page, final String sessionID) { //TODO update adding retweets
		try{
			return jdbcTemplate.query(MQ_GET_TWEETS + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, sessionID, sessionID, id);
		}catch(Exception e) { return null; } //DataAccessException or SQLException
	}

	/**
	 * Generates random tweet ID.
	 * 
	 * @return Tweet ID.
	 */
	private String randomTweetId() {
		char[] characterArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		char[] id = new char[TWEET_ID_LENGTH];
		Random rand = new Random();

		int i = TWEET_ID_LENGTH-1;
		while(i>=0){
			id[i] = characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return new String(id);
	}
	
	@Override
	public List<Tweet> getTweetsByHashtag(final String hashtag, final int resultsPerPage, final int page, final String sessionID) {
		try{
			return jdbcTemplate.query(MQ_GET_TWEETS_WITH_HASHTAG + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, sessionID, sessionID, hashtag.toUpperCase());
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null; } //DataAccessException or SQLException
	}
	
	@Override
	public List<Tweet> getTweetsByMention(final String userID, final int resultsPerPage, final int page, final String sessionID) {
		try{
			return jdbcTemplate.query(MQ_GET_TWEETS_WITH_MENTION + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, sessionID, sessionID, userID);
		}catch(Exception e) { return null; } //DataAccessException or SQLException
	}
	
	@Override
	public List<Tweet> searchTweets(String text, final int resultsPerPage, final int page, final String sessionID) {
		try{
			final List<Tweet> ans = jdbcTemplate.query(MQ_GET_TWEETS_CONTAINING + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, sessionID, sessionID, text.toUpperCase());
			return ans;
		} catch (Exception e){ return null; }
	}

	@Override
	public  List<Tweet> getGlobalFeed(final int resultsPerPage, final int page, final String sessionID) {
		try {
			final List<Tweet> ans = jdbcTemplate.query(MQ_GET_GLOBAL_FEED + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, sessionID, sessionID);
			return ans;
		} catch (Exception e){ return null; }
	}
	
	@Override
	public List<Tweet> getLogedInFeed(final String userID, final int resultsPerPage, final int page) {
		try {
			final List<Tweet> ans = jdbcTemplate.query(MQ_LOGGED_IN_FEED + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, userID, userID, userID, userID);
			return ans;
		} catch (Exception e){ return null; }
	}
	
	@Override
	public Integer countTweets(final String userID) {
		try{
			Integer ans = jdbcTemplate.queryForObject(SQL_COUNT_TWEETS, Integer.class, userID);
			return ans;
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	@Override
	public void increaseFavoriteCount(final String tweetID) {
		try{
			jdbcTemplate.update(SQL_INCREASE_FAVORITES, tweetID);
		} catch (DataAccessException e) { return;}
	}

	@Override
	public void decreaseFavoriteCount(final String tweetID) {
		try{
			jdbcTemplate.update(SQL_DECREASE_FAVORITES, tweetID);
		} catch (DataAccessException e) { return;}
	}

	@Override
	public void increaseRetweetCount(final String tweetID) {
		try{
			jdbcTemplate.update(SQL_INCREASE_RETWEETS, tweetID);
		} catch (DataAccessException e) { return;}
	}

	@Override
	public void decreaseRetweetCount(final String tweetID) {
		try{
			jdbcTemplate.update(SQL_DECREASE_RETWEETS, tweetID);
		} catch (DataAccessException e) { return;}
	}

	@Override
	public Tweet retweet(final String tweetID, final User user) {
		final Map<String, Object> args = new HashMap<String, Object>();
		Tweet ans;
		String id = randomTweetId();
		Timestamp thisMoment = new Timestamp(new Date().getTime());
		try {
			ans = new Tweet(id, user, thisMoment, tweetID); 
		} catch (IllegalArgumentException e) { return null; }
		args.put(TWEET_ID, id);
		args.put(MESSAGE, null);
		args.put(USER_ID, user.getId());
		args.put(TIMESTAMP, thisMoment);
		args.put(COUNT_FAVORITES, 0); //TODO nulls & check
		args.put(COUNT_RETWEETS, 0); //TODO nulls & check
		args.put(REPLY_FROM, null);
		args.put(REPLY_TO, null);
		args.put(RETWEET_FROM, tweetID);
		jdbcInsert.execute(args);
		return ans;
	}
	
	@Override
	public Tweet getTweet(final String tweetID, final String sessionID) {
		if(tweetID == null)
			return null;
		try {
			final List<Tweet> list = jdbcTemplate.query(MQ_GET_BY_ID, tweetRowMapper, sessionID, sessionID, tweetID);
			if (list.isEmpty()) {
				return null; // TODO difference between no tweet found and
								// DataAccessException pending
			}
			return list.get(0);
		} catch (Exception e) {
			return null; } // SQLException or DataAccessException
	}
	
	@Override
	public Boolean isRetweeted(final String tweetID, final String userID) {
		try{
			Boolean ans = jdbcTemplate.queryForObject(SQL_IS_RETWEETED, Boolean.class, tweetID, userID);
			return ans;
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	@Override
	public void unretweet(final String tweetID, final String userID) {
		try{
			jdbcTemplate.update(SQL_UNRETWEET, tweetID, userID);
		} catch (DataAccessException e) { return;}
		
	}

	@Override
	public List<Tweet> getFavorites(String id, int resultsPerPage, int page, final String sessionID) {
		try {
			final List<Tweet> ans = jdbcTemplate.query(MQ_GET_FAVORITES + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, sessionID, sessionID, id);
			return ans;
		} catch (Exception e){ return null; }		
	}
	
	private static class TweetRowMapper implements RowMapper<Tweet>{

		@Override
		public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Boolean isRetweeted = (rs.getInt(IS_RETWEETED)==1);
			Boolean isFavorited = (rs.getInt(IS_FAVORITED)==1);
			return new Tweet(rs.getString(MESSAGE), rs.getString(TWEET_ID),
					new User(rs.getString(USERNAME), rs.getString(EMAIL), rs.getString(FIRST_NAME), rs.getString(LAST_NAME), rs.getString(USER_ID), rs.getBoolean(VERIFIED)),
					rs.getTimestamp(TIMESTAMP), rs.getInt(COUNT_RETWEETS), rs.getInt(COUNT_FAVORITES), rs.getString(RETWEET_FROM), isRetweeted, isFavorited);
		}

	}
}
