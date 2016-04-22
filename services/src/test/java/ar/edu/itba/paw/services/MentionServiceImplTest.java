package ar.edu.itba.paw.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.MentionDAO;
import ar.edu.itba.paw.persistence.UserDAO;

public class MentionServiceImplTest {

	@Mock
	private MentionDAO mentionDAO;
	@Mock
	private UserDAO userDAO;
	
	private static final String MESSAGE = "tweet @hola @pepe @jajaj", ID = "12345";

	private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
			FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";

	private static Timestamp timestamp;
	private static User u;
	private static Tweet t;

	
	private MentionServiceImpl ms;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		timestamp = new Timestamp(System.currentTimeMillis());
		u = new User(USERNAME,EMAIL,FIRSTNAME,LASTNAME,UID);
		t = new Tweet(MESSAGE,ID, u, timestamp);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(userDAO.getByUsername(any(String.class))).thenReturn(u);
        ms = new MentionServiceImpl();
        ms.setUserDAO(userDAO);
        ms.setMentionDAO(mentionDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void registerTest() {
		ms.register(t);
		Set<String> mentions = t.getMentions();
		verify(userDAO,times(mentions.size())).getByUsername(any(String.class));
		verify(mentionDAO,times(mentions.size())).create(any(String.class),any(String.class));
		
		for (String string : mentions) {
			verify(userDAO).getByUsername(eq(string));
		}
	}

}
