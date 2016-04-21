package ar.edu.itba.paw.persistence;

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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;

/**
 * 
 *Testing model 
 *
 */
@Repository
public class UserJDBC implements UserDAO {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	private static final String FIRSTNAME = "firstName";
	private static final String LASTNAME = "lastName";
	private static final String ID = "userID";
	private static final String USERS = "users";
	
	private static final int	USERNAME_MAX_LENGTH = 100;
	private static final int	PASSWORD_MAX_LENGTH = 100;
	private static final int	EMAIL_MAX_LENGTH = 100;
	private static final int	FIRSTNAME_MAX_LENGTH = 100;
	private static final int	LASTNAME_MAX_LENGTH = 100;
	private static final int	USER_ID_LENGTH = 12;
	
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final String SQL_GET_BY_USERNAME = "SELECT * FROM " + USERS + " WHERE " + USERNAME + " = ?";
	private static final String SQL_GET_USERS_CONTAINING = "select * from " + USERS + " where " + USERNAME + " LIKE ('%' || ? || '%')";
	private static final String SQL_LOG_IN = SQL_GET_BY_USERNAME + " AND " + PASSWORD + " = ?";
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final UserRowMapper userRowMapper;

	@Autowired
	public UserJDBC(final DataSource ds) {
		userRowMapper = new UserRowMapper();
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USERS);
		try {
		jdbcTemplate.execute(SQL_CREATE_TABLE + USERS +" ("
				+ USERNAME + " varchar(" + USERNAME_MAX_LENGTH + ") NOT NULL," 
				+ PASSWORD + " varchar(" + PASSWORD_MAX_LENGTH + ") NOT NULL,"
				+ EMAIL + " varchar(" + EMAIL_MAX_LENGTH + ") NOT NULL,"
				+ FIRSTNAME + " varchar(" + FIRSTNAME_MAX_LENGTH + ") NOT NULL,"
				+ LASTNAME + " varchar(" + LASTNAME_MAX_LENGTH + ") NOT NULL,"
				+ ID + " char(" + USER_ID_LENGTH + ") NOT NULL,"
				+ "PRIMARY KEY ("+ ID +"));");
		} catch (DataAccessException e) {
			//TODO db error
		}
	}

	/**
	 * Sketchy method needed to be replaced
	 *
	 * @return a "random" userId
     */

	private String randomUserId() {
		String charactersString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";
		char[] characterArray = charactersString.toCharArray();
		String userId = "";
		Random rand = new Random();

		int i = USER_ID_LENGTH;
		while(i>0){
			userId += characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return userId;
	}

	@Override
	public User create(final String username, final String password, final String email, final String firstName, final String lastName) {
		if(!isValidUser(username, password, email, firstName, lastName))
		{
			System.out.println("invalid username");
			return null;
		}
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(USERNAME, username);
		args.put(PASSWORD, password);
		args.put(EMAIL, email);
		args.put(FIRSTNAME, firstName);
		args.put(LASTNAME, lastName);
        String userId = randomUserId();
		args.put(ID, userId);
		try {

			jdbcInsert.execute(args);
		} catch (DataAccessException e) { return null; }

		return new User(username, email, firstName, lastName, userId);
	}
	
	private boolean isValidUser(final String username, final String password, final String email, final String firstName, final String lastName) {
		boolean isLengthValid = (username.length() <= USERNAME_MAX_LENGTH && password.length() <= PASSWORD_MAX_LENGTH && email.length() <= PASSWORD_MAX_LENGTH && firstName.length() <= FIRSTNAME_MAX_LENGTH && lastName.length() <= LASTNAME_MAX_LENGTH);
		boolean noEmptyParameters = (username.length() > 0 && password.length() > 0 && email.length() > 0 && firstName.length() > 0 && lastName.length() > 0);
		
		return isLengthValid && noEmptyParameters && isUsernameAvailable(username);
	}

	@Override
	public User getByUsername(final String username) {

		try{
			final List<User> list = jdbcTemplate.query(SQL_GET_BY_USERNAME, userRowMapper, username);
	        if (list.isEmpty()) {
	                return null; //TODO difference between no user found and DataAccessException pending
	        }
	        return list.get(0);
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	@Override
	public List<User> searchUsers(final String text, final int resultsPerPage, final int page) {
		try{
			return jdbcTemplate.query(SQL_GET_USERS_CONTAINING + " LIMIT "+ resultsPerPage + " OFFSET " + (page-1)*resultsPerPage, userRowMapper, text);
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	@Override
	public Boolean isUsernameAvailable(String username) {
		return getByUsername(username)==null;
	}
	
	@Override
	public User logInUser(String username, String password) {
		try{
			final List<User> list = jdbcTemplate.query(SQL_LOG_IN, userRowMapper, username, password);
	        if (list.isEmpty()) {
	                return null; //TODO difference between no user found and DataAccessException pending
	        }
	        return list.get(0);
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	
	private static class UserRowMapper implements RowMapper<User> {

		@Override
        public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                return new User(rs.getString(USERNAME),
                		rs.getString(EMAIL),
                		rs.getString(FIRSTNAME),
                		rs.getString(LASTNAME),
                		rs.getString(ID));
        }
	}

}
