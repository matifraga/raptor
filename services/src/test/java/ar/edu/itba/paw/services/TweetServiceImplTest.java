package ar.edu.itba.paw.services;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.TweetDAO;

public class TweetServiceImplTest {

	private static final String MESSAGE = "hola soy un tweet";
	private static final String ID = "12345";
	
	private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
			FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";
	
	private static final String HASHTAG = "#test";
	
	private static final String SEARCH = "search";
	
	private static final int RESULTSPERPAGE = 1, PAGE = 1;
	   
	private static User owner;
	private static Tweet tweet;
	private static Timestamp timestamp;
	
	private TweetServiceImpl ts;
	
	@Mock
	private TweetDAO tweetDAO;
	
	@Mock
	private HashtagService hashtagService;
	@Mock
	private MentionService mentionService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		timestamp = new Timestamp(System.currentTimeMillis());
		owner = new User(USERNAME,EMAIL,FIRSTNAME,LASTNAME,UID);
		tweet = new Tweet(ID,MESSAGE,owner,timestamp);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        ts = new TweetServiceImpl();
        ts.setTweetDAO(tweetDAO);
        when(tweetDAO.create(MESSAGE, owner)).thenReturn(tweet);
        ts.setHashtagService(hashtagService);
        ts.setMentionService(mentionService);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void registerTest() {
		
		ts.register(MESSAGE,owner);
		verify(tweetDAO).create(eq(MESSAGE), eq(owner));
		verify(hashtagService).register(eq(tweet));
		verify(mentionService).register(eq(tweet));

	}

	
	@Test
	public void getHashtagsTest() {
		
		ts.getHashtag(HASHTAG, RESULTSPERPAGE, PAGE);
		verify(tweetDAO).getTweetsByHashtag(eq(HASHTAG), eq(RESULTSPERPAGE), eq(PAGE));
		
	}
	
	@Test
	public void searchTweetsTest() {
		
		ts.searchTweets(SEARCH, RESULTSPERPAGE, PAGE);
		verify(tweetDAO).searchTweets(eq(SEARCH), eq(RESULTSPERPAGE), eq(PAGE));
	}
	
	@Test
	public void getMentionsTest() {
		ts.getMentions(UID, RESULTSPERPAGE, PAGE);
		verify(tweetDAO).getTweetsByMention(eq(UID), eq(RESULTSPERPAGE), eq(PAGE));
	}
	
}
