package hw1;

/**
 * Position Class, should implements IPosition.
 * @author kerry
 *
 */
public class Position implements IPosition {
	private int quant;
	private String symbol;
	
	/**
	 * Constructor of a Position.
	 * @param s the symbole of the new Position;
	 * @param q the quantity of the new Position;
	 */
	Position(String s, int q){
		quant = q;
		symbol = s;
	}
	
	void setQuantity(int newValue){
		quant = newValue;
	}
	
	/**
	 * check if the Position is empty, i.e. the quantity is 0.
	 * @return ture if the quantity is 0; false otherwise.
	 */
	boolean isEmptyPosition(){
		return quant == 0;
	}
	
	/**
	 * Make a position to string. This is overrided because we need to
	 * make a portfolio to string, so firstly we need to make a position to string.
	 */
	@Override
	public String toString(){
		return symbol+": "+quant;
	}
	
	/**
	 * Override the equals method because we need to test if 2 positions are
	 * equal in unit test.
	 * <p>Firstly we need to check if the parameter obj is a instance of Positoin.
	 * return false if not.</p>
	 * <p>Then we check if they are the name in string. If they are same
	 * in string, the content should be equal.</p>
	 */
	@Override
	public boolean equals(Object obj){
		if(! (obj instanceof Position))
			return false;
		return this.toString().equals(obj.toString());
	}

	@Override
	public int getQuantity() {
		return quant;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

}
