package hw1;

import java.util.HashSet;
import java.util.Iterator;

public class Portfolio implements IPortfolio {
	private HashSet<Position> positions = new HashSet<Position>();
	private PositionIter iter;

	@Override
	public void newTrade(String symbol, int quantity) {
		Iterator<Position> iter = positions.iterator();
		Position cur = null;
		while(iter.hasNext()){
			cur =  iter.next();
			if(cur.getSymbol().equals(symbol)){
				break;
			}
		}
		if(cur == null){
			positions.add(new Position(symbol, quantity));
		}
		else{
			cur.setQuantity(cur.getQuantity()+quantity);
			if(cur.isEmptyPosition()){
				positions.remove(cur);
			}
		}
	}

	@Override
	public IPositionIter getPositionIter() {
		iter = new PositionIter(positions.iterator());
		return iter;
	}

}
