package System.Hardware.Ellementaries;

import java.time.Instant;

import System.Diagnostics.ProcessMy;

public final class Core
{
	private int _id;
	private Instant _loadedCycles;
	private ProcessMy _loadedProcess;
		
	public Core(int aID){
		_id = aID;
		_loadedProcess = ProcessMy.NULL;
		_loadedCycles = Instant.ofEpochSecond(0);
	}
	
	public String toString(){
		String aValue;

		aValue ="["+_id + ",Pr:"+_loadedProcess.toString()+",CC:"+ _loadedCycles.getEpochSecond() + "]";

		return aValue;
	}
	
	public void IncreaseLoadCycle(long aAmount){
		_loadedCycles = _loadedCycles.plusSeconds(aAmount);
	}
	
	public Instant GetCoreLoad(){
		return _loadedCycles;
	}
	
	public void SetProcessPerformed(ProcessMy aProcess){
		
		_loadedProcess = aProcess;
	}
	
	public void ResetProcessPerformed(){		
		_loadedProcess = ProcessMy.NULL;
	}
	
	public ProcessMy GetProcessPerformed(){
		
		return _loadedProcess;
	}
	
	public ProcessMy Release(){	
		ProcessMy _valRet = new ProcessMy();
		_valRet = _loadedProcess;
		ResetProcessPerformed();
		
		return _valRet;
	}

	public void Tick() {	
		_loadedCycles = _loadedCycles.plusSeconds(1);
		_loadedProcess.Perform();
	}
}
