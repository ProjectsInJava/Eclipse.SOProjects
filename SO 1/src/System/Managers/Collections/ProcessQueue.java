package System.Managers.Collections;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import System.Diagnostics.ProcessMy;
import System.Diagnostics.Enums.ProcessState;


@SuppressWarnings("serial")
public  class ProcessQueue extends LinkedList<ProcessMy>
{
	protected String _name;
	
	public ProcessQueue(){								
		super();	
	}
	
	public ProcessQueue(String [] aArgs){
		
		for(int i = 0 ;i< aArgs.length;i+=3)
			enqueue(new ProcessMy(aArgs[i],aArgs[i+1],aArgs[i+2]));
	}
	
	public ProcessQueue(LinkedList<Integer> aArgs){
		
		for(int i = 0 ;i< aArgs.size()/2;i++)
			enqueue(new ProcessMy(i,aArgs.get(2*i),aArgs.get(2*i+1)));
	}
	
	public ProcessQueue(ProcessQueue _readyQueue) {
		addAll(_readyQueue);
	}

	public boolean enqueue(ProcessMy aProcess){			return offer(aProcess);		}

	public ProcessMy dequeue(){							return poll();		}
	
	public List<ProcessMy> DequeueFinishedProcesses(){
		
		LinkedList<ProcessMy> listShape = new LinkedList<ProcessMy>(this);	
		
		List<ProcessMy> ended = listShape.stream()
				.filter(process -> process.GetState().equals(ProcessState.END))
				.collect(Collectors.toList());
		
		removeIf(process -> process.GetState().equals(ProcessState.END));

		return ended;
	}
//				
	public void ResetPreviousPages(){
		stream().
			forEach(process -> process.ResetData());
	}
//	
	public void AddPages(PageQueue _tempPageQueueRefference) {
		
		ResetPreviousPages();
		
		for(int i = 0 ; i <size();i++)
		{
			for(int j = 0 ; j<get(i).GetTimeExecute().getEpochSecond();j++)
			{
				get(i).AddPage(_tempPageQueueRefference.dequeue());
			}
		}
	}
	
	public Integer SummaryTimeExecute(){
		
		return (int) stream().mapToLong(process -> process.GetTimeExecute().getEpochSecond())
				.sum();
	}
//		
//		int aTempSize = _tempPageQueueRefference.size();
//		Page _tempLastDeffinitellyInRamPage = _tempPageQueueRefference.peekLast();
//		
//		long aKummulateExecutionTime  = stream()
//										.mapToLong(process ->process.GetTimeTotalProcesorValue())
//										.sum();
//		
//		
//		
//		for(int i = 0 ; i<aKummulateExecutionTime -aTempSize;i++) // Gdy jest zbyt malo stron dla 1:1 ProcessTick:Page
//		{
//			_tempPageQueueRefference.enqueue(_tempLastDeffinitellyInRamPage);
//		}
//		
//		if(Customer.Instance.GetSchedulingType().ordinal() - Scheduling.RR.ordinal() !=0)
//		{
//					
//			for(int i = 0 ; i<size();i++)
//			{
//				if(_tempPageQueueRefference.size()!=0)
//					(get(i)).AddPagesByProcessExecuteRatio(_tempPageQueueRefference);
//			}
//			
//			if(_tempPageQueueRefference.size()!=0)
//			{
//				(peekLast()).AddAdditionalPages(_tempPageQueueRefference);
//			}
//		}
//		else
//		{			
//			for(int i = 0 ; i<_tempPageQueueRefference.size();i++)
//			{
//				for(int j = 0 ; j <size();j++)
//				{
//					if(get(j).GetPages().size()<(int)get(j).GetTimeExecute().getEpochSecond())
//					{			
//						get(j).AddPage(_tempPageQueueRefference.dequeue());
//					}
//				}
//
//			}
//		}
//	}
	
}