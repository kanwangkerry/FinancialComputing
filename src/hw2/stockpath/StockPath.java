package hw2.stockpath;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

/**
 * The interface for creating StockPath. The returned list should be ordered by
 * date.
 * Should implement methods:
 * <p>
 * getPrices();
 * </p>
 * 
 * @author kerry
 * 
 */

public interface StockPath {
	/**
	 * Generate a stock path, which means a path of prices of the stock (the price 
	 * of the stock everyday).
	 * @return A list of (date, price) paris.
	 */
	public List<Pair<DateTime, Double>> getPrices();
}