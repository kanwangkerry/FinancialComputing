package hw2;

import org.apache.commons.math3.distribution.NormalDistribution;

public class NormalRandomVector implements RandomVectorGenerator {
	double[] result = new double[Constant.Days];
	@Override
	public double[] getVector() {	
		NormalDistribution normal = new NormalDistribution();
		for(int i = 0 ; i < Constant.Days ; i++){
			result[i] = normal.sample();
		}
		return result;
	}

}
