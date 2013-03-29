package hw1.test;

import static org.junit.Assert.*;
import hw1.IPortfolio;
import hw1.IPosition;
import hw1.IPositionIter;
import hw1.Portfolio;

import org.junit.Test;

/**
 * Some simple unit test to test if the module is correct.
 * @author kerry
 *
 */
public class ModuleTest {

	
	/**
	 * testIter: test if the PositionIter acts correctly.
	 * <p>test if it will a position.</p>
	 */
	@Test
	public void testIter1() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		assertNotNull(iter.getNextPosition());
	}

	/**
	 * testIter: test if the PositionIter acts correctly.
	 * <p>test if it will return null if there is no positions.</p>
	 */
	@Test
	public void testIter2() {
		IPortfolio p1 = new Portfolio();
		IPositionIter iter = p1.getPositionIter();
		assertNull(iter.getNextPosition());
	}

	/**
	 * testIter: test if the PositionIter acts correctly.
	 * <p>test if it will return null after scanned all the positions.</p>
	 */
	@Test
	public void testIter3() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		iter.getNextPosition();
		assertNull(iter.getNextPosition());
	}

	/**
	 * testIter: test if the PositionIter acts correctly.
	 * <p>test if it will return a position before the iter is exhausted.</p>
	 */
	@Test
	public void testIter4() {
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

	/**
	 * testIter: test if the PositionIter acts correctly.
	 * <p>test if it will return null after scanned all the positions.</p>
	 */
	@Test
	public void testIter5() {
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

	/**
	 * testPosition: test the method that provided by Position class.
	 * <p>test if getQuantity is correct.</p>
	 */
	@Test
	public void testPosition1() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getQuantity(), 100);
	}

	/**
	 * testPosition: test the method that provided by Position class.
	 * <p>test if getSymbol is correct.</p>
	 */
	@Test
	public void testPosition2() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("IBM", 100);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getSymbol(), "IBM");
	}

	/**
	 * testPosition: test the method that provided by Position class.
	 * <p>test if setQuantity is correct: we make a trade and setQuantity should
	 * be called. The getQuantity should return the same as we imagined.</p>
	 */
	@Test
	public void testPosition3() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("IBM", 100);
		p1.newTrade("IBM", -200);
		IPositionIter iter = p1.getPositionIter();
		assertEquals(iter.getNextPosition().getQuantity(), -100);
	}

	/**
	 * testTrade: test the trade method of Portfolio.
	 * <p>iterate on a Portfolio after several trads and 
	 * check if the symbol and quantity are correct.</p>
	 */
	@Test
	public void testTrade1() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 1000);
		p1.newTrade("BB", 300);
		p1.newTrade("BB", -100);
		p1.newTrade("CC", -600);
		IPositionIter iter = p1.getPositionIter();
		IPosition p = iter.getNextPosition();
		if (p.getSymbol().equals("AA"))
			assertEquals(p.getQuantity(), 1100);
		else if (p.getSymbol().equals("BB"))
			assertEquals(p.getQuantity(), 200);
		else if (p.getSymbol().equals("CC"))
			assertEquals(p.getQuantity(), -600);
		else
			fail("Unexpected Name");
	}

	/**
	 * testTrade: test the trade method of Portfolio.
	 * <p>iterate on a Portfolio after several trads and 
	 * check if the symbol and quantity are correct.</p>
	 */
	@Test
	public void testTrade2() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 1000);
		p1.newTrade("BB", 300);
		p1.newTrade("BB", -100);
		p1.newTrade("CC", -600);
		IPositionIter iter = p1.getPositionIter();
		iter.getNextPosition();
		IPosition p = iter.getNextPosition();
		if (p.getSymbol().equals("AA"))
			assertEquals(p.getQuantity(), 1100);
		else if (p.getSymbol().equals("BB"))
			assertEquals(p.getQuantity(), 200);
		else if (p.getSymbol().equals("CC"))
			assertEquals(p.getQuantity(), -600);
		else
			fail("Unexpected Name");
	}

	/**
	 * testTrade: test the trade method of Portfolio.
	 * <p>iterate on a Portfolio after several trads and 
	 * check if the symbol and quantity are correct.</p>
	 */
	@Test
	public void testTrade3() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 1000);
		p1.newTrade("BB", 300);
		p1.newTrade("BB", -100);
		p1.newTrade("CC", -600);
		IPositionIter iter = p1.getPositionIter();
		iter.getNextPosition();
		iter.getNextPosition();
		IPosition p = iter.getNextPosition();
		if (p.getSymbol().equals("AA"))
			assertEquals(p.getQuantity(), 1100);
		else if (p.getSymbol().equals("BB"))
			assertEquals(p.getQuantity(), 200);
		else if (p.getSymbol().equals("CC"))
			assertEquals(p.getQuantity(), -600);
		else
			fail("Unexpected Name");
	}

	/**
	 * testTrade: test the trade method of Portfolio.
	 * <p>We add an unexpected name in the portfolio, and try to find 
	 * if it's correctly added into the portfolio. If true, it will throw an
	 * fail message.</p>
	 */
	@Test(expected = AssertionError.class)
	public void testTrade4() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 1000);
		p1.newTrade("BB", 300);
		p1.newTrade("BB", -100);
		p1.newTrade("CC", -600);
		p1.newTrade("DD", 300);
		IPositionIter iter = p1.getPositionIter();
		IPosition p = iter.getNextPosition();
		while (p != null) {
			if (p.getSymbol().equals("AA") || p.getSymbol().equals("BB")
					|| p.getSymbol().equals("CC"))
				p = iter.getNextPosition();
			else
				fail("Unexpected Name");
		}
	}

}
