package hw2;

public class RealRandomVector implements RandomVectorGenerator {
	
	private RandomVectorGenerator normal;
	private boolean useAntiThetic = false;
	private double lastResult[];
	
	public RealRandomVector(RandomVectorGenerator normal){
		this.normal = normal;
		
	}

	@Override
	public double[] getVector() {
		if(this.useAntiThetic){
			
			for(int i = 0 ; i < Constant.Days ; i++){
				lastResult[i] = -lastResult[i];
			}
			return lastResult;
		}
		else{
			lastResult = this.normal.getVector();
			return lastResult;
		}
	}

}
