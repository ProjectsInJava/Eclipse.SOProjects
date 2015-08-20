package System.Managers;
// multiplikat algorytm
// grzech sieci
// zmienna losowa o rozkladzie Gaussa
// rownowazenie obciazenia
// Hil Graph
// Stream Graph
// aktualne obciazenie
// wyolbrzymienie , styczne sytuacje , progowe

// region ******************* DETAIL_FORMATTER ************************
//RAM.Instance.toString()+_tempDequeued.toString()+_amountOfPageFaults
// endregion

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import System.Algorithms.Enums.Framing;
import System.Algorithms.Enums.Paging;
import System.Algorithms.Framing.EqualsAlgo;
import System.Algorithms.Framing.NullAlgo;
import System.Algorithms.Framing.ProportionalAlgo;
import System.Algorithms.Framing.SphereAlgo;
import System.Algorithms.Interface.FrameAlgo;
import System.Algorithms.Interface.PageAlgo;
import System.Algorithms.Paging.FIFOAlgo;
import System.Algorithms.Paging.LFUAlgo;
import System.Algorithms.Paging.LRUAlgo;
import System.Algorithms.Paging.NULLAlgo;
import System.Algorithms.Paging.OPTAlgo;
import System.Algorithms.Paging.RANDAlgo;
import System.Diagnostics.ProcessMy;
import System.Hardware.HDD;
import System.Hardware.CPU;
import System.Hardware.RAM;
import System.Hardware.Ellementaries.Core;
import System.Managers.Collections.PageQueue;
import System.Managers.Elementary.Page;

public class VMM extends Manager{
// region ********************************************************** MEMBERS_&_SINGLETON_&_CTOR **********************************************************
	 	
	public static VMM Instance = new VMM();

	private AtomicInteger  _amountOfPageFaults;
	private ProcessMy _currentProcess;
	
	private static FrameAlgo _framingAlgo;
	private PageAlgo _pagingAlgo;
	
	public PageQueue _waitingQueue;
	public HDD _hddInstance = HDD.Instance;							// to debugging only
	public RAM _ramInstance = RAM.Instance;							// to debugging only
	Page _tempDequeued ;
	
	private VMM()	{
		clear();		
	}
// endregion
// region ********************************************************** MAIN_LOOP **************************
	@Override
	protected void init(Object aValue){
		SetCurrentProcess((ProcessMy) aValue);
		UpdateWaitingQueue();
	}
	
	@Override
	protected void work(Object aValue){
	
		Page _toAllocate = Page.NULL;
		_tempDequeued = _waitingQueue.dequeue();	
		
		if(isItEnd(_tempDequeued , page-> page!= Page.NULL)){
			
			Page _copyFromHDD = HDD.Instance.Get(_tempDequeued);
			_toAllocate = RAMAllocation(_copyFromHDD);
			HDD.Instance.Update(_toAllocate);
			
			conditionalEnd();
		}
		else{	
			setDone(true);
		}
	}
	
	protected Object cleanup(Object aValue){
	
		int retVal = _currentProcess.GetPageFault();
		_currentProcess = ProcessMy.NULL;
		setDone(false);
		
		return retVal;
	}	
// endregion	
// region ********************************************************** RAM_HDD_ACCESS_METHODS ***********************************

	private Page allocatePageToRAM( Page aTempDequeued ) {
		
		Page valRet;
		aTempDequeued.SetTimeRAMAllocation(CPU.Instance.GetClock());	
		valRet = aTempDequeued;
		
		_currentProcess.GetFrames().set(0, aTempDequeued);
		
		return valRet;
	}
	
	private Page RAMAllocation(Page aTempDequeued) {
		
		Page valRet = Page.NULL;
		
		if(!_currentProcess.GetFrames().stream().anyMatch(page -> page.equals(aTempDequeued))){
			
			_pagingAlgo.SupplyPull(VMM.Instance._currentProcess);
			_pagingAlgo.SupplyPush();
			
			_currentProcess.AddPageFault();
			
			valRet = allocatePageToRAM(aTempDequeued);
			
			// *************** UPdaate Frames From Process To Ram ********************************************
			
			List<Page> _ramSubset =RAM.Instance.subList(_currentProcess.GetFramesStartIdx(),
					_currentProcess.GetFramesEndIdx());
				
			for(int i = 0 ; i<_ramSubset.size();i++){
				_ramSubset.set(i, _currentProcess.GetFrames().get(i));
			}
		}
//
		if(_framingAlgo == NullAlgo.Instance)
			CPU.Instance.Tick();
		
		return valRet;
	}
// endregion
// region ********************************************************** DETERMINING_ALGOS ********************************
	
		private Function<Paging,PageAlgo> EnumToAlgo = name -> determinePagingAlgorithm(name);
		
		private PageAlgo determinePagingAlgorithm(Paging aType) { 			
			
			PageAlgo _retVal = null;

			if(aType.equals(Paging.FIFO)){			_retVal = FIFOAlgo.Instance;               }
			if(aType.equals(Paging.OPT) ){			_retVal = OPTAlgo.Instance;	               }
			if(aType.equals(Paging.LRU) ){			_retVal = LRUAlgo.Instance;	               }
			if(aType.equals(Paging.LFU) ){			_retVal = LFUAlgo.Instance;	               }
			if(aType.equals(Paging.RAND)){			_retVal = RANDAlgo.Instance;               }
			if(aType.equals(Paging.NULL)){			_retVal = NULLAlgo.Instance;               }
			
			return _retVal;
		}
		
		private FrameAlgo determineFramingAlgorithm(Framing aType) {
			
			FrameAlgo _retVal= null;
			
			switch(aType){

				case EQUALS:		_retVal = EqualsAlgo.Instance;				break;
				case PROPORTIONAL:	_retVal = ProportionalAlgo.Instance;		break;
				case SPHERE:		_retVal = SphereAlgo.Instance;				break;
			}
			
			return _retVal;
		}
			
		public void SetFramingType(Framing aType) {		
			_framingAlgo = determineFramingAlgorithm(aType);
		}
		
		public void SetPagingType(Paging aAlgo){
			_pagingAlgo = EnumToAlgo.apply(aAlgo);
		}
		
		@SuppressWarnings("unchecked")
		public static void AllocateFramesWithAlgo(List<Core> aCores,boolean aDispatcherNotRun, boolean aFraminingPrediction){
			
			if(!aDispatcherNotRun || aFraminingPrediction){			
					
				List<Core> _cores =aCores.stream()
				.filter(core -> !core.GetProcessPerformed().equals(ProcessMy.NULL))
				.collect(Collectors.toList());
				
				if(!_cores.isEmpty() && !GetFramingAlgo().getClass().equals(NullAlgo.class))
				{
					VMM.GetFramingAlgo().SupplyPull(_cores);
					int _tempIdx=0;
					int _tempLength=0;
				
				// *********** MATRIX_OF_START_IDX_AND_LENGTH_OF_ALGO_DETERMINED_RAM_SPACES *************
					LinkedList<LinkedList<Integer>> _algoPushing = 
							(LinkedList<LinkedList<Integer>>) VMM.GetFramingAlgo().SupplyPush();
					
					for(int i = 0 ; i <_cores.size();i++){					
						_tempIdx=_algoPushing.get(i).get(0);       
						_tempLength=_algoPushing.get(i).get(1);   
					
						_cores.get(i).GetProcessPerformed()
							.GiveFrames(_tempIdx, _tempLength);		
					}	
				}
			}
		}
		
		public static FrameAlgo GetFramingAlgo() {
			return _framingAlgo;
		}

		public PageAlgo GetPagingAlgo() {
			return _pagingAlgo;
		}
		
// endregion
// region ********************************************************** PROPERTIES_ACCESS_METHODS ***********************************

	private void UpdateWaitingQueue(){
		for(int i = 0 ; i <CPU.Instance.GetQuantum().getEpochSecond();i++)
			_waitingQueue.add(_currentProcess.GetPages().dequeue());
	}

	public void SetCurrentProcess(ProcessMy aProcess) {
		_currentProcess = aProcess;
	}
	
	public ProcessMy GetCurrentProcess() {
		return _currentProcess;
	}

// endregion
// region ********************************************************** OTHER_METHODS ***************	
	public String toString()	{
//		return "PM ["+_vmmClock.getEpochSecond()+","+"CPQ"+_currentQueue.toString()+","+ifDone()+","+_scheduler.toString()+"]";
		return "VMM "+"["+CPU.Instance.GetClock().getEpochSecond()+","+"PageFaults:"+_amountOfPageFaults+"]";
	}
	
	
	private void clear(){		
		_tempDequeued = Page.NULL; // to debugging only
		_currentProcess = ProcessMy.NULL;
		
		_framingAlgo = NullAlgo.Instance;
		_pagingAlgo = NULLAlgo.Instance;
		_waitingQueue = new PageQueue();
		_ramInstance.clear();
	}
	
	
	private void conditionalEnd() {
		if( _waitingQueue.isEmpty() ) 
			setDone(true);		
	}

	private boolean isItEnd(Page aTested, Predicate<Page> aPredicate) {  
		  return  aPredicate.test(aTested);
	}
// endregion
}
