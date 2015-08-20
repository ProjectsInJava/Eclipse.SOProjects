package System.Diagnostics;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import System.Collections.Generic.GenQueue;
import System.Hardware.RAM;
import System.Managers.Collections.PageQueue;
import System.Managers.Elementary.Page;

public class ProcessMyWithData extends ProcessMyWithTime
{
	private PageQueue _data;
	private AtomicInteger  _amountOfPageFaults;
	protected PageQueue _frames;
	protected int _framesStartIdx;
	protected int _framesEndIdx;
	
// region ******************************** CTORS ******************************************************************
	protected ProcessMyWithData(){											
		super();		
		init();			
		GenerateOwnPageRefference();
	}

	protected ProcessMyWithData(ProcessMyWithData aCopyProcess){			
		super(aCopyProcess);
		init();
		CopyFrom(aCopyProcess);
	}	
	
	protected ProcessMyWithData(int aStartTime, int aExecute){				
		super(aStartTime,aExecute);	
		init();	
		GenerateOwnPageRefference();
	}
	protected ProcessMyWithData(String aStartTime, String aExecute){		
		super(aStartTime,aExecute);
		init(); 
		GenerateOwnPageRefference();
	}
	
	protected void CopyFrom(ProcessMyWithData aCopiedProcess){				
		_data = aCopiedProcess._data;		
	}
// endregion
// region ******************************** PAGE_WORKING_METHODS **************************************************	
	public PageQueue GetPages(){
		return _data;			
	}
	
	public void SetPages(PageQueue aPages){	
		_data = aPages;				
	}
	
	public void AddPagesByProcessExecuteRatio(PageQueue aPages){
		_data.clear();
		
		for (int i = 0; i < GetTimeExecuteValue(); i++) {				
			_data.enqueue(aPages.dequeue());		
		}	
	}
	
	public void AddPage(Page aPage)	{										
		_data.enqueue(aPage);			
	}
	
	public void AddAdditionalPages(PageQueue aPages){
		
		int aTempSize = aPages.size();
		for (int i = 0; i < aTempSize; i++) {								
			_data.enqueue(aPages.dequeue());	
		}	
	}
	
//	public boolean GiveFrames(List<Page> tempPageQueue) {
//		
////		return _frames.addAll(tempPageQueue.subList(0, tempPageQueue.size()));
//		return _frames.addAll(tempPageQueue);
//	}
	
	public void SyncSingleFrameToRAM(int aIdx,Page aPage){		
		RAM.Instance.set(aIdx, aPage);
	}	
	
	public PageQueue GetFrames() {		
		return _frames;
	}
	
	public int GetFramesStartIdx(){
		return _framesStartIdx;
	}

	public int GetFramesEndIdx(){
		return _framesEndIdx;
	}
	
	private void init(){
		_data = new PageQueue();
		_frames = new PageQueue();
		_amountOfPageFaults = new AtomicInteger(0);
	}
	
	public void ResetData(){												
		_data.clear();				
	}
	private void GenerateOwnPageRefference(){
		
		Random _randVal = new Random();
		
		GenQueue<String> _tempGenQueue = new GenQueue<String>();
		
		while(_tempGenQueue.size()!=GetTimeExecuteValue())
		{
			String randVal = String.valueOf(_randVal.nextInt((int) GetTimeExecuteValue()));
			
			if(!_tempGenQueue.contains(randVal))							
				_tempGenQueue.enqueue(randVal);	
		}
		
		int tempGenQueueSize = _tempGenQueue.size();
		
		for(int i = 0 ;i<tempGenQueueSize;i++)								
			_data.enqueue(new Page(_tempGenQueue.dequeue()));	
	}
	
	public void AddPageFault(){
		_amountOfPageFaults.incrementAndGet();
	}
	
	public Integer GetPageFault(){
		return _amountOfPageFaults.get();
	}

	// endregion
// region ******************************** STANDARD_METHODS ******************************** 
	
	public int compareTo(ProcessMyWithData arg0) {		
		int first = 1;
	
		if(_data.containsAll(arg0.GetPages())) first = 0;

		return first + super.compareTo(arg0);
	}
	
	public String toString(){	
		String first = "";
		String second  = "";
		
		boolean secondBool = false;
		
//		first = _data.toString();
//		secondBool  = _frames.size()!=0 ? true : false ;
//		second = secondBool ? "true" : "false";
		second = String.valueOf(_amountOfPageFaults.get());
		
		return first+",FR:"+second+","+ super.toString();	
	}
	

	//endregion
}
// region ******************************** UNUSED_METHODS_WITH_ADDITIONAL_INF ******************************** 
//	
//	private static GenericQueue<String> _hiddenTrainingInstructions =  new GenericQueue<String>();
//	private static GenericQueue<String> _hiddenTestInstructions =  new GenericQueue<String>();
//}
////•	Procesy wykonują się współbieżnie (ale niekoniecznie równolegle – podział czasu) 
////•	Stany procesu: 
////	- nowy: proces został utworzony 
////	- wykonywany: są wykonywane instrukcje procesu 
////	- oczekujący: proces czeka na wystąpienie zdarzenia 
////	- gotowy: proces czeka na przydzielenie procesora 
////	- zakończony: proces zakończył wykonanie 
////	
////	Reprezentacja procesu w systemie (zawartość bloku kontrolnego procesu): 
////		- stan procesu 
////		- identyfikator (unikalny numer – w UNIX’ie – PID)
////		- licznik rozkazów 
////		- rejestry procesora 
////		- wskaźnik do kolejki procesów
////		- informacje o pamięci zajętej przez proces 
////		- wykaz otwartych (używanych plików)
////
////		Tworzenie procesu:
////			-	za pomocą funkcji systemowej (np. fork() w UNIX)
////			-	proces utworzony przez inny proces (macierzysty, ew. rodzic) nazywamy potomnym.
////			-	potomek  uzyskuje nową pulę zasobów lub korzysta z zasobów rodzica (mniejsze przeciążenie systemu).
////
////			Zakończenie procesu:
////			-	po ostatniej instrukcji.
////			-	specjalna funkcją (np. exit() w UNIX)
////			-	na żądania rodzica ( abort() )
////			-	niekiedy: zakończenie kaskadowe: po zakończeniu rodzica kończone są procesy potomne.
////
////private void implementHiddenTrainingInstructions() 
////{
////	_hiddenTrainingInstructions.Enqueue("!!!!!<6><6> Rule!!!!!");
////	_hiddenTrainingInstructions.Enqueue("Start 0110LEFT");
////	_hiddenTrainingInstructions.Enqueue("CountDown ");
////	_hiddenTrainingInstructions.Enqueue("It Ram Updates");
////	_hiddenTrainingInstructions.Enqueue("Right Tying");
////	_hiddenTrainingInstructions.Enqueue("End 0001");
////	_hiddenTrainingInstructions.Enqueue("Assume 0000");
////
////	_hiddenTrainingInstructions.Enqueue("Hello World Cascade");
////	_hiddenTrainingInstructions.Enqueue("Hello World Right Tying");
////	_hiddenTrainingInstructions.Enqueue("Start");
//////	_hiddenTrainingInstructions.Enqueue("Left Tying");
////	_hiddenTrainingInstructions.Enqueue("Stress flow on tics 15bpm no errors chill");
////	_hiddenTrainingInstructions.Enqueue("Right to Left Leg");
////
////	_hiddenTrainingInstructions.Enqueue("End 0000");
////	_hiddenTrainingInstructions.Enqueue("Ram Updates");
////	_hiddenTrainingInstructions.Enqueue("Left Tying - Comeback");		
////}
////
//////	private ProcessQueue<Process> _fatherQueue;
//private FIFOPageQueue _instructions = new FIFOPageQueue();

////
////private void implementHiddenTestInstructions() 
////{
////	_hiddenTestInstructions.Enqueue("as like above but uptime replaced with flow , no mind");
////}

//public ProcessQueue<Process> FatherQueue()
//{
//	return _fatherQueue;
//}
//
//public String FatherQueueName()
//{
//	String tReturnedValue = _fatherQueue == null ? "Without Family Queue" : _fatherQueue.GetName();
//	return tReturnedValue;
//}
//
//public void SetFatherQueue(ProcessQueue<Process> aFatherQueue)
//{
//	_fatherQueue = aFatherQueue;
//}
// endregion