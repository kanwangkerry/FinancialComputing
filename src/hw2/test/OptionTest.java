package hw2.test;

import static org.junit.Assert.*;
import hw2.AsiaCallOptionPayOut;
import hw2.Constant;
import hw2.EuroCallOptionPayOut;
import hw2.GBMStockPath;
import hw2.NormalRandomVector;
import hw2.PayOut;
import hw2.RandomVectorGenerator;
import hw2.RealRandomVector;
import hw2.SimulationManage;
import hw2.StateTracker;
import hw2.StockPath;

import org.junit.Assert;
import org.junit.Test;

public class OptionTest {

	@Test
	public void testPayOutEu() {
		PayOut e = new EuroCallOptionPayOut();
		double result = e.getPayout(new GBMStockPath());
		assertTrue(result >= 0);
	}

	@Test
	public void testPayOutAs() {
		PayOut e = new AsiaCallOptionPayOut();
		double result = e.getPayout(new GBMStockPath());
		assertTrue(result >= 0);
	}


	@Test
	public void testRandomVector1() {
		RandomVectorGenerator g = new NormalRandomVector();
		RandomVectorGenerator r = new RealRandomVector(g);
		double[] result1 = r.getVector();
		double[] result2 = r.getVector();
		Assert.assertEquals(result1[100] + " " + result2[100], result1[100],
				-result2[100], 0);
	}

	@Test
	public void testRandomVector2() {
		RandomVectorGenerator g = new NormalRandomVector();
		RandomVectorGenerator r = new RealRandomVector(g);
		double[] result1 = r.getVector();
		Assert.assertEquals(result1.length, Constant.Days);
	}

	@Test
	public void testRandomVector3() {
		RandomVectorGenerator g = new NormalRandomVector();
		RandomVectorGenerator r = new RealRandomVector(g);
		double[] result1 = r.getVector();
		double[] result2 = r.getVector();
		for (int i = 0; i < result2.length; i++) {
			result2[i] = -result2[i];
		}
		Assert.assertArrayEquals(result1, result2, 0);
	}

	@Test
	public void testSimulationManageEuro() {
		SimulationManage m = new SimulationManage();
		StateTracker s = m.simulate(true);
		Assert.assertTrue(Constant.PercentIn96 * s.getSigma()
				/ Math.sqrt(s.getN()) <= Constant.errorPercentage
				* s.getException());
	}
	
	@Test
	public void testSimulationManageAsia() {
		SimulationManage m = new SimulationManage();
		StateTracker s = m.simulate(false);
		Assert.assertTrue(Constant.PercentIn96 * s.getSigma()
				/ Math.sqrt(s.getN()) <= Constant.errorPercentage
				* s.getException());
	}
}
