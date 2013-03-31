package hw2.payout;

import hw2.simulation.Constant;
import hw2.stockpath.StockPath;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

/**
 * Implementation of Europe call option payout.
 * Implement method:
 * <p>public double getPayout(StockPath path)</p>
 * @author kerry
 *
 */
public class EuroCallOptionPayOut implements PayOut {
	/**
	 * Get the value of the Europe call option with the given stock path.
	 * <p>For Europe call option, the pay out should be the maximum between 
	 * 0 and value of the last days's price on the stock path minus strike price.</p>
	 */
	@Override
	public double getPayout(StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		return Math.pow(Math.E, -Constant.r * Constant.Days)
				* Math.max(prices.get(Constant.Days).getValue()
						- Constant.EuroStrikePrice, 0);
	}

}
