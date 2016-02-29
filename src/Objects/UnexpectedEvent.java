package Objects;

import java.io.Serializable;

public class UnexpectedEvent implements Serializable {
	
	private static Sensor alertedSensor;
	
	/**
	 * Get if sensor alerted.
	 * @return Sensor.
	 */
	public Sensor getAlertedSensor() {
		return alertedSensor;
	}
	
	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public static String parameters() {
		return ("" + alertedSensor);
	}
	
}
