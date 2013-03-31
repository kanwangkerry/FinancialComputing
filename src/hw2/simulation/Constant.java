package hw2.simulation;

/**
 * The class which defines all the constant of the problem.
 * @author kerry
 *
 */
public class Constant {
	/**
	 * The option expiration duration.
	 */
	public final static int Days = 252;
	/**
	 * The y value of 96% errors in Normal Distribution. 
	 */
	public final static double PercentIn96 = 2.054;
	/**
	 * Daily volatility.
	 */
	public final static double sigma = 0.01;
	/**
	 * Daiyly r.
	 */
	public final static double r = 0.0001;
	/**
	 * Initial price of the IBM option.
	 */
	public final static double InitValue = 152.35;
	/**
	 * The strike price of the Europe Call option.
	 */
	public final static double EuroStrikePrice = 165;
	/**
	 * The strike price of the Asia Call option.
	 */
	public final static double AsiaStrikePrice = 164;
	/**
	 * Error percentage.s
	 */
	public final static double errorPercentage = 0.01;
}
