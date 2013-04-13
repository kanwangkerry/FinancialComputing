package hw3;

public class SimulationParameter {
	public enum OptionType {
		Asia, Europen
	};

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

	public static SimulationParameter fromString(String content) {
		SimulationParameter result;
		String p[] = content.split(" ");
		OptionType to;
		if (p[5].charAt(0) == 'A')
			to = OptionType.Asia;
		else
			to = OptionType.Europen;
		result = new SimulationParameter(Integer.parseInt(p[0]),
				Double.parseDouble(p[1]), Double.parseDouble(p[2]),
				Double.parseDouble(p[3]), Double.parseDouble(p[4]), to, p[6]);
		return result;
	}

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
	 * The type of the option;
	 */
	public OptionType type;

	public String topic;
}
