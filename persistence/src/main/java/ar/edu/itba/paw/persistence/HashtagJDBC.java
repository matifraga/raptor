package ar.edu.itba.paw.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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
	private static final String TIME = "timestamp";
	
	private static final int LIMIT = 5;
	private static final int INTERVAL = 12;
		
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "; 
	private static final String SQL_GET_TRENDINGS = "SELECT " + HASHTAG + " FROM " 
					+ HASHTAGS + " WHERE " + TIME + " >= ? - INTERVAL " 
					+ INTERVAL + " HOUR ORDER BY SUM(" + HASHTAG + ") DESCLIMIT " + LIMIT; 

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final HashtagRowMapper hashtagRowMapper;
	private final Timestamp timestamp;

	@Autowired
	public HashtagJDBC(final DataSource ds) {
		hashtagRowMapper = new HashtagRowMapper();
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(HASHTAGS);
		timestamp = new Timestamp(new Date().getTime());
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
		System.out.println(hashtag);
		if(hashtag.length() >= 256){
			return;
		}
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(HASHTAG, hashtag);
		args.put(TWEET_ID, tweetID);
		jdbcInsert.execute(args);
	}

	@Override
	public List<String> getTrendingTopics() {
		try{
			return jdbcTemplate.query(SQL_GET_TRENDINGS, hashtagRowMapper, timestamp.getTime()); //TODO check timestamp
		} catch(Exception e) { return null; } //DataAccessException or SQLException
	}
	
	private static class HashtagRowMapper implements RowMapper<String> {
		
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(HASHTAG);
		}
	}

}
