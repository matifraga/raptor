import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FavoriteHibernateDAO;
import ar.edu.itba.paw.persistence.TweetDAO;
import ar.edu.itba.paw.persistence.UserDAO;

public class FavoriteHibernateDAOTest {

    @Autowired
    private TweetDAO tweetDAO;

    @Autowired
    private UserDAO userDAO;

    private FavoriteHibernateDAO favoriteHibernateDAO;
    private static Tweet tweet;
    private static User user;

    private static String USERNAME = "username", FIRST_NAME = "firstName",
            LAST_NAME = "lastName", EMAIL = "email@raptor.com", PASSWORD = "rawr";
    private static boolean VERIFIED = true;

    private static String MSG = "I hate testing";
    private static Date date;
    private static int INITIAL_LIKES = 0, RETWEETS = 0;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        date = new Date(System.currentTimeMillis());
        user = new User(USERNAME,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD,VERIFIED);
        tweet = new Tweet(MSG,user,date,RETWEETS,INITIAL_LIKES,null);
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
    @Transactional
    @Rollback(true)
    void favoriteTest() {
        user =  userDAO.create(USERNAME,PASSWORD,EMAIL,FIRST_NAME,LAST_NAME);
        tweet = tweetDAO.create(MSG,user);
        favoriteHibernateDAO.favorite(tweet,user);
        assert (favoriteHibernateDAO.isFavorited(tweet,user));
    }
}
