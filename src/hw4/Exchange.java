package hw4;

import java.util.HashMap;
import java.util.Map;

/**
 * A Class the manage the Exchange.
 * @author kerry
 *
 */
public class Exchange {
	/**
	 * A map of the symbol and the {@link ExchangeBook}. For each Symbol, we should have 
	 * a individual {@link ExchangeBook}.
	 */
	public HashMap<String, ExchangeBook> books = new HashMap<String, ExchangeBook>();
	/**
	 * A map of orderID and {@link Order}. Used to mark the tomb to remove and update.
	 */
	Map<String, Order> orders = new HashMap<String, Order>();

	/**
	 * Insert a {@link Order} into its {@link ExchangeBook}
	 * @param o The order to be inserted.
	 */
	public void insertOrder(Order o) {
		if (!orders.containsKey(o.getOrderId())) {
			orders.put(o.getOrderId(), o);
		}
		if (books.containsKey(o.getSymbol())) {
			books.get(o.getSymbol()).insertOrder(o);
		} else {
			ExchangeBook temp = new ExchangeBook();
			temp.insertOrder(o);
			books.put(o.getSymbol(), temp);
		}
	}

	/**
	 * Update a order with {@link CxROrder}.
	 * <p>To update or remove, we need to use the map {@link orders} to get the 
	 * order directly, updated it or mark as a tomb, and push it back to the queue.</p>
	 * @param o
	 * @throws Exception
	 */
	public void updateOrder(CxROrder o) throws Exception {
		if (!orders.containsKey(o.getOrderId())) {
			throw new Exception("Order ID not found");
		}
		Order curOrder = orders.get(o.getOrderId());
		// remove the order from the original queue
		curOrder.setDeleted();

		// insert the updated order into queue
		if (o.getSize() != 0) {
			Order nOrder = new Order(curOrder.getSymbol(), o.getSize(),
					curOrder.getOrderId(), o.getPrice(), curOrder.isAsk());
			this.insertOrder(nOrder);
		}
	}
	
	/**
	 * Print the book's detail
	 */
	public void printBookDetail()
	{
		for(String s : books.keySet()){
			System.out.println("Book: "+ s);
			books.get(s).printBookDetail();
		}
	}
	
	/**
	 * Print the book's top
	 */
	public void printBookTop()
	{
		for(String s : books.keySet()){
			System.out.println("Book: "+ s);
			books.get(s).printBookTop();
		}
		
	}

	/**
	 * Print the book's number of orders
	 */
	public void printBookOrderNumber()
	{
		for(String s : books.keySet()){
			System.out.println("Book: "+ s);
			books.get(s).printBookOrderNum();
		}
		
	}
	
	/**
	 *  Set the exchange to silent, and remove all the redundent output.
	 */
	public void setSilent(){
		for(String s : books.keySet()){
			books.get(s).silent = true;
		}
	}

}
