package System.Hardware;

import java.util.HashSet;

import System.Managers.Collections.PageQueue;
import System.Managers.Elementary.Page;

@SuppressWarnings("serial")
public class HDD extends HashSet<Page>
{
	public static final HDD Instance= new HDD();

	private HDD(){
		super();
	}
	
	public Page Get(Page aValue){	
		Page _valToRet = null;
			
		_valToRet =	stream().filter(page -> page.equals(aValue))
				.findAny().get();
			
		return _valToRet;
	}
	
	public void Allocate(PageQueue aReferencePageQueueString) {	
		addAll(new HashSet<>(aReferencePageQueueString));
	}
	
	public boolean Update(Page aValue){			
		boolean _valRet = true;
		_valRet =  remove(aValue);
		add(aValue);
			
		return _valRet;
	}
}