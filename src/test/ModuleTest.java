package test;

import static org.junit.Assert.*;
import hw1.IPortfolio;
import hw1.IPositionIter;
import hw1.Portfolio;

import org.junit.Test;

public class ModuleTest {

	@Test
	public void testIter1(){
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		assertNotNull(iter.getNextPosition());
	}
	
	@Test
	public void testIter2(){
		IPortfolio p1 = new Portfolio();
		IPositionIter iter = p1.getPositionIter();
		assertNull(iter.getNextPosition());
	}
	
	@Test
	public void testIter3(){
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		iter.getNextPosition();
		assertNull(iter.getNextPosition());
	}
	
	@Test
	public void testIter4(){
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 50);
		p1.newTrade("BB", 1000);
		p1.newTrade("BB", 100);
		p1.newTrade("CC", 100);
		IPositionIter iter = p1.getPositionIter();
		iter.getNextPosition();
		iter.getNextPosition();
		assertNotNull(iter.getNextPosition());
	}
	
	@Test
	public void testIter5(){
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 50);
		p1.newTrade("BB", 1000);
		p1.newTrade("BB", 100);
		p1.newTrade("CC", 100);
		IPositionIter iter = p1.getPositionIter();
		iter.getNextPosition();
		iter.getNextPosition();
		iter.getNextPosition();
		assertNull(iter.getNextPosition());
	}
	
	@Test
	public void testPosition1() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getQuantity(), 100);
	}
	
	@Test
	public void testPosition2() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("IBM", 100);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getSymbol(), "IBM");
	}

}
