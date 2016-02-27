package Objects;

import java.io.Serializable;

public class UnexpectedEvent implements Serializable {
	
	private Sensor alertedSensor;
	
	/**
	 * Get if sensor alerted.
	 * @return Sensor.
	 */
	public Sensor getAlertedSensor() {
		return alertedSensor;
	}
	
}
