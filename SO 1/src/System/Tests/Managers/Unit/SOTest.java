package System.Tests.Managers.Unit;


import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import System.Algorithms.Enums.Framing;
import System.Algorithms.Enums.Paging;
import System.Algorithms.Enums.Scheduling;
import System.Hardware.CPU;
import System.Hardware.HDD;
import System.Hardware.RAM;
import System.Managers.ProcessManager;
import System.Managers.SO;
import System.Managers.VMM;
import System.Managers.Collections.PageQueue;
import System.Managers.Collections.ProcessQueue;
import junit.framework.TestCase;

import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SOTest extends TestCase 
{
// region ********************************************************** TEMP_REFERENCE_STRINGS_&_INIT **********************************************************
	String [] _processStringRefference1 = new String[]
			{
			"1","0","24",
			"2","0","3",
			"3","0","3"
			};
	
	String [] _processStringRefference2 = new String[]
			{
			"1","0","8",
			"2","1","4",
			"3","2","9",
			"4","3","5"
			};
	
	String [] _processStringRefference4 = new String[]
			{
			"1","0","6",
			"2","0","8",
			"3","0","7",
			"4","0","3"
			};
	
	String [] _processStringRefference5 = new String[]
			{
			"1","0","8",
			"2","2","5",
			"3","4","1",
			"4","5","2"
			};
	// region **************************** VALUES_FOR_EXERCISE_4 ***************************************************
	
	String [] _processStringRefference8 = new String[]
			{
			"1","0","4",
			"2","1","3",
			"3","2","5",
			"4","1","5",
			"5","1","3",
			"6","1","4",
			"7","2","3",
			"8","2","5",
			"9","3","3",
			"10","4","4"	
			};
	
	String[] _pageStringRefferenceProcess1 = new String[]	{
			"1",
			"2",
			"3",
			"2",
	};
	
	String[] _pageStringRefferenceProcess7 = new String[]	{
			"3",
			"2",
			"1",
	};
	
	String[] _pageStringRefferenceProcess10 = new String[]	{
			"4",
			"3",
			"1",
			"4",
	};
	
	String[] _pageStringRefferenceProcess8 = new String[]	{
			"3",
			"2",
			"3",
			"1",
			"1"
	};
	String[] _pageStringRefferenceProcess2 = new String[]	{
			"2",
			"1",
			"3",
	};
	
	String[] _pageStringRefferenceProcess9 = new String[]	{
			"2",
			"3",
			"1",
	};
	
	String[] _pageStringRefferenceProcess4 = new String[]	{
			"2",
			"1",
			"1",
			"3",
			"4",
	};
	
	String[] _pageStringRefferenceProcess5 = new String[]	{
			"4",
			"4",
			"3",
	};
	
	String[] _pageStringRefferenceProcess6 = new String[]	{
			"1",
			"3",
			"2",
			"4",
	};
	
	String[] _pageStringRefferenceProcess3 = new String[]	{
			"1",
			"3",
			"2",
			"4",
			"3",
	};
	
	PageQueue _globalPageRefference;
	
	// endregion
	
	String [] aOwnRefferenceString ;
	SO aSO;
	VMM aVMM = VMM.Instance;

	HDD tempHDD = HDD.Instance;
	RAM tempRam = RAM.Instance;
	CPU tempCPU = CPU.Instance;
	
	int aAmountOfRAMFrames;
	int aAmountOfCPUCores;
	int aAmountOfProcessesPerCore;
	int aAmountOfOwnProcessesToGenerate;
	
	List<Object> _outRegister;
	
	String[] _pageStringRefference = new String[]	{
			"7","0","1","2","0","3","0","4","2","3","0","3","2","1","2","0","1","7",
			"0","1"
	};

	@BeforeClass	
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@Before
	protected void setUp() throws Exception {
		aSO = SO.Instance;
		
		_globalPageRefference = new PageQueue();
		
		aAmountOfRAMFrames = 3;
		aAmountOfProcessesPerCore = 1;
		aAmountOfCPUCores = 1;
		
		aAmountOfOwnProcessesToGenerate = 40;	
		//// OWN AMOUNT OF PROCESSES TO GENERATE
		
//		aOwnRefferenceString = new String[aAmountOfOwnProcessesToGenerate*3];	//// OWN PROCESSES STRING REFFERENCE
//		generateOwnProcessesStringRefference();

		CPU.Instance.CreateCores(aAmountOfCPUCores);
		RAM.Instance.SetCycleSize(aAmountOfRAMFrames);
		
		// KAZDY PROCES MA STRONY STWORZONE WEDLUG ZASADY LOKALNOSCI ODWOLAN BADZ Poprzez wlasny ciag
	}
//endregion
	@Test
	public void testInit() {
		
		assertNotNull(aSO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSO_SJF_Schedule_LRU_Paging_Equals_Framing_Ex4() {
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.LRU);
		VMM.Instance.SetFramingType(Framing.EQUALS);
		
		CPU.Instance.CreateCores(3);
		RAM.Instance.SetCycleSize(6);
		
		SO.Instance.SetProcessQueue(_processStringRefference8);
		SO.Instance.GetProcessQueue().get(0).SetPages(new PageQueue(_pageStringRefferenceProcess1));
		SO.Instance.GetProcessQueue().get(1).SetPages(new PageQueue(_pageStringRefferenceProcess2));
		SO.Instance.GetProcessQueue().get(2).SetPages(new PageQueue(_pageStringRefferenceProcess3));
		SO.Instance.GetProcessQueue().get(3).SetPages(new PageQueue(_pageStringRefferenceProcess4));
		SO.Instance.GetProcessQueue().get(4).SetPages(new PageQueue(_pageStringRefferenceProcess5));
		SO.Instance.GetProcessQueue().get(5).SetPages(new PageQueue(_pageStringRefferenceProcess6));
		SO.Instance.GetProcessQueue().get(6).SetPages(new PageQueue(_pageStringRefferenceProcess7));
		SO.Instance.GetProcessQueue().get(7).SetPages(new PageQueue(_pageStringRefferenceProcess8));
		SO.Instance.GetProcessQueue().get(8).SetPages(new PageQueue(_pageStringRefferenceProcess9));
		SO.Instance.GetProcessQueue().get(9).SetPages(new PageQueue(_pageStringRefferenceProcess10));
		
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess1));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess2));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess5));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess7));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess9));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess6));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess10));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess3));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess8));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess4));
		
		HDD.Instance.Allocate(_globalPageRefference);
		SO.Instance.SetIfEx4(true);
		
		_outRegister = (List<Object>) SO.Instance.Run(new ProcessQueue());
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(3.0,_outRegister.get(0));
		assertEquals(25.0,_outRegister.get(1)); 
		// jest 25 a nie 29 poniewa¿ przeglada czasy rejestracji po wszystkich stronach z RAM
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSO_SJF_Schedule_LRU_Paging_Proportional_Framing_Ex4() {
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.LRU);
		VMM.Instance.SetFramingType(Framing.PROPORTIONAL);
		
		CPU.Instance.CreateCores(3);
		RAM.Instance.SetCycleSize(6);
		
		SO.Instance.SetProcessQueue(_processStringRefference8);
		SO.Instance.GetProcessQueue().get(0).SetPages(new PageQueue(_pageStringRefferenceProcess1));
		SO.Instance.GetProcessQueue().get(1).SetPages(new PageQueue(_pageStringRefferenceProcess2));
		SO.Instance.GetProcessQueue().get(2).SetPages(new PageQueue(_pageStringRefferenceProcess3));
		SO.Instance.GetProcessQueue().get(3).SetPages(new PageQueue(_pageStringRefferenceProcess4));
		SO.Instance.GetProcessQueue().get(4).SetPages(new PageQueue(_pageStringRefferenceProcess5));
		SO.Instance.GetProcessQueue().get(5).SetPages(new PageQueue(_pageStringRefferenceProcess6));
		SO.Instance.GetProcessQueue().get(6).SetPages(new PageQueue(_pageStringRefferenceProcess7));
		SO.Instance.GetProcessQueue().get(7).SetPages(new PageQueue(_pageStringRefferenceProcess8));
		SO.Instance.GetProcessQueue().get(8).SetPages(new PageQueue(_pageStringRefferenceProcess9));
		SO.Instance.GetProcessQueue().get(9).SetPages(new PageQueue(_pageStringRefferenceProcess10));
		
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess1));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess2));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess5));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess7));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess9));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess6));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess10));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess3));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess8));
		_globalPageRefference.addAll(new PageQueue(_pageStringRefferenceProcess4));
		
		HDD.Instance.Allocate(_globalPageRefference);
		SO.Instance.SetIfEx4(true);
		
		_outRegister = (List<Object>) SO.Instance.Run(new ProcessQueue());
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(3.0,_outRegister.get(0));
		assertEquals(25.0,_outRegister.get(1)); // jest 25
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_OPT_Paging_OwnPages_With_EQuals_AndProcesses() {
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.OPT);
		VMM.Instance.SetFramingType(Framing.EQUALS);
		
		CPU.Instance.CreateCores(2);
		RAM.Instance.SetCycleSize(15);
		
		SO.Instance.SetProcessQueue(_processStringRefference8);
		
		SO.Instance.SetPageQueue(new PageQueue());
		
		_outRegister = (List<Object>) SO.Instance.Run(new ProcessQueue());
		
		
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
//		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_OPT_Paging__Own_With_Proportional_AndProcesses() {
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.OPT);
		VMM.Instance.SetFramingType(Framing.PROPORTIONAL);
		CPU.Instance.CreateCores(2);
		RAM.Instance.SetCycleSize(15);
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(new PageQueue());
		
		_outRegister = (List<Object>) SO.Instance.Run(new ProcessQueue());
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
//		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_OPT_Paging_RandomPagesAndProcesses() {
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.OPT);
		CPU.Instance.CreateCores(4);
		RAM.Instance.SetCycleSize(150);
		
//		SO.Instance.SetPageQueue(_pageStringRefference);
		
		_outRegister = (List<Object>) SO.Instance.Run(new ProcessQueue());
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
//		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_FIFO_Paging() {
		
		System.out.println("\n********************************* FCFS **************************************");
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.FIFO);
		VMM.Instance.SetFramingType(Framing.EQUALS);
		SO.Instance.SetIfLocalityRefference(true);
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+ VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);

		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(15,_outRegister.get(1));	
	}
	

	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_OPT_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.OPT);	
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));	
		
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_LRU_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.LRU);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+"		 "+_pageFaultsSize);
	
		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(12,_outRegister.get(1));	

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_LFU_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.LFU);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(17.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingFCFS_RAND_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		VMM.Instance.SetPagingType(Paging.RAND);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(17.0,_outRegister.get(0));
		
		
		
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingSJF_FIFO_Paging() {
		System.out.println("\n********************************* SJF **************************************");
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.FIFO);
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);

		assertEquals(3.0,_outRegister.get(0));
//		assertEquals(15,_outRegister.get(1));	

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingSJF_OPT_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.OPT);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(3.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));	
		
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingSJF_LRU_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.LRU);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+"		 "+_pageFaultsSize);
	
		assertEquals(3.0,_outRegister.get(0));
//		assertEquals(12,_outRegister.get(1));	

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingSJF_LFU_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.LFU);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(3.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingSJF_RAND_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		VMM.Instance.SetPagingType(Paging.RAND);		
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
		assertEquals(3.0,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingRR_FIFO_Pagin() {
		System.out.println("\n********************************* RR *********************************");
		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
		VMM.Instance.SetPagingType(Paging.FIFO);
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);

//		assertEquals(5.67,_outRegister.get(0));
//		assertEquals(15,_outRegister.get(1));	

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingRR_OPT_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
		VMM.Instance.SetPagingType(Paging.OPT);
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
//		assertEquals(5.67,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));	
		
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingRR_LRU_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
		VMM.Instance.SetPagingType(Paging.LRU);
				
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+"		 "+_pageFaultsSize);
//	
//		assertEquals(5.67,_outRegister.get(0));
//		assertEquals(12,_outRegister.get(1));	

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingRR_LFU_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
		VMM.Instance.SetPagingType(Paging.LFU);
		
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
//		assertEquals(5.67,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSOFullWorkingRR_RAND_Paging() {
		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
		VMM.Instance.SetPagingType(Paging.RAND);
				
		SO.Instance.SetProcessQueue(_processStringRefference1);
		SO.Instance.SetPageQueue(_pageStringRefference);
			
		_outRegister = (List<Object>) SO.Instance.Run(new Integer(0));
		
		String _averageAccessSize = "AverageAccessTime:"+_outRegister.get(0).toString();
		String _pageFaultsSize = "PageFaults:"+_outRegister.get(1).toString();
		
		System.out.println("\nScheduling:"+ProcessManager.Instance.GetSchedAlgo()+"\n" + _averageAccessSize);
		System.out.println("Paging:		"+VMM.GetFramingAlgo()+" 		"+_pageFaultsSize);
	
//		assertEquals(5.67,_outRegister.get(0));
//		assertEquals(9,_outRegister.get(1));
		
	}
	
	@Test
	public void testSOFullWorkingOPTPaging() {
////		System.out.println(CPU.Instance.GetCpuClock().toString());
//		VMM.Instance.SetPagingType(Paging.OPT);
//		
//		_outRegister = 	(List<Object>) SO.Instance.Run(new Integer(0));
//	
//		System.out.println("AverageAccessTime:"+_outRegister.get(1).toString());
//		System.out.println("PageFaults:"+_outRegister.get(0).toString());
//		
//		assertEquals(10.25,SO.Instance._outRegister.get(1));
//		assertEquals(9,SO.Instance._outRegister.get(0));
	}
}
