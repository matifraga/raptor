package ar.edu.itba.paw.services;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

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
import ar.edu.itba.paw.persistence.HashtagDAO;


public class HashtagServiceImplTest {

    private static final String MESSAGE = "tweet #test #hola #jajaj";
    private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
            FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";
    @Mock
    private HashtagDAO hashtagDAO;
    private HashtagServiceImpl hs;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User u = new User(USERNAME, EMAIL, FIRSTNAME, LASTNAME, UID, false);
        new Tweet(MESSAGE, u, timestamp);
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

    /*@Test
    public void registerTest() {
        hs.register(t);

        Set<String> hashtags = t.getHashtags();

        verify(hashtagDAO, times(hashtags.size())).create(any(String.class), any(Tweet.class));

        for (String string : hashtags) {
            verify(hashtagDAO).create(eq(string), eq(t));
        }

    }*/

    @Test
    public void getTrendingTopicsTest() {
        int COUNT = 1;
        hs.getTrendingTopics(COUNT);
        verify(hashtagDAO).getTrendingTopics(eq(COUNT));

    }

}
