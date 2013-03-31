package hw2;

public class StateTracker {
	double avgOfValue=0;
	double avgOfValueSquare = 0;
	int n = 0;
	public void update(double value){
		this.avgOfValue = (this.avgOfValue * n + value) / (n+1);
		this.avgOfValueSquare = (this.avgOfValueSquare * n + value*value)/(n+1);
		n++;
	}
	
	public double getSigma(){
		return Math.sqrt(this.avgOfValueSquare - this.avgOfValue*this.avgOfValue);
	}
	
	public double getException(){
		return avgOfValue;
	}
	
	public int getN(){
		return n;
	}
}
