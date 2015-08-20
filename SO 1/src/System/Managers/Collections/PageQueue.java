package System.Managers.Collections;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import System.Collections.Generic.GenQueue;
import System.Managers.Elementary.Page;

@SuppressWarnings("serial")
public class PageQueue extends GenQueue<Page>
{
	public int _cycleSize;
// region ******************************** CTORS ******************************** 
	
	public PageQueue(){
		super();						
	}
	
	public PageQueue(int aCycleSize){						
		super(aCycleSize);		
		SetCycleSize(aCycleSize);
		Collections.fill(this, Page.NULL);	
	}
		
	public PageQueue(Collection<? extends Page> c) {
		super(c);		
		SchedulePages();
	}
	
	public PageQueue(String [] aArgs){						
		addAllStrings(Arrays.asList(aArgs));	
		SchedulePages();
	}
	
	public PageQueue(LinkedList<Integer> aArgs){						
		addAllInt(aArgs);	
		SchedulePages();
	}
	
	private void SchedulePages(){

		Page tempDequeued;
		
		for (int i = 0; i < size(); i++) {
			tempDequeued = get(i);
			LinkedList<Integer> tempIdxs = GetIndexesOf(tempDequeued);
			
			List<Instant> tempRegTimes = tempIdxs.stream()
					.map(idx -> Page.IntToInstant.apply(idx))
					.collect(Collectors.toList());
	
			tempDequeued.SetRegisterTimes(tempRegTimes);
		}		
	}
	
	public boolean addAllStrings(List <String> aCollection){
		
		aCollection.forEach(id -> add(StringToPage.apply(id)));
	
		return aCollection.size() == size();
	}
	
	public boolean addAllInt(List <Integer> aCollection){
		
		aCollection.forEach(id -> add(IntToPage.apply(id)));
	
		return aCollection.size() == size();
	}
	
	public static Function<String,Page> StringToPage = name -> new Page(name);
	public static Function<Integer,Page> IntToPage = name -> new Page(name);
	public static Function<Integer,Instant> IntToInstant = val -> Instant.ofEpochSecond(val);
//	public static Function<String,LinkedList<Integer>> StringToRegisterTimes = name -> new Page(name);


// region ******************************** Property_access_Methods ********************************
	
	public  int GetCycleSize(){						return _cycleSize;		}
	
	public  int GetFreeFramesSize(){	
		
		return _cycleSize;		
		}

	public  void SetCycleSize(int aCycleSize){	
		
		_cycleSize = aCycleSize;
		
		clear();
		
		List<Page> _tempNullPages = Stream.generate(() -> Page.NULL)
        	.limit(_cycleSize)
        	.collect(Collectors.toList());
		
		addAll(_tempNullPages);
		
	}

	public boolean enqueue(Page aProcess){			return super.enqueue(aProcess);		}

	public Page dequeue(){							return super.dequeue();		}
	
	public LinkedList<Integer> GetIndexesOf(Page aPage){
		
		LinkedList<Integer> _tempList =  new LinkedList<Integer>();
		
		for(int i =0 ; i <size();i++){
			
			if(get(i).equals(aPage)) _tempList.add(i);
		}
		
		return _tempList;
	}
	
	public void Reset(){
		Collections.fill(this, Page.NULL);
	}
	
//	public String toString(){
//		
//		String joinedFirstNames = stream()
//				 .map(val -> val.toString())
//				 .collect(Collectors.joining(", "));
//		
//		return joinedFirstNames;
//	}
//	public LinkedList<Integer> GetIndexesOf(Page aPage,String aChain){
////		
//		LinkedList<Integer> _tempList =  new LinkedList<Integer>();
////		
////		for(int i =0 ; i <aChain.length();i++){
////			
////			if(aChain.charAt(i).equals(String.valueOf(aPage.GetID()))) _tempList.add(i);
////		}
////		
//		return _tempList;
//		
//	}
// endregion


}
