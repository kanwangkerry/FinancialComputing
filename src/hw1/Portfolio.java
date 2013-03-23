package hw1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Portfolio Class, should implement IPortfolio interface
 * @author kerry
 */
public class Portfolio implements IPortfolio {
	/**
	 * Variables in the Portforlio Class:
	 * I use HashSet to save Positions because the order of the Positions
	 * does not matters if 2 portfolios are same, i.e. it should be a set,
	 * not a list.
	 */

	private HashSet<Position> positions = new HashSet<Position>();
	
	/**
	 * Also I need to save the PositionIter as a variable. When we need to
	 * get the iterator of the positions, we need to return it.
	 */
	private PositionIter iter;

	/**
	 * <p>This overrides the default equals function. It is overrided 
	 * because we need to use assertEquals in unit test.</p>
	 * <p>This function firstly check if this param obj is a instance of 
	 * Porfolio Class. If not it will return false;
	 * Then it will check the 2 objects by toString() method. If they 
	 * are same in string, then the content of 2 Portfolio should be the
	 * same.</p>
	 */
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Portfolio))
			return false;
		return this.toString().equals(obj.toString());
	}
	
	/**
	 * <p>This overrides the default toString function. It is overrided 
	 * because we need to use toString in equals method.</p>
	 * <p>This function firstly put all the elements in the HashSet
	 * positions into a new ArrayList, then sort the ArrayList by a new
	 * Comparator which just compare the symbol of 2 Positions. Thus the
	 * output string should be sorted by the positions' name, so the order
	 * will not matters.</p>
	 */
	@Override
	public String toString(){
		ArrayList<Position> value = new ArrayList<Position>(positions);
		Collections.sort(value, new Comparator<Position>(){
			@Override
			public int compare(Position o1, Position o2) {
				return o1.getSymbol().compareTo(o2.getSymbol());
			}
		});
		return value.toString();
	}
	
	/**
	 * Implementation of newTrade method. Make a new trade in the portfolio.
	 * <p>Firstly I use a iterator to scan the HashSet positions.</p>
	 * <p>When I found a position which have a same name with the parameter
	 * symbol, I set the boolean found to true and set the quantity of 
	 * this position to the new value (quantity+cur.getQuantity). Then I
	 * check if it is an empty position (quantity is 0). If so, I remove it 
	 * from the portfolio.</p>
	 * <p>If i did not find one, just make a new position and insert it
	 * into the portfolio.</p>
	 */
	@Override
	public void newTrade(String symbol, int quantity) {
		Iterator<Position> iter = positions.iterator();
		Position cur = null;
		boolean found = false;
		while(iter.hasNext()){
			cur =  iter.next();
			if(cur.getSymbol().equals(symbol)){
				found = true;
				break;
			}
		}
		if(!found){
			positions.add(new Position(symbol, quantity));
		}
		else{
			cur.setQuantity(cur.getQuantity()+quantity);
			if(cur.isEmptyPosition()){
				positions.remove(cur);
			}
		}
	}

	/**
	 * Get the position iterator of the porfolio.
	 * <p>Here we must firstly initial iter to the iterator of the HashSet positions.
	 * Then return the variable iter.</p>
	 */
	@Override
	public IPositionIter getPositionIter() {
		iter = new PositionIter(positions.iterator());
		return iter;
	}

}
