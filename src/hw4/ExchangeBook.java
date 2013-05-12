package hw4;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * ExchangeBook is the book for a certain symbol.
 * @author kerry
 *
 */
public class ExchangeBook {

	/**
	 * A boolean to check if it is silent mode
	 */
	public static boolean silent = false;

	/**
	 * Bid book is a HashMap, use price as key and a {@link LinkedList} of
	 * orders as value.
	 */
	// use comparator to make suer the sort is correct when we meet a NaN.
	SortedMap<Double, LinkedList<Order>> bid = new TreeMap<Double, LinkedList<Order>>(
			new Comparator<Double>() {
				@Override
				public int compare(Double o1, Double o2) {
					if (Double.isNaN(o1))
						return 1;
					if (Double.isNaN(o2))
						return -1;
					return Double.compare(o1, o2);
				}

			});

	/**
	 * Ask book is a HashMap, use price as key and a {@link LinkedList} of
	 * orders as value.
	 */
	// use comparator to make suer the sort is correct when we meet a NaN.
	SortedMap<Double, LinkedList<Order>> ask = new TreeMap<Double, LinkedList<Order>>(
			new Comparator<Double>() {
				@Override
				public int compare(Double o1, Double o2) {
					if (Double.isNaN(o1))
						return -1;
					if (Double.isNaN(o2))
						return 1;
					return Double.compare(o1, o2);
				}
			});

	/**
	 * Insert a order into the book.
	 * 
	 * @param o
	 *            The order to be inserted.
	 */
	public void insertOrder(Order o) {
		Double price = new Double(o.getPrice());
		// check if the order is a ask. If so add it into the ask book
		// ohter wise add it to the bid book.
		if (o.isAsk()) {
			// if there is already some order at this price,
			// just add the order into the queue.
			if (ask.containsKey(price)) {
				LinkedList<Order> temp = ask.get(price);
				temp.addLast(o);
			} else {
				// else we need to create a new queue at this price.
				LinkedList<Order> temp = new LinkedList<Order>();
				temp.addLast(o);
				ask.put(price, temp);
			}
		} else {
			// Everything is similar as the ask order, but operate on the bid
			// book.
			if (bid.containsKey(price)) {
				LinkedList<Order> temp = bid.get(price);
				temp.addLast(o);
			} else {
				LinkedList<Order> temp = new LinkedList<Order>();
				temp.addLast(o);
				bid.put(price, temp);
			}
		}

		// update the books. This update will make only one trade,
		// so we need to keep updating it until there is no more trades.
		while (updateBooks())
			;
	}

	/**
	 * Get the first exist order at this Price
	 * <p>
	 * Noticing that we have some "tomb" orders, we need to check if the order
	 * is a tomb. If so, we need to remove it and try to get the next valid
	 * order.
	 * </p>
	 * 
	 * @param list
	 *            On which book should we get the order.
	 * @param price
	 *            At which price should we get the order.
	 * @return The first valid, not tomb order. If there is no valid order,
	 *         return null.
	 */
	public Order getFirstExistOrder(SortedMap<Double, LinkedList<Order>> list,
			Double price) {
		Order o = null;
		// check until we get a not tomb order.
		while (!list.get(price).isEmpty()) {
			o = list.get(price).getFirst();
			if (o.isDel()) {
				// we will remove the tomb order.
				list.get(price).remove(o);
				o = null;
			} else {
				break;
			}
		}
		return o;
	}

	/**
	 * Update the book to make all the possible trades. If there is a possible
	 * trade that we can find on the 2 books, we can make this trade and update
	 * the book.
	 * 
	 * @return {@link true} if we made a trade. {@link false} if there is no
	 *         possible trade.
	 */
	public boolean updateBooks() {

		Double sellPrice = new Double(0);
		Double buyPrice = new Double(0);
		Order sell = null, buy = null;
		// traverse the 2 books to get the valid "best" price.
		while (!ask.isEmpty()) {
			sellPrice = ask.firstKey();
			sell = this.getFirstExistOrder(ask, sellPrice);
			if (sell == null)
				ask.remove(sellPrice);
			else
				break;
		}
		while (!bid.isEmpty()) {
			buyPrice = bid.lastKey();
			buy = this.getFirstExistOrder(bid, buyPrice);
			if (buy == null)
				bid.remove(buyPrice);
			else
				break;
		}
		// If the book is empty, there should be no valid trade, then we return.
		if (ask.isEmpty() || bid.isEmpty())
			return false;

		// This means the best prices of the 2 books shows that there can be a
		// trade.
		// Make this trade.
		if (sellPrice <= buyPrice) {
			// If it is not in silent, print the trade
			if (!silent) {
				System.out.println("Order " + sell.getOrderId()
						+ " traded with order " + buy.getOrderId());
			}
			// update the order's size: remove the traded quantities.
			if (sell.getSize() > buy.getSize()) {
				sell.setSize(sell.getSize() - buy.getSize());
				bid.get(buyPrice).remove(buy);
				if (bid.get(buyPrice).isEmpty()) {
					bid.remove(buyPrice);
				}
			} else if (sell.getSize() < buy.getSize()) {
				buy.setSize(buy.getSize() - sell.getSize());
				ask.get(sellPrice).remove(sell);
				if (ask.get(sellPrice).isEmpty()) {
					ask.remove(sellPrice);
				}
			} else {
				// This means we need to remove the both order because there is
				// nothing left
				bid.get(buyPrice).remove(buy);
				if (bid.get(buyPrice).isEmpty()) {
					bid.remove(buyPrice);
				}
				ask.get(sellPrice).remove(sell);
				if (ask.get(sellPrice).isEmpty()) {
					ask.remove(sellPrice);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Print the book's top.
	 */
	public void printBookTop() {
		Double sellPrice = new Double(0);
		Double buyPrice = new Double(0);
		Order sell = null, buy = null;
		while (!ask.isEmpty()) {
			sellPrice = ask.firstKey();
			sell = this.getFirstExistOrder(ask, sellPrice);
			if (sell == null)
				ask.remove(sellPrice);
			else {
				Order temp = sell;
				int counter = 0;
				System.out.println("Ask book top: ");
				//Traverse the book to print the order at this price
				while (true) {
					sell = this.getFirstExistOrder(ask, sellPrice);
					if (sell == temp) {
						if (counter == 1)
							break;
						else
							counter = 1;
					}
					ask.get(sellPrice).remove(sell);
					ask.get(sellPrice).addLast(sell);
					System.out.print(sell.getSize()
							+ " @ "
							+ (sell.getPrice() == Double.MIN_VALUE ? Double.NaN
									: sell.getPrice()) + " "
							+ sell.getOrderId());
				}
				break;
			}
		}
		while (!bid.isEmpty()) {
			buyPrice = bid.lastKey();
			buy = this.getFirstExistOrder(bid, buyPrice);
			if (buy == null)
				bid.remove(buyPrice);
			else {
				Order temp = buy;
				int counter = 0;
				System.out.println("Bid book top: ");
				while (true) {
					buy = this.getFirstExistOrder(bid, buyPrice);
					if (buy == temp) {
						if (counter == 1)
							break;
						else
							counter = 1;
					}
					bid.get(buyPrice).remove(buy);
					bid.get(buyPrice).addLast(buy);
					System.out.print(buy.getSize()
							+ " @ "
							+ (buy.getPrice() == Double.MAX_VALUE ? Double.NaN
									: buy.getPrice()) + " " + buy.getOrderId());
				}
				break;
			}
		}
	}

	/**
	 * Print the book's detail information.
	 * Similar to the above functions.
	 */
	public void printBookDetail() {
		Double sellPrice = new Double(0);
		Double buyPrice = new Double(0);
		Order sell = null, buy = null;
		System.out.println("Ask book: ");
		for (Double p : ask.keySet()) {
			sellPrice = p;
			sell = this.getFirstExistOrder(ask, sellPrice);
			if (sell == null) {
				ask.remove(sellPrice);
				continue;
			} else {
				Order temp = sell;
				int counter = 0;
				while (true) {
					sell = this.getFirstExistOrder(ask, sellPrice);
					if (sell == temp) {
						if (counter == 1)
							break;
						else
							counter = 1;
					}
					ask.get(sellPrice).remove(sell);
					ask.get(sellPrice).addLast(sell);
					System.out
							.println((sell.getPrice() == Double.MIN_VALUE ? Double.NaN
									: sell.getPrice())
									+ ",ask," + sell.getSize());
				}
			}
		}

		System.out.println("Bid book: ");
		for (Double p : bid.keySet()) {
			buyPrice = p;
			buy = this.getFirstExistOrder(bid, buyPrice);
			if (buy == null) {
				bid.remove(buyPrice);
				continue;
			} else {
				Order temp = buy;
				int counter = 0;
				while (true) {
					buy = this.getFirstExistOrder(bid, buyPrice);
					if (buy == temp) {
						if (counter == 1)
							break;
						else
							counter = 1;
					}
					bid.get(buyPrice).remove(buy);
					bid.get(buyPrice).addLast(buy);
					System.out
							.println((buy.getPrice() == Double.MIN_VALUE ? Double.NaN
									: buy.getPrice())
									+ ",bid," + buy.getSize());
				}
			}
		}
	}

	/**
	 * Print the number of the orders at each price.
	 * Similar to the above functions.
	 */
	public void printBookOrderNum() {
		Double sellPrice = new Double(0);
		Double buyPrice = new Double(0);
		Order sell = null, buy = null;
		System.out.println("Ask book: ");
		int x;
		for (Double p : ask.keySet()) {
			sellPrice = p;
			sell = this.getFirstExistOrder(ask, sellPrice);
			if (sell == null) {
				ask.remove(sellPrice);
				continue;
			} else {
				Order temp = sell;
				int counter = 0;
				x = 0;
				while (true) {
					sell = this.getFirstExistOrder(ask, sellPrice);
					if (sell == temp) {
						if (counter == 1)
							break;
						else
							counter = 1;
					}
					ask.get(sellPrice).remove(sell);
					ask.get(sellPrice).addLast(sell);
					x++;
				}
				System.out.println("total orders @ " + sellPrice + ": " + x);
			}
		}

		System.out.println("Bid book: ");
		for (Double p : bid.keySet()) {
			buyPrice = p;
			buy = this.getFirstExistOrder(bid, buyPrice);
			if (buy == null) {
				bid.remove(buyPrice);
				continue;
			} else {
				Order temp = buy;
				int counter = 0;
				x = 0;
				while (true) {
					buy = this.getFirstExistOrder(bid, buyPrice);
					if (buy == temp) {
						if (counter == 1)
							break;
						else
							counter = 1;
					}
					bid.get(buyPrice).remove(buy);
					bid.get(buyPrice).addLast(buy);
					x++;
				}
				System.out.println("total orders @ " + buyPrice + ": " + x);
			}
		}
	}

}
