package Objects;

import java.awt.Point;

/**
 * SHARED OBJECTS
 * Used to represent a single item: it's ID, location, weight and reward.
 */
public class Item {
	
	private String ID;
	private Point location;
	private double weight;
	private double reward;

	public Item(String ID, int x, int y, Double weight, Double reward) {
	
		this.ID = ID;
		this.location = new Point(x, y);
		
		this.weight = weight;
		this.reward = reward;
	}
	
	
	/**
	 * Get the ID of the item.
	 * @return The ID.
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Get location of the item.
	 * @return The location.
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * Get weight of the item.
	 * @return The weight.
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Get reward of the item.
	 * @return The reward.
	 */
	public double getReward() {
		return reward;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Item [ID=" + ID + ", location=" + location + ", weight=" + weight + ", reward=" + reward + "]";
	}


}
