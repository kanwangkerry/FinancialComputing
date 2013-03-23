package hw1;

/**
 * Interface of PositionIter
 * It should contain a method to return the next postion in the portfolio.
 * If it has already reach the end of the portfolio, 
 * it should return null;
 * @author kerry
 */
public interface IPositionIter {
	/**
	 * Get the next postion of the iterator.
	 * <p>This method returns the next Position in the bag and null if we already
	 * iterated over all the position</p>
	 * @return the next Position;
	 */
	public IPosition getNextPosition();
}