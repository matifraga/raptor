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
	
	private static final int USERLIST_SIZE = 10;
	
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final String SQL_GET_BY_USERNAME = "SELECT * FROM " + USERS + " WHERE " + USERNAME + " = ? LIMIT 1";
	private static final String SQL_GET_USERS_CONTAINING = "select * from " + USERS + " where " + USERNAME + " LIKE ('%' || ? || '%') LIMIT "+ USERLIST_SIZE;
	
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
				+ USERNAME + " varchar(100) NOT NULL," 
				+ PASSWORD + " varchar(100) NOT NULL,"
				+ EMAIL + " varchar(100),"
				+ FIRSTNAME + " varchar(100) NOT NULL,"
				+ LASTNAME + " varchar(100) NOT NULL,"
				+ ID + " varchar(100) NOT NULL,"
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

		int i = 12;
		while(i>0){
			userId += characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return userId;
	}

	@Override
	public User create(final String username, final String password, final String email, final String firstName, final String lastName) {
		if(username.length() >= 256 || password.length() >= 256 || email.length() >= 256 || firstName.length() >= 256 || lastName.length() >= 256 )
			return null;
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put(USERNAME, username);
		args.put(PASSWORD, password);
		args.put(EMAIL, email);
		args.put(FIRSTNAME, firstName);
		args.put(LASTNAME, lastName);
        String userId = randomUserId();
		args.put(ID, userId);
		jdbcInsert.execute(args);

		return new User(username, email, firstName, lastName, userId);
	}

	//TODO SQL injection?
	@Override
	public User getByUsername(String username) {

		try{
			final List<User> list = jdbcTemplate.query(SQL_GET_BY_USERNAME, userRowMapper, username);
	        if (list.isEmpty()) {
	                return null; //TODO difference between no user found and DataAccessException pending
	        }
	        return list.get(0);
		} catch(Exception e) { return null; } //SQLException or DataAccessException
	}
	
	@Override
	public List<User> searchUsers(String text) {
		try{
			return jdbcTemplate.query(SQL_GET_USERS_CONTAINING, userRowMapper, text);
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
