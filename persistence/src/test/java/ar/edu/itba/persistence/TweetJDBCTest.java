package ar.edu.itba.persistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.TweetJDBC;
import ar.edu.itba.paw.persistence.UserJDBC;

public class TweetJDBCTest {

	private TweetJDBC tweetJDBC;
	private UserJDBC userJDBC;

	private static final String UNAME = "user", PASSWORD = "password",
			EMAIL = "email@email.com", FIRSTNAME = "user", LASTNAME = "user",
			ID = "12345";
	private static final String MESSAGE = "hola soy un tweet";

	private static final int RESULTSPERPAGE = 5, PAGE = 1;

	private static User user;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		SimpleDriverDataSource sds = new SimpleDriverDataSource();
		sds.setDriverClass(JDBCDriver.class);
		sds.setUrl("jdbc:hsqldb:mem:paw");
		sds.setUsername("hq");
		sds.setPassword("");

		userJDBC = new UserJDBC(sds);
		tweetJDBC = new TweetJDBC(sds);

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
