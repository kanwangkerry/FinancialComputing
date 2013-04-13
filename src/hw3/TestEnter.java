package hw3;

import static org.junit.Assert.*;

import org.junit.Test;

import hw3.SimulationParameter.OptionType;

/**
 * A test enter of HW3. It is hard to write a unit test because every module
 * of this homework should be in a single thread, and functions are hard to test.
 * So I just write some simple tests to test my program, and also I have a enter
 * (main method) in this class.
 * @author kerry
 */
public class TestEnter {
	
	/**
	 * This is enter of the program. In order to test my program,
	 * just call the following functions, and it will automatically 
	 * generates new threads for servers and clients and middleware,
	 * and execute the reqeust and print the result.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		/**
		 * Start the middle ware.
		 */
		ActiveMQBroker.startBroker();
		/**
		 * Start 3 client
		 */
		PayoutCalculatorClient.startListening();
		PayoutCalculatorClient.startListening();
		PayoutCalculatorClient.startListening();

		/**
		 * Here are 2 sample requests: parameters are set the same as 
		 * hw2.
		 */
		SimulationParameter p1 = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Asia");
		SimulationParameter p2 = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 165, OptionType.Europen, "Europe");
		SimulationServer.startRequest(p1);
		SimulationServer.startRequest(p2);
	}
	
	/**
	 * Test the {@link SimulationParameter}
	 */
	@Test
	public void test1(){
		SimulationParameter p1 = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Asia");
		assertEquals(p1.toString(), "252 0.01 1.0E-4 152.35 164.0 A Asia");
	}
	
	/**
	 * Test the {@link SimulationParameter}
	 */
	@Test
	public void test2(){
		SimulationParameter p1 = new SimulationParameter(252, 0.01, 0.0001,
				152.35, 164, OptionType.Asia, "Asia");
		SimulationParameter p2 = SimulationParameter.fromString("252 0.01 0.0001 152.35 164 A Asia");
		assertEquals(p1.toString(), p2.toString());
	}
	
}
