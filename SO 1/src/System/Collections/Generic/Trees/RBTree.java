package System.Collections.Generic.Trees;

import java.util.LinkedList;
import java.util.stream.Collectors;

import System.Collections.Generic.Enums.COLOUR;


public class RBTree<K extends Comparable<K> , V>
{
	    private COLOUR _actualColor;
	    private Node _root;

	    public Node RootForTest()
	    {
	    	return _root;
	    }
	    public RBTree()  {
	    	_root=null;}
	    
	    private boolean isRed( Node aKey){
	    	return aKey!= null && _actualColor==COLOUR.RED;
	    }

	    public K find(K aKey)
	    {
	    	Node t = search(aKey);
	    	return t !=null ? t._key._keyValue : null;
	    }
	        
	    private Node search(K _key)     {
	        Node node = _root;
	        int cmp=0;
	        while (node != null &&(cmp = _key.compareTo((K) node._key))!=0)
	        {
	        	node = cmp < 0 ? node.left : node.right;
	        }
	        
	        return node;
	    }
	    
	    private Node rotateL(Node t){ 
	    
	    	Node aKey=t.right;   
	    	t.right=aKey.left;	    
	    	aKey.left=t;	    
	    	aKey._actualColor=_actualColor;  
	    	_actualColor=COLOUR.RED;     
	    	return aKey; 
	      }
	      
	    private Node rotateR(Node t){ 
	    	Node aKey=t.left;
	      t.left=aKey.right;
	      aKey.right=t;
	      aKey._actualColor=_actualColor;
	      _actualColor=COLOUR.RED;
	      return aKey; }
	      
	     private void colorFlip(Node t){
	    	if( t._actualColor== COLOUR.RED)
	    		{
	    		 t._actualColor = COLOUR.BLACK;
	    		}
	    		else
	    		{
	    			t._actualColor = COLOUR.RED;
	    		}
	    	
	    	if( t.left._actualColor== COLOUR.RED)
    		{
	    		t.left._actualColor = COLOUR.BLACK;
    		}
	    		else
	    		{
	    			t.left._actualColor = COLOUR.RED;
	    		}
	    	
	    	if( t.right._actualColor== COLOUR.RED)
    		{
	    		t.right._actualColor = COLOUR.BLACK;
    		}
		    	else
		    	{
	    		 t.right._actualColor = COLOUR.RED;
		    	}
	     }

	      public void insert(K aKey, V aValue)
	      {
//	    	  if(_root == null)
//	    	  {
//	    		  _root = new Node(aKey);
//	    	  }
	    	  _root= insert(aKey,aValue,_root);
	    	  
	    	  _root._actualColor=COLOUR.BLACK;
	    }
	      
	      protected Node insert(K aKey,V aValue, Node t) {
	       
	    	  if(t== null)
	    		  {
	    		  	t=new Node(aKey,aValue);
	    		  }
	    	  else 
	    	  { 
	    		  int cmp=aKey.compareTo((K) t._key._keyValue);
	         
		    	  if(isRed(t.left) && isRed(t.right))
		             {
		    		  colorFlip(t);
		             }
		          if(cmp<0)
		        	  {
		        	  	t.left=insert(aKey,aValue,t.left);
		        	  }
		             else if(cmp>0) 
		             {
		            	 t.right=insert(aKey,aValue,t.right);
		             }
		             else
		            	 {
		            	 	throw new RuntimeException("Duplicate                                  "+aKey.toString());
		            	 }
	          t=fixUp(t);  
	              }     
	    	  
	        return t;
	    }

	    private Node fixUp(Node t) {   
	    	if(isRed(t.right))
	              t=rotateL(t);
	        if(isRed(t.left) && isRed(t.left.left))
	            t=rotateR(t);
	        if(isRed(t.left) && isRed(t.right))
	            colorFlip(t);
	        return t; 
	    }    
	    
	   private Node moveRedR( Node t) {
		   colorFlip(t);
	    
		   if(isRed(t.left.left))
	    
		   { 
			   t=rotateR(t); colorFlip(t); 
		   }
	     return t; 
	  }
	     
	   private Node moveRedL( Node t) { 
		   colorFlip(t);
	   
		   if(isRed(t.right.left))
	       { 
			   t.right=rotateR(t.right); t=rotateL(t); colorFlip(t);
	       }
		   return t;
	   }

	   public void delete(K aKey){
		  _root=delete(aKey,_root);
	      if(_root!=null) _root._actualColor=COLOUR.BLACK;
	      }
	    
	   protected Node delete(K aKey, Node t) {

	    	if(t==null) 
	    	  {
	    		System.out.println("Pusty Root");
	    	  	throw new RuntimeException("Not found  "+aKey);
	    	  }
	    	else 
	    	{ 
	    	  int cmp=aKey.compareTo( t._key._keyValue);
	             
	    	  if(cmp<0) 
	          { if(!isRed(t.left) && !isRed(t.left.left))
	                            t=moveRedL(t);
	                  t.left=delete(aKey,t.left);
	          }
	           
	    	  else
	    	  {
	    		  if( isRed(t.left)) t=rotateR(t);
	                  if(aKey.compareTo(t._key._keyValue)==0&&t.right == null) return null;
	                    else 
	                    { if(!isRed(t.right) && !isRed(t.right.left))
	                               t=moveRedR(t);
	                           if(aKey.compareTo(t._key._keyValue)>0)
	                                t.right=delete(aKey,t.right);
	                           else t.right=detachMin(t.right, t);
	                    }  
	    	  }
	     }   
	      
	     return fixUp(t);
	     
	    }  
	     
	    protected Node detachMin(Node t, Node del) {
	     
	    	if(t.left==null) 
	    	{
	    		del._key=t._key; t=null;
	    	}   
	    	else 
	    	{
	    		if(!isRed(t.left) && !isRed(t.left.left))    		 
	    			{
	    				t=moveRedL(t);
	    			}
	    		
	             t.left=detachMin(t.left,del);
	             t=fixUp(t); 
	        }
	    	
	      return t;
	    }  
	  
	    public String toString() {
	    	return toString(_root,0);
	    }
	     
	    private String toString(Node t,int pos)  {   
		    String result="";
		    if(_root.left!=null || _root.right!=null)
		    {
		    	String spaces="                                                                                                                                                                                                     ";
		    	if(t!=null ) result=result+toString(t.right,pos+4)+spaces.substring(0,pos)
		    			+String.format("%s%s",t._key,(_actualColor== COLOUR.RED ? "/R" :"/B"))+toString(t.left,pos+4);
		    	else result=result+String.format("%n");
		    }
		    else
		    {
		    	result+=_root._key._keyValue.toString()+"V:"+_root._key.GetValues() + (_actualColor== COLOUR.RED ? "/R" :"/B");
		    }
		    return result;
	    }    
	    
	    class Node {
	    	RBElement<K,V> _key;
	    	Node left;
	    	Node right;
	      
	      COLOUR _actualColor;
	      
	      Node(K aKey,V aValue)
	      { 
	    	  _key = new RBElement<K,V>(aKey);
	    	  _key.add(aValue);

	    	  left = right = null;
	    	  _actualColor = COLOUR.RED;
	      }
	    }  
	    
	    @SuppressWarnings("serial")
		public class RBElement<K extends Comparable<K>, V> extends LinkedList<V>
	    {
			public K _keyValue;
	    	
	    	@SuppressWarnings("unchecked")
			public RBElement(Object aKey) 
	    	{
	    		super();
	    		_keyValue = (K) aKey;
	    	}

	    	@Override
	    	public boolean add(V aValue)
	    	{
	    		return super.add(aValue);
	    	}
	    	
	    	public COLOUR GetColour()
	    	{
	    		return null;
	    	}

	    	public String GetValues() 
	    	{
	    		return stream().
	    				map(temp -> temp.toString())
	    				.collect(Collectors.joining(","));
	    	}
	
	    }
	}


//region OLD

//public static final Object ELEMENT_NULL = null;
//private Object[] _keys;
////private BSTPrinter _printer;
//
//private int _size;
//boolean [] _ifThereIsSomething;
//
//public RBTree()
//{
//	
////	_shift = new BSTShiftBuilder(_size,4);
//	
//	_ifThereIsSomething = new boolean[aSize+1];
//	initializeABooleans();
//}
//
//// Zadanie na 3.0:
//// ZaimplementowaÄ‡ piÄ™Ä‡ poniĹĽszych funkcje dla drzewa RBTree, implementacja moĹĽe byÄ‡ rekurencyjna:
//public boolean insert(K key, V value)
//{
//		if(_ifThereIsSomething[1] == false)
//		{
//
//			Add(key,1,value,1);
//			_ifThereIsSomething[1] = true;			
//		}
//		else
//		{
//			for(int i = 1 ;i<=CheckLevel()*2;)
//			{			
//				if(_keys[i].compareTo(key)>=0)
//				{
//					i=2*i;
//				}
//				else
//				{
//					i=2*i +1;
//				}
//				if(!_ifThereIsSomething[i])
//				{
//					_keys[i] = key;
//					_values[i] = value;
//					_ifThereIsSomething[i] = true;
//					return true;
//				}
//				else
//				{
//					if(check((K) key) == value)
//					{
//						_values[i] = value;
//						return true;
//					}
//				}
//			}
//		}
//		return true;
//
//	}
//	// Dodawanie elementĂłw do drzewa
//	// return true jeĹ›li udaĹ‚o siÄ™ dodaÄ‡ element
//	// return false w przeciwnym razie
//
//public void printInorder(PrintStream out)
//{	
////	for(int i = 1 ;i<=CheckLevel()*2;)
////	{			
////		if(_keys[i].compareTo(key)>=0)
////		{
////			i=2*i;
////		}
////		else
////		{
////			i=2*i +1;
////		}
//}
//public void printPreorder(PrintStream out)
//{
//}
//public void printPostorder(PrintStream out)
//{
//}
//private void Add(String key,int iK, Integer value,int iV)
//{
//	_keys[iK] = key;
//	_values[iV] = value;
//}
//private void initializeABooleans()
//{
//	for (int i = 0; i < _ifThereIsSomething.length; i++) 
//	{
//		_ifThereIsSomething[i] = false;
//	}
//	
//}
//public V check(K key)
//{
//	V returnedValue = null;
//	
//	for(int i = 1 ; i<(_size+1);i++)
//	{
//		if(_keys[i] == key)
//		{
//			returnedValue = (V) _values[i];
//		}
//	}		
//	return returnedValue;
//}
////// Zadanie na +0.5:
////// ZaimplementowaÄ‡ poniĹĽszÄ… funkcjÄ™, implementacja moĹĽe byÄ‡ rekurencyjna:
////public boolean remove(K key)
////{
//////	_keys.
////}
//
//public Integer CheckLevel()
//{
//	int i =-1;
//	boolean breakFlag = false;
//	
//	if(_size == 0)
//	{
//		return 0;
//	}
//	else
//	{
//		while(!breakFlag)
//		{
//			i++;
//			breakFlag = BelongsTo.CheckNaturalSerie(_size,new NaturalSerie(2, i, i+1,true,true));
//		}
//	}
//	
//	return i;
//}
//
//// PRINTER
////private BSTShiftBuilder _shift;
//
////public void PrintTree()
////{
////	String tempString = new String();
////	
////	for(int i = 1;i<_shift.size();i++)
////	{
////		tempString = "";
////		
////		for(int j = 1;j<(int) Math.pow(2, i);j++)
////		{
////			if(j!=1 && j==(int) Math.pow(2, i-1))
////			{
////				tempString+=_shift.get(i-1);
////			}
////			tempString+=_shift.get(i)+_keys[j];		
////		}
////		
////		System.out.println(tempString);		
////	}
////}
//// Zadanie na +1.0:
//// Implementacja funkcji insert, remove, check w sposĂłb iteracyjny!
//// Zadanie na +0.5:
//// Wybierz jednÄ… z funkcji printInorder, printPreorder, printPostorder, zaimplementuj tÄ… funkcjÄ™ w sposĂłb iteracyjny!
//// Uwaga: wybĂłr funkcji moĹĽe byÄ‡ krytyczny.
//
//public RBElementTest Root() 
//{
//	// TODO Auto-generated method stub
//	return null;
//}
//
//public COLOUR GetColour()
//{
//	return COLOUR.COLOUR.RED;
//}
//
//public boolean IfRotate() {
//	// TODO Auto-generated method stub
//	return false;
//}
//
//public boolean IfRecolour() {
//	// TODO Auto-generated method stub
//	return false;
//}
//
//public boolean ContainsKey(K aKey) 
//{
//	// TODO Auto-generated method stub
//	return false;
//}
//
//public void RemoveKey(RBElementTest<Integer, Integer> _newElement) {
//	// TODO Auto-generated method stub
//	
//}
//
//public boolean CheckAfterRecolour() {
//	// TODO Auto-generated method stub
//	return false;
//}
//
//public void InsertKey(Object aValue,Object aKey) 
//{
//	
//}
//
// MoĹĽna dodawaÄ‡ funkcje do klasy ale muszÄ… one byÄ‡ private!
// Do zrobienia: funkcja main, testy dla kaĹĽdej wymaganej funkcji WYMAGANE.
// Uwaga: NIE PRZYJMUJÄ� zadania bez testĂłw dla KAĹ»DEGO przypadku specjalnego kaĹĽdej zaimplementowanej funkcji.
// endregion