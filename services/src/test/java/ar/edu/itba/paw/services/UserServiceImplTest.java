package ar.edu.itba.paw.services;

import ar.edu.itba.paw.persistence.UserDAO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceImplTest {


    private static final String USERNAME = "Juan",
            PASSWORD = "1234",
            EMAIL = "juan@gmail.com",
            FIRSTNAME = "Juan",
            LASTNAME = "perez",
            ID = "1234";

    private static final String TEXT = "search";
    private static final int RESULTSPERPAGE = 1, PAGE = 1;

    private UserServiceImpl us;

    @Mock
    private UserDAO userDAO;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(userDAO.getFollowers(any(String.class), any(Integer.class), any(Integer.class))).thenReturn(new ArrayList<>());
        when(userDAO.getFollowing(any(String.class), any(Integer.class), any(Integer.class))).thenReturn(new ArrayList<>());
        us = new UserServiceImpl();
        us.setUserDao(userDAO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRegister() {

        us.register(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
        verify(userDAO).create(eq(USERNAME), eq(PASSWORD), eq(EMAIL), eq(FIRSTNAME), eq(LASTNAME));

    }

    @Test
    public void getUserWithUsernameTest() {
        us.getUserWithUsername(USERNAME);
        verify(userDAO).getByUsername(eq(USERNAME));
    }


    @Test
    public void searchUsersTest() {
        us.searchUsers(TEXT, RESULTSPERPAGE, PAGE);
        verify(userDAO).searchUsers(eq(TEXT), eq(RESULTSPERPAGE), eq(PAGE));
    }

    @Test
    public void logInuserTest() {
        us.authenticateUser(USERNAME, PASSWORD);
        verify(userDAO).authenticateUser(eq(USERNAME), eq(PASSWORD));
    }

    @Test
    public void getFollowersTest() {
        us.getFollowers(ID, RESULTSPERPAGE, PAGE);
        verify(userDAO).getFollowers(eq(ID), eq(RESULTSPERPAGE), eq(PAGE));
    }

    @Test
    public void getFollowingTest() {
        us.getFollowing(ID, RESULTSPERPAGE, PAGE);
        verify(userDAO).getFollowing(eq(ID), eq(RESULTSPERPAGE), eq(PAGE));
    }
}
