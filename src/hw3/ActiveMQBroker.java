package hw3;

import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.PropertyConfigurator;

/**
 * The class to set up the middleware of the program.
 * 
 * @author kerry
 * 
 */
public class ActiveMQBroker {
	/**
	 * Start the broker as the middleware. This function need to be called
	 * before setting up the client and server. It will folk a new thread and
	 * start the broker at address tcp://localhost:61616.
	 * @throws Exception
	 */
	public static void startBroker() throws Exception {
		//folk a new thread to run the broker
		Thread t0 = new Thread() {
			public void run() {
				try {
					//config the log4j properties.
					PropertyConfigurator.configure("conf/log4j.properties");
					//start a broker service at tcp://localhost:61616
					BrokerService broker = new BrokerService();
					broker.setBrokerName("Payout");
					broker.addConnector("tcp://localhost:61616");
					broker.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t0.start();
	}
}
