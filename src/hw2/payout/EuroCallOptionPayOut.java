package hw2.payout;

import hw2.stockpath.StockPath;
import hw3.SimulationParameter;

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
	 * Settings of calculating the payout
	 */
	SimulationParameter p;
	public EuroCallOptionPayOut(SimulationParameter s){
		p = s;
	}
	/**
	 * Get the value of the Europe call option with the given stock path.
	 * <p>For Europe call option, the pay out should be the maximum between 
	 * 0 and value of the last days's price on the stock path minus strike price.</p>
	 */

	@Override
	public double getPayout(StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		return Math.pow(Math.E, -p.r * p.duration)
				* Math.max(prices.get(p.duration).getValue()
						- p.strikePrice, 0);
	}

}
