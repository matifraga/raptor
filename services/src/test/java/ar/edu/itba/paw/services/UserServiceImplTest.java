package ar.edu.itba.paw.services;

import static org.junit.Assert.*;
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

    private UserServiceImpl us;

    @Mock
    private UserDAO userDao;
    
    
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
        us.setUserDao(userDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegister() {
		
		us.register(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME);
		verify(userDao).create(eq(USERNAME), eq(PASSWORD), eq(EMAIL), eq(FIRSTNAME), eq(LASTNAME));
       
	}
	
	@Test
	public void getUserWithUsernameTest() {
		us.getUserWithUsername(USERNAME);
		verify(userDao).getByUsername(eq(USERNAME));
	}
}
