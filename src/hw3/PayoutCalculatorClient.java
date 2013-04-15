package hw3;

import hw2.payout.AsiaCallOptionPayOut;
import hw2.payout.EuroCallOptionPayOut;
import hw2.payout.PayOut;
import hw2.stockpath.GBMStockPath;
import hw2.stockpath.StockPath;
import hw3.SimulationParameter.OptionType;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * The class for client side.
 * <p>
 * For the client side, we need both Consumer and Producer. Here Consumer should
 * be used to process the message from the server, which should contain the
 * configurations of a simulation, and simulate a stock path with the configuration,
 * and finally get the payout of the simulation. Producer should be used to send 
 * the result payout back to the server as a feedback on the specific topic. 
 * </p>
 * 
 * @author kerry
 * 
 */
public class PayoutCalculatorClient {
	/**
	 * connection for producer.
	 */
	Connection pConnection;
	/**
	 * session for producer.
	 */
	Session pSession;
	/**
	 * Destination for consumer
	 */
	Destination cDestination;
	/**
	 * connection for consumer
	 */
	Connection cConnection;
	/**
	 * Session for consumer
	 */
	Session cSession;
	/**
	 * Producer for client
	 */
	MessageProducer producer;
	/**
	 * Consumer for client
	 */
	MessageConsumer consumer;

	/**
	 * Start a request listener.
	 * This is a static method so users do not need to care other implementation.
	 * Just call this method to start a listener or client, and it will folk a new thread
	 * and run the request.
	 * <p></p>
	 * If you want to run multiple client, you should call this function multiple times.
	 */
	public static void startListening() {
		// folk a new thread as a listener.
		Thread t1 = new Thread() {
			public void run() {
				try {
					// create a new client and start it.
					PayoutCalculatorClient c = new PayoutCalculatorClient();
					c.startClient();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
	}

	/**
	 * Start the client. This is the most important function in client side. This 
	 * function sets up both the consumer and producer.
	 * <p>For the consumer: the consumer of the client should firstly process 
	 * the message come from the middleware. This should be a message 
	 * of the configuration. Then it should generate a stock path using the
	 * configuration and calculate the payout.</p>
	 * <p>For the producer: after getting the payout, the client should 
	 * use a producer to send the payout back to the middleware on a specific
	 * topic. This will be received by the server as a feedback</p>
	 * @throws JMSException
	 */
	public void startClient() throws JMSException {
		// create producer connections
		final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		pConnection = connectionFactory.createConnection();
		pSession = pConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		pConnection.start();

		// create consumer connections
		cConnection = connectionFactory.createConnection();
		cSession = cConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		cDestination = cSession.createQueue("Simulation Queue");
		cConnection.start();
		consumer = cSession.createConsumer(cDestination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					//process the message send to this client
					if (message instanceof TextMessage) {
						//transform the message into a simulation parameter
						SimulationParameter sp = SimulationParameter
								.fromString(((TextMessage) message).getText());
						//use the simulation parameter to calculate a random payout
						PayOut p;
						if (sp.type == OptionType.Europen) {
							p = new EuroCallOptionPayOut(sp);
						} else {
							p = new AsiaCallOptionPayOut(sp);
						}
						StockPath path = new GBMStockPath(sp);
						double result = p.getPayout(path);
						//send this topic with producer back to the middleware.
						//use the topic of this parameter
						Topic topic = pSession.createTopic(sp.topic);
						producer = pSession.createProducer(topic);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						TextMessage m = pSession.createTextMessage();
						//the message being sent is mainly the result of the payout
						m.setText("" + result);
						producer.send(m);
						producer.close();
					}

				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Close the connections of the server.
	 * @throws JMSException
	 */
	public void close() throws JMSException {
		if (producer != null)
			producer.close();
		if (consumer != null)
			consumer.close();
		if (pSession != null)
			pSession.close();
		if (pConnection != null)
			pConnection.close();
		if (cSession != null)
			cSession.close();
		if (cConnection != null)
			cConnection.close();
	}
}
