package Objects;

import java.awt.Point;

public class Item {
	
	private String name;
	private Point location;
	private int weight;

	public Item(String name, Point location, int weight) {
	
		this.name = name;
		this.location = location;
		this.weight = weight;
		
	}
	
	
	/**
	 * Get the name of the item.
	 * @return The name.
	 */
	public String getName() {
		return name;
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
	public int getWeight() {
		return weight;
	}
	

}
