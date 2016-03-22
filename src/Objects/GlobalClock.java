package Objects;

public class GlobalClock {
	/**
	 * At the start of the program current time is always 0
	 */
	private static int currentTime = 0;
	
	/**
	 * Get the current time
	 * @return
	 */
	public static int getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * Increases time by 1
	 */
	public static void increaseTime() {
		currentTime++;
	}
}
