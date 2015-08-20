package System.Managers.Elementary;

import java.util.function.Supplier;

public class Page extends PageTime implements Comparable<Page>{

	public Page() {						super();			}

	public Page(String aID) {			super(aID);			}
	
	public Page(int aID) {				super(aID);			}
	
//	public Page(Page aPage) {			super(aPage);		}
	
//	public Page(Page aPage,Collection<>) {			super(aPage);		}

	@Override
	public int compareTo(Page o) {		return super.compareTo(o);		}

	public static Page NULL = new Page();	
	
	public static Supplier<? extends Page> GetNullPage() {
		
		return () -> Page.NULL; 
	}
	
	
}