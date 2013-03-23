package hw1;

import java.util.Iterator;

/**
 * PositionIter Class, should implement IPositionIter
 * @author kerry
 *
 */
public class PositionIter implements IPositionIter{
	private Iterator<Position> i;
	
	/**
	 * Constructor of PositionIter. 
	 * Adapter: make the private iterator i to the input iterator. So just
	 * need to call the input one when we need the method of i.
	 * @param iterator the input iterator.
	 */
	PositionIter(Iterator<Position> iterator){
		i = iterator;
	}

	/**
	 * Get the next position in the portfolio.
	 * <p>Use adapter: call the local variable i's method to impelment 
	 * PositionIter's method.</p> 
	 * <p>If there are no elements in i, we return null. Or we return the
	 * next element in i.</p>
	 */
	@Override
	public IPosition getNextPosition() {
		if(i.hasNext())
			return (IPosition) i.next();
		else
			return null;
	}

}
