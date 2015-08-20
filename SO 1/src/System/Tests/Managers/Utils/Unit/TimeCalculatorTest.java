package System.Tests.Managers.Utils.Unit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Managers.Collections.ProcessQueue;
import System.Managers.Utils.MetricsCalculator;
import junit.framework.TestCase;

public class TimeCalculatorTest extends TestCase 
{
	MetricsCalculator _expectTimeCalculator;
	
	String [] aTempRefferenceString = new String[]
			{
			"1","0","24",
			"2","0","3",
			"3","0","3"
			};
	
	ProcessQueue aTempWaitingQueue;
	double aEllapsedTime= 10.00;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception 
	{
		super.setUp();
		aTempWaitingQueue = new ProcessQueue(aTempRefferenceString);
		_expectTimeCalculator = new MetricsCalculator();
		
	
		aTempWaitingQueue.stream().forEach(process -> process.IncreaseTimeWaiting((long) aEllapsedTime));
		_expectTimeCalculator.addAll(aTempWaitingQueue);
	}

	@Test
	public void test() 
	{
		assertNotNull(_expectTimeCalculator);
	}
	
	@Test
	public void testFIFO() 
	{
		assertEquals(aEllapsedTime,_expectTimeCalculator.GetAverage());
	}
}
