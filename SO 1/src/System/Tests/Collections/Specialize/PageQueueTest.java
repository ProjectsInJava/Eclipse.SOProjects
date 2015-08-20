package System.Tests.Collections.Specialize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Collections.Generic.GenQueue;
import System.Managers.Collections.PageQueue;
import System.Managers.Elementary.Page;
import junit.framework.TestCase;

public class PageQueueTest extends TestCase {

	PageQueue tempQueue;
	String [] tempArray1 = new String[]{"1","2","3","2","3","4"};
	String [] tempArray2 = new String[]{"1","2","1","2","1","4"};
	String[] _pageStringRefference1 = new String[]	{
			"7","0","1","2","0","3","0","4","2","3","0","3","2","1","2","0","1","7",
			"0","1"
	};

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{

	}

	@Before
	protected void setUp() throws Exception{
		
		tempQueue = new PageQueue(tempArray1);
	}

	@Test
	public void testProcessQueue(){
		
		assertNotNull(tempQueue);
		assertEquals(tempArray1.length,tempQueue.size());

	}
//
	@Test
	public void testEnqueuePage(){
		
		tempQueue.clear();
		
		Page tempPage1 = new Page("3");
		
		tempQueue.enqueue(tempPage1);
		
		assertEquals(1,tempQueue.size());
		assertEquals(true,tempQueue.contains(tempPage1));
	}
	
	@Test
	public void testEnqueueTwiceAndDequeueOnce(){
		
		tempQueue.clear();
		
		Page tempPage1 = new Page("3");
		Page tempPage2 = new Page("5");
		Page tempPage3 = new Page("4");
		Page tempPage4 = new Page("7");
		
		
		tempQueue.enqueue(tempPage1);
		tempQueue.enqueue(tempPage2);
		tempQueue.enqueue(tempPage3);
//		tempQueue.enqueue(tempPage4);
		
		assertEquals(3,tempQueue.size());
		tempQueue.set(0, tempPage4);
		assertEquals(tempPage4,tempQueue.get(0));
	}
	
	

	@Test
	public void testDequeuePage(){
		
		Page tempPage1 = tempQueue.dequeue();
		Page tempPage2 = new Page(1);
		
		assertEquals(tempPage2,tempPage1);
		assertEquals(false,tempQueue.contains(tempPage1));
	}
	
	@Test
	public void testPeek(){
		
		tempQueue.clear();
		
		Page tempPage1 = new Page("3");
		Page tempPage2 = new Page("5");
		
		tempQueue.enqueue(tempPage1);
		tempQueue.enqueue(tempPage2);
		
		assertEquals(2,tempQueue.size());
		assertEquals(tempPage1,tempQueue.peek());
		assertEquals(true,tempQueue.contains(tempPage1));		
	}
	
	@Test
	public void testGetFirst(){
		
		tempQueue.clear();
		
		Page tempPage1 = new Page("3");
		Page tempPage2 = new Page("5");
		
		tempQueue.enqueue(tempPage1);
		tempQueue.enqueue(tempPage2);
		
		assertEquals(2,tempQueue.size());
		assertEquals(tempPage1,tempQueue.getFirst());
		assertEquals(true,tempQueue.contains(tempPage1));		
	}
	
	@Test
	public void testGetLast(){
		
		tempQueue.clear();
		
		Page tempPage1 = new Page("3");
		Page tempPage2 = new Page("5");
		
		tempQueue.enqueue(tempPage1);
		tempQueue.enqueue(tempPage2);
		
		assertEquals(2,tempQueue.size());
		assertEquals(tempPage2,tempQueue.getLast());
		assertEquals(true,tempQueue.contains(tempPage2));		
	}
	
	@Test
	public void testGetRegisterTimesOfSpecificPage(){
		
		tempQueue = new PageQueue(tempArray2);
		
		Page tempDequeued = tempQueue.peek();
		List<Integer> _tempIndexes = tempQueue.GetIndexesOf(tempDequeued);
		
		assertEquals(3,_tempIndexes.size());
		assertEquals(true,_tempIndexes.containsAll(Arrays.asList(0,2,4)));	
	}
//	
//	@Test
//	public void testSchedulingPages(){
//		
//		tempQueue = new PageQueue(_pageStringRefference1);
//		tempQueue.SchedulePages();
//		
//		Page tempDequeued = tempQueue.peek();
//		GenQueue<Instant> _tempRegTimes = tempDequeued.GetTimeRegister();
//		
//		assertEquals(2,_tempRegTimes.size());
//		assertEquals(true,_tempRegTimes.containsAll(Arrays.asList(Instant.ofEpochSecond(0)
//				,Instant.ofEpochSecond(17))));	
//	}
	
	
	@Test
	public void testGetIndexesOfSpecificPage(){
		
		tempQueue = new PageQueue(tempArray2);
		
		Page tempDequeued = tempQueue.peek();
		
		List<Integer> _allocationTimes = tempQueue.stream()
		.filter(page -> page.equals(tempDequeued))
		.map(page -> tempQueue.indexOf(tempDequeued))
		.collect(Collectors.toList());
		
		assertEquals(3,_allocationTimes.size());
	}
	@Test
	public void testDistinctPageQueue(){
		
		tempQueue = new PageQueue(tempArray2);
		
		List<Page> _DistincedPages = tempQueue.stream()
				.distinct()
				.collect(Collectors.toList());
		
//		System.out.println(_DistincedPages);
		
//		assertEquals(3,_allocationTimes.size());
	}
	
	@Test
	public void testRetainPages(){
		
		tempQueue = new PageQueue();
		tempQueue.enqueue(new Page(1));
		tempQueue.enqueue(new Page(2));
		tempQueue.enqueue(new Page(3));
		tempQueue.enqueue(new Page(4));
		
		PageQueue tempQueue1 = new PageQueue();
		tempQueue1.enqueue(new Page(1));

		tempQueue1.enqueue(new Page(3));
		
		tempQueue.retainAll(tempQueue1);
	}
	
	
	@Test
	public void testRemoveIf(){
		
		tempQueue = new PageQueue();
		tempQueue.enqueue(new Page(1));
		tempQueue.enqueue(new Page(2));
		tempQueue.enqueue(new Page(3));
		tempQueue.enqueue(new Page(4));
		
		tempQueue.removeIf(page -> page.equals(new Page(1)));

		assertEquals(3,tempQueue.size());
		assertTrue(!tempQueue.contains(new Page(1)));
	}

	
	@Test
	public void testSubList(){
		
		tempQueue = new PageQueue();
		tempQueue.enqueue(new Page(1));
		tempQueue.enqueue(new Page(2));
		tempQueue.enqueue(new Page(3));
		
		tempQueue.enqueue(new Page(4));
		
		List<Page> _tempList = tempQueue.subList(tempQueue.indexOf(new Page(2)), tempQueue.size());
		
//		List<Page> _tempList = tempQueue.subList(0,2);
		
//		_tempList.forEach(temp -> System.out.println(temp.toString()));
//		tempQueue.forEach(temp -> System.out.println(temp.toString()));
	}
	
	
	@Test
	public void testPageQueueCastToArrayList(){
		
		tempQueue = new PageQueue();
		tempQueue.enqueue(new Page(1));
		tempQueue.enqueue(new Page(2));
		tempQueue.enqueue(new Page(3));
		
		ArrayList<Page> _collectionToHaveCastedQueue = new ArrayList<>();
		_collectionToHaveCastedQueue = tempQueue;
		
		assertEquals(_collectionToHaveCastedQueue,tempQueue);
		
//		List<Page> _tempList = tempQueue.subList(0,2);
		
//		_tempList.forEach(temp -> System.out.println(temp.toString()));
//		tempQueue.forEach(temp -> System.out.println(temp.toString()));
	}
}
