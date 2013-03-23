package hw1;

/**
 * Interface of Position.
 * The functions of a Position class that can be accessed from 
 * outside should only contains:
 * <p>int getQuantity();</p>
 * <p>String getSymbol();</p>
 * 
 * Other implementation details should not be explosed to the outside.
 * @author kerry
 */
public interface IPosition {
	/**
	 * Get the quantity of the position;
	 * @return quantity of the position;
	 */
	public int getQuantity();
	/**
	 * Get the symbol of the position;
	 * @return symbol of the position;
	 */
	public String getSymbol();
}