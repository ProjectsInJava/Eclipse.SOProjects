package System.Managers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import System.Hardware.CPU;
import System.Hardware.HDD;
import System.Hardware.RAM;
import System.Managers.Collections.PageQueue;
import System.Managers.Collections.ProcessQueue;
import System.Managers.Utils.RecurrenceGenerator;

public class SO extends Manager{
// region ********************************************************** MEMBERS_&_SINGLETON **********************************************************
	public static SO Instance = new SO();
	
	private PageQueue _queuePageWaiting;
	
	private ProcessQueue _queueProcessWaiting;
	
	private boolean _ifLocalityRefference;
	private boolean _ifEx4;
	
	private HDD _hdd = HDD.Instance;

	public List<Object> _outRegister;

	public static Instant _cpuClock;
// endregion
// region ********************************************************** CTOR **********************************************************
	private SO()	{
// 
		_queueProcessWaiting = new ProcessQueue();
		_outRegister = new LinkedList<Object>();

		_ifLocalityRefference = false;
		_ifEx4 = false;
//		_summedAmountOfPageFaults = PageCalculator.Instance;
//		_processStringRefference;
//		_pageStringRefference=""	;
//		
//		_ifConsoleEnabled= true;
//		_expectTimeCalculator = new TimeCalculator<ProcessMy>();

	
//		_cpuClock = CPU.Instance.GetClock(); // Uzyc DURATION
	}
// endregion
// region ********************************************************** MAIN_LOOP ***************************************************
	@Override
	protected void init(Object aValue){ 

		_outRegister.add(new Object()); // 0 AccessTime
		_outRegister.add(new Integer(0));  // 1 PAgeFaults
		
		if(!_ifEx4)
		{
			if(_queueProcessWaiting.isEmpty())
				_queueProcessWaiting = RandomProcessQueue();		
		
		
			if(_queuePageWaiting.isEmpty())		
				_queuePageWaiting = RandomPageQueue();					
		
			_queueProcessWaiting.AddPages((PageQueue) _queuePageWaiting.clone());
		
			HDD.Instance.Allocate(_queuePageWaiting);
		}
	}
	@Override
	protected void work(Object aValue){
		
		_outRegister.set(0,ProcessManager.Instance.Run(_queueProcessWaiting));

		setDone(true);
	}
	@Override
	protected Object cleanup(Object aValue) {
		setDone(false);
		
		CPU.Instance.ClockReset();

		List<Object> tempOutRegister = updateOutRegistersWithMetrics();
		_outRegister.clear();
		
		hardwareReset();
		
		return tempOutRegister;
	}
// endregion
// region ********************************************************** OTHER_METHODS ********************************************
	private List<Object> updateOutRegistersWithMetrics() {
		List<Object> tempOutRegister = new ArrayList<Object>(_outRegister.size());
		tempOutRegister.add(_outRegister.get(0));
		tempOutRegister.add(_outRegister.get(1));
		return tempOutRegister;
	}
	private void hardwareReset() {
		RAM.Instance.clear();
		HDD.Instance.clear();
	}	

	private void AllocatePagesToProcesses() {
		_queueProcessWaiting.AddPages(_queuePageWaiting);
	}

	public PageQueue GetPageQueueRefferenece(){			
		return _queuePageWaiting;		
	}
	
	public void SetPageQueue(String[] aValue){
		_queuePageWaiting = new PageQueue(aValue);
	}
	
	public void SetPageQueue(PageQueue aValue){
		_queuePageWaiting =aValue;
	}
	
	public void SetProcessQueue(String[] aValue){
		_queueProcessWaiting = new ProcessQueue(aValue);	
	}
	

	
	public ProcessQueue RandomProcessQueue() {	
		Random _tempRand = new Random();
		LinkedList<Integer> _generatedValues;
		ProcessQueue _tempQueue;
		
		_tempQueue = new ProcessQueue();
		int aAmountOfProcesses = _tempRand.nextInt(100);
		int aSize = aAmountOfProcesses*2;
		int aIngredient = 2;
		
		_generatedValues = RecurrenceGenerator.Generate(aSize,aIngredient,1);

		_tempQueue = new ProcessQueue(_generatedValues);	
		
		return _tempQueue;
	}
	
	public PageQueue RandomPageQueue() {
		LinkedList<Integer> _generatedValues;
		PageQueue _tempQueue;
		
		_tempQueue = new PageQueue();
		int aSize = _queueProcessWaiting.SummaryTimeExecute();
		int aIngredient = 4;
		
		_generatedValues = RecurrenceGenerator.Generate(aSize,aIngredient,1);

		_tempQueue.addAllInt(_generatedValues);	
		
		return _tempQueue;
	}
	
	public void SetIfLocalityRefference(boolean aValue) {
		_ifLocalityRefference = aValue;
	}
	
	public boolean GetIfLocalityRefference() {
		return _ifLocalityRefference;
	}
	public ProcessQueue GetProcessQueue() {
		return _queueProcessWaiting;
	}
	public void SetIfEx4(boolean aVal) {
		_ifEx4 = aVal;
	}
// endregion
}