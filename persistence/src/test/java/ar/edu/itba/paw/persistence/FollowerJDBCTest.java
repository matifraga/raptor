package ar.edu.itba.paw.persistence;

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
public class FollowerJDBCTest {

    private static final String PASSWORD = "raptor",
            EMAIL = "raptor@gmail.com ", FIRSTNAME = "rap", LASTNAME = "tor";

    private static final String UNAME1 = "@user1", UNAME2 = "@user2",
            UNAME3 = "@user3";

    @Autowired
    DataSource ds;

    @Autowired
    FollowerJDBC followerJDBC;

    @Autowired
    UserDAO userDAO;

    JdbcTemplate jdbcTemplate;

    User u1, u2, u3;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "followers");

        u1 = userDAO.create(UNAME1, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        u2 = userDAO.create(UNAME2, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        u3 = userDAO.create(UNAME3, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void followTest() {
        followerJDBC.follow(u1.getId(), u2.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "followers"));
    }

    @Test
    public void unfollowtest() {
        followerJDBC.follow(u1.getId(), u2.getId());
        followerJDBC.unfollow(u1.getId(), u2.getId());
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "followers"));
    }

    @Test
    public void countFollowersTest() {
        followerJDBC.follow(u1.getId(), u2.getId());
        assertEquals(new Integer(1), followerJDBC.countFollowers(u2.getId()));
    }

    @Test
    public void countFollowingTest() {
        followerJDBC.follow(u1.getId(), u2.getId());
        assertEquals(new Integer(1), followerJDBC.countFollowing(u1.getId()));
    }

}
