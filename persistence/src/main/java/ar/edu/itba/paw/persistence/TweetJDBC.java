package ar.edu.itba.paw.persistence;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

import static ar.edu.itba.paw.persistence.UserJDBC.*;
import static ar.edu.itba.paw.persistence.HashtagJDBC.*;
import static ar.edu.itba.paw.persistence.MentionJDBC.*;
import static ar.edu.itba.paw.persistence.FollowerJDBC.*;

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
	
	private static final int	MESSAGE_MAX_LENGTH = 256;
	static final int	TWEET_ID_LENGTH = 12;
	
	private static final String TWEET_SELECT = TWEETS + "." + TWEET_ID + ", " + MESSAGE + ", " + TWEETS + "." + USER_ID
						+ " AS " + USER_ID + ", " + TIMESTAMP + ", " + USERNAME + ", " + FIRST_NAME 
						+ ", " + LAST_NAME + ", " + EMAIL;
	
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "; 
	
	private static final String SQL_GET_TWEETS = "select " + TWEET_SELECT + " from " + TWEETS + ", " 
						+ USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID + 
						" AND " + USERS + "." + USER_ID + " = ? ORDER BY " 
						+ TIMESTAMP + " DESC";

	private static final String SQL_GET_TWEETS_WITH_HASHTAG = "select " + TWEET_SELECT + " from " + TWEETS + ", " 
						+ HASHTAGS + ", " + USERS + " where " + HASHTAGS + "." + TWEET_ID + " = " + TWEETS + "." + TWEET_ID + 
						" AND " + TWEETS + "." + USER_ID + " = " + USERS + "." + USER_ID + " AND " + HASHTAG + " = ? ORDER BY " 
						+ TIMESTAMP + " DESC";
	
	private static final String SQL_GET_TWEETS_WITH_MENTION = "select " + TWEET_SELECT + " from " + TWEETS + ", " 
						+ MENTIONS + ", " + USERS + " where " + MENTIONS + "." + TWEET_ID + " = " + TWEETS + "." + TWEET_ID + 
						" AND " + TWEETS + "." + USER_ID + " = " + USERS + "." + USER_ID + 
						" AND " + MENTIONS + "." + USER_ID + " = ? ORDER BY " 
						+ TIMESTAMP + " DESC";
	
	
	private static final String SQL_GET_TWEETS_CONTAINING = "select " + TWEET_SELECT + " from " + TWEETS 
						+ ", " + USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID + 
						" AND " + MESSAGE + " LIKE ('%' || ? || '%') ORDER BY " + TIMESTAMP + " DESC";

	private static final String SQL_GET_GLOBAL_FEED = "select " + TWEET_SELECT + " from " + TWEETS + ", "
						+ USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID
						+ " ORDER BY " + TIMESTAMP + " DESC";
		
	private static final String SQL_GET_LOGED_IN_FEED = "select " + TWEET_SELECT + " from " + TWEETS + ", "
			+ USERS + " where " + USERS + "." + USER_ID + " = " + TWEETS + "." + USER_ID
			+ " AND (" + USERS + "." + USER_ID + " IN (" + SQL_GET_FOLLOWING_IDS + ") OR " + USERS + "." + USER_ID + "= ?)" 
			+ " ORDER BY " + TIMESTAMP + " DESC";
	
	private static final String SQL_COUNT_TWEETS = "SELECT COUNT(aux) FROM (" + SQL_GET_TWEETS + ") as aux";

	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final TweetRowMapper tweetRowMapper;

	@Autowired
	public TweetJDBC(final DataSource ds) {
		tweetRowMapper = new TweetRowMapper();
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TWEETS);
		try{
			jdbcTemplate.execute(SQL_CREATE_TABLE + TWEETS + " ("
					+ TWEET_ID +" char(" + TWEET_ID_LENGTH + ") NOT NULL,"
					+ MESSAGE +" varchar(" + MESSAGE_MAX_LENGTH + ") NOT NULL,"
					+ USER_ID +" char(" + USER_ID_LENGTH + ") NOT NULL,"
					+ TIMESTAMP +" TIMESTAMP NOT NULL,"
					+ "PRIMARY KEY ("+ TWEET_ID +"),"
					+ "FOREIGN KEY (" + USER_ID + ") REFERENCES " + USERS + " ON DELETE CASCADE ON UPDATE RESTRICT);");
		} catch (DataAccessException e) {
			//TODO db error
		}
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
		jdbcInsert.execute(args);
		return ans;
	}

	@Override
	public List<Tweet> getTweetsByUserID(final String id, final int resultsPerPage, final int page) { //TODO update adding retweets
		try{
			return jdbcTemplate.query(SQL_GET_TWEETS + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, id);
		}catch(Exception e) { return null; } //DataAccessException or SQLException
	}

	/**
	 * Generates random tweet ID.
	 * 
	 * @return Tweet ID.
	 */
	private String randomTweetId() {
		String charactersString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";
		char[] characterArray = charactersString.toCharArray();
		String id = "";
		Random rand = new Random();

		int i = TWEET_ID_LENGTH;
		while(i>0){
			id += characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return id;
	}
	
	@Override
	public List<Tweet> getTweetsByHashtag(final String hashtag, final int resultsPerPage, final int page) {
		try{
			return jdbcTemplate.query(SQL_GET_TWEETS_WITH_HASHTAG + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, hashtag);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null; } //DataAccessException or SQLException
	}
	
	@Override
	public List<Tweet> getTweetsByMention(final String userID, final int resultsPerPage, final int page) {
		try{
			return jdbcTemplate.query(SQL_GET_TWEETS_WITH_MENTION + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, userID);
		}catch(Exception e) { return null; } //DataAccessException or SQLException
	}
	
	@Override
	public List<Tweet> searchTweets(String text, final int resultsPerPage, final int page) {
		try{
			final List<Tweet> ans = jdbcTemplate.query(SQL_GET_TWEETS_CONTAINING + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, text);
			return ans;
		} catch (Exception e){ return null; }
	}

	@Override
	public  List<Tweet> getGlobalFeed(final int resultsPerPage, final int page) {
		try {
			final List<Tweet> ans = jdbcTemplate.query(SQL_GET_GLOBAL_FEED + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper);
			return ans;
		} catch (Exception e){ return null; }
	}
	
	@Override
	public List<Tweet> getLogedInFeed(String userID, int resultsPerPage, int page) {
		try {
			final List<Tweet> ans = jdbcTemplate.query(SQL_GET_LOGED_IN_FEED + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, tweetRowMapper, userID, userID);
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
	

	private static class TweetRowMapper implements RowMapper<Tweet>{

		@Override
		public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Tweet(rs.getString(MESSAGE),rs.getString(TWEET_ID),new User(rs.getString(USERNAME), rs.getString(EMAIL), rs.getString(FIRST_NAME), rs.getString(LAST_NAME), rs.getString(USER_ID)), rs.getTimestamp(TIMESTAMP));
		}

	}
}
