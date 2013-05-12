package hw4;

import java.util.Iterator;

import orderGenerator.Message;
import orderGenerator.NewOrder;
import orderGenerator.OrderCxR;
import orderGenerator.OrdersIterator;

/**
 * A common runner, will output book top and trade information during the processing.
 * It will output a book detail at the end of processing.
 * <p>Details are almost the same with SilentRunner.</p>
 * @author kerry
 *
 */
public class Runner {
	public static void main(String args[]) {
		Iterator<Message> iter = OrdersIterator.getIterator();
		Exchange ex = new Exchange();
		while (iter.hasNext()) {
			Message x = iter.next();
			if (x instanceof NewOrder) {

				NewOrder order = (NewOrder) x;

				ex.insertOrder(new Order(order.getSymbol(), Math.abs(order
						.getSize()), order.getOrderId(), order.getLimitPrice(),
						order.getSize() < 0));
			} else if (x instanceof OrderCxR) {
				OrderCxR cxR = (OrderCxR) x;

				try {
					ex.updateOrder(new CxROrder(cxR.getSize(),
							cxR.getOrderId(), cxR.getLimitPrice()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ex.printBookTop();
		}
		ex.printBookDetail();
	}
}
