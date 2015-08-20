package System.Tests.Managers.Unit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import System.Algorithms.Enums.Framing;
import System.Algorithms.Enums.Paging;
import System.Algorithms.Framing.NullAlgo;
import System.Diagnostics.ProcessMy;
import System.Hardware.CPU;
import System.Hardware.HDD;
import System.Hardware.RAM;
import System.Managers.SO;
import System.Managers.VMM;
import System.Managers.Collections.PageQueue;
import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VMMTest extends TestCase {
	final int aPageRefferenceSize = 1000;
	
	String[] _pageStringRefference1 = new String[]	{
			"7","0","1","2","0","3","0","4","2","3","0","3","2","1","2","0","1","7",
			"0","1"
	};
	
	String[] _pageStringRefference2 = new String[]	{
			"2","3","4","2","1","3","7","5","4","3"
	};
	
	String[] _pageStringRefferenceCore1 = new String[]	{
			"1",
			"2",
			"3",
			"2",
			
			"3",
			"2",
			"1",
			
			"4",
			"3",
			"1",
			"4",
			
			"3",
			"2",
			"3",
			"1",
			"1"
	};
	
	String[] _pageStringRefferenceCore2 = new String[]	{
			"2",
			"1",
			"3",
			
			"2",
			"3",
			"1",
			
			"2",
			"1",
			"1",
			"3",
			"4",
	};
	
	String[] _pageStringRefferenceCore3 = new String[]	{
			"4",
			"4",
			"3",
			
			"1",
			"3",
			"2",
			"4",
			
			"1",
			"3",
			"2",
			"4",
			"3",
	};

	PageQueue _pageQueueRefference;
	
	int aAmountOfRAMFrames;
	int aAmountOfHDDPages;

	HDD tempHDDDriver = HDD.Instance;
	SO tempSO = SO.Instance;
	VMM tempVMM = VMM.Instance;
	RAM tempRam = RAM.Instance;
	CPU tempCpu = CPU.Instance;
	ProcessMy _tempProcess;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		aAmountOfRAMFrames = 3;
		aAmountOfHDDPages = 15;
		
		RAM.Instance.SetCycleSize(aAmountOfRAMFrames);
		
		_pageQueueRefference = new PageQueue(_pageStringRefference1);	
		_tempProcess = new ProcessMy(1,0,_pageQueueRefference.size());
		_tempProcess.GiveFrames(RAM.Instance.subList(0, RAM.Instance.size()));
		_tempProcess.SetPages(_pageQueueRefference);
		
		HDD.Instance.Allocate(_pageQueueRefference);
	}	
	
	@Test
	public void testOPT() {	
		init();
		
		VMM.Instance.SetPagingType(Paging.OPT);
			
		int aRetVal = (int) VMM.Instance.Run(_tempProcess);
		
		System.out.println("PageFaults OPT:"+aRetVal);
		System.out.println("Data For Upper OPT:\n"+StringArrayToChain(_pageStringRefference1));
		assertEquals(9,aRetVal);
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();
	}

	
	@Test
	public void testOPTWithRandomPages() {	
		init();
		
		VMM.Instance.SetPagingType(Paging.OPT);
			
		HDD.Instance.clear();
		HDD.Instance.Allocate(_tempProcess.GetPages());
		SO.Instance.SetIfLocalityRefference(true);
		
		int aRetVal = (int) VMM.Instance.Run(_tempProcess);

		assertTrue(true);
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();
	}
	
	@Test
	public void testLRUForEx4Core1() {	
		init();
		HDD.Instance.clear();	
		
		RAM.Instance.SetCycleSize(2);
		VMM.Instance.SetPagingType(Paging.LRU);
		_pageQueueRefference=new PageQueue(_pageStringRefferenceCore1);
		_tempProcess = new ProcessMy(1,0,_pageQueueRefference.size());
		_tempProcess.GiveFrames(RAM.Instance.subList(0, 2));
		_tempProcess.SetPages(_pageQueueRefference);
		
		HDD.Instance.Allocate(_pageQueueRefference);
				
		int aRetValForCore1 = (int) VMM.Instance.Run(_tempProcess);

		assertEquals(11,aRetValForCore1);
		
		HDD.Instance.clear();	
		CPU.Instance.ClockReset();
	}
	
	@Test
	public void testLRUForEx4Core2() {	
		init();
		HDD.Instance.clear();	
		
		RAM.Instance.SetCycleSize(4);
		VMM.Instance.SetPagingType(Paging.LRU);
		_pageQueueRefference=new PageQueue(_pageStringRefferenceCore2);
		_tempProcess = new ProcessMy(1,0,_pageQueueRefference.size());
		_tempProcess.GiveFrames(RAM.Instance.subList(2, 4));
		_tempProcess.SetPages(_pageQueueRefference);
		
		HDD.Instance.Allocate(_pageQueueRefference);
				
		int aRetValForCore2 = (int) VMM.Instance.Run(_tempProcess);

		assertEquals(8,aRetValForCore2);
		
		HDD.Instance.clear();	
		CPU.Instance.ClockReset();
	}
	
	@Test
	public void testLRUForEx4Core3() {	
		init();
		HDD.Instance.clear();	
		
		RAM.Instance.SetCycleSize(6);
		VMM.Instance.SetPagingType(Paging.LRU);
		_pageQueueRefference=new PageQueue(_pageStringRefferenceCore3);
		_tempProcess = new ProcessMy(1,0,_pageQueueRefference.size());
		_tempProcess.GiveFrames(RAM.Instance.subList(4, 6));
		_tempProcess.SetPages(_pageQueueRefference);
		
		HDD.Instance.Allocate(_pageQueueRefference);
				
		int aRetValForCore3 = (int) VMM.Instance.Run(_tempProcess);

		assertEquals(10,aRetValForCore3);
		
		HDD.Instance.clear();	
		CPU.Instance.ClockReset();
	}
	
	@Test
	public void testLRU() {	
		init();
		
		VMM.Instance.SetPagingType(Paging.LRU);
				
		int aRetVal = (int) VMM.Instance.Run(_tempProcess);
		
		System.out.println("PageFaults LRU:"+aRetVal);
		System.out.println("Data For Upper LRU:"+StringArrayToChain(_pageStringRefference1));
		assertEquals(12,aRetVal);
		
		HDD.Instance.clear();	
		CPU.Instance.ClockReset();
	}
	
	@Test
	public void testLFU() {	
		init();
		HDD.Instance.clear();
		
		VMM.Instance.SetPagingType(Paging.LFU);
		
		_pageQueueRefference = new PageQueue(_pageStringRefference2);	
		_tempProcess.SetPages(_pageQueueRefference);
		
		HDD.Instance.Allocate(_pageQueueRefference);
					
		int aRetVal = (int) VMM.Instance.Run(_tempProcess);
		System.out.println("PageFaults LFU:"+aRetVal);
		System.out.println("Data For Upper LFU:"+StringArrayToChain(_pageStringRefference2));
		
		assertEquals(9,aRetVal);
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();	
	}
	
	@Test
	public void testRAND() {	
		init();
		VMM.Instance.SetPagingType(Paging.RAND);
				
		int aRetVal = (int) VMM.Instance.Run(_tempProcess);
		
		System.out.println("PageFaults RAND:"+aRetVal);
		System.out.println("Data For Upper RAND:"+StringArrayToChain(_pageStringRefference1));
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();		
	}
	@Test
	public void testFIFO() {	
		init();
			
		VMM.Instance.SetPagingType(Paging.FIFO);
		
		int aRetVal = (int) VMM.Instance.Run(_tempProcess);
		
		assertEquals(15,aRetVal);	
		System.out.println("PageFaults FIFO:"+aRetVal);
		System.out.println("Data For Upper RAND:"+StringArrayToChain(_pageStringRefference1));
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEqualsFrameAllocatorWithThreeProcessesAndThreeCores() {	
		init();
			
		VMM.Instance.SetFramingType(Framing.EQUALS);
		CPU.Instance.CreateCores(3);
		RAM.Instance.SetCycleSize(6);
		
		LinkedList<LinkedList<Integer>> _valuesAfterAlgo = new LinkedList<>();
		LinkedList<Integer> _tempInnerList = new LinkedList<>();
		
		CPU.Instance.get(0).SetProcessPerformed(new ProcessMy(1,0,3));
		CPU.Instance.get(1).SetProcessPerformed(new ProcessMy(2,0,4));
		CPU.Instance.get(2).SetProcessPerformed(new ProcessMy(3,0,6));
		
		VMM.AllocateFramesWithAlgo(CPU.Instance,true,true);
		
		CPU.Instance.stream().forEach((core) -> {
			_tempInnerList.clear();
			
			_tempInnerList.add(core.GetProcessPerformed().GetFramesStartIdx());
			_tempInnerList.add(core.GetProcessPerformed().GetFramesEndIdx());
			
			_valuesAfterAlgo.add((LinkedList<Integer>) _tempInnerList.clone());
		}
		);

		assertEquals(_valuesAfterAlgo.get(0).get(0).intValue(),0);
		assertEquals(_valuesAfterAlgo.get(0).get(1).intValue(),2);
		
		assertEquals(_valuesAfterAlgo.get(1).get(0).intValue(),2);
		assertEquals(_valuesAfterAlgo.get(1).get(1).intValue(),4);
		
		assertEquals(_valuesAfterAlgo.get(2).get(0).intValue(),4);
		assertEquals(_valuesAfterAlgo.get(2).get(1).intValue(),6);
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEqualsFrameAllocatorWithTwoProcessesAndThreeCores() {	
		init();
			
		VMM.Instance.SetFramingType(Framing.EQUALS);
		CPU.Instance.CreateCores(3);
		RAM.Instance.SetCycleSize(6);
		
		LinkedList<LinkedList<Integer>> _valuesAfterAlgo = new LinkedList<>();
		LinkedList<Integer> _tempInnerList = new LinkedList<>();
		
		CPU.Instance.get(0).SetProcessPerformed(new ProcessMy(1,0,3));
		CPU.Instance.get(1).SetProcessPerformed(new ProcessMy(2,0,4));
		
		VMM.AllocateFramesWithAlgo(CPU.Instance,true,true);
		
		CPU.Instance.stream().forEach((core) -> {
			_tempInnerList.clear();
			
			_tempInnerList.add(core.GetProcessPerformed().GetFramesStartIdx());
			_tempInnerList.add(core.GetProcessPerformed().GetFramesEndIdx());
			
			_valuesAfterAlgo.add((LinkedList<Integer>) _tempInnerList.clone());
		}
		);

		assertEquals(_valuesAfterAlgo.get(0).get(0).intValue(),0);
		assertEquals(_valuesAfterAlgo.get(0).get(1).intValue(),3);
		
		assertEquals(_valuesAfterAlgo.get(1).get(0).intValue(),3);
		assertEquals(_valuesAfterAlgo.get(1).get(1).intValue(),6);
		
		HDD.Instance.clear();
		CPU.Instance.ClockReset();
	}

	@Test
	public void testCompareFrameAlgo() {	
		assertEquals(NullAlgo.class,VMM.GetFramingAlgo().getClass());
	}
	
	private String StringArrayToChain(String [] aArray){
		List<String> tempRetList = Arrays.asList(aArray);
		
		String retVal = tempRetList.stream()
				 .collect(Collectors.joining(","));
		
		return retVal;
	}
	
	private void init() {
		
		RAM.Instance.Reset();
		CPU.Instance.ClockReset();
	}	
	
	
}
