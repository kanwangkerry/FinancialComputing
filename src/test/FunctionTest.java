package test;

import static org.junit.Assert.*;
import hw1.IPortfolio;
import hw1.Portfolio;

import org.junit.Test;

public class FunctionTest {
	
	@Test
	public void testRemoveZero1() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -100);
		assertEquals(p1, p2);
	}

	@Test
	public void testRemoveZero2() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("BB", 100);
		p1.newTrade("AA", -100);
		p2.newTrade("BB", 100);
		assertEquals(p1, p2);
	}
	
	@Test
	public void testRemoveZero3() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -100);
		assertNull(p1.getPositionIter().getNextPosition());
	}
	

}
