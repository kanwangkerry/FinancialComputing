package hw3;

/**
 * Record of the parameters of a simulation.
 * Should be sent by the simulation server.
 * @author kerry
 *
 */
public class SimulationParameter {
	/**
	 * Option types.
	 * @author kerry
	 *
	 */
	public enum OptionType {
		Asia, Europen
	};

	/**
	 * Constructor. Create a simulation parameter.
	 * @param d duration of the option.
	 * @param si sigma of the option per day.
	 * @param r1 r of the option per day.
	 * @param iv initial price of the option.
	 * @param sp strike price.
	 * @param t Option type.
	 * @param to topic of a certain request.
	 */
	public SimulationParameter(int d, double si, double r1, double iv,
			double sp, OptionType t, String to) {
		this.duration = d;
		this.sigma = si;
		this.r = r1;
		this.InitValue = iv;
		this.strikePrice = sp;
		this.type = t;
		this.topic = to;
	}

	/**
	 * Generate a SimulationParameter from a string. 
	 * Mainly used to parse a message sent by the server and 
	 * create the simulation parameter.
	 * @param content Message that contains the simulation parameter
	 * @return The parameter parsed from the input message
	 */
	public static SimulationParameter fromString(String content) {
		//Every variable of the parameter is splitted by space
		SimulationParameter result;
		String p[] = content.split(" ");
		OptionType to;
		// 'A' and 'E' stands for the option type
		if (p[5].charAt(0) == 'A')
			to = OptionType.Asia;
		else
			to = OptionType.Europen;
		//Generate and return a simulation parameter.
		result = new SimulationParameter(Integer.parseInt(p[0]),
				Double.parseDouble(p[1]), Double.parseDouble(p[2]),
				Double.parseDouble(p[3]), Double.parseDouble(p[4]), to, p[6]);
		return result;
	}

	/**
	 * Transform a simulation parameter into string.
	 * Used by the server to send message.
	 */
	public String toString() {
		String result = this.duration + " " + this.sigma + " " + this.r + " "
				+ this.InitValue + " " + this.strikePrice + " ";
		if (this.type == OptionType.Asia)
			result += "A ";
		else
			result += "E ";
		return result + topic;
	}

	/**
	 * The option expiration duration.
	 */
	public int duration;
	/**
	 * Daily volatility.
	 */
	public double sigma;
	/**
	 * Daiyly r.
	 */
	public double r;
	/**
	 * Initial price of the option.
	 */
	public double InitValue;
	/**
	 * The strike price of the Europe Call option.
	 */
	public double strikePrice;
	/**
	 * The type of the option.
	 */
	public OptionType type;

	/**
	 * The topic of the simulation.
	 */
	public String topic;
}
