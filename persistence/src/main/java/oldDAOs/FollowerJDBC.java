package oldDAOs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.persistence.FollowerDAO;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Testing model
 */
@Repository
public class FollowerJDBC implements FollowerDAO {

    /*package*/ static final String FOLLOWERS = "followers";
    /*package*/ static final String FOLLOWER_ID = "followerID";
    /*package*/ static final String FOLLOWING_ID = "followingID";
    /*package*/ static final String SQL_GET_FOLLOWING_IDS = "SELECT " + FOLLOWING_ID + " FROM " + FOLLOWERS + " WHERE " + FOLLOWER_ID + " = ?";
    /*package*/ static final String SQL_GET_FOLLOWER_IDS = "SELECT " + FOLLOWER_ID + " FROM " + FOLLOWERS + " WHERE " + FOLLOWING_ID + " = ?";

    private static final String SQL_IS_FOLLOWER = "SELECT EXISTS( SELECT * FROM " + FOLLOWERS +
            " WHERE " + FOLLOWER_ID + " = ? AND " +
            FOLLOWING_ID + " = ?)";
    private static final String SQL_UNFOLLOW = "DELETE FROM " + FOLLOWERS +
            " WHERE " + FOLLOWER_ID + " = ? AND " + FOLLOWING_ID + " = ?";
    private static final String SQL_COUNT_FOLLOWERS = "SELECT COUNT(" + FOLLOWER_ID + ") FROM (" + SQL_GET_FOLLOWER_IDS + ") AS aux";
    private static final String SQL_COUNT_FOLLOWING = "SELECT COUNT(" + FOLLOWING_ID + ") FROM (" + SQL_GET_FOLLOWING_IDS + ") AS aux";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public FollowerJDBC(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(FOLLOWERS);
    }

    @Override
    public void follow(final String followerID, final String followingID) {
        final Map<String, Object> args = new HashMap<>();
        args.put(FOLLOWER_ID, followerID);
        args.put(FOLLOWING_ID, followingID);
        try {
            jdbcInsert.execute(args);
        } catch (DataAccessException e) {
        }
    }

    @Override
    public Boolean isFollower(final String followerID, final String followingID) {
        try {
            return jdbcTemplate.queryForObject(SQL_IS_FOLLOWER, Boolean.class, followerID, followingID);
        } catch (Exception e) {
            return null;
        } //SQLException or DataAccessException
    }

    @Override
    public void unfollow(final String followerID, final String followingID) {
        try {
            jdbcTemplate.update(SQL_UNFOLLOW, followerID, followingID);
        } catch (DataAccessException e) {
        }
    }

    @Override
    public Integer countFollowers(final String userID) {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_FOLLOWERS, Integer.class, userID);
        } catch (Exception e) {
            return null;
        } //SQLException or DataAccessException
    }

    @Override
    public Integer countFollowing(final String userID) {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_FOLLOWING, Integer.class, userID);
        } catch (Exception e) {
            return null;
        } //SQLException or DataAccessException
    }
}
