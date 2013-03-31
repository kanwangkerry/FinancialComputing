package hw2;

public class SimulationManage {
	public StateTracker simulate(boolean isEuro) {
		PayOut p;
		if (isEuro) {
			p = new EuroCallOptionPayOut();
		} else {
			p = new AsiaCallOptionPayOut();
		}
		StockPath path = new GBMStockPath();
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
				+ s.getException());
		return s;
	}

	public static boolean checkIfStop(StateTracker s) {
		if (s.getN() % 20000 == 0) {
			System.out
					.println("Current simulation time: " + s.getN()
							+ " Current Error: " + Constant.errorPercentage
							* s.getException() + " Current sigma: "
							+ Constant.PercentIn96 * s.getSigma()
							/ Math.sqrt(s.getN()));
		}
		if (Constant.PercentIn96 * s.getSigma() / Math.sqrt(s.getN()) <= Constant.errorPercentage
				* s.getException())
			return true;
		else
			return false;
	}

}
