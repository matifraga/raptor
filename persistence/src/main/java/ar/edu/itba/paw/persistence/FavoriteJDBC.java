package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * 
 * Testing model
 *
 */
@Repository
public class FavoriteJDBC implements FavoriteDAO {

	static final String FAVORITE_ID = "favoriteID";
	static final String TWEET_ID = "tweetID";
	static final String FAVORITES = "favorites";
	
	private static final String SQL_IS_FAVORITE = "SELECT EXISTS( SELECT * FROM " + FAVORITES + " WHERE " + TWEET_ID
			+ " = ? AND " + FAVORITE_ID + " = ?)";
	private static final String SQL_UNFAVORITE = "DELETE FROM " + FAVORITES + " WHERE " + TWEET_ID + " = ? AND "
			+ FAVORITE_ID + " = ?";
	
	static final String SQL_GET_FAVORITE_IDS = "SELECT " + FAVORITE_ID + " FROM " + FAVORITES + " WHERE "
			+ FAVORITE_ID + " = ?";
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public FavoriteJDBC(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(FAVORITES);
	}
	
	@Override
	public void favorite(final String tweetID, final String userID) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(TWEET_ID, tweetID);
		args.put(FAVORITE_ID, userID);
		try{
			jdbcInsert.execute(args);
		} catch (DataAccessException e) { return; }
	}

	@Override
	public Boolean isFavorited(final String tweetID, final String userID) {
		try{
			Boolean ans = jdbcTemplate.queryForObject(SQL_IS_FAVORITE, Boolean.class, tweetID, userID);
			return ans;
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}

	@Override
	public void unfavorite(final String tweetID, final String userID) {
		try{
			jdbcTemplate.update(SQL_UNFAVORITE, tweetID, userID);
		} catch (DataAccessException e) { return;}
	}
}
