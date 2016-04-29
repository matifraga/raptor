package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import static ar.edu.itba.paw.persistence.UserJDBC.*;
import static ar.edu.itba.paw.persistence.TweetJDBC.*;

/**
 * 
 * Testing model
 *
 */
@Repository
public class MentionJDBC implements MentionDAO {

	final static String MENTIONS = "mentions";
	static final String USER_ID = "userID";
	static final String TWEET_ID = "tweetID";
	
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "; 
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public MentionJDBC(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(MENTIONS);
		try {
		jdbcTemplate.execute(SQL_CREATE_TABLE + MENTIONS + " ("
				+ USER_ID +" char(" + USER_ID_LENGTH + ") NOT NULL, "
				+ TWEET_ID +" char(" + TWEET_ID_LENGTH + ") NOT NULL, "
				+ "PRIMARY KEY ("+ USER_ID +" , " + TWEET_ID + "),"
				+ "FOREIGN KEY ("+ USER_ID + ") REFERENCES " + USERS + " ON DELETE CASCADE ON UPDATE RESTRICT,"
				+ "FOREIGN KEY ("+ TWEET_ID + ") REFERENCES " + TWEETS + " ON DELETE CASCADE ON UPDATE RESTRICT);");
		} catch (DataAccessException e) {
			//TODO db error
		}
	}

	@Override
	public void create(final String userID, final String tweetID) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(USER_ID, userID);
		args.put(TWEET_ID, tweetID);
		try {
			jdbcInsert.execute(args);
		} catch (DataAccessException e) { return; }	}	
}
