package hw1;

public interface IPortfolio {
	
	public void newTrade(String symbol, int quantity);
	public IPositionIter getPositionIter();
}