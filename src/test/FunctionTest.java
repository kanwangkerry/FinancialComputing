package test;

import static org.junit.Assert.*;
import hw1.IPortfolio;
import hw1.IPositionIter;
import hw1.Portfolio;
import hw1.Position;

import org.junit.Test;

public class FunctionTest {

	@Test
	public void testRemoveZero() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("BB", 100);
		p1.newTrade("AA", -100);
		p2.newTrade("BB", 100);
		assertEquals(p1, p2);
	}
	
	@Test
	public void testNew1() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getQuantity(), 100);
	}
	
	@Test
	public void testNew2() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("IBM", 100);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getSymbol(), "IBM");
	}

}
