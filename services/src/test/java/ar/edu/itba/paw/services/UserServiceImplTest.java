package ar.edu.itba.paw.services;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.persistence.UserDAO;


public class UserServiceImplTest {

	
    private static final String USERNAME = "Juan",
    							PASSWORD = "1234", 
    							EMAIL = "juan@gmail.com",
    							FIRSTNAME = "Juan", 
    							LASTNAME = "perez";

    private static final String TEXT = "search";
    private static final int RESULTSPERPAGE = 1, PAGE = 1;
    
    private UserServiceImpl us;

    @Mock
    private UserDAO UserDAO;
    
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

        us = new UserServiceImpl();
        us.setUserDao(UserDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegister() {
		
		us.register(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
		verify(UserDAO).create(eq(USERNAME), eq(PASSWORD), eq(EMAIL), eq(FIRSTNAME), eq(LASTNAME));
       
	}
	
	@Test
	public void getUserWithUsernameTest() {
		us.getUserWithUsername(USERNAME);
		verify(UserDAO).getByUsername(eq(USERNAME));
	}
	
	
	@Test
	public void searchUsersTest() {
		us.searchUsers(TEXT, RESULTSPERPAGE, PAGE);
		verify(UserDAO).searchUsers(eq(TEXT), eq(RESULTSPERPAGE), eq(PAGE));
	}
	
	@Test
	public void getUSerWithUsernameTest() {
		us.getUserWithUsername(USERNAME);
		verify(UserDAO).getByUsername(eq(USERNAME));
	}
	
}
