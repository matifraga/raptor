package ar.edu.itba.paw.models;

import org.junit.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class TweetTest {

    private static final String MESSAGE = "tweet";
    private static final String INVALIDMESSAGE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String HASHTAGS = "#test #test #retest #estoesuntest";

    private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
            FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";

    private static User owner;
    private static Timestamp time;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        owner = new User(USERNAME, EMAIL, FIRSTNAME, LASTNAME, UID, false);
        time = new Timestamp(System.currentTimeMillis());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validTweetTest() {

        Tweet t = new Tweet(MESSAGE, owner, time);

        assert (t.getMsg().equals(MESSAGE));
        //assert(t.getTimestamp().equals(time));
        assert (t.getOwner().equals(owner));

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidTweetTest() {
        new Tweet(INVALIDMESSAGE, owner, time);
    }

    @Test
    public void getHashtagsTest() {
        Tweet t = new Tweet(HASHTAGS, owner, time);
        Set<String> hashtagSet = new HashSet<>();
        hashtagSet.add("test");
        hashtagSet.add("test");
        hashtagSet.add("retest");
        hashtagSet.add("estoesuntest");
        assert (t.getHashtags().equals(hashtagSet));

    }
}
