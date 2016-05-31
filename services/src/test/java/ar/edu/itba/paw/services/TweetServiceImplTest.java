package ar.edu.itba.paw.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;

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

    private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
            FIRSTNAME = "test", LASTNAME = "user", PASSWORD = "password";

    private static final String SESSION_USERNAME = "@testUser", SESSION_EMAIL = "testUser@gmail.com",
    		SESSION_FIRSTNAME = "test", SESSION_LASTNAME = "user", SESSION_PASSWORD = "password";

    private static final String HASHTAG = "#test";

    private static final String SEARCH = "search";

    private static final int RESULTSPERPAGE = 1, PAGE = 1;

    private static User owner;
    private static User sessionUser;
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
        owner = new User(USERNAME, EMAIL, FIRSTNAME, LASTNAME, PASSWORD, false);
        sessionUser = new User(SESSION_USERNAME, SESSION_EMAIL, SESSION_FIRSTNAME, SESSION_LASTNAME, SESSION_PASSWORD, false);
        tweet = new Tweet(MESSAGE, owner, timestamp);
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
        when(tweetDAO.getGlobalFeed(any(Integer.class), any(Integer.class), any(User.class))).thenReturn(arrayList);
        when(tweetDAO.getLogedInFeed(any(User.class), any(Integer.class), any(Integer.class))).thenReturn(arrayList);
        when(tweetDAO.getTweetsForUser(any(User.class), any(Integer.class), any(Integer.class), any(User.class))).thenReturn(arrayList);
        when(tweetDAO.countTweets(any(User.class))).thenReturn(1);
        when(tweetDAO.isRetweeted(any(Tweet.class), any(User.class))).thenReturn(true);
        when(tweetDAO.getTweetById(tweet.getId(), sessionUser)).thenReturn(tweet);
        when(tweetDAO.retweet(eq(tweet), any(User.class))).thenReturn(tweet);

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

        ts.getHashtag(HASHTAG, RESULTSPERPAGE, PAGE, sessionUser);
        verify(tweetDAO).getTweetsByHashtag(eq(HASHTAG), eq(RESULTSPERPAGE), eq(PAGE), eq(sessionUser));

    }

    @Test
    public void searchTweetsTest() {

        ts.searchTweets(SEARCH, RESULTSPERPAGE, PAGE, sessionUser);
        verify(tweetDAO).searchTweets(eq(SEARCH), eq(RESULTSPERPAGE), eq(PAGE), eq(sessionUser));
    }

    @Test
    public void getMentionsTest() {
        ts.getMentions(owner, RESULTSPERPAGE, PAGE, sessionUser);
        verify(tweetDAO).getTweetsByMention(eq(owner), eq(RESULTSPERPAGE), eq(PAGE), eq(sessionUser));
    }


    @Test
    public void globalFeedTest() {
        ts.globalFeed(RESULTSPERPAGE, PAGE, sessionUser);
        verify(tweetDAO).getGlobalFeed(eq(RESULTSPERPAGE), eq(PAGE), eq(sessionUser));
    }


    @Test
    public void currentSessionFeedTest() {
        ts.currentSessionFeed(owner, RESULTSPERPAGE, PAGE);
        verify(tweetDAO).getLogedInFeed(eq(owner), eq(RESULTSPERPAGE), eq(PAGE));
    }

    @Test
    public void countTweetsTest() {
        ts.countTweets(owner);
        verify(tweetDAO).countTweets(eq(owner));
    }

    @Test
    public void increaseFavoriteCount() {
        ts.increaseFavoriteCount(tweet);
        verify(tweetDAO).increaseFavoriteCount(eq(tweet));
    }

    @Test
    public void decreaseFavoriteCount() {
        ts.decreaseFavoriteCount(tweet);
        verify(tweetDAO).decreaseFavoriteCount(eq(tweet));
    }

    @Test
    public void increaseRetweetCount() {
        ts.increaseRetweetCount(tweet);
        verify(tweetDAO).increaseRetweetCount(eq(tweet));
    }

    @Test
    public void decreaseRetweetCount() {
        ts.decreaseRetweetCount(tweet);
        verify(tweetDAO).decreaseRetweetCount(eq(tweet));
    }

    @Test
    public void getTimelineTest() {
        ts.getTimeline(owner, RESULTSPERPAGE, PAGE, sessionUser);
        verify(tweetDAO).getTweetsForUser(eq(owner), eq(RESULTSPERPAGE), eq(PAGE), eq(sessionUser));
    }


    @Test
    public void isRetweetedTest() {
        ts.isRetweeted(tweet, owner);
        verify(tweetDAO).isRetweeted(eq(tweet), eq(owner));
    }

    @Test
    public void unreTweetTest() {
        ts.unretweet(tweet, owner);
        verify(tweetDAO).unretweet(eq(tweet), eq(owner));
        verify(tweetDAO).decreaseRetweetCount(eq(tweet));
    }

    @Test
    public void getTweetTest() {
        ts.getTweet(tweet.getId(), sessionUser);
        verify(tweetDAO).getTweetById(eq(tweet.getId()), eq(sessionUser));
    }

    @Test
    public void retweetTest() {
        ts.retweet(tweet, owner);
        verify(tweetDAO).retweet(eq(tweet), eq(owner));
        verify(tweetDAO).increaseRetweetCount(eq(tweet));
    }

    @Test
    public void getFavoritesTest() {
        ts.getFavorites(owner, RESULTSPERPAGE, PAGE, sessionUser);
        verify(tweetDAO).getFavorites(eq(owner), eq(RESULTSPERPAGE), eq(PAGE), eq(sessionUser));
    }

}
