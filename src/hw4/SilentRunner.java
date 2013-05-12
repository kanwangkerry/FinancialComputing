package hw4;

import java.util.Iterator;

import orderGenerator.Message;
import orderGenerator.NewOrder;
import orderGenerator.OrderCxR;
import orderGenerator.OrdersIterator;

/**
 * Silent runner, will not output anything during the processing.
 * It will output a book detail and a book order number at the end of processing.
 * @author kerry
 *
 */
public class SilentRunner {
	public static void main(String args[]) {
		Iterator<Message> iter = OrdersIterator.getIterator();
		Exchange ex = new Exchange();
		//set to silent
		ex.setSilent();
		// traverse every message from the iterator.
		while (iter.hasNext()) {
			Message x = iter.next();
			//Test if x is a NewOrder or a OrderCxR.
			if (x instanceof NewOrder) {
				//Handle new order
				NewOrder order = (NewOrder) x;
				ex.insertOrder(new Order(order.getSymbol(), Math.abs(order
						.getSize()), order.getOrderId(), order.getLimitPrice(),
						order.getSize() < 0));
			} else if (x instanceof OrderCxR) {
				//Handle a CxR order.
				OrderCxR cxR = (OrderCxR) x;
				try {
					ex.updateOrder(new CxROrder(cxR.getSize(),
							cxR.getOrderId(), cxR.getLimitPrice()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ex.printBookOrderNumber();
		ex.printBookDetail();
	}
}
