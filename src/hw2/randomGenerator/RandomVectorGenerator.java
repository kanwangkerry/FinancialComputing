package hw2.randomGenerator;

/**
 * The interface for Random vector generator
 * Should implement methods:
 * <p>
 * public double[] getVector();
 * </p>
 * @author kerry
 *
 */
public interface RandomVectorGenerator {
	/**
	 * Generate a array who's elements are all random.
	 * Generally the distribution of the elements should be a certain distribution.
	 * 
	 * @return An array of double who's elements are all random.
	 */
	public double[] getVector();
}