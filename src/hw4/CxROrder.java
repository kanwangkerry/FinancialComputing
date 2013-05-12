package hw4;

/**
 * The class used to update a order.
 * @author kerry
 *
 */
public class CxROrder {
	private String orderId;
	private double price;
	private int size;
	
	/**
	 * Constructor. Make a CxROrder.
	 * @param size the updated size of the order.
	 * @param id the id of the order to be updated.
	 * @param p the updated price of the order.
	 */
	public CxROrder(int size, String id, double p){
		this.orderId = id;
		this.price = p;
		this.size = size;
	}
	
	/**
	 * Get the id of the order to be updated.
	 * @return
	 */
	public String getOrderId(){
		return orderId;
	}
	
	/**
	 * Get the updated price.
	 * @return
	 */
	public double getPrice(){
		return price;
	}
	
	/**
	 * Get the updated size.
	 * @return
	 */
	public int getSize(){
		return size;
	}

}
