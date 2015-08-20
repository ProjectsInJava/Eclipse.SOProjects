package System.Tests.Old;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import System.Algorithms.Enums.Scheduling;
import System.Diagnostics.ProcessMy;
import System.Hardware.CPU;
import System.Managers.ProcessManager;
import System.Managers.Collections.ProcessQueue;
import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchedulerProcessTest extends TestCase {

	// region ******************************** MEMBERS ******************************** 
	String [] aTempRefferenceString1 = new String[]
			{
			"1","0","24",
			"2","0","3",
			"3","0","3"
			};
	
	String [] aTempRefferenceString2 = new String[]
			{
			"1","0","24",
			"2","1","3",
			"3","1","3"
			};
	ProcessManager aTempScheduler;
	ProcessQueue aTempWaitingQueue;
	CPU _tempCpu = CPU.Instance;

	// endregion
	// region ******************************** INITIALIZERS ******************************** 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	@Before
	protected void setUp() throws Exception {
		aTempWaitingQueue = new ProcessQueue(aTempRefferenceString2);
		aTempScheduler = ProcessManager.Instance;
		_tempCpu.CreateCores(1);
	}
	@Test
	public void testInit() {
		assertNotNull(aTempScheduler);
	}	
	// endregion
	// region ******************************** QUEUE_SCHEDULINGS ********************************
	@SuppressWarnings("unchecked")
	@Test
	public void testFIFORun() {
		ProcessQueue aDestinedScheduledProcess = new ProcessQueue();
		aDestinedScheduledProcess.add(new ProcessMy(1,0,24));
		
		aTempScheduler.SetSchedulingType(Scheduling.FCFS);
		
		ProcessQueue aScheduledProcess = new ProcessQueue();
		aScheduledProcess.addAll((Collection<? extends ProcessMy>) aTempScheduler.Run(aTempWaitingQueue));
		assertTrue(aScheduledProcess.equals(aDestinedScheduledProcess));
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSJFRun() {
		
		ProcessQueue aDestinedScheduledProcess = new ProcessQueue();
		aDestinedScheduledProcess.add(new ProcessMy(1,0,24));		
		
		ProcessQueue aScheduledProcess = new ProcessQueue();
		aScheduledProcess.addAll((Collection<? extends ProcessMy>) aTempScheduler.Run(aTempWaitingQueue));
		assertTrue(aScheduledProcess.equals(aDestinedScheduledProcess));
	}
	
	@Test
	public void testTimeReadyToWork() {
		_tempCpu.ClockReset();
		_tempCpu.Tick();
		
		LinkedList<Instant > _tempTimes = new LinkedList<>();
		_tempTimes.add(Instant.ofEpochSecond(0));
		_tempTimes.add(Instant.ofEpochSecond(0));
		_tempTimes.add(Instant.ofEpochSecond(1));
		_tempTimes.add(Instant.ofEpochSecond(2));
		_tempTimes.add(Instant.ofEpochSecond(3));
		
		List<Instant > _tempFiltered = _tempTimes.stream()
				.filter(ProcessManager.Instance.IsReadyToWork())
				.collect(Collectors.toList());
		
		System.out.println(_tempFiltered.toString());
				
	}
//	ReadyToWork
	@SuppressWarnings("unchecked")
	@Test
	public void testSRFRun() {
		
		ProcessQueue aDestinedScheduledProcess = new ProcessQueue();
		aDestinedScheduledProcess.add(new ProcessMy(1,0,24));		
		
		aTempScheduler.SetSchedulingType(Scheduling.SRF);
		
		ProcessQueue aScheduledProcess = new ProcessQueue();
		aScheduledProcess.addAll((Collection<? extends ProcessMy>) aTempScheduler.Run(aTempWaitingQueue));
		assertTrue(aScheduledProcess.equals(aDestinedScheduledProcess));
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRRRun() {
				
		ProcessQueue aDestinedScheduledProcess = new ProcessQueue();
		aDestinedScheduledProcess.add(new ProcessMy(1,0,24));	
		
		aTempScheduler.SetSchedulingType(Scheduling.RR);
		
		ProcessQueue aScheduledProcess = new ProcessQueue();
		aScheduledProcess.addAll((Collection<? extends ProcessMy>) aTempScheduler.Run(aTempWaitingQueue));
		assertTrue(aScheduledProcess.equals(aDestinedScheduledProcess));
		
	}
	@Test
	public void testSetTypeScheduling() {
		aTempScheduler.SetSchedulingType(Scheduling.SJF);
		assertEquals(Scheduling.SJF,aTempScheduler.GetSchedAlgo());
	}
	// endregion
}
