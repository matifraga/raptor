package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.TweetDAO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TweetServiceImplTest {

    private static final String MESSAGE = "hola soy un tweet";
    private static final String ID = "12345";

    private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
            FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";

    private static final String HASHTAG = "#test";

    private static final String SEARCH = "search";
    private static final String SESSION_ID = "session";

    private static final int RESULTSPERPAGE = 1, PAGE = 1;

    private static User owner;
    private static Tweet tweet;

    private TweetServiceImpl ts;

    @Mock
    private TweetDAO tweetDAO;

    @Mock
    private HashtagService hashtagService;
    @Mock
    private MentionService mentionService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        owner = new User(USERNAME, EMAIL, FIRSTNAME, LASTNAME, UID, false);
        tweet = new Tweet(ID, MESSAGE, owner, timestamp);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ArrayList<Tweet> arrayList = new ArrayList<>();
        ts = new TweetServiceImpl();
        ts.setTweetDAO(tweetDAO);
        when(tweetDAO.create(MESSAGE, owner)).thenReturn(tweet);
        when(tweetDAO.getGlobalFeed(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(arrayList);
        when(tweetDAO.getLogedInFeed(any(String.class), any(Integer.class), any(Integer.class))).thenReturn(arrayList);
        when(tweetDAO.getTweetsByUserID(any(String.class), any(Integer.class), any(Integer.class), any(String.class))).thenReturn(arrayList);
        when(tweetDAO.countTweets(any(String.class))).thenReturn(1);
        when(tweetDAO.isRetweeted(any(String.class), any(String.class))).thenReturn(true);
        when(tweetDAO.getTweet(tweet.getId(), SESSION_ID)).thenReturn(tweet);
        when(tweetDAO.retweet(eq(tweet.getId()), any(User.class))).thenReturn(tweet);

        ts.setHashtagService(hashtagService);
        ts.setMentionService(mentionService);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void registerTest() {

        ts.register(MESSAGE, owner);
        verify(tweetDAO).create(eq(MESSAGE), eq(owner));
        verify(hashtagService).register(eq(tweet));
        verify(mentionService).register(eq(tweet));

    }


    @Test
    public void getHashtagsTest() {

        ts.getHashtag(HASHTAG, RESULTSPERPAGE, PAGE, SESSION_ID);
        verify(tweetDAO).getTweetsByHashtag(eq(HASHTAG), eq(RESULTSPERPAGE), eq(PAGE), eq(SESSION_ID));

    }

    @Test
    public void searchTweetsTest() {

        ts.searchTweets(SEARCH, RESULTSPERPAGE, PAGE, SESSION_ID);
        verify(tweetDAO).searchTweets(eq(SEARCH), eq(RESULTSPERPAGE), eq(PAGE), eq(SESSION_ID));
    }

    @Test
    public void getMentionsTest() {
        ts.getMentions(UID, RESULTSPERPAGE, PAGE, SESSION_ID);
        verify(tweetDAO).getTweetsByMention(eq(UID), eq(RESULTSPERPAGE), eq(PAGE), eq(SESSION_ID));
    }


    @Test
    public void globalFeedTest() {
        ts.globalFeed(RESULTSPERPAGE, PAGE, SESSION_ID);
        verify(tweetDAO).getGlobalFeed(eq(RESULTSPERPAGE), eq(PAGE), eq(SESSION_ID));
    }


    @Test
    public void currentSessionFeedTest() {
        ts.currentSessionFeed(owner.getId(), RESULTSPERPAGE, PAGE);
        verify(tweetDAO).getLogedInFeed(eq(owner.getId()), eq(RESULTSPERPAGE), eq(PAGE));
    }

    @Test
    public void countTweetsTest() {
        ts.countTweets(owner);
        verify(tweetDAO).countTweets(eq(owner.getId()));
    }

    @Test
    public void increaseFavoriteCount() {
        ts.increaseFavoriteCount(tweet.getId());
        verify(tweetDAO).increaseFavoriteCount(eq(tweet.getId()));
    }

    @Test
    public void decreaseFavoriteCount() {
        ts.decreaseFavoriteCount(tweet.getId());
        verify(tweetDAO).decreaseFavoriteCount(eq(tweet.getId()));
    }

    @Test
    public void increaseRetweetCount() {
        ts.increaseRetweetCount(tweet.getId());
        verify(tweetDAO).increaseRetweetCount(eq(tweet.getId()));
    }

    @Test
    public void decreaseRetweetCount() {
        ts.decreaseRetweetCount(tweet.getId());
        verify(tweetDAO).decreaseRetweetCount(eq(tweet.getId()));
    }

    @Test
    public void getTimelineTest() {
        ts.getTimeline(owner.getId(), RESULTSPERPAGE, PAGE, SESSION_ID);
        verify(tweetDAO).getTweetsByUserID(eq(owner.getId()), eq(RESULTSPERPAGE), eq(PAGE), eq(SESSION_ID));
    }


    @Test
    public void isRetweetedTest() {
        ts.isRetweeted(tweet.getId(), owner);
        verify(tweetDAO).isRetweeted(eq(tweet.getId()), eq(owner.getId()));
    }

    @Test
    public void unreTweetTest() {
        ts.unretweet(tweet.getId(), owner);
        verify(tweetDAO).unretweet(eq(tweet.getId()), eq(owner.getId()));
        verify(tweetDAO).decreaseRetweetCount(eq(tweet.getId()));
    }

    @Test
    public void getTweetTest() {
        ts.getTweet(tweet.getId(), SESSION_ID);
        verify(tweetDAO).getTweet(eq(tweet.getId()), eq(SESSION_ID));
    }

    @Test
    public void retweetTest() {
        ts.retweet(tweet.getId(), owner);
        verify(tweetDAO).retweet(eq(tweet.getId()), eq(owner));
        verify(tweetDAO).increaseRetweetCount(eq(tweet.getId()));
    }

    @Test
    public void getFavoritesTest() {
        ts.getFavorites(tweet.getId(), RESULTSPERPAGE, PAGE, SESSION_ID);
        verify(tweetDAO).getFavorites(eq(tweet.getId()), eq(RESULTSPERPAGE), eq(PAGE), eq(SESSION_ID));
    }

}
