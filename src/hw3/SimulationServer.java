package hw3;

import hw2.simulation.SimulationManage;
import hw2.simulation.StateTracker;

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
 * Simualtion server: works as the simulation management.
 * @author kerry
 *
 */
public class SimulationServer {

	/**
	 * Connections and sessions for setting up transport with middleware.
	 */
	Connection pConnection;
	Session pSession;
	Destination pDestination;
	Connection cConnection;
	Session cSession;
	Destination cDestination;
	MessageProducer producer;
	MessageConsumer consumer;

	/**
	 * Parameter for the simulation.
	 */
	SimulationParameter p;
	/**
	 * State tracker the manage the simulation.
	 */
	StateTracker s;
	
	int counter = 0;

	/**
	 * Constructor. Initialize the {@link SimulationParameter} and the {@link StateTracker}
	 * @param sp
	 */
	SimulationServer(SimulationParameter sp) {
		p = sp;
		s = new StateTracker();
		s.setTopic(sp.topic);
	}
	
	/**
	 * Start a request with a simulation parameter.
	 * This is a static method so users do not need to care other implementation.
	 * Just call this method to start a request, and it will folk a new thread
	 * and run the request.
	 * @param sp The parameter of the simulation.
	 * @throws Exception
	 */
	public static void startRequest(SimulationParameter sp) throws Exception{
		final SimulationParameter sp1 = sp;
		Thread t1 = new Thread(){
			public void run(){
				SimulationServer s = new SimulationServer(sp1);
				try {
					s.startServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
	}

	/**
	 * Start a server for simulation.
	 * <p>This is the most important function for server. In this function,
	 * we need to firstly set up both the Consumer and Producer:
	 * The consumer mainly process the feedback from clients, and producer 
	 * send the new request to clients to generates some new stock path.</p>
	 *
	 * Setting up of the consumer:
	 * <p>We need to firstly collect the feedback of the client on the certain 
	 * topic, check if currently we have enough stock paths. If so,
	 * we need to stop this connection and print the final result. Otherwise, we
	 * need to generate 100 new messages and send them to middleware.</p>
	 * 
	 * Setting up of the producer:
	 * <p>Producer only need to send message to the queue. So it can be set
	 * up easily, and it only need to send message when it is called.</p>
	 * @throws Exception
	 */
	public void startServer() throws Exception {
		ConnectionFactory connectionFactory;
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");

		pConnection = connectionFactory.createConnection();
		pConnection.start();
		pSession = pConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		pDestination = pSession.createQueue("Simulation Queue");
		producer = pSession.createProducer(pDestination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		cConnection = connectionFactory.createConnection();
		cConnection.start();
		cSession = cConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = cSession.createTopic(p.topic);
		consumer = cSession.createConsumer(topic);
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					try {
						double temp = Double
								.parseDouble(((TextMessage) message).getText());
						s.update(temp);
						if (s.getN() > 100 &&  SimulationManage.checkIfStop(s)) {
							System.out.print("Topic: "+p.topic);
							System.out.println("Total simulation paths: "
									+ (s.getN() + 1));
							System.out
									.println("Final simulation value for the option: "
											+ s.getExpectation());
							consumer.close();
						} else {
							counter ++;
							if(counter == 100){
								counter = 0;
								for (int i = 0; i < 100; i++) {
									sendSimulateRequest();
								}
							}
						}
					} catch (Exception e) {
					}
				}
			}
		});

		for (int i = 0; i < 100; i++) {
			this.sendSimulateRequest();
		}
	}

	/**
	 * Send the producer a new message.
	 * @throws Exception
	 */
	public void sendSimulateRequest() throws Exception {
		sendMessage(pSession, producer, p.toString());
	}

	/**
	 * Close all the connections of the Server.
	 * @throws JMSException
	 */
	void close() throws JMSException {
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

	/**
	 * Generate a new message and do the actual work to send it to the middleware.
	 * @param session
	 * @param producer
	 * @param m The message need to be sent.
	 * @throws Exception
	 */
	void sendMessage(Session session, MessageProducer producer, String m)
			throws Exception {
		TextMessage message = session.createTextMessage(m);
		producer.send(message);
	}

}