package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.FollowerDAO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by lumarzo on 04/05/16.
 */
public class FollowerServiceImplTest {

    private static final String USERNAME1 = "Juan",
            EMAIL1 = "juan@gmail.com",
            FIRSTNAME1 = "Juan",
            LASTNAME1 = "Perez",
            ID1 = "1234";
    private static final String USERNAME2 = "Pepe",
            EMAIL2 = "pepe@gmail.com",
            FIRSTNAME2 = "Pepe",
            LASTNAME2 = "Perez",
            ID2 = "4321";
    private static final boolean verified = false;
    private static User juan;
    private static User pepe;
    @Mock
    FollowerDAO followerDAO;
    private FollowerServiceImpl followerService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        juan = new User(USERNAME1, EMAIL1, FIRSTNAME1, LASTNAME1, ID1, verified);
        pepe = new User(USERNAME2, EMAIL2, FIRSTNAME2, LASTNAME2, ID2, verified);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(followerDAO.isFollower(any(User.class), any(User.class))).thenReturn(true);
        when(followerDAO.countFollowers(any(User.class))).thenReturn(1);
        when(followerDAO.countFollowing(any(User.class))).thenReturn(1);
        followerService = new FollowerServiceImpl();
        followerService.setFollowerDAO(followerDAO);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void followTest() {
        //followerService.follow(juan, pepe);
        //verify(followerDAO).follow(eq(juan), eq(pepe));
    }

    @Test
    public void sameUser() {
        followerService.follow(juan, juan);
        verifyZeroInteractions(followerDAO);
    }

    @Test
    public void isFollowerTest() {
        boolean follower = followerService.isFollower(juan, pepe);
        assert (follower);
        verify(followerDAO).isFollower(eq(juan), eq(pepe));
    }

    /*@Test
    public void unfollowTest() {
        followerService.unfollow(juan, pepe);
        verify(followerDAO).unfollow(eq(juan), eq(pepe));
    }*/

    @Test
    public void countFollowersTest() {
        int followers = followerService.countFollowers(juan);
        assertEquals(followers, 1);
        verify(followerDAO).countFollowers(eq(juan));
    }

    @Test
    public void countFollowingTest() {
        int following = followerService.countFollowing(juan);
        assertEquals(following, 1);
        verify(followerDAO).countFollowing(eq(juan));
    }
}
