package hw1;

public class Position implements IPosition {
	private int quant;
	private String symbol;
	
	Position(String s, int q){
		quant = q;
		symbol = s;
	}
	
	void setQuantity(int newValue){
		quant = newValue;
	}
	
	boolean isEmptyPosition(){
		return quant == 0;
	}
	
	public String toString(){
		return symbol+": "+quant;
	}
	
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
