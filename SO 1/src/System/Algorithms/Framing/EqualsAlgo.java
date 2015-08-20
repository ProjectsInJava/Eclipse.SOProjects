package System.Algorithms.Framing;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import System.Algorithms.Interface.FrameAlgo;
import System.Diagnostics.ProcessMy;
import System.Hardware.CPU;
import System.Hardware.RAM;
import System.Managers.Elementary.Page;

public class EqualsAlgo extends FrameAlgo{
	
	public static final EqualsAlgo Instance = new EqualsAlgo();
	
	private EqualsAlgo(){ // equals i proportional powinien zwracac proporcje, 
		super();
	}

	@Override	
	public Object SupplyPush() {
		
		calculate();
		Object tempRetVal = _tempOutRegister.clone();
		_tempOutRegister.clear();
		
		return tempRetVal;
	}

	@Override
	protected void calculate() {	
		
		int _tempIdx = 0;
		int _tempVal = 0;
	
		
		for(int i =  0 ;i <_tempInRegister.size();i++){
			
			LinkedList<Integer> _tempList = new LinkedList<Integer>();
			
			_tempVal = RAM.Instance.GetCycleSize()/_tempInRegister.size(); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			_tempList.add(_tempIdx);
			_tempList.add(_tempIdx + _tempVal);
			
			_tempOutRegister.add(_tempList);
			_tempIdx = _tempIdx + _tempVal;
		}
	}
}
