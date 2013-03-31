package hw2.payout;

import hw2.simulation.Constant;
import hw2.stockpath.StockPath;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class EuroCallOptionPayOut implements PayOut {
	@Override
	public double getPayout(StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		return Math.pow(Math.E, -Constant.r * Constant.Days)
				* Math.max(prices.get(Constant.Days).getValue()
						- Constant.EuroStrikePrice, 0);
	}

}
