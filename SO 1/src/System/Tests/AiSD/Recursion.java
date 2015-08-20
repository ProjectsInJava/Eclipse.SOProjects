package System.Tests.AiSD;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Recursion {

	public Elem pocz;
	LinkedList<Integer> _lista = new LinkedList<>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		pocz = new Elem(0,new Elem(1,null));
		
//		for(int i =0 ; i < _lista.size();i++){
//			pocz 
//		}
//		
	}

	@Test
	public void test() {
		
		pocz = new Elem(0,new Elem(1,new Elem(2,new Elem(3,new Elem(4,new Elem(5,new Elem(6,new Elem(7,new Elem(8,null)))))))));
		
//		System.out.println("elem Pocz");
//		print(pocz);
//		System.out.println(_lista);
//		_lista.clear();
//		
//		System.out.println("elem copy ofPocz");
//		Elem copyy = copy2(pocz);
////		pocz= null;
//		_lista.clear();
//		
//		System.out.println("new elem copy ofPocz");
//		print(copyy);
//		System.out.println(_lista);
//		_lista.clear();
		
//		System.out.println("new elem copyRataj ofPocz");
//		Elem copyy = kopiuj(pocz);
//		pocz= null;
//		print(copyy);
//		System.out.println(_lista);
//		_lista.clear();
//		
		System.out.println("new elem ItcopyRataj ofPocz");
		Elem gcopyy = kopiujIt(5);
		pocz= null;
		print(gcopyy);
		System.out.println(_lista);
		_lista.clear();
//		
//		System.out.println("elem copyCoDrugi of Pocz");
//		print(copyCoDrugi(pocz));
//		System.out.println(_lista);
		
	}
	
	class Elem
	{
		public int val;
		public Elem nast;
		
		public Elem(int aVal,Elem aNast){
			val = aVal;
			nast = aNast;
		}
	}

	void print(Elem a){
		
		if(a==null) {
			System.out.println("koniec");
		}else
		{
			_lista.add(a.val);
			print(a.nast);
		}	
	}
	
	Elem copy(Elem a){
		
		Elem pom = new Elem(-1,null);
		
		if(a==null) {
			return null;
		}else
		{
			pom.val = a.val; // acumulator
			pom.nast = copy(a.nast); // 
		}	
		
		return pom;
	}
	
	Elem kopiuj(Elem pocz) {
		
		Elem acc = new Elem(-1,null);
		
			if(pocz == null){
				return null;
				
			}
			else{
				acc =  new Elem(pocz.val,kopiuj(pocz.nast));
			}
			
			return acc;
		}
	
	
	Elem kopiujIt(int n) {
		
			int i = 0;
			
			Elem acc = new Elem(-1,null);
			
			if(i == n){
				return null;	
			}
			else{
				acc = new Elem(i++,kopiuj(pocz.nast));
			}
			
			return acc;
	}
	 
	Elem copy2(Elem a){
		
		Elem pom = new Elem(-1,null);
		
		if(a==null) {
			return null;
		}else
		{
			pom = new Elem(a.val,copy(a.nast)); // 
		}	
		
		return pom;
	}
	
	Elem copyCoDrugi(Elem a){
		
		Elem pom = new Elem(-1,null);
		
		if(a==null || a.nast==null) {
			return null;
		}else
		{
			pom.val = a.val; // acumulator
			pom.nast = copyCoDrugi(a.nast.nast); // 
		}	
		
		return pom;
	}
	
}
