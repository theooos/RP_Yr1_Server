package Objects;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * SHARED OBJECTS
 * Used to represent a single item: it's ID, location, weight and reward.
 */
public class Item {

	private final String ID;
	private final int x;
	private final int y;
	private final double reward;
	private final double weight;

	public static Map<String, Item> items = new HashMap<String, Item>();

	public Item(String ID, int x, int y, double reward, double weight) {

		this.ID = ID;	
		this.x = x;
		this.y = y;
		this.reward = reward;
		this.weight = weight;

	}


	/**
	 * Get the x - coord.
	 * @return y coord.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y - coord.
	 * @return x coord.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Get reward of the item.
	 * @return The reward.
	 */
	public double getReward() {
		return reward;
	}

	/**
	 * Get weight of the item.
	 * @return The weight.
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Get ID of the item.
	 * @return The ID.
	 */
	public String getID() {
		return ID;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Item [ID=" + ID + ", x=" + x + ", y=" + y + ", reward=" + reward + ", weight=" + weight + "]";
	}

}
