package hw2.payout;

import hw2.stockpath.StockPath;

//The interface for calculating the payout function
public interface PayOut {
	public double getPayout(StockPath path);
}