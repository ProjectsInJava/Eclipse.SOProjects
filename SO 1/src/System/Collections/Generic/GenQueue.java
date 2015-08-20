package System.Collections.Generic;

import java.util.ArrayList;
import java.util.Collection;


@SuppressWarnings("serial")
public class GenQueue<T extends Comparable<T>> extends ArrayList<T>
{
	
	public GenQueue() {									super();					}
	
	public GenQueue(int aSize) {						super(aSize);					}

	public GenQueue(Collection<? extends T> c) {		super(c);					}
	
	public T dequeue(){			
		
			T tempVal = null;
			
			if(size()!=0)
			{
				tempVal =get(0);
				remove(0);
			}
			else
			{
				
			}
			
			
			return tempVal;				
		}
	
	public boolean enqueue(T aValue){					return add(aValue);		}
	
	public T getLast(){									return get(size()-1);	}
	
	public T getFirst(){								return get(0);			}
	
	public T peek() {									return get(0);			}
	
}
