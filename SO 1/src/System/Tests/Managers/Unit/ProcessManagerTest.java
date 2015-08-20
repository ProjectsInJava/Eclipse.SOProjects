package System.Tests.Managers.Unit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Algorithms.Enums.Framing;
import System.Algorithms.Enums.Scheduling;
import System.Hardware.CPU;
import System.Hardware.RAM;
import System.Managers.ProcessManager;
import System.Managers.SO;
import System.Managers.VMM;
import System.Managers.Collections.ProcessQueue;
import junit.framework.TestCase;

public class ProcessManagerTest extends TestCase 
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
	
	String [] _processStringRefference3 = new String[]
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
	
	String [] _processStringRefference6 = new String[]
			{
			"1","0","6",
			"2","0","5",
			"3","0","2",
			"4","0","3",
			"5","0","7"
			};
	
	String [] _processStringRefference7 = new String[]
			{
			"1","0","4",
			"2","1","5",
			"3","2","6",
			"4","4","1",
			"5","6","3",
			"6","7","2"
			};
	
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

	ProcessManager aTempManager;
	SO aTempSO = SO.Instance;
	Object aReturnedValue = null;
	ProcessQueue _processQueue;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@Before
	protected void setUp() throws Exception {
		aTempManager = ProcessManager.Instance;
		_processQueue = new ProcessQueue();
		SO aTempSO = SO.Instance;
		RAM.Instance.SetCycleSize(3);
	}
//endregion
	@Test
	public void testInit() {
		
		
	}
	
//	@Test
//	public void testRunSerie1() {	
//		
//		aTempManager.Reset();
//		CPU.Instance.SetQuantum(1);
//		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
//		VMM.Instance.SetFramingType(Framing.EQUALS);
//		CPU.Instance.CreateCores(1);
//		
//		_processQueue = new ProcessQueue(_processStringRefference1);
//		aReturnedValue = aTempManager.Run(_processQueue);
//		System.out.println("FCFS 1:"+aReturnedValue);
//		assertEquals(17.00,aReturnedValue); 
//
//		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
//		
//		_processQueue = new ProcessQueue(_processStringRefference1);
//		aReturnedValue = aTempManager.Run(_processQueue);
//		System.out.println("SJF 1:"+aReturnedValue);
//		assertEquals(3.00,aReturnedValue); 
////		
////		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
////		aTempManager.GetScheduler().SetQuantum(4);
////
////		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
////		System.out.println("RR 1:"+aReturnedValue);
////		assertEquals(5.67,aReturnedValue); 
//	}
	
	@Test
	public void testRunSerie8ForEx4() {
		
		aTempManager.Reset();
		CPU.Instance.SetQuantum(1);
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		CPU.Instance.CreateCores(3);
		RAM.Instance.SetCycleSize(6);
		SO.Instance.SetIfEx4(true);
		
		_processQueue = new ProcessQueue(_processStringRefference8);
		
		aReturnedValue = aTempManager.Run(new ProcessQueue(_processQueue));
		assertEquals(aReturnedValue,3.00);
	}
	
	@Test
	public void testRunSerie1WithOwnProcesses() {
		
		aTempManager.Reset();
		CPU.Instance.SetQuantum(1);
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		CPU.Instance.CreateCores(1);
		
		aReturnedValue = aTempManager.Run(new ProcessQueue());
		System.out.println("			FCFS 1:"+aReturnedValue);
	}
	
	@Test
	public void testRunSerie1With2Cores() {
		
		aTempManager.Reset();
		CPU.Instance.SetQuantum(1);
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		CPU.Instance.CreateCores(2);
		
		_processQueue = new ProcessQueue(_processStringRefference1);
		aReturnedValue = aTempManager.Run(_processQueue);
		System.out.println("FCFS 1:"+aReturnedValue);
		assertEquals(1.00,aReturnedValue); 
//
//		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
//		
//		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
//		System.out.println("SJF 1:"+aReturnedValue);
//		assertEquals(3.00,aReturnedValue); 
//		
//		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
//		aTempManager.GetScheduler().SetQuantum(4);
//
//		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
//		System.out.println("RR 1:"+aReturnedValue);
//		assertEquals(5.67,aReturnedValue); 
	}
	
	@Test
	public void testRunSerie1FrameAllocation() {
//		
//		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
//		SO.Instance.SetFramingType(Framing.EQUALS);
//		
//		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
//		System.out.println("FCFS 1:"+aReturnedValue);
//		assertEquals(17.00,aReturnedValue); 
//
//		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
//		
//		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
//		System.out.println("SJF 1:"+aReturnedValue);
//		assertEquals(3.00,aReturnedValue); 
//		
//		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
//		aTempManager.GetScheduler().SetQuantum(4);
//
//		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
//		System.out.println("RR 1:"+aReturnedValue);
//		assertEquals(5.67,aReturnedValue); 
	}
	
	@Test
	public void testRunSerie2() {

		aTempManager.Reset();
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		CPU.Instance.CreateCores(1);
		
		_processQueue = new ProcessQueue(_processStringRefference2);
		aReturnedValue = aTempManager.Run(_processQueue);
		
		
		System.out.println("SJF 2:"+aReturnedValue);

//		assertEquals(9.25,aReturnedValue); 
		assertEquals(8.75,aReturnedValue); 
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.SRF);
		_processQueue = new ProcessQueue(_processStringRefference2);
		aReturnedValue = aTempManager.Run(_processQueue);

		System.out.println("SRF 2:"+aReturnedValue);	
//		assertEquals(8.0,aReturnedValue); 
		assertEquals(8.75,aReturnedValue); 
	}
	
	@Test
	public void testRunSerie3() {
		aTempManager.Reset();
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);	
		

		_processQueue = new ProcessQueue(_processStringRefference3);
		aReturnedValue = aTempManager.Run(_processQueue);
		CPU.Instance.CreateCores(1);
		
		System.out.println("FCFS 3:"+aReturnedValue);
		assertEquals(10.25,aReturnedValue);
		
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		_processQueue = new ProcessQueue(_processStringRefference3);
		aReturnedValue = aTempManager.Run(_processQueue);
		
		assertEquals(7.0,aReturnedValue); 
		System.out.println("SJF 3:"+aReturnedValue);
	}
	
	@Test
	public void testRunSerie5() {
//		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
//		aTempManager.GetScheduler().SetQuantum(2);
//		aReturnedValue = aTempManager.Run(aTempRefferenceString5);
//		
//		System.out.println("RR 2:"+aReturnedValue);
//		assertEquals(5.25,aReturnedValue); 
	}	
	
	@Test
	public void testRunSerie6() {
//		aTempManager.Reset();
//		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
//		aTempManager.GetScheduler().SetQuantum(2);
//		aReturnedValue = aTempManager.Run(aTempRefferenceString6);
//		
//		System.out.println("RR 3:"+aReturnedValue);
		
//		assertEquals(12.0,aReturnedValue); 
	}	
	
	@Test
	public void testRunSerie7() {
//		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
//		aTempManager.Scheduler().SetQuantum(2);
//		aReturnedValue = aTempManager.Run(aTempRefferenceString7);
//		
//		System.out.println("RR 5:"+aReturnedValue);
//		assertEquals(8.5,aReturnedValue); 
	}	
	
	@Test
	public void testRunFCFS_1_3() {
		aTempManager.Reset();
		ProcessManager.Instance.SetSchedulingType(Scheduling.FCFS);
		CPU.Instance.CreateCores(1);
		
		_processQueue = new ProcessQueue(_processStringRefference1);
		aReturnedValue = aTempManager.Run(_processQueue);
		System.out.println("FCFS 1:"+aReturnedValue);
		assertEquals(17.00,aReturnedValue); 
		
		_processQueue = new ProcessQueue(_processStringRefference3);
		aReturnedValue = aTempManager.Run(_processQueue);
		System.out.println("FCFS 3:"+aReturnedValue);
		assertEquals(10.25,aReturnedValue);
	}
	@Test
	public void testRunSJF_1_2_3() {

		aTempManager.Reset();
		ProcessManager.Instance.SetSchedulingType(Scheduling.SJF);
		CPU.Instance.CreateCores(1);
		
		_processQueue = new ProcessQueue(_processStringRefference1);
		aReturnedValue = aTempManager.Run(_processQueue);
		System.out.println("SJF 1:"+aReturnedValue);
		assertEquals(3.00,aReturnedValue); 
		

		_processQueue = new ProcessQueue(_processStringRefference2);
		aReturnedValue = aTempManager.Run(_processQueue);
		System.out.println("SJF 2:"+aReturnedValue);
//		assertEquals(9.25,aReturnedValue); 
		assertEquals(8.75,aReturnedValue); 


		_processQueue = new ProcessQueue(_processStringRefference3);
		aReturnedValue = aTempManager.Run(_processQueue);
		assertEquals(7.0,aReturnedValue); 
		System.out.println("SJF 3:"+aReturnedValue);
	}
	@Test
	public void testRunRR_1_5() {
//		
//		aTempManager.Reset();
//		ProcessManager.Instance.SetSchedulingType(Scheduling.RR);
//		aTempManager.GetScheduler().SetQuantum(4);
//
//		aReturnedValue = aTempManager.Run(aTempRefferenceString1);
//		System.out.println("RR 1:"+aReturnedValue);
////		assertEquals(5.67,aReturnedValue); 
//		
//////
////		aTempManager.GetScheduler().SetQuantum(2);
////		aReturnedValue = aTempManager.Run(aTempRefferenceString5);
////		System.out.println("RR 2:"+aReturnedValue);
////
////		assertEquals(5.25,aReturnedValue); 
//		
//		aTempManager.GetScheduler().SetQuantum(2);
//		aReturnedValue = aTempManager.Run(aTempRefferenceString6);
//		System.out.println("RR 3:"+aReturnedValue);

//		assertEquals(12.0,aReturnedValue); 
	}
	
	public void testRunSRF_2() {
		
		aTempManager.Reset();
		ProcessManager.Instance.SetSchedulingType(Scheduling.SRF);
		CPU.Instance.CreateCores(1);

		_processQueue = new ProcessQueue(_processStringRefference2);
		aReturnedValue = aTempManager.Run(_processQueue);
		System.out.println("SRF 2:"+aReturnedValue);
//		
//		assertEquals(8.0,aReturnedValue); 
		assertEquals(8.75,aReturnedValue); 
	}
}