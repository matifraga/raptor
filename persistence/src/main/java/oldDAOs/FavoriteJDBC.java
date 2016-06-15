package oldDAOs;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.persistence.FavoriteDAO;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Testing model
 */
@Repository
public class FavoriteJDBC implements FavoriteDAO {

	/*package*/ static final String FAVORITE_ID = "favoriteID";
	/*package*/ static final String TWEET_ID = "tweetID";
	/*package*/ static final String FAVORITES = "favorites";
	/*package*/ static final String SQL_GET_FAVORITE_IDS = "SELECT " + FAVORITE_ID + " FROM " + FAVORITES + " WHERE "
			+ FAVORITE_ID + " = ?";

	private static final String SQL_IS_FAVORITE = "SELECT EXISTS( SELECT * FROM " + FAVORITES + " WHERE " + TWEET_ID
			+ " = ? AND " + FAVORITE_ID + " = ?)";
	private static final String SQL_UNFAVORITE = "DELETE FROM " + FAVORITES + " WHERE " + TWEET_ID + " = ? AND "
			+ FAVORITE_ID + " = ?";
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public FavoriteJDBC(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(FAVORITES);
	}

	public void favorite(final String tweetID, final String userID) {
		final Map<String, Object> args = new HashMap<>();
		args.put(TWEET_ID, tweetID);
		args.put(FAVORITE_ID, userID);
		try {
			jdbcInsert.execute(args);
		} catch (DataAccessException e) {
		}
	}

	public Boolean isFavorited(final String tweetID, final String userID) {
		try {
			return jdbcTemplate.queryForObject(SQL_IS_FAVORITE, Boolean.class, tweetID, userID);
		} catch (Exception e) {
			return null;
		} //SQLException or DataAccessException
	}

	public void unfavorite(final String tweetID, final String userID) {
		try {
			jdbcTemplate.update(SQL_UNFAVORITE, tweetID, userID);
		} catch (DataAccessException e) {
		}
	}

	public void favorite(Tweet tweet, User user) {

	}

	@Override
	public Boolean isFavorited(Tweet tweet, User user) {
		return null;
	}

	@Override
	public void unfavorite(Tweet tweet, User user) {

	}
}
