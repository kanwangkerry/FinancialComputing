package hw2.simulation;

/**
 * Track the state of the simulation.
 * 
 * @author kerry
 * 
 */
public class StateTracker {
	/**
	 * Average of the option values;
	 */
	double avgOfValue = 0;
	/**
	 * Average of the square of the option values;
	 */
	double avgOfValueSquare = 0;
	/**
	 * Number of the option values;
	 */
	int n = 0;

	/**
	 * Topic of the current simulation: used by the server to track the specific
	 * simulation
	 */
	String topic = null;

	/**
	 * Set the topic in the state.
	 * @param s topic of the simulation.
	 */
	public void setTopic(String s) {
		topic = s;
	}

	/**
	 * Update the current state.
	 * 
	 * @param value
	 *            The new option value.
	 */
	public void update(double value) {
		this.avgOfValue = (this.avgOfValue * n + value) / (n + 1);
		this.avgOfValueSquare = (this.avgOfValueSquare * n + value * value)
				/ (n + 1);
		n++;
	}

	/**
	 * Get the variance of the option values.
	 * 
	 * @return Variance of the option values.
	 */
	public double getSigma() {
		return Math.sqrt(this.avgOfValueSquare - this.avgOfValue
				* this.avgOfValue);
	}

	/**
	 * Get the mean of the option values.
	 * 
	 * @return Mean of the option values.
	 */
	public double getExpectation() {
		return avgOfValue;
	}

	/**
	 * Get the number of the option values.
	 * 
	 * @return Number of the option values.
	 */
	public int getN() {
		return n;
	}
}
