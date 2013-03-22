package hw1;

import java.util.Iterator;

public class PositionIter implements IPositionIter{
	private Iterator<Position> i;
	
	PositionIter(Iterator<Position> iterator){
		i = iterator;
	}

	@Override
	public IPosition getNextPosition() {
		if(i.hasNext())
			return (IPosition) i.next();
		else
			return null;
	}

}
