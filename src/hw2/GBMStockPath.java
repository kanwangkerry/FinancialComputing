package hw2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class GBMStockPath implements StockPath {
	
	List<Pair<DateTime, Double>> path;
	
	private int index;
	private double[] randomVector;
	
	private double localConstant;
	
	public GBMStockPath(){
		RandomVectorGenerator normal = new NormalRandomVector();
		RandomVectorGenerator g = new RealRandomVector(normal);
		this.randomVector = g.getVector();
		this.index = 0;
		this.path = new ArrayList<Pair<DateTime, Double>>();
		localConstant = Constant.r - Constant.sigma*Constant.sigma;
	}
	
	private double getNextRandom(){
		return randomVector[index++];
	}

	Pair<DateTime, Double> getNextPrice(){
		Pair<DateTime, Double> temp;
		temp = path.get(path.size()-1);
		double price = temp.getValue() * Math.pow(Math.E, localConstant+ Constant.sigma* this.getNextRandom());
		Pair<DateTime, Double> result = new Pair<DateTime, Double>(temp.getKey().plusDays(1), price);
		return result;
	}
	
	@Override
	public List<Pair<DateTime, Double>> getPrices() {
		int n = 0;
		Pair<DateTime, Double> temp = new Pair<DateTime, Double>(new DateTime(), Constant.InitValue);
		path.add(temp);
		while(n < Constant.Days){
			path.add(this.getNextPrice());
		}
		return path;
	}

}
