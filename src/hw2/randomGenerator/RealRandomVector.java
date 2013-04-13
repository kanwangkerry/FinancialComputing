package hw2.randomGenerator;

/**
 * The real implementation of RandomVectorGenerator.
 * <p>
 * Implements a Anti-Thetic decorator. Use a Normal distributed
 * RandomVectorGenerator as decoratee.
 * </p>
 * 
 * @author kerry
 * 
 */
public class RealRandomVector implements RandomVectorGenerator {
	
	/**
	 * Lenght of the vector
	 */
	int duration;
	/**
	 * A normal distributed RandomVectorGenerator. Used as decoratee.
	 */
	RandomVectorGenerator normal;
	/**
	 * record this time should use Anti-Thetic or not.
	 */
	private boolean useAntiThetic = false;
	/**
	 * Record the last result This is designed to save memory and to avoid too
	 * much memory allocation and collection.
	 */
	double lastResult[];
	/**
	 * Record the Anti-Thetic result. This is designed to save memory and to
	 * avoid too much memory allocation and collection.
	 */
	double[] result;

	/**
	 * Contructor. Make a decorator with a normal distributed
	 * RandomVectorGenerator as decoratee.
	 * 
	 * @param normal
	 *            A normal distributed RandomVectorGenerator
	 */
	public RealRandomVector(int Duration) {
		this.duration = Duration;
		result = new double[duration];
		this.normal = new NormalRandomVector(Duration);
	}

	/**
	 * Generate a real random vector.
	 * <p>
	 * This method use a Anti-thetic style: when we generate a random vector, we
	 * should immediately generate a vector who's every element is the negative
	 * of the former one.
	 * </p>
	 * <p>
	 * We generate the first vector from the a normal distributed
	 * RandomVectorGenerator
	 * </p>
	 */
	@Override
	public double[] getVector() {
		if (this.useAntiThetic) {
			this.useAntiThetic = false;
			for (int i = 0; i < this.duration; i++) {
				result[i] = -lastResult[i];
			}
			return result;
		} else {
			lastResult = this.normal.getVector();
			this.useAntiThetic = true;
			return lastResult;
		}
	}

}
