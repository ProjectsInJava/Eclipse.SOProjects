package System.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import System.Managers.Manager;
import System.Managers.Elementary.Transition;

public class StateMachine extends Manager{

	private ArrayList<Transition> _determinedTransitions;
	private Transition _state;
	private StringBuffer _signalsWaiting;	
	private String _tempDequeued;
	private boolean _outReg;
	
	public static final StateMachine Instance = new StateMachine();

	private StateMachine(){
		_state = new Transition();
		_outReg = false;
	}
	
	@Override
	protected void init(Object aValue) {
		_signalsWaiting = new StringBuffer((String) aValue);
	}

	@Override
	protected void work(Object aValue) {
		
			if(_signalsWaiting.length()!=0)		
			{
				_tempDequeued = _signalsWaiting.substring(0,1);
				_signalsWaiting.deleteCharAt(0);
				
				List<Transition> acceptable =_determinedTransitions.stream()
						.filter(trans -> trans.GetStart().equals(_state.GetActual()))
						.filter(trans -> trans.GetSignal().contains(_tempDequeued))
						.collect(Collectors.toList());
											
					if(acceptable.isEmpty()) {				
						setDone(true);
					}
					else{					
						_state.SetActual(acceptable.get(0).GetEnd());
					}
			}	
			else{			
				setDone(true);
				
				if(_state.GetActual().equals(_state.GetEnd())){	
					_outReg = true;
				}					
			}
	}
	
	@Override
	protected Object cleanup(Object aValue) {
		setDone(false);
		boolean valRet = reset();
		return valRet;
	}
	
	private boolean reset(){			
		boolean valRet = _outReg;
		
		_outReg = false;
		_state.SetActual("0");
		_state.SetStart("0");
		
		return valRet;
	}
	
	public void SetFinalState(String aValue){		
		_state.SetEnd(aValue);
	}
	
	public void SetTransitions(ArrayList<Transition> aValue){
		_determinedTransitions = (ArrayList<Transition>) aValue;
	}

	public void SetStartState(String aState) {
		_state.SetStart(aState);
	}
	public void SetActualState(String aState) {
		_state.SetActual(aState);
	}
}
