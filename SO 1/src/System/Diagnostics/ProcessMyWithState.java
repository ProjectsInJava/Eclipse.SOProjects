package System.Diagnostics;

import java.time.Instant;
import java.util.function.Consumer;

import System.Diagnostics.Enums.ProcessState;

public class ProcessMyWithState extends ProcessMyWithData{
	
	private ProcessState _state;
	
// region ********************************* CTORS ******************************************************************************
	protected ProcessMyWithState(){											
		super();			
		init();			
	}

	protected ProcessMyWithState(String aStartTime, String aExecute){		
		super(aStartTime,aExecute);			
		init();			
	}

	protected ProcessMyWithState(int aStartTime, int aExecute){				
		super(aStartTime,aExecute);			
		init();				
	}
	
	protected ProcessMyWithState(ProcessMyWithState aCopyProcess){			
		super(aCopyProcess);			
		copyFrom(aCopyProcess);			
	}

	private void copyFrom(ProcessMyWithState aCopyProcess)	{				
		_state = aCopyProcess.GetState();			
	}
	
	private void init(){													
		New();			
	}

//endregion
// region ********************************* STATES *****************************************************************************
	public void Ready(){													
		_state = ProcessState.READY;		
	}

	public void New(){														
		_state = ProcessState.NEW;			
	}

	public void Suspend(){													
		_state = ProcessState.WAITING;		
	}
	
	public void Activate(){													
		_state = ProcessState.ACTIVE;		
	}

	public void Start(){													
		_state = ProcessState.START;		
	}
	
	public void End(){														
		_state = ProcessState.END;			
	}

	public ProcessState GetState(){											
		return _state;		
	}
	
	public int GetStateOrdinal(){											
		return _state.ordinal();		
	}
	
	public String toString(){	
		String first = "";
//		first = GetState().toString();
		
		return first+","+super.toString();			
	}
	
	public boolean handleStateChange(){		
		
		boolean valRet = false;
		
		if(GetTimeExecute().equals(Instant.ofEpochSecond(0)))	
			{
				End();	
				valRet = true;
			}
		
		return valRet;
	}
//	
//	public static Consumer<ProcessMyWithState> ChangeStates(){
//		return process -> process.handleStateChange();			
//	}
	
	public int compareStatesTo(ProcessState arg0){ 						
		return GetState().ordinal() - arg0.ordinal();
	}
	
// endregion
// region ********************************* STANDARD_METHODS ******************************** 
	protected int compareTo(ProcessMyWithState arg0) {		
		int first = GetStateOrdinal() - arg0.GetStateOrdinal() == 0 ? 0 : 1 ;
			
		return first + super.compareTo(arg0);
	}
	
	// endregion
}

