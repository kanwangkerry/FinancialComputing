package hw2.stockpath;

import hw2.randomGenerator.NormalRandomVector;
import hw2.randomGenerator.RandomVectorGenerator;
import hw2.randomGenerator.RealRandomVector;
import hw2.simulation.Constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class GBMStockPath implements StockPath {
	
	List<Pair<DateTime, Double>> path;
	
	private int index;
	private double[] randomVector;
	
	private double localConstant;
	private RandomVectorGenerator g;
	
	public GBMStockPath(){
		RandomVectorGenerator normal = new NormalRandomVector();
		g = new RealRandomVector(normal);
		this.path = new ArrayList<Pair<DateTime, Double>>();
		localConstant = Constant.r - Constant.sigma*Constant.sigma/2;
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
		this.randomVector = g.getVector();
		this.index = 0;
		path.clear();
		Pair<DateTime, Double> temp = new Pair<DateTime, Double>(new DateTime(), Constant.InitValue);
		path.add(temp);
		while(n++ < Constant.Days){
			path.add(this.getNextPrice());
		}
		return path;
	}

}
