package oldDAOs;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.persistence.MentionDAO;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Testing model
 */
@Repository
public class MentionJDBC implements MentionDAO {

    /*package*/ static final String MENTIONS = "mentions";
    /*package*/ static final String USER_ID = "userID";
    /*package*/ static final String TWEET_ID = "tweetID";

    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public MentionJDBC(final DataSource ds) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(MENTIONS);
    }

    public void create(final String userID, final String tweetID) {

        final Map<String, Object> args = new HashMap<>();
        args.put(USER_ID, userID);
        args.put(TWEET_ID, tweetID);
        try {
            jdbcInsert.execute(args);
        } catch (DataAccessException e) {}
    }

    @Override
    public void create(User user, Tweet tweet) {

    }
}
