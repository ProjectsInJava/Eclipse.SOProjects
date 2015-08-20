package System.Managers.Elementary;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import System.Collections.Generic.GenQueue;
import System.Hardware.CPU;

public class PageTime extends PageValue{
// region ******************************** MEMBERS ******************************** 

	private GenQueue <Instant> _timeRegister;
	private Instant _timeRamAllocation;

// endregion
// region ******************************** CTORS ******************************** 
	public PageTime(){							
		super();
		init();
	}
	
	public PageTime(int aID){					
		super(aID);
		
		init();
	}

	public PageTime(String aID){				
		super(aID);
		init();
	}
	
	private void init(){
		_timeRegister = new GenQueue<Instant>();
		_timeRamAllocation = Instant.ofEpochSecond(-1);
	}	
	
	protected PageTime(PageTime aPage){	 		 
		copyFrom(aPage);		
	}
	
	private void copyFrom(PageTime aPage){	 
		
			_timeRegister = aPage.GetTimeRegister();		
			_timeRamAllocation = aPage.GetTimeRAMAllocation();	
		}
	
//endregion
// region ******************************** TIME_OPERATIONS ********************************
	public void SetTimeRAMAllocation(Instant aValue){	
		_timeRamAllocation = aValue;		
	}

	public Instant GetTimeRAMAllocation(){	
		return _timeRamAllocation;			
	}

	public void SetRegisterTimes(GenQueue<Instant> aNewRegisterTimes){	
		_timeRegister = aNewRegisterTimes;	
	}
	
	public void SetRegisterTimes(List<Instant> aNewRegisterTimes){	
		_timeRegister = new GenQueue<Instant>(aNewRegisterTimes);	
	}
	
	public GenQueue<Instant> GetTimeRegister(){		
		return _timeRegister;				
	}	
	
	public void AddTimeRegister(Instant aValue){		
		_timeRegister.add(aValue);			
	}

	@SuppressWarnings("unchecked")
	public Instant GetTheNearInFutureTimeRegister(){

		GenQueue<Instant>	tempRegisterTime =(GenQueue<Instant>) _timeRegister.clone();
		
		tempRegisterTime.removeIf(IsBeforeActualTime());
		
		if(tempRegisterTime.size()==0){	
			return Instant.MAX;
		}

		return tempRegisterTime.dequeue();
	}	
	
	@SuppressWarnings("unchecked")
	public Instant GetTheNearInPastTimeRegister() {
		GenQueue<Instant> tempRegisterTime =(GenQueue<Instant>) _timeRegister.clone();
		Collections.reverse(tempRegisterTime);
		
		tempRegisterTime.removeIf(IsAfterActualTime());
		
		if(tempRegisterTime.size()==0){		
			return Instant.MIN;
		}
		
		return tempRegisterTime.dequeue();		
	}
	
	public long GetFrequencyInPastOfTimeRegister(){	
		long retVal = 0;
		
			retVal = this._timeRegister.stream()
						.filter(IsBeforeActualTime())
						.count();
	
		return retVal;
	}

	public void ResetFrequency(){	
		_timeRegister.removeIf(IsBeforeActualTime());
	}
//	
	private Predicate <Instant> IsBeforeActualTime(){ 
		return time-> time.isBefore(CPU.Instance.GetClock());
	}
	
	private Predicate <Instant> IsAfterActualTime(){ 
		return time-> time.isAfter(CPU.Instance.GetClock());
	}
	
	public static Function<Integer,Instant> IntToInstant = val -> Instant.ofEpochSecond(val);
	
// endregion
// region ******************************** Comparator_toSTRING **************************************************	
	
	public int compareTo(PageTime arg0){		
		int first = 1;	
		first = _timeRamAllocation.equals((arg0.GetTimeRAMAllocation())) ? 0 : 1;
		
		return  first + super.compareTo(arg0);			
	}
	
	public String toString(){
		String aValue ="";
		String aSecond ="";
		String aThird ="";
		String aFourth ="";
		String aFifth ="";
		
	
//		aSecond =",RT:"+GetFrequencyInPastOfTimeRegister();   	
//		aThird =",RAT:"+_timeRamAllocation.getEpochSecond();  		
		
//		aFourth = "RegisterTimes:"+_timeRegister.toString();
		aFourth = "RegisterTimes:"+GetTheNearInPastTimeRegister().getEpochSecond();
//		aFifth = ",TimeRegInFut:"+GetTheNearInFutureTimeRegister().getEpochSecond();
		aValue ="["+super.toString()+aSecond+aThird+aFourth+aFifth+"]";
		
		return aValue;		
	}
}
