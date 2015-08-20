package System.Tests.Lambdas;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class LambdasTest extends TestCase {

	Integer arg = 2;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testRetAddTwo() {
		
		int tempRet = RetAddTwo.apply(arg);
		assertEquals(4,tempRet);
	}
	
	@Test
	public void testMultipleByThree() {
		int tempRet = MultipleByThree.apply(arg);
		assertEquals(6,tempRet);
	}
	
	@Test
	public void testRetAddTwoAndThenMultipleByThree() {
		
		int tempRet = RetAddTwo.andThen(MultipleByThree).apply(arg);
		System.out.println(tempRet);
		assertEquals(12,tempRet);
	}
	
	@Test
	public void testRetAddTwoComposeMultipleByThree() {
		
		int tempRet = RetAddTwo.compose(MultipleByThree).apply(arg);
		System.out.println(tempRet);
		assertEquals(8,tempRet);
	}
	
	@Test
	public void testSetVal() {
		
		Integer _tempVal = 5;
//		SetVal.accept(arg);
		System.out.println(_tempVal);
//		assertEquals(3,_tempVal.intValue());
	}
	
	
//	@Test
//	public void testOptional() {
//		
//		LinkedList<Integer >_tempList = new LinkedList<>();
//		_tempList.add(new Integer(2));
//		
//		Optional<Integer>_tempMonad; 	
//		_tempMonad = Optional.ofNullable(new Integer(2));
//		_tempMonad.filter(val -> val>3);
//		
//		Integer _finalVal = _tempMonad.orElse(new Integer(3));
//		System.out.println("Optional " +_finalVal);
//		
//		assertEquals(new Integer(3),_finalVal);
////		Integer _tempVal = 5;
////		SetVal.accept(arg);
////		System.out.println(_tempVal);
////		assertEquals(3,_tempVal.intValue());
//	}
	
	@Test
	public void testOptionalOrElseGet() {
		
//		LinkedList<Integer >_tempList = new LinkedList<>();
//		_tempList.add(new Integer(2));
//		
//		Optional<Integer>_tempMonad; 	
//		_tempMonad = Optional.ofNullable(new Integer(2));
//		_tempMonad.filter(val -> val>3);
//		
////		_tempMonad.orElseGet(System.out.println("jol"));
		
	}

	
	private Function<Integer,Integer> RetAddTwo = x -> x+2;
	private Function<Integer,Integer> MultipleByThree = x -> x*3;
//	private Consumer<Integer> SetVal = x -> x + 3;
}
