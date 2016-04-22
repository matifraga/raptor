package ar.edu.itba.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJDBC;

public class UserJDBCTest {

	private UserJDBC userJDBC;

	private static final String USERNAME = "@raptorTest", PASSWORD = "raptor",
			EMAIL = "raptor@gmail.com ", FIRSTNAME = "rap", LASTNAME = "tor";

	private static final String USERNAME2 = "@rawr";

	private static final String INVALIDUSERNAME = "";

	private static final String UNAME1 = "@user1", UNAME2 = "@user2",
			UNAME3 = "@user3";

	private static final String SEARCHALL = "";
	private static final int RESULTSPERPAGE = 3, PAGE = 1;

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

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void createTest() {
		User u = userJDBC.create(USERNAME2, PASSWORD, EMAIL, FIRSTNAME,
				LASTNAME);
		User other = userJDBC.getByUsername(USERNAME2);

		System.out.println(u);
		System.out.println(other);

		assert (u.getUsername().equals(USERNAME));
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

		List<User> ls = new ArrayList<User>();
		ls.add(u1);
		ls.add(u2);
		ls.add(u3);

		List<User> search = userJDBC.searchUsers(SEARCHALL, RESULTSPERPAGE,
				PAGE);
		assert (ls.equals(search));

		ls.removeAll(ls);

		ls.add(u1);

		search = userJDBC.searchUsers(UNAME1, RESULTSPERPAGE, PAGE);

	}

}