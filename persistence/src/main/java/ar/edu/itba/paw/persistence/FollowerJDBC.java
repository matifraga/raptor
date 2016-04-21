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
public class FollowerJDBC implements FollowerDAO {

	private final static String FOLLOWERS = "followers";
	private final static String USERS = "users";
	private static final String FOLLOWER_ID = "followerID";
	private static final String FOLLOWING_ID = "followingID";
	
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "; 
	
	private static final String SQL_IS_FOLLOWER = "SELECT EXISTS( SELECT * FROM " + FOLLOWERS + 
								" WHERE " + FOLLOWER_ID + " = ? AND " +
								FOLLOWING_ID + " = ?)";
	private static final String SQL_UNFOLLOW = "DELETE FROM " + FOLLOWERS + 
								" WHERE " + FOLLOWER_ID + " = ? AND " + FOLLOWING_ID + " = ?";
	
	private static final String SQL_GET_FOLLOWING_IDS = "SELECT " + FOLLOWING_ID + " FROM " + FOLLOWERS + " WHERE " + FOLLOWER_ID + " = ?";
	private static final String SQL_GET_FOLLOWER_IDS = "SELECT " + FOLLOWER_ID + " FROM " + FOLLOWERS + " WHERE " + FOLLOWING_ID + " = ?";
	
	private static final String SQL_COUNT_FOLLOWERS = "SELECT COUNT(" + FOLLOWER_ID + ") FROM (" + SQL_GET_FOLLOWER_IDS + ") AS aux";
	private static final String SQL_COUNT_FOLLOWING = "SELECT COUNT(" + FOLLOWING_ID + ") FROM (" + SQL_GET_FOLLOWING_IDS + ") AS aux";
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public FollowerJDBC(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(FOLLOWERS);
		try {
		jdbcTemplate.execute(SQL_CREATE_TABLE + FOLLOWERS + " ("
				+ FOLLOWER_ID +" varchar(12) NOT NULL, "
				+ FOLLOWING_ID +" varchar(12) NOT NULL, "
				+ "PRIMARY KEY ("+ FOLLOWER_ID +" , " + FOLLOWING_ID + "),"
				+ "FOREIGN KEY ("+ FOLLOWER_ID + ") REFERENCES " + USERS + " ON DELETE CASCADE ON UPDATE RESTRICT,"
				+ "FOREIGN KEY ("+ FOLLOWING_ID + ") REFERENCES " + USERS + " ON DELETE CASCADE ON UPDATE RESTRICT);");
		} catch (DataAccessException e) {
			//TODO db error
		}
	}

	@Override
	public void follow(final String followerID, final String followingID) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(FOLLOWER_ID, followerID);
		args.put(FOLLOWING_ID, followingID);
		try{
			jdbcInsert.execute(args);
		} catch (DataAccessException e) { return; }
	}

	@Override
	public Boolean isFollower(final String followerID, final String followingID) {
		try{
			Boolean ans = jdbcTemplate.queryForObject(SQL_IS_FOLLOWER, Boolean.class, followerID, followingID);
			return ans;
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}

	@Override
	public void unfollow(final String followerID, final String followingID) {
		try{
					jdbcTemplate.update(SQL_UNFOLLOW, followerID, followingID);
		} catch (DataAccessException e) { return;}
	}

	@Override
	public Integer countFollowers(final String userID) {
		try{
			Integer ans = jdbcTemplate.queryForObject(SQL_COUNT_FOLLOWERS, Integer.class, userID);
			return ans;
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	@Override
	public Integer countFollowing(final String userID) {
		try{
			Integer ans = jdbcTemplate.queryForObject(SQL_COUNT_FOLLOWING, Integer.class, userID);
			return ans;
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
}
