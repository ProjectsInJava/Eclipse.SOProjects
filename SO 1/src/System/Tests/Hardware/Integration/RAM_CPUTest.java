package System.Tests.Hardware.Integration;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import System.Collections.Generic.GenQueue;
import System.Diagnostics.ProcessMy;
import System.Hardware.CPU;
import System.Hardware.RAM;
import System.Managers.Collections.PageQueue;
import System.Managers.Collections.ProcessQueue;
import System.Managers.Elementary.Page;
import System.Managers.Utils.RecurrenceGenerator;
import junit.framework.TestCase;

public class RAM_CPUTest extends TestCase {
	
	CPU _tempCPU = CPU.Instance;
	RAM _tempRAM = RAM.Instance;
	Instant _tempClock;
	ProcessMy _tempProcess1;
	ProcessMy _tempProcess2;
	
	Page _tempPage ;
	GenQueue<Instant> _tempRegisterTimes;
	GenQueue<Instant> _desireRegisterTimes;
	
	RecurrenceGenerator _locality;
	LinkedList<Integer> _generatedValuesForProcessQueue;
	LinkedList<Integer> _generatedValuesForPageQueue;
	
	PageQueue _tempPageQueue;
	ProcessQueue _tempProcessQueue;
	
	int _aSizeOfPagesToGenerate;
	int _aIngredient;
	int aAmountOfCores;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		_tempProcessQueue = new ProcessQueue();
		_tempPageQueue = new PageQueue();
		
		
		aAmountOfCores = 2;
		_aIngredient = 3;
		
		_tempCPU.CreateCores(aAmountOfCores);
		_tempPage = new Page(1);
		
		_tempRegisterTimes = new GenQueue<Instant>();
		_desireRegisterTimes = new GenQueue<Instant>();
		
		_tempRegisterTimes.add(Instant.ofEpochSecond(0));
		_tempRegisterTimes.add(Instant.ofEpochSecond(1));
		_tempRegisterTimes.add(Instant.ofEpochSecond(2));
		
		_tempPage.SetRegisterTimes(_tempRegisterTimes);
		

		_generatedValuesForProcessQueue = new LinkedList<>();
		_tempPageQueue = new PageQueue();
			
	}	

	@Test
	public void testResetFrequency() {
		
		_desireRegisterTimes.add(Instant.ofEpochSecond(1));
		_desireRegisterTimes.add(Instant.ofEpochSecond(2));
		
		GenQueue<Instant> pageRegTimes = _tempPage.GetTimeRegister();
		
		_tempCPU.Tick();
		_tempCPU.Tick();
		
		_tempPage.ResetFrequency();
		
		pageRegTimes.removeAll(_desireRegisterTimes);
		
		assertEquals(0,pageRegTimes.size());		
	}
	
	@Test
	public void testAllocateProcessesToCpuAndTheirFramesToRam() {
		
		_tempCPU.CreateCores(aAmountOfCores);
				
		_tempRAM.SetCycleSize(8);
		
		GenerateProcesses(aAmountOfCores);
		
		_aSizeOfPagesToGenerate= _tempProcessQueue.SummaryTimeExecute();
		
		GeneratePages(_aSizeOfPagesToGenerate);
		
		_tempProcessQueue.AddPages(_tempPageQueue);
		
		boolean _isAllocatedDone = _tempCPU.AllocateProcesses(_tempProcessQueue);
		boolean _isFramesGivingDone = true;
				
		assertTrue(_isAllocatedDone);
		
		int aIdxOfStartAndEndOfRam = 0;
		
		for(int i = 0 ; i <aAmountOfCores;i++){
				_isFramesGivingDone &= CPU.Instance.GetCore(i).GetProcessPerformed()
					.GiveFrames(RAM.Instance.subList(aIdxOfStartAndEndOfRam,aIdxOfStartAndEndOfRam+=4));
		}
		
		assertTrue(_isFramesGivingDone);

	}
	
	
	@Test
	public void testAllocateProcessesToCpuAndTheirFramesToRamEquals() {
		
		_tempCPU.CreateCores(aAmountOfCores);
				
		_tempRAM.SetCycleSize(8);
		
		GenerateProcesses(aAmountOfCores);
		
		_aSizeOfPagesToGenerate= _tempProcessQueue.SummaryTimeExecute();
		
		GeneratePages(_aSizeOfPagesToGenerate);
		
		_tempProcessQueue.AddPages(_tempPageQueue);
		
		boolean _isAllocatedDone = _tempCPU.AllocateProcesses(_tempProcessQueue);
		boolean _isFramesGivingDone = true;
				
		assertTrue(_isAllocatedDone);
		
		int aIdxOfStartAndEndOfRam = 0;
		
		for(int i = 0 ; i <aAmountOfCores;i++)
		{
				_isFramesGivingDone &= CPU.Instance.GetCore(i).GetProcessPerformed()
					.GiveFrames(RAM.Instance.subList(aIdxOfStartAndEndOfRam,aIdxOfStartAndEndOfRam+=4));
		}
		
		LinkedList<Integer> _toTestAmountOfFrames = new LinkedList<>();
		_toTestAmountOfFrames.add(4);
		_toTestAmountOfFrames.add(4);
		
		List<Integer> _testedFrames = CPU.Instance
				.stream()
				.map(core -> core.GetProcessPerformed().GetFrames().size())
				.collect(Collectors.toList());
		
		assertTrue(_testedFrames.containsAll(_toTestAmountOfFrames));
		assertTrue(_isFramesGivingDone);
	}
	
	@Test
	public void testFunction() {
	
		Instant _tempInst = Page.IntToInstant.apply(new Integer(7));
		System.out.println(_tempInst);
	}
	
	private void GenerateProcesses(int aAmountOfProcesses){
		
		_generatedValuesForProcessQueue = RecurrenceGenerator.GenerateForProcesses(aAmountOfProcesses,_aIngredient,1);

		_tempProcessQueue = new ProcessQueue(_generatedValuesForProcessQueue);	

		for (int i = 0; i < aAmountOfProcesses; i++) {
			_tempProcessQueue.get(i).SetTimeShift(0);
			
		}
	}
	
	private void GeneratePages(int aAmountOfPages){	
		
		_generatedValuesForPageQueue = RecurrenceGenerator.Generate(aAmountOfPages,_aIngredient,0);
		
		_tempPageQueue.addAllInt(_generatedValuesForPageQueue);
	}
}
