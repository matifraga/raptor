package ar.edu.itba.persistence;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.TweetJDBC;
import ar.edu.itba.paw.persistence.UserJDBC;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TweetJDBCTest {




	private static final String UNAME = "user", PASSWORD = "password",
			EMAIL = "email@email.com", FIRSTNAME = "user", LASTNAME = "user",
			ID = "12345";
	private static final String MESSAGE = "hola soy un tweet";

	private static final int RESULTSPERPAGE = 5, PAGE = 1;

	private static User user;
	

	@Autowired
	private DataSource ds;

	@Autowired
	private UserJDBC userJDBC;
	@Autowired
	private TweetJDBC tweetJDBC;

	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "tweets");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		user = userJDBC.create(UNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createTest() {
		
		
		Tweet t = tweetJDBC.create(MESSAGE, user);

		assert (t.getMsg().equals(MESSAGE));
		assert (t.getOwner().equals(user));

	}

	/*
	 * @Test public void getTweetsByUserIdTest() {
	 * 
	 * List<Tweet> tweetList = new ArrayList<Tweet>();
	 * 
	 * tweetList.add(tweetJDBC.create(MESSAGE, user));
	 * 
	 * assert(tweetList.equals(tweetJDBC.getTweetsByUserID(user.getId(),
	 * RESULTSPERPAGE, PAGE)));
	 * 
	 * tweetList.add(tweetJDBC.create(MESSAGE, user));
	 * 
	 * assert(tweetList.equals(tweetJDBC.getTweetsByUserID(user.getId(),
	 * RESULTSPERPAGE, PAGE)));
	 * 
	 * tweetList.add(tweetJDBC.create(MESSAGE, user));
	 * 
	 * assert(tweetList.equals(tweetJDBC.getTweetsByUserID(user.getId(),
	 * RESULTSPERPAGE, PAGE)));
	 * 
	 * tweetList.add(tweetJDBC.create(MESSAGE, user));
	 * 
	 * assert(tweetList.equals(tweetJDBC.getTweetsByUserID(user.getId(),
	 * RESULTSPERPAGE, PAGE)));
	 * 
	 * 
	 * }
	 */
}
