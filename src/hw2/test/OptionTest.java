package hw2.test;

import static org.junit.Assert.*;

import java.util.List;

import hw2.payout.AsiaCallOptionPayOut;
import hw2.payout.EuroCallOptionPayOut;
import hw2.payout.PayOut;
import hw2.randomGenerator.RandomVectorGenerator;
import hw2.randomGenerator.RealRandomVector;
import hw2.simulation.Constant;
import hw2.simulation.SimulationManage;
import hw2.simulation.StateTracker;
import hw2.stockpath.GBMStockPath;
import hw2.stockpath.StockPath;
import hw3.SimulationParameter;
import hw3.SimulationParameter.OptionType;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * Some unit test to test the functions of some modules.
 * 
 * @author kerry
 * 
 */
public class OptionTest {

	/**
	 * Test the pay out of the Europe call option.
	 */
	@Test
	public void testPayOutEu() {
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 165, OptionType.Europen, "Test");
		PayOut e = new EuroCallOptionPayOut(p);
		double result = e.getPayout(new GBMStockPath(p));
		assertTrue(result >= 0);
	}

	/**
	 * Test the pay out of the Asia call option.
	 */
	@Test
	public void testPayOutAs() {
		
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Test");
		PayOut e = new AsiaCallOptionPayOut(p);
		double result = e.getPayout(new GBMStockPath(p));
		assertTrue(result >= 0);
	}

	/**
	 * Test the generation of the random vector. Test the anti-thetic.
	 */
	@Test
	public void testRandomVector1() {
		RandomVectorGenerator r = new RealRandomVector(Constant.Days);
		double[] result1 = r.getVector();
		double[] result2 = r.getVector();
		Assert.assertEquals(result1[100] + " " + result2[100], result1[100],
				-result2[100], 0);
	}

	/**
	 * Test the generation of the random vector. Test the length of the random
	 * vector. It should be equal to the {@link Constant.Days}
	 */
	@Test
	public void testRandomVector2() {
		RandomVectorGenerator r = new RealRandomVector(Constant.Days);
		double[] result1 = r.getVector();
		Assert.assertEquals(result1.length, Constant.Days);
	}

	/**
	 * Test the generation of the random vector. Test the anti-thetic for the
	 * whole vector.
	 */
	@Test
	public void testRandomVector3() {
		RandomVectorGenerator r = new RealRandomVector(Constant.Days);
		double[] result1 = r.getVector();
		double[] result2 = r.getVector();
		for (int i = 0; i < result2.length; i++) {
			result2[i] = -result2[i];
		}
		Assert.assertArrayEquals(result1, result2, 0);
	}

	/**
	 * Test the stock path. Test the length of the stock path.
	 */
	@Test
	public void testStockPath1() {
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 165, OptionType.Asia, "Test");
		StockPath s = new GBMStockPath(p);
		Assert.assertEquals(s.getPrices().size(), Constant.Days + 1);
	}

	/**
	 * Test the stock path. Test the initial value of the stock path. It should
	 * be equal to {@link Constant.InitValue}.
	 */
	@Test
	public void testStockPath2() {
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Test");
		StockPath s = new GBMStockPath(p);
		Assert.assertEquals(s.getPrices().get(0).getValue(), new Double(
				Constant.InitValue));
	}

	/**
	 * Test stock path. Test the date.
	 */
	@Test
	public void testStockPath3() {
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Test");
		StockPath s = new GBMStockPath(p);
		List<Pair<DateTime, Double>> temp = s.getPrices();
		Assert.assertEquals(temp.get(3).getKey().getDayOfYear(), temp.get(4)
				.getKey().getDayOfYear() - 1);
	}

	/**
	 * Test stock path. Test the date.
	 */
	@Test
	public void testStockPath4() {
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 165, OptionType.Asia, "Test");
		StockPath s = new GBMStockPath(p);
		List<Pair<DateTime, Double>> temp = s.getPrices();
		Assert.assertTrue(temp.get(100).getKey().getDayOfYear() < temp.get(200)
				.getKey().getDayOfYear());
	}

	/**
	 * Test the simulatoin manager. Test the end condition.
	 */
	@Test
	public void testSimulationManageEuro() {
		SimulationManage m = new SimulationManage();
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 165, OptionType.Europen, "Test");
		StateTracker s = m.simulate(p);
		Assert.assertTrue(Constant.PercentIn96 * s.getSigma()
				/ Math.sqrt(s.getN()) <= Constant.errorPercentage
				* s.getExpectation());
	}

	/**
	 * Test the simulatoin manager. Test the end condition.
	 */
	@Test
	public void testSimulationManageAsia() {
		SimulationManage m = new SimulationManage();
		SimulationParameter p = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Test");
		StateTracker s = m.simulate(p);
		Assert.assertTrue(Constant.PercentIn96 * s.getSigma()
				/ Math.sqrt(s.getN()) <= Constant.errorPercentage
				* s.getExpectation());
	}
}
