package ar.edu.itba.paw.persistence;

import static ar.edu.itba.paw.persistence.FollowerJDBC.SQL_GET_FOLLOWER_IDS;
import static ar.edu.itba.paw.persistence.FollowerJDBC.SQL_GET_FOLLOWING_IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import ar.edu.itba.paw.models.User;

/**
 * 
 * Testing model
 *
 */
@Repository
public class UserJDBC implements UserDAO {

	static final String USERNAME = "username";
	static final String PASSWORD = "password";
	static final String EMAIL = "email";
	static final String FIRST_NAME = "firstName";
	static final String LAST_NAME = "lastName";
	static final String USER_ID = "userID";
	static final String USERS = "users";
	static final String VERIFIED = "verified";

	private static final int USERNAME_MAX_LENGTH = 100;
	private static final int PASSWORD_MAX_LENGTH = 100;
	private static final int EMAIL_MAX_LENGTH = 100;
	private static final int FIRSTNAME_MAX_LENGTH = 100;
	private static final int LASTNAME_MAX_LENGTH = 100;
	static final int USER_ID_LENGTH = 12;
	
	private static final String SQL_GET_BY_USERNAME = "SELECT * FROM " + USERS
			+ " WHERE UPPER(" + USERNAME + ") = ?";
	
	private static final String SQL_GET_USERS_CONTAINING = "select * from "
			+ USERS + " where UPPER(" + USERNAME + ") LIKE ('%' || ? || '%')";
	
	private static final String SQL_LOG_IN = "SELECT * FROM " + USERS
			+ " WHERE " + USERNAME + " = ?" + " AND " + PASSWORD + " = ?";

	private static final String SQL_GET_FOLLOWING_USERS = "SELECT * FROM "
			+ USERS + " WHERE " + USER_ID + " IN (" + SQL_GET_FOLLOWING_IDS + ")";
	private static final String SQL_GET_FOLLOWER_USERS = "SELECT * FROM "
			+ USERS + " WHERE " + USER_ID + " IN (" + SQL_GET_FOLLOWER_IDS + ")";
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final UserRowMapper userRowMapper;

	@Autowired
	public UserJDBC(final DataSource ds) {
		userRowMapper = new UserRowMapper();
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USERS);
	}

	/**
	 * Sketchy method needed to be replaced
	 *
	 * @return a "random" userId
	 */

	private String randomUserId() {
		char[] characterArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		char[] userId = new char[USER_ID_LENGTH];
		Random rand = new Random();

		int i = USER_ID_LENGTH-1;
		while (i >= 0) {
			userId[i] = characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return new String(userId);
	}

	@Override
	public User create(final String username, final String password,
			final String email, final String firstName, final String lastName) {
		if (!isValidUser(username, password, email, firstName, lastName)) {
			System.out.println("invalid username");
			return null;
		}
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(USERNAME, username);
		args.put(PASSWORD, password);
		args.put(EMAIL, email);
		args.put(FIRST_NAME, firstName);
		args.put(LAST_NAME, lastName);
		String userId = randomUserId();
		args.put(USER_ID, userId);
		args.put(VERIFIED, false);
		try {
			jdbcInsert.execute(args);
		} catch (DataAccessException e) { return null; }

		return new User(username, email, firstName, lastName, userId, false);
	}

	private boolean isValidUser(final String username, final String password,
			final String email, final String firstName, final String lastName) {
		boolean isLengthValid = (username.length() <= USERNAME_MAX_LENGTH
				&& password.length() <= PASSWORD_MAX_LENGTH
				&& email.length() <= EMAIL_MAX_LENGTH
				&& firstName.length() <= FIRSTNAME_MAX_LENGTH && lastName
				.length() <= LASTNAME_MAX_LENGTH);
		boolean noEmptyParameters = (username.length() > 0
				&& password.length() > 0 && email.length() > 0
				&& firstName.length() > 0 && lastName.length() > 0);

		return isLengthValid && noEmptyParameters
				&& isUsernameAvailable(username);
	}

	@Override
	public User getByUsername(final String username) {

		try {
			final List<User> list = jdbcTemplate.query(SQL_GET_BY_USERNAME,
					userRowMapper, username.toUpperCase());
			if (list.isEmpty()) {
				return null; // TODO difference between no user found and
								// DataAccessException pending
			}
			return list.get(0);
		} catch (Exception e) {	return null; } // SQLException or DataAccessException
	}

	@Override
	public List<User> searchUsers(final String text, final int resultsPerPage,
			final int page) {
		try {
			return jdbcTemplate.query(
					SQL_GET_USERS_CONTAINING + " LIMIT " + resultsPerPage
							+ " OFFSET " + (page - 1) * resultsPerPage,
					userRowMapper, text.toUpperCase());
		} catch (Exception e) { return null; } // SQLException or DataAccessException
	}

	@Override
	public Boolean isUsernameAvailable(String username) {
		return getByUsername(username.toUpperCase()) == null;
	}

	@Override
	public User logInUser(String username, String password) {
		try {
			final List<User> list = jdbcTemplate.query(SQL_LOG_IN,
					userRowMapper, username, password);
			if (list.isEmpty()) {
				return null; // TODO difference between no user found and
								// DataAccessException pending
			}
			return list.get(0);
		} catch (Exception e) { return null; } // SQLException or DataAccessException
	}

	private static class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(final ResultSet rs, final int rowNum)
				throws SQLException {
			return new User(rs.getString(USERNAME), rs.getString(EMAIL),
					rs.getString(FIRST_NAME), rs.getString(LAST_NAME),
					rs.getString(USER_ID), rs.getBoolean(VERIFIED));
		}
	}

	@Override
	public List<User> getFollowers(final String userId,
			final int resultsPerPage, final int page) {
		try {
			return jdbcTemplate.query(
					SQL_GET_FOLLOWER_USERS + " LIMIT " + resultsPerPage
							+ " OFFSET " + (page - 1) * resultsPerPage,
					userRowMapper, userId);
		} catch (Exception e) { return null; } // SQLException or DataAccessException
	}

	@Override
	public List<User> getFollowing(final String userId,
			final int resultsPerPage, final int page) {
		try {
			return jdbcTemplate.query(
					SQL_GET_FOLLOWING_USERS + " LIMIT " + resultsPerPage
							+ " OFFSET " + (page - 1) * resultsPerPage,
					userRowMapper, userId);
		} catch (Exception e) { return null; } // SQLException or DataAccessException
	}

}
