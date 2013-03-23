package hw1;

/**
 * Interface of Portfolio
 * The functions of a Portfolio Classthat can used accessed from
 * outside should only contains:
 * void newTrade(String symbol, int quantity)
 * and
 * IPositionIter getPositionIter();
 * @author kerry
 */
public interface IPortfolio {
	
	/**
	 * make a new trade in the portfolio.
	 * @param symbol the name of the position in the trade.
	 * @param quantity the quantity of the position that will be trade, e.g. 
	 * -100 means sells 100 of this position and 200 means buy 200;
	 */
	public void newTrade(String symbol, int quantity);
	
	/**
	 * get the position iterator of this Portfolio
	 * @return the PositionIter of the Portfolio
	 */
	public IPositionIter getPositionIter();
}