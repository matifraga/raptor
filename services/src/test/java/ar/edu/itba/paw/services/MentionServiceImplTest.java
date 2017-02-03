package ar.edu.itba.paw.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.MentionDAO;
import ar.edu.itba.paw.persistence.UserDAO;

public class MentionServiceImplTest {

    private static final String MESSAGE = "tweet @hola @pepe @jajaj";
    private static final String USERNAME = "@testUser", EMAIL = "testUser@gmail.com",
            FIRSTNAME = "test", LASTNAME = "user", UID = "12345abcd";
    private static User u;
    private static Tweet t;
    @Mock
    private MentionDAO mentionDAO;
    @Mock
    private UserDAO userDAO;
    private MentionServiceImpl ms;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        u = new User(USERNAME, EMAIL, FIRSTNAME, LASTNAME, UID, false);
        t = new Tweet(MESSAGE, u, timestamp);
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

    /*@Test
    public void registerTest() {
        ms.register(t);
        Set<String> mentions = t.getMentions();
        verify(userDAO, times(mentions.size())).getByUsername(any(String.class));
        verify(mentionDAO, times(mentions.size())).create(any(User.class), any(Tweet.class));

        for (String string : mentions) {
            verify(userDAO).getByUsername(eq(string));
        }
    }*/

}
