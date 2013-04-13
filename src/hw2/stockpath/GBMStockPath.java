package hw2.stockpath;

import hw2.randomGenerator.RandomVectorGenerator;
import hw2.randomGenerator.RealRandomVector;
import hw3.SimulationParameter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

/**
 * Implementation of StockPath.
 * This implementation uses the Geometric Brownian motion (GBM) model, 
 * which defines the price of day t.
 * 
 * @author kerry
 *
 */
public class GBMStockPath implements StockPath {
	
	/**
	 * A list to save the stock path. Designed to optimize memory usage.
	 */
	List<Pair<DateTime, Double>> path;
	
	private int index;
	private double[] randomVector;
	
	private double localConstant;
	
	/**
	 * A random vector Generator of the stock path.
	 * In our implementation this should be a RealRandomVector.
	 */
	private RandomVectorGenerator g;
	
	/**
	 * A parameter to save the configuration of a stock path.
	 */
	SimulationParameter p;
	/**
	 * Constructor.
	 * <p> Need to initial the random vector generator.
	 * </p>
	 */
	
	public GBMStockPath(SimulationParameter s){
		p = s;
		g = new RealRandomVector(p.duration);
		this.path = new ArrayList<Pair<DateTime, Double>>();
		localConstant = p.r - p.sigma*p.sigma/2;
	}
	
	/**
	 * Private function to get the next number in the random vector. 
	 * @return the next random number in the vector.
	 */
	private double getNextRandom(){
		return randomVector[index++];
	}

	/**
	 * Figure out the price of next day. This is relevant to the current price
	 * , sigma, r and a random sample of Normal Distribution.
	 * <p>The method is given by the GBM model.</p>
	 * @return The (date, price) pair of the next day.
	 */
	Pair<DateTime, Double> getNextPrice(){
		Pair<DateTime, Double> temp;
		temp = path.get(path.size()-1);
		double price = temp.getValue() * Math.pow(Math.E, localConstant+ p.sigma* this.getNextRandom());
		Pair<DateTime, Double> result = new Pair<DateTime, Double>(temp.getKey().plusDays(1), price);
		return result;
	}
	
	/**
	 * Generate a stock path using the GBM model. 
	 * <p>This method calls {@link getNextPrice} the Generate the price of 
	 * the next day. The initial price is given by the problem and has been set
	 * in the {@link SimulationParameter} {@link p}.</p>
	 * <p>Each time this function been called, it should generate a new random
	 * vector first, and then generate the price.</p>
	 */
	@Override
	public List<Pair<DateTime, Double>> getPrices() {
		int n = 0;
		this.randomVector = g.getVector();
		this.index = 0;
		path.clear();
		Pair<DateTime, Double> temp = new Pair<DateTime, Double>(new DateTime(), p.InitValue);
		path.add(temp);
		while(n++ < p.duration){
			path.add(this.getNextPrice());
		}
		return path;
	}

}
