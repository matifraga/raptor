package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.persistence.TweetJDBC.TIMESTAMP;
import static ar.edu.itba.paw.persistence.TweetJDBC.TWEETS;

/**
 * Testing model
 */
@Repository
public class HashtagJDBC implements HashtagDAO {

    /*package*/ static final String HASHTAGS = "hashtags";
    /*package*/ static final String HASHTAG = "hashtag";
    /*package*/ static final String TWEET_ID = "tweetID";

    private static final int INTERVAL = 20000; // in seconds

    @SuppressWarnings("unused") //For Mock DB testing
    private static final String SQL_GET_TRENDINGS_HSQL = "SELECT " + HASHTAG + ", COUNT (" + HASHTAG + ") as hCount, MAX(" + TIMESTAMP + ") as maxTime" +
            " FROM " + HASHTAGS + ", " + TWEETS +
            " WHERE " + TWEETS + "." + TWEET_ID + " = " + HASHTAGS + "." + TWEET_ID +
            " AND (UNIX_TIMESTAMP(CURRENT_TIMESTAMP)-UNIX_TIMESTAMP(" + TIMESTAMP + ")) <= " + INTERVAL +
            " GROUP BY " + HASHTAG + " ORDER BY hCount DESC, maxTime DESC LIMIT ?";

    private static final String SQL_GET_TRENDINGS = "SELECT UPPER(" + HASHTAG + "), MAX(hashtag) as hashtag, COUNT (" + HASHTAG + ") as hCount, MAX(" + TIMESTAMP + ") as maxTime" +
            " FROM " + HASHTAGS + ", " + TWEETS +
            " WHERE " + TWEETS + "." + TWEET_ID + " = " + HASHTAGS + "." + TWEET_ID +
            " AND age(?," + TIMESTAMP + ") <= '1 hours'" +
            " GROUP BY UPPER(" + HASHTAG + ") ORDER BY hCount DESC, maxTime DESC LIMIT ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final HashtagRowMapper hashtagRowMapper;

    @Autowired
    public HashtagJDBC(final DataSource ds) {
        hashtagRowMapper = new HashtagRowMapper();
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(HASHTAGS);
    }

    @Override
    public void create(final String hashtag, final String tweetID) {
        if (hashtag.length() >= 256) {
            return;
        }
        final Map<String, Object> args = new HashMap<>();
        args.put(HASHTAG, hashtag);
        args.put(TWEET_ID, tweetID);
        try {
            jdbcInsert.execute(args);
        } catch (DataAccessException e) {
        }
    }

    @Override
    public List<String> getTrendingTopics(final int count) {

        try {
            Timestamp timestamp = new Timestamp(new Date().getTime());

            return jdbcTemplate.query(SQL_GET_TRENDINGS, hashtagRowMapper, timestamp, count); // new Timestamp(new Date().getTime()),
        } catch (Exception e) {
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
