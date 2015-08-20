package System.Hardware;

import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Collectors;

import System.Hardware.Ellementaries.Core;
import System.Managers.Collections.ProcessQueue;

@SuppressWarnings("serial")
public class CPU extends ArrayList<Core>
{
	private Instant _cpuClock;
	private Instant _quantum;
	
	public static final CPU Instance = new CPU();
	
	private CPU(){
		
		_cpuClock = Instant.ofEpochSecond(0);
		_quantum = Instant.ofEpochSecond(1);
		CreateCores(1);
	}

	public void Tick() {	
		_cpuClock = _cpuClock.plusSeconds(1);
		
		stream().forEach(core -> core.Tick());	
	}
	
	public Instant GetClock(){
		return CPU.Instance._cpuClock;
	}

	public void ClockTick() {	
		_cpuClock = _cpuClock.plusSeconds(1);
	}
		
	public Instant ClockReset(){
		return CPU.Instance._cpuClock = Instant.ofEpochSecond(0);
	}
	
	public void SetQuantum(long aValue){
		_quantum = Instant.ofEpochSecond(aValue);	
	}
	
	public Instant GetQuantum(){
		return _quantum;	
	}

	public void CreateCores(int aAmountOfCores) {
		
		clear();
		
		for(int i = 0 ;i<aAmountOfCores;i++){
			add(new Core(i));
		}	
	}

	public Core GetCore(int aIdx) {
		return get(aIdx);
	}
	
	public void SyncToRAM(int aIdx, RAM aRam){	
		
		for(int i = 0 ; i <aRam.size();i++){		
			get(aIdx).GetProcessPerformed().GetFrames().set( i, RAM.Instance.get(i));
		}
	}

	public boolean AllocateProcesses(ProcessQueue _tempProcessQueue) {
				
		if(size()<_tempProcessQueue.size()) return false;
		
		for (int i = 0; i < _tempProcessQueue.size(); i++) {		
			get(i).SetProcessPerformed(_tempProcessQueue.get(i));	
		}
		
		return true;	
	}
	
	public String toString(){
		String second = "";
		
		second = stream()
			     .map(i -> i.toString())
			     .collect(Collectors.joining(","));
		
		return "[Clock:"+_cpuClock.getEpochSecond()+",Cores:"+second+",CoresSize:"+ size()+ "]";
	}
}
