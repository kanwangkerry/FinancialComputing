package hw2.simulation;

import hw2.payout.AsiaCallOptionPayOut;
import hw2.payout.EuroCallOptionPayOut;
import hw2.payout.PayOut;
import hw2.stockpath.GBMStockPath;
import hw2.stockpath.StockPath;
import hw3.SimulationParameter;
import hw3.SimulationParameter.OptionType;

/**
 * Simulation manager of the Option price.
 * <p>
 * The simulation continously generate a new stock path and figure out the
 * payout, then update the track of the price state. This will end when we find
 * that the y*sigma/sqrt(N) is less then the error:
 * {@link Constant.errorPercentage} * E(v(s, t))
 * </p>
 * 
 * @author kerry
 * 
 */
public class SimulationManage {
	/**
	 * Simulate to figure out the option payout.
	 * <p>
	 * This simulation will do as described above. An important thing is firstly
	 * we must simulate the stock path several times (1000 in my implementation)
	 * to make avoid the uncertainty of random simulation
	 * </p>
	 * 
	 * @param sp The parameters of the simulation.
	 * @return Return the {@link StateTracker} of the current simulation, which
	 *         contains the states of the simulation.
	 */
	public StateTracker simulate(SimulationParameter sp) {
		PayOut p;
		if (sp.type == OptionType.Europen) {
			p = new EuroCallOptionPayOut(sp);
		} else {
			p = new AsiaCallOptionPayOut(sp);
		}
		StockPath path = new GBMStockPath(sp);
		StateTracker s = new StateTracker();
		double result;
		// pre-simulate: at least 1000 times to avoid unexceptional errors
		int n = 0;
		while (n++ < 1000) {
			result = p.getPayout(path);
			s.update(result);
		}
		while (true) {
			result = p.getPayout(path);
			s.update(result);
			if (checkIfStop(s))
				break;
		}
		System.out.println("Total simulation paths: " + (s.getN() + 1));
		System.out.println("Final simulation value for the option: "
				+ s.getExpectation());
		return s;
	}

	/**
	 * Check if the simulation should stop. The condition to stop is
	 * y*sigma/sqrt(N) is less then the error: {@link Constant.errorPercentage}
	 * * E(v(s, t)).
	 * This will print the state sometimes. 
	 * @param s The current state of the simulation.
	 * @return true if it should stop.
	 */
	public static boolean checkIfStop(StateTracker s) {
		if (s.getN() % 2000 == 0) {
			if(s.topic != null){
				System.out.print(s.topic + ": ");
			}
			System.out
					.println("Current simulation time: " + s.getN()
							+ " Current mean: " + s.getExpectation() 
							+ " Current Error: " + Constant.errorPercentage
							* s.getExpectation() + " Current sigma: "
							+ Constant.PercentIn96 * s.getSigma()
							/ Math.sqrt(s.getN()));
		}
		if (Constant.PercentIn96 * s.getSigma() / Math.sqrt(s.getN()) <= Constant.errorPercentage
				* s.getExpectation())
			return true;
		else
			return false;
	}

}
