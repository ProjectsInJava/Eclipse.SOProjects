package System.Managers;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import System.Algorithms.Enums.Scheduling;
import System.Algorithms.Interface.SchedAlgo;
import System.Algorithms.Paging.NULLAlgo;
import System.Algorithms.ProcessSched.FCFSAlgo;
import System.Algorithms.ProcessSched.RRAlgo;
import System.Algorithms.ProcessSched.SJFAlgo;
import System.Algorithms.ProcessSched.SRFAlgo;
import System.Diagnostics.ProcessMy;
import System.Diagnostics.Enums.ProcessState;
import System.Hardware.CPU;
import System.Hardware.HDD;
import System.Hardware.RAM;
import System.Hardware.Ellementaries.Core;
import System.Managers.Collections.ProcessQueue;
import System.Managers.Utils.MetricsCalculator;

public class ProcessManager extends Manager{
// region ********************************************************** MEMBERS_&_SINGLETON **********************************************************
	public static ProcessManager Instance = new ProcessManager();
	
	private ProcessQueue _queueReady;
	private ProcessQueue _queueWaiting;

	RAM _ram = RAM.Instance;
	HDD _hdd = HDD.Instance;
	VMM _vmm = VMM.Instance;
	
	MetricsCalculator _finishedProcesses;
	
	Object _outRegister;
	
	private Instant _maxfinishTime;
	
	private SchedAlgo _schedAlgo;
	
	private boolean _ifDispatcherNotRun;
	private boolean _ifOnlyTestMode;	
	private boolean _ifFramingPrediction;	
	
// endregion
// region ********************************************************** CTOR **********************************************************
	private ProcessManager()	{
		
		_queueReady = new ProcessQueue();
		_ifFramingPrediction = false;
		_ifDispatcherNotRun = true;
		_cpu = CPU.Instance;
		_ifOnlyTestMode = true;	
		_maxfinishTime = Instant.ofEpochSecond(0);
				
		_finishedProcesses = new MetricsCalculator();
		_queueWaiting = new ProcessQueue();
	}
// endregion
// region ********************************************************** MAIN_LOOP **************************
	@Override
	protected void init(Object aValue){
		_queueReady.clear();
		initializeWaitingQueue(aValue);			
		_maxfinishTime = Instant.ofEpochSecond(_queueWaiting.SummaryTimeExecute());			
	}
	
	@Override
	protected void work(Object aValue){	
		
		scheduleProcesses();	
		dispatcherAllocatesThemToCPU();
		VMM.AllocateFramesWithAlgo(CPU.Instance,_ifDispatcherNotRun,_ifFramingPrediction);	
		endIfFinished();
		
		if(!ifDone()){		
			pagesSwapping();
			timeEllapse();				
			updateStatesIfAnyProcessFinished();		
			releaseFinishedProcessesAndSendThemToCalculator();			
			forRR();
		}
	}
	
	@Override
	protected Object cleanup(Object aValue){		
		Reset();	
		updateMetrics();
		setDone(false);
		
		return _outRegister;
	}
// endregion
// region ********************************************************** WORK_MAIN_METHODS ***********	
	private void scheduleProcesses() {	
		List<ProcessMy> _readyProcesses = _queueWaiting.stream()
				.filter(process -> IsReadyToWork().test(process.GetTimeShift()))
				.collect(Collectors.toList());
		
		_queueReady.addAll(_readyProcesses);
		_queueWaiting.removeAll(_queueReady);		

		// **************** SCHEDULING_ALGO ********************
		
		_schedAlgo.SupplyPull(_queueReady);
		_queueReady = (ProcessQueue) _schedAlgo.SupplyPush();		
	}
	
	private void dispatcherAllocatesThemToCPU() {
		_ifDispatcherNotRun = true;
			
		List<Core> _freeCores = CPU.Instance.stream()
		.filter(core -> core.GetProcessPerformed().equals(ProcessMy.NULL))
		.collect(Collectors.toList());
		
		for(int i = 0; i <_freeCores.size() && i<_queueReady.size();i++){			
			_freeCores.get(i).SetProcessPerformed(_queueReady.dequeue());
			_ifDispatcherNotRun&=false;
		}
	}
	
	private void endIfFinished(){		
		if(CPU.Instance.GetClock().equals(_maxfinishTime))				
			setDone(true);		
		}	
	
	private void pagesSwapping() {
		if(!VMM.Instance.GetPagingAlgo().getClass().equals(NULLAlgo.class)){
			
			List<Core> _toService = _cpu.stream()
			.filter(core -> !core.GetProcessPerformed().equals(ProcessMy.NULL))
			.collect(Collectors.toList());

			_toService.stream()
				.map(core -> core.GetProcessPerformed())
				.forEach(process -> VMM.Instance.Run(process));			
		}
	}
	
	private void timeEllapse(){ // TODO
		long aUtilizationCPUPhase = calculateIt();
		
		for(int k = 0 ; k<aUtilizationCPUPhase;k++){			
			increaseProcessesWaitingTimes();
			_cpu.Tick();
		}
	}
	
	private void updateStatesIfAnyProcessFinished() {	
		CPU.Instance.stream()
		.forEach(core -> core.GetProcessPerformed().handleStateChange() );
	}
	
	private void releaseFinishedProcessesAndSendThemToCalculator() {
		
		CPU.Instance.stream()
			.map(core -> core.GetProcessPerformed())
			.forEach(process -> process.handleStateChange() );
		
		List<Core> _finishedCores = CPU.Instance.stream()
			.filter(core -> core.GetProcessPerformed().GetState().equals(ProcessState.END))
			.collect(Collectors.toList());
		
		_ifFramingPrediction = _finishedCores.size()!=0 ? true : false ;

		_finishedCores.stream()
			.map(core -> core.Release())
			.forEach(process -> sendToCalculator(process));
	}
	
	private void forRR() {
		boolean first = _schedAlgo.IfExpropriative();

//		for(int i = 0 ;i<_finishedProcesses.size();i++){
//			updatesTimeShift(_finishedCores);
			
//						if(_scheduler.CurrentAlgo().IfExpropriative()){				
//							for(int j = 0 ;j < _queueCurrent.size();j++){
//								_queueWaiting.enqueue(_queueCurrent.dequeue());				
//							}
//						}

//		}
	}

// endregion
// region ********************************************************** CLEAN_UP_MAIN_METHODS *******
	public void Reset() {
		CPU.Instance.SetQuantum(1);
		_queueReady.clear();
		
		if(_ifOnlyTestMode ) 
			_cpu.ClockReset();
		
		_queueReady.clear();
	}
	
	private void updateMetrics() {
		_outRegister = (double) _finishedProcesses.GetAverage();
		SO.Instance._outRegister.set(1, new Double(_finishedProcesses.GetPageFaults()));
		
		_finishedProcesses.clear();
	}
// endregion	
// region ********************************************************** OTHER_METHODS ***************
	private SchedAlgo determineSchedulingAlgorithm(Scheduling aType) {
		SchedAlgo _retVal = null;
		
		switch(aType){
		
			case FCFS:	_retVal = FCFSAlgo.Instance;		break;
			case SJF:	_retVal = SJFAlgo.Instance;			break;
			case SRF:	_retVal = SRFAlgo.Instance;			break;
			case RR:	_retVal = RRAlgo.Instance;			break;
		}
		
		return _retVal;
	}
	
	private void updatesTimeShift(List<Core> _finishedCores) {
		_finishedCores.stream()
			.map(core -> core.GetProcessPerformed())
			.forEach(process -> process.SetTimeShift(_cpu.GetClock().getEpochSecond()));
	}
	
	private void initializeWaitingQueue(Object aValue){	
		if(!((ProcessQueue) aValue).isEmpty())	
			_queueWaiting = (ProcessQueue) aValue;
		else
			_queueWaiting = SO.Instance.RandomProcessQueue();
	}

		private Predicate <Instant> IsReadyBeforeActualTime(){ 
			return time-> time.isBefore(CPU.Instance.GetClock());
		}
		
		public Predicate <Instant> IsReadyToWork(){
			return IsReadyBeforeActualTime().or(time -> time.equals(CPU.Instance.GetClock()));
		}
		
		private void sendToCalculator(ProcessMy aFinished) {
			_finishedProcesses.add(aFinished);				
		}	

		public void SetSchedulingType(Scheduling aType){			
			_schedAlgo = determineSchedulingAlgorithm(aType);		
		}
		
		public SchedAlgo GetSchedAlgo(){	
			return _schedAlgo;	
		}
	
		private long calculateIt() {
			
			Instant tempQuantum =CPU.Instance.GetQuantum();

			long aRetValue = tempQuantum.getEpochSecond();
			
			if(ProcessManager.Instance.GetSchedAlgo() instanceof RRAlgo){
				aRetValue = setRetValueRightlyToUtilization(tempQuantum, aRetValue);
			}
			
			return aRetValue;
		}
		private long setRetValueRightlyToUtilization(Instant tempQuantum, long aRetValue) {
			
//			if(_queueCurrent.peek().GetTimeExecute().compareTo(tempQuantum)<0){
//				
//				aRetValue = _queueCurrent.peek().GetTimeExecuteValue(); // SetToCPU
//			}
			return aRetValue;
		}
		private void increaseProcessesWaitingTimes(){
			
			_queueReady.stream()
				.forEach(process -> process.IncreaseTimeWaiting(1));
		}

	public String toString(){
		return "PM ["+_cpu.GetClock().getEpochSecond()+","+CPU.Instance.GetQuantum().getEpochSecond()+","+ifDone()+"]";
	}
// endregion
}
