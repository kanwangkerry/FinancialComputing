package hw2.randomGenerator;

import hw2.simulation.Constant;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Implementation of NormalRandomVector. Implemented methods:
 * <p>
 * public double[] getVector();
 * </p>
 * 
 * @author kerry
 * 
 */
public class NormalRandomVector implements RandomVectorGenerator {
	/**
	 * A local variable to save the Random Vector. This is designed to optimize
	 * memory usage and to avoid too much memory allocation and collection.
	 */
	double[] result = new double[Constant.Days];

	/**
	 * Get a vector who's elements obey a normal distribution.
	 * <p>Should use the lib commons math3.</p>
	 */
	@Override
	public double[] getVector() {
		NormalDistribution normal = new NormalDistribution();
		for (int i = 0; i < Constant.Days; i++) {
			result[i] = normal.sample();
		}
		return result;
	}

}
