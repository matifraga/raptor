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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserJDBCTest {


    private static final String USERNAME = "@raptorTest", PASSWORD = "raptor",
            EMAIL = "raptor@gmail.com ", FIRSTNAME = "rap", LASTNAME = "tor";

    private static final String USERNAME2 = "@rawr";

    private static final String INVALIDUSERNAME = "";

    private static final String UNAME1 = "@user1", UNAME2 = "@user2",
            UNAME3 = "@user3";

    private static final String SEARCHALL = "";
    private static final int RESULTSPERPAGE = 3, PAGE = 1;


    @Autowired
    private DataSource ds;

    @Autowired
    private UserJDBC userJDBC;

    @Autowired
    private FollowerJDBC followerJDBC;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createTest() {
        User u = userJDBC.create(USERNAME2, PASSWORD, EMAIL, FIRSTNAME,
                LASTNAME);
        User other = userJDBC.getByUsername(USERNAME2);

        assert (u.getUsername().equals(USERNAME2));
        assert (u.getEmail().equals(EMAIL));
        assert (u.getFirstName().equals(FIRSTNAME));
        assert (u.getLastName().equals(LASTNAME));

        assert (u.equals(other));

    }

    @Test
    public void createInvalidTest() {
        User u = userJDBC.create(INVALIDUSERNAME, PASSWORD, EMAIL, FIRSTNAME,
                LASTNAME);
        assert (u == null);

        userJDBC.create(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        u = userJDBC.create(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);

        assert (u == null);

    }

    @Test
    public void searchUsersTest() {
        User u1 = userJDBC.create(UNAME1, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        User u2 = userJDBC.create(UNAME2, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        User u3 = userJDBC.create(UNAME3, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);

        List<User> ls = new ArrayList<>();
        ls.add(u1);
        ls.add(u2);
        ls.add(u3);


        List<User> search = userJDBC.searchUsers(SEARCHALL, RESULTSPERPAGE,
                PAGE);


        for (User user : ls) {
            assert (search.contains(user));
        }

        ls.clear();

        ls.add(u1);

        search = userJDBC.searchUsers(UNAME1, RESULTSPERPAGE, PAGE);

    }

    @Test
    public void getByUsernameTest() {
        User u1 = userJDBC.create(UNAME1, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        assertEquals(u1, userJDBC.getByUsername(UNAME1));
    }

    @Test
    public void isUsernameAvailableTest() {
        userJDBC.create(UNAME1, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        assertFalse(userJDBC.isUsernameAvailable(UNAME1));
    }

    @Test
    public void authenticateUser() {
        User u1 = userJDBC.create(UNAME1, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        assertEquals(u1, userJDBC.authenticateUser(UNAME1, PASSWORD));
    }

    @Test
    public void getFollowersFollowingTest() {
        User u1 = userJDBC.create(UNAME1, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        User u2 = userJDBC.create(UNAME2, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        User u3 = userJDBC.create(UNAME3, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);

        followerJDBC.follow(u2.getId(), u1.getId());
        followerJDBC.follow(u1.getId(), u3.getId());

        assert (userJDBC.getFollowers(u1.getId(), RESULTSPERPAGE, PAGE).contains(u2));
        assert (userJDBC.getFollowing(u1.getId(), RESULTSPERPAGE, PAGE).contains(u3));
    }
}