package hw2;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class AsiaCallOptionPayOut implements PayOut {
	@Override
	public double getPayout(StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		double sum = 0;
		for(int i = 1 ; i <= Constant.Days ; i++){
			sum += prices.get(i).getValue();
		}
		return Math.pow(Math.E, -Constant.r * Constant.Days)
				* Math.max(sum/(Constant.Days)- Constant.AsiaStrikePrice, 0);
	}

}
