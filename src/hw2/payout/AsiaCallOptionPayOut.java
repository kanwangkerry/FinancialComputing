package hw2.payout;

import hw2.stockpath.StockPath;
import hw3.SimulationParameter;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

/**
 * Implementation of Aisa call option payout.
 * Implement method:
 * <p>public double getPayout(StockPath path)</p>
 * @author kerry
 *
 */
public class AsiaCallOptionPayOut implements PayOut {
	/**
	 * Settings for calculate the payout.
	 */
	SimulationParameter p;
	public AsiaCallOptionPayOut(SimulationParameter s){
		p = s;
	}
	/**
	 * Get the value of the Aisa call option with the given stock path.
	 * <p>For Asia call option, the pay out should be the maximum between 
	 * 0 and value of the average price on the stock path minus strike price.</p>
	 */
	@Override
	public double getPayout(StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		double sum = 0;
		for(int i = 1 ; i <= p.duration ; i++){
			sum += prices.get(i).getValue();
		}
		return Math.pow(Math.E, -p.r * p.duration)
				* Math.max(sum/(p.duration)- p.strikePrice, 0);
	}

}
