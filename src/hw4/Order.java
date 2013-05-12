package hw4;

/**
 * A class to save the information of a order in the book.
 * 
 * @author kerry
 * 
 */
public class Order {
	private String symbol;
	private String orderId;
	private double price;
	private int size;
	private boolean isAskOrder;
	private boolean isDelete = false;

	/**
	 * Constructor. Create a book.
	 * 
	 * @param s
	 *            The symbol of the order.
	 * @param size
	 *            The size of the order.
	 * @param id
	 *            The id of the order.
	 * @param p
	 *            The price of the order.
	 * @param isAsk
	 *            Is this a ask order or a bid order.
	 */
	public Order(String s, int size, String id, double p, boolean isAsk) {
		this.symbol = s;
		this.orderId = id;
		this.setPrice(p);
		this.size = size;
		this.isAskOrder = isAsk;
	}

	/**
	 * Get the symbol of the order.
	 * 
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Get the id of the order.
	 * 
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Set the price of the order.
	 * <p>
	 * Note that this price could be NaN, so we need to set it to the MIN_VALUE
	 * or MAX_VALUE, depend on if it is a ask order.
	 * </p>
	 * 
	 * @param p
	 *            The price of the order.
	 */
	public void setPrice(double p) {
		if (Double.isNaN(p)) {
			if (this.isAskOrder) {
				price = Double.MIN_VALUE;
			} else {
				price = Double.MAX_VALUE;
			}
		} else
			this.price = p;
	}

	/**
	 * Get the price of the order.
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set the size of the order.
	 * @param nSize
	 */
	public void setSize(int nSize) {
		this.size = nSize;
	}

	/**
	 * Get the size of the order.
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Test if the order an ask order.
	 * @return {@link true} if it is a ask order.
	 */
	public boolean isAsk() {
		return isAskOrder;
	}

	/**
	 * Set the order to a "tomb".
	 */
	public void setDeleted() {
		this.isDelete = true;
	}

	/**
	 * Test if this order is a tomb.
	 * @return
	 */
	public boolean isDel() {
		return this.isDelete;
	}
}
