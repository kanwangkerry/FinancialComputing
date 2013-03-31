package hw2.payout;

import hw2.stockpath.StockPath;

/**
 * The interface for calculating the payout function
 * Method of this interface:
 * public double getPayout(StockPath path);
 * @author kerry
 *
 */
public interface PayOut {
	/**
	 * Get the payout from a certain stock path.
	 * <p>This method should use the given StockPath to figure out the payout 
	 * of the option.</p>
	 * @param path A given stock path.
	 * @return the pay out of this option under this given stock path.
	 */
	public double getPayout(StockPath path);
}