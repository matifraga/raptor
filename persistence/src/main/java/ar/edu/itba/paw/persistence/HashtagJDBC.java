package ar.edu.itba.paw.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * 
 * Testing model
 *
 */
@Repository
public class HashtagJDBC implements HashtagDAO {

	private static final String HASHTAGS = "hashtags";
	private static final String HASHTAG = "hashtag";
	private static final String TWEET_ID = "tweetID";
	private static final String TWEETS = "tweets";
	private static final String TIMESTAMP = "timestamp";
	
	private static final int INTERVAL = 60000; // in seconds
		
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "; 
	private static final String SQL_GET_TRENDINGS = "SELECT " + HASHTAG + ", COUNT (" + HASHTAG + ") as hCount, MAX(" + TIMESTAMP + ") as maxTime" +
													" FROM " + HASHTAGS + ", " + TWEETS + 
													" WHERE " + TWEETS + "." + TWEET_ID + " = " + HASHTAGS + "." + TWEET_ID + 
													" AND (UNIX_TIMESTAMP(CURRENT_TIMESTAMP)-UNIX_TIMESTAMP(" + TIMESTAMP + ")) <= "+ INTERVAL +
													" GROUP BY " + HASHTAG + " ORDER BY hCount DESC, maxTime DESC LIMIT ?";

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final HashtagRowMapper hashtagRowMapper;

	@Autowired
	public HashtagJDBC(final DataSource ds) {
		hashtagRowMapper = new HashtagRowMapper();
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(HASHTAGS);
		try {
		jdbcTemplate.execute(SQL_CREATE_TABLE + HASHTAGS + " ("
				+ HASHTAG +" varchar(256) NOT NULL, "
				+ TWEET_ID +" varchar(256) NOT NULL, "
				+ "PRIMARY KEY ("+ HASHTAG +" , " + TWEET_ID + "),"
				+ "FOREIGN KEY ("+ TWEET_ID + ") REFERENCES " + TWEETS + " ON DELETE CASCADE ON UPDATE RESTRICT);");
		} catch (DataAccessException e) {
			//TODO db error
		}
	}

	@Override
	public void create(final String hashtag, final String tweetID) {
		if(hashtag.length() >= 256){
			return;
		}
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(HASHTAG, hashtag);
		args.put(TWEET_ID, tweetID);
		try {
			jdbcInsert.execute(args);
		} catch (DataAccessException e) { return; }
	}

	@Override
	public List<String> getTrendingTopics(final int count) {
		try{
			return jdbcTemplate.query(SQL_GET_TRENDINGS, hashtagRowMapper, count); // new Timestamp(new Date().getTime()),
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		} //DataAccessException or SQLException
	}
	
	private static class HashtagRowMapper implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(HASHTAG);
		}
	}

}
