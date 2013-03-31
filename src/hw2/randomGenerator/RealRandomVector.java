package hw2.randomGenerator;

import hw2.simulation.Constant;

public class RealRandomVector implements RandomVectorGenerator {
	
	private RandomVectorGenerator normal;
	private boolean useAntiThetic = false;
	private double lastResult[];
	double[] result = new double[Constant.Days];
	
	public RealRandomVector(RandomVectorGenerator normal){
		this.normal = normal;
		
	}

	@Override
	public double[] getVector() {
		if(this.useAntiThetic){
			this.useAntiThetic = false;
			for(int i = 0 ; i < Constant.Days ; i++){
				result[i] = -lastResult[i];
			}
			return result;
		}
		else{
			lastResult = this.normal.getVector();
			this.useAntiThetic = true;
			return lastResult;
		}
	}

}
