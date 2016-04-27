package ar.edu.itba.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MentionJDBCTest {

	
	private static final String MESSAGE = "hola soy un tweet";


	private static final String USERNAME = "@raptorTest", PASSWORD = "raptor",
			EMAIL = "raptor@gmail.com ", FIRSTNAME = "rap", LASTNAME = "tor";


	private static final String SEARCHALL = "";
	private static final int RESULTSPERPAGE = 3, PAGE = 1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void test() {
		//fail("Not yet implemented");
	}

}
