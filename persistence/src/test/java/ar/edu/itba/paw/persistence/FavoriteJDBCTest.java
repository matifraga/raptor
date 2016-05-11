package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FavoriteJDBCTest {

    private static final String USERNAME = "@raptorTest", PASSWORD = "raptor",
            EMAIL = "raptor@gmail.com ", FIRSTNAME = "rap", LASTNAME = "tor";

    private static final String MESSAGE = "hola soy un tweet";

    @Autowired
    DataSource dataSource;

    @Autowired
    FavoriteJDBC favoriteJDBC;

    @Autowired
    TweetDAO tweetJDBC;

    @Autowired
    UserDAO userDAO;

    JdbcTemplate jdbcTemplate;

    User user;
    Tweet tweet;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tweets");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "favorites");
        user = userDAO.create(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        tweet = tweetJDBC.create(MESSAGE, user);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void favoriteTest() {
        favoriteJDBC.favorite(tweet.getId(), user.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "favorites"));
    }

    @Test
    public void unfavoriteTest() {
        favoriteJDBC.favorite(tweet.getId(), user.getId());
        favoriteJDBC.unfavorite(tweet.getId(), user.getId());
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "favorites"));
    }
}
