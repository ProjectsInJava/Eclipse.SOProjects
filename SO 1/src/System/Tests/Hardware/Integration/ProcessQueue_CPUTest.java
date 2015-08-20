package System.Tests.Hardware.Integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Diagnostics.ProcessMy;
import System.Hardware.CPU;
import System.Managers.Collections.ProcessQueue;
import junit.framework.TestCase;

public class ProcessQueue_CPUTest extends TestCase {
	
	CPU _cpu = CPU.Instance;
	ProcessQueue _processQueue;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		_processQueue = new ProcessQueue();
		_cpu.CreateCores(1);
	}

	@Test
	public void testAllocateProcessFromQueueToCore() {
		
		ProcessMy _tempProcess1 = new ProcessMy(1,1,1);
		ProcessMy _tempProcess2 = new ProcessMy(2,2,2);
		ProcessMy _tempProcess3 = new ProcessMy(3,3,3);
		
		_processQueue.add(_tempProcess1);
		_processQueue.add(_tempProcess2);
		_processQueue.add(_tempProcess3);
		
		_cpu.GetCore(0).SetProcessPerformed(_processQueue.get(0));
		
		_cpu.stream().forEach(core -> core.Tick());
		
		assertTrue(_cpu.GetCore(0).GetProcessPerformed().equals(new ProcessMy(1,1,0)));
		assertTrue(_processQueue.get(0).equals(new ProcessMy(1,1,0)));
		assertTrue(_processQueue.get(0).equals(_cpu.GetCore(0).GetProcessPerformed()));		
	}

}
