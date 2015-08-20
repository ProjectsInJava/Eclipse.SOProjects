package System.Diagnostics;

import java.util.Comparator;
import java.util.List;

import System.Hardware.RAM;
import System.Managers.Elementary.Page;

public class ProcessMy extends ProcessMyWithState implements Comparable<ProcessMy>{
	
	private int _iD;
// region ******************************** CTORS ******************************************************************************
	public ProcessMy(){															
		super();	
		init();			
	}
	
	public ProcessMy(String aId,String aStartTime, String aExecute){			
		super(aStartTime,aExecute);
		_iD = Integer.valueOf(aId);		
	}
		
	public ProcessMy(int aId,int aStartTime, int aExecute){						
		super(aStartTime,aExecute);				
		_iD = aId;		
	}
	
	public ProcessMy(ProcessMy aCopyProcess){									
		super(aCopyProcess);		
		copyFrom(aCopyProcess);			
	}

	private void copyFrom(ProcessMy aCopyProcess)	{							
		_iD = aCopyProcess.GetID();			
	}
	
	private void init(){														
		_iD = -1;			
	}
// endregion
// region ******************************** Property_access_methods *****************************************************************************	
	public int GetID()		{													
		return _iD;			
	}
	
	public void SetID(int aId){													
		_iD = aId;			
	}
	
// endregion
// region ******************************** STANDARD_METHODS ***********************************************************
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _iD;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessMy other = (ProcessMy) obj;
		if (_iD != other._iD)
			return false;
		return true;
	}
	@Override
	public int compareTo(ProcessMy arg0) {
		int first = _iD - arg0._iD;
		
		return first + super.compareTo(arg0);
	}
	
	public String toString(){	return "["+_iD+","+super.toString()+"]";		}
	
	public static final ProcessMy NULL = new ProcessMy();
// endregion
	
	public static Comparator<? super ProcessMy> IDComparator(){
		
		return Comparator.comparing(process-> ((ProcessMy) process).GetID());
	}
	
	public void Perform() {
		DecreaseTimeExecute(1); 
	}

	public void GiveFrames(int aTempStartIdx, int aTempEndIdx) {
		
		_frames.clear();
		_frames.addAll(RAM.Instance.subList(aTempStartIdx,aTempEndIdx));
		
		_framesStartIdx = aTempStartIdx;
		_framesEndIdx =  aTempEndIdx;	
	}
	
	public boolean GiveFrames(List<Page> tempPageQueue) {
		
//		return _frames.addAll(tempPageQueue.subList(0, tempPageQueue.size()));
		return _frames.addAll(tempPageQueue);
	}
}
