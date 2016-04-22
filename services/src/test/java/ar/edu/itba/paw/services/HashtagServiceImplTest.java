package ar.edu.itba.paw.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import ar.edu.itba.paw.persistence.HashtagDAO;


public class HashtagServiceImplTest {


@Mock
private HashtagDAO hashtagDAO;

private HashtagServiceImpl hs;

private static Tweet t;
private static User u;

private static final String MESSAGE = "tweet #test #hola #jajaj", ID = "12345";

private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
		FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";

private static Timestamp timestamp;

private static int COUNT = 1;

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
		
		hs = new HashtagServiceImpl();
		hs.setHashtagDAO(hashtagDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void registerTest() {
		hs.register(t);
		
		Set<String> hashtags = t.getHashtags();
		
		verify(hashtagDAO,times(hashtags.size())).create(any(String.class),any(String.class));
		
		for (String string : hashtags) {
			verify(hashtagDAO).create(eq(string), eq(ID));
		}
		
	}
	
	@Test
	public void getTrendingTopicsTest() {
		hs.getTrendingTopics(COUNT);
		verify(hashtagDAO).getTrendingTopics(eq(COUNT));
		
	}

}
