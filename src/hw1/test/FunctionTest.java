package hw1.test;

import static org.junit.Assert.*;
import hw1.IPortfolio;
import hw1.IPosition;
import hw1.IPositionIter;
import hw1.Portfolio;

import org.junit.Test;

/**
 * Some compound test that test the function of Portfolio.
 * @author kerry
 *
 */
public class FunctionTest {
	
	/**
	 * testRemoveZero: test if the portfolio remains consistent if we make
	 * a position to a empty position.
	 * <p>After removing the only position, p1 should be equals to an empty
	 * portfolio.</p>
	 */
	@Test
	public void testRemoveZero1() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -100);
		assertEquals(p1, p2);
	}

	/**
	 * testRemoveZero: test if the portfolio remains consistent if we make
	 * a position to a empty position.
	 * <p>After removing "AA", p1 should only have "BB: 100" in the portfolio</p>
	 */
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
	
	/**
	 * testRemoveZero: test if the portfolio remains consistent if we make
	 * a position to a empty position.
	 * <p>After removing "AA", p1 should get a null if we try to get the
	 * next position of p1's iterator.</p>
	 */
	@Test
	public void testRemoveZero3() {
		IPortfolio p1 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -100);
		assertNull(p1.getPositionIter().getNextPosition());
	}
	
	/**
	 * testSamePortfolioAfterTrade: we give a random consequence of trades to p1
	 * and test if p1 equals p2 where p2 is the ideal result of such trades.
	 */
	@Test
	public void testSamePortfolioAfterTrade1() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -200);
		p1.newTrade("BB", 300);
		
		p2.newTrade("AA", -100);
		p2.newTrade("BB", 300);	
		assertEquals(p1, p2);
	}
	
	/**
	 * testSamePortfolioAfterTrade: we give a random consequence of trades to p1
	 * and test if p1 equals p2 where p2 is the ideal result of such trades.
	 */
	@Test
	public void testSamePortfolioAfterTrade2() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -200);
		p1.newTrade("BB", 300);
		p1.newTrade("CC", 10000);
		p1.newTrade("BB", 1000);
		
		p2.newTrade("AA", -100);
		p2.newTrade("BB", 1300);
		p2.newTrade("CC", 10000);	
		assertEquals(p1, p2);
	}
	

	/**
	 * testSamePositionAfterTrade: we test if the position is same as it should 
	 * be after a consequence of trads on the position.
	 * <p>Test if "AA" is correct after 2 trades.</p>
	 */
	@Test
	public void testSamePositionAfterTrade1() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", -200);
		
		p2.newTrade("AA", -100);
		assertEquals(p1.getPositionIter().getNextPosition(), p2.getPositionIter().getNextPosition());
	}
	
	/**
	 * testSamePositionAfterTrade: we test if the position is same as it should 
	 * be after a consequence of trads on the position.
	 * <p>Test if "AA" is correct after 2 trades.</p>
	 */
	@Test
	public void testSamePositionAfterTrade2() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 10000);
		
		p2.newTrade("AA", 10100);
		assertEquals(p1.getPositionIter().getNextPosition(), p2.getPositionIter().getNextPosition());
	}
	
	/**
	 * testSamePositionAfterTrade: we test if the position is same as it should 
	 * be after a consequence of trads on the position.
	 * <p>Test if "BB" is correct after 2 trades. Use an iterator to find "BB"
	 * in the portfolio.</p>
	 */
	@Test
	public void testSamePositionAfterTrade3() {
		IPortfolio p1 = new Portfolio();
		IPortfolio p2 = new Portfolio();
		p1.newTrade("AA", 100);
		p1.newTrade("AA", 10000);
		p1.newTrade("BB", 200);
		p1.newTrade("BB", -100);
		
		p2.newTrade("AA", 10100);
		p2.newTrade("BB", 100);
		
		IPositionIter iter;
		iter = p1.getPositionIter();
		IPosition pos1 = iter.getNextPosition();
		while(pos1 != null){
			if(pos1.getSymbol().equals("BB"))
				break;
			pos1 = iter.getNextPosition();
		}
		
		iter = p2.getPositionIter();
		IPosition pos2 = iter.getNextPosition();
		while(pos2 != null){
			if(pos2.getSymbol().equals("BB"))
				break;
			pos2 = iter.getNextPosition();
		}
		assertEquals(pos1, pos2);
	}

}
