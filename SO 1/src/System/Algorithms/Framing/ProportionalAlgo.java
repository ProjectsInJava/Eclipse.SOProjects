package System.Algorithms.Framing;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import System.Algorithms.Interface.FrameAlgo;
import System.Diagnostics.ProcessMy;
import System.Hardware.CPU;
import System.Hardware.RAM;
import System.Hardware.Ellementaries.Core;
import System.Managers.Elementary.Page;

public class ProportionalAlgo extends FrameAlgo{
	
	public static final ProportionalAlgo Instance = new ProportionalAlgo();
	
	private ProportionalAlgo(){
		
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
		
		int _tempTimeExecute = 0;
		int _tempSumOfTimeExecute = (int) _tempInRegister.stream()
				.mapToLong(core -> core.GetProcessPerformed().GetTimeExecute().getEpochSecond())
				.sum();
		
		for(int i =  0 ;i <_tempInRegister.size();i++){
			
			LinkedList<Integer> _tempList = new LinkedList<Integer>();
			_tempTimeExecute = (int) _tempInRegister.get(i).GetProcessPerformed().GetTimeExecute().getEpochSecond();
			
			_tempVal = _tempTimeExecute*RAM.Instance.GetCycleSize()/_tempSumOfTimeExecute;
			if(_tempVal==0) ++_tempVal;
			
			_tempList.add(_tempIdx);
			_tempList.add(_tempIdx + _tempVal);
			
			_tempOutRegister.add(_tempList);
			_tempIdx = _tempIdx + _tempVal;
		}
	}
}
