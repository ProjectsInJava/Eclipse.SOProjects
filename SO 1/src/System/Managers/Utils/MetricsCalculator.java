package System.Managers.Utils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import System.Diagnostics.ProcessMy;

@SuppressWarnings("serial")
public class MetricsCalculator extends LinkedList<ProcessMy>{
	
	public Double GetAverage(){
		
		Set<ProcessMy> _tempSet = stream().collect(Collectors.toSet());
		
		Double _tempAverage = _tempSet.stream()
				.filter(process -> process.compareTo(ProcessMy.NULL)!=0)
				.mapToLong(process -> process.GetTimeWaitingValue())			
				.average().getAsDouble();
		
		Double truncatedDouble=new BigDecimal(_tempAverage).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return truncatedDouble;
	}
	
	public Double GetPageFaults(){
		
		Set<ProcessMy> _tempSet = stream().collect(Collectors.toSet());
		
		Double _tempSumOfFaults = (double) _tempSet.stream()	
				.mapToInt(process -> process.GetPageFault())
				.sum();
		
		return _tempSumOfFaults;
	}
}
