package System.Tests.Hardware.Unit;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Hardware.HDD;
import System.Managers.Collections.PageQueue;
import System.Managers.Elementary.Page;
import junit.framework.TestCase;

public class HDDTest extends TestCase {

	HDD tempHdd = HDD.Instance;
	PageQueue _tempPageQueueRefference;
	
	Page _tempSearchedPageToRamAllocate7 = new Page(7);
	Page _tempSearchedPageToRamAllocate0 = new Page(0);
	Page _tempSearchedPageToRamAllocate1 = new Page(1);
	Page _tempSearchedPageToRamAllocate2 = new Page(2);
	Page _tempSearchedPageToRamAllocate3 = new Page(3);
	Page _tempSearchedPageToRamAllocate4 = new Page(4);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		_tempPageQueueRefference = new PageQueue(_tempPageRefference);

		tempHdd.Allocate(_tempPageQueueRefference);
	}

	String[] _tempPageRefference = new String[]	{
			"7","0","1","2","0","3","0","4","2","3","0","3","2","1","2","0","1","7",
			"0","1"
	};

	@Test
	public void testHDDcontains()
	{
		
		assertTrue(tempHdd.containsAll(Arrays
				.asList(
					_tempSearchedPageToRamAllocate7,
					_tempSearchedPageToRamAllocate0,
					_tempSearchedPageToRamAllocate1
				)));
	}
	
	@Test
	public void testSize(){
		
		assertEquals(6,tempHdd.size());
	}
	
	@Test
	public void testHDDIndexes(){
		
		assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate7).GetTimeRegister().size()!=0);
		assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate0).GetTimeRegister().size()!=0);
		assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate1).GetTimeRegister().size()!=0);
		assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate2).GetTimeRegister().size()!=0);
	    assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate3).GetTimeRegister().size()!=0);
	    assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate4).GetTimeRegister().size()!=0);
	}
	
	@Test
	public void testHDDAllocatePage(){
		
		tempHdd.clear();
		PageQueue tempSheculedPages =new PageQueue(_tempPageRefference);
		tempHdd.Allocate(tempSheculedPages);
		
		assertTrue(tempHdd.contains(_tempSearchedPageToRamAllocate7));
		assertTrue(tempHdd.contains(_tempSearchedPageToRamAllocate0));
		assertTrue(tempHdd.contains(_tempSearchedPageToRamAllocate1));
		assertTrue(tempHdd.contains(_tempSearchedPageToRamAllocate2));
	    assertTrue(tempHdd.contains(_tempSearchedPageToRamAllocate3));
	    assertTrue(tempHdd.contains(_tempSearchedPageToRamAllocate4));
	}
	
	@Test
	public void testHDDUpdatePage(){
		
		tempHdd.Update(new Page(7));
		assertTrue(tempHdd.Get(_tempSearchedPageToRamAllocate7).GetTimeRegister().size()==0);
	}
	
	@Test
	public void testHDDtoString() {

		String tempAVal = HDD.Instance.toString();
		
		System.out.println(tempAVal);
		assertNotNull(tempAVal);
	}
}
