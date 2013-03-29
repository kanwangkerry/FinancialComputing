package hw2;

import org.apache.commons.math3.distribution.NormalDistribution;

public class NormalRandomVector implements RandomVectorGenerator {

	@Override
	public double[] getVector() {
		double[] result = new double[Constant.days+1];
		NormalDistribution normal = new NormalDistribution();
		for(int i = 0 ; i <= Constant.days ; i++){
			result[i] = normal.sample();
		}
		return result;
	}

}
