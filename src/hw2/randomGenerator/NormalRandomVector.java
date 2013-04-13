package hw2.randomGenerator;

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
	 * Length of the vector.
	 */
	public int duration;
	/**
	 * A local variable to save the Random Vector. This is designed to optimize
	 * memory usage and to avoid too much memory allocation and collection.
	 */
	double[] result;
	
	public NormalRandomVector(int d){
		duration = d;
		result = new double[duration];
	}

	/**
	 * Get a vector who's elements obey a normal distribution.
	 * <p>Should use the lib commons math3.</p>
	 */
	@Override
	public double[] getVector() {
		NormalDistribution normal = new NormalDistribution();
		for (int i = 0; i < duration; i++) {
			result[i] = normal.sample();
		}
		return result;
	}

}
