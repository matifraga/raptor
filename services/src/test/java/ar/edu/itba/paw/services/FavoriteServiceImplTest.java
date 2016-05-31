package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FavoriteDAO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by lumarzo on 04/05/16.
 */
public class FavoriteServiceImplTest {

    private static final String USERNAME = "Juan",
            EMAIL = "juan@gmail.com",
            FIRSTNAME = "Juan",
            LASTNAME = "perez",
            ID = "1234",
            MESSAGE = "tweet";
    private static final boolean VERIFIED = false;
    private static Tweet tweet;
    private static User user;
    @Mock
    FavoriteDAO favoriteDAO;
    @Mock
    TweetService tweetService;
    private FavoriteServiceImpl favoriteService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user = new User(USERNAME, EMAIL, FIRSTNAME, LASTNAME, ID, VERIFIED);
        tweet = new Tweet(MESSAGE, user, timestamp);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //when(favoriteDAO.isFavorite(any(String.class), any(String.class))).thenReturn(true);
        //when(favoriteDAO.countFavorites(any(String.class))).thenReturn(1);
        favoriteService = new FavoriteServiceImpl();
        favoriteService.setFavoriteDAO(favoriteDAO);
        favoriteService.setTweetService(tweetService);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void favoriteTest() {
        favoriteService.favorite(tweet, user);
        verify(tweetService).increaseFavoriteCount(tweet);
        verify(favoriteDAO).favorite(eq(tweet), eq(user));
    }

/*
    @Test
    public void isFavoriteTest() {
        boolean favorite = favoriteService.isFavorite(tweet, user);
        assert (favorite);
        verify(favoriteDAO).isFavorite(eq(tweet.getId()), eq(user.getId()));
    }
*/

    @Test
    public void unfavoriteTest() {
        favoriteService.unfavorite(tweet, user);
        verify(tweetService).decreaseFavoriteCount(tweet);
        verify(favoriteDAO).unfavorite(eq(tweet), eq(user));
    }
/*
    @Test
    public void countFacoritesTest() {
        int count = favoriteService.countFavorites(tweet.getId());
        assertEquals(count, 1);
        verify(favoriteDAO).countFavorites(eq(tweet.getId()));
    }
 */
}
