package System.Tests.Atomic;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Atomic.Developer;
import System.Atomic.Developer.Language;
import junit.framework.TestCase;

public class DeveloperTest extends TestCase {

	Developer _tempDeveloper ;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

//	 public Developer(String firstName, String lastName, String city, Integer age, Language ... languages)
	@Test
	public void testDeveloper() {
		_tempDeveloper = new Developer ("Pawel","Korycinski","Wroclaw",20,Developer.Language.C,Developer.Language.GROOVY,Developer.Language.SCALA);
		assertNotNull(_tempDeveloper);
	}

	@Test
	public void testGetProgrammingLanguages() {
		_tempDeveloper = new Developer ("Pawel","Korycinski","Wroclaw",20,Developer.Language.C,Developer.Language.GROOVY,Developer.Language.SCALA);
		
		List<Language> _tempReturnedList = Arrays.asList(Developer.Language.C,Developer.Language.GROOVY,Developer.Language.SCALA);
		assertEquals(_tempReturnedList,_tempDeveloper.getProgrammingLanguages());
		
		System.out.println(_tempReturnedList);
	}
	
	@Test
	public void testChangeGettedProgrammingLanguages() {
		_tempDeveloper = new Developer ("Pawel","Korycinski","Wroclaw",20,Developer.Language.C,Developer.Language.GROOVY,Developer.Language.SCALA);
		
		_tempDeveloper.getProgrammingLanguages().add(Developer.Language.JAVA);
		
		assertTrue(true);
	}
	
	

}
