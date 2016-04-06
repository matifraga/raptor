package ar.edu.itba.paw.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Tweet;

/**
 * 
 * Testing model
 *
 */
@Repository
public class TweetJDBC implements TweetDAO {
	
	private static final String ID = "ID";
	private static final String MESSAGE = "message";
	private static final String USER_ID = "userID";
	private static final String TIMESTAMP = "timestamp";
	private static final String TWEETS = "tweets";
	
	private static final int TIMELINE_SIZE = 10;
	
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "; 
	private static final String SQL_GET_TWEETS = "select * from tweets where " + USER_ID + " = ? ORDER BY " + TIMESTAMP + " LIMIT "+ TIMELINE_SIZE;
	private static final String SQL_GET_TWEETS_CONTAINING = "select * from " + TWEETS + " where " + MESSAGE + " LIKE ('%' || ? || '%') ORDER BY " + TIMESTAMP + " LIMIT "+ TIMELINE_SIZE;
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public TweetJDBC(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TWEETS);
		jdbcTemplate.execute(SQL_CREATE_TABLE + TWEETS + " ("
				+ ID +" varchar(256),"
				+ MESSAGE +" varchar(256),"
				+ USER_ID +" varchar(256)," 
				+ TIMESTAMP +" TIMESTAMP,"
				+ "primary key ("+ ID +"))");
	}

	@Override
	public Tweet create(final String msg, final String userID) {
		final Map<String, Object> args = new HashMap<String, Object>();
		String id = randomTweetId();
		Timestamp thisMoment = new Timestamp(new Date().getTime());
		args.put(ID, id);
		args.put(MESSAGE, msg);
		args.put(USER_ID, userID);
		args.put(TIMESTAMP, thisMoment);
		jdbcInsert.execute(args);
		try {
			return new Tweet(msg, id, userID, thisMoment);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public List<Tweet> getTweetsByUserID(final String id) { //TODO update adding retweets
		try{
			List<Tweet> ans = jdbcTemplate.query(SQL_GET_TWEETS, new TweetRowMapper(), id);
			return ans;
		}catch(Exception e){
			return null;
		}

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

		int i = 12;
		while(i>0){
			id += characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return id;
	}
	
	private static class TweetRowMapper implements RowMapper<Tweet>{

		@Override
		public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Tweet(rs.getString(MESSAGE),rs.getString(ID),rs.getString(USER_ID), rs.getTimestamp(TIMESTAMP));
		}

	}

	@Override
	public List<Tweet> searchTweets(String text) {
		try{
			final List<Tweet> ans = jdbcTemplate.query(SQL_GET_TWEETS_CONTAINING, new TweetRowMapper(), text);
			return ans;
		} catch(Exception e){
			return null;
		}
	}

}
