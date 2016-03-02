package Objects;

import java.awt.Point;
import java.util.UUID;

public class RobotInfo {

	private UUID name;
	private Point position;
	private Direction direction;

	public RobotInfo(UUID name, Point position, Direction direction) {
		this.name = name;
		this.position = position;
		this.direction = direction;
	}

	/**
	 * By default, all robots face north
	 * @param name Name of the robot
	 * @param position Initial position of the robot, given through a {@code Point} object
	 */
	public RobotInfo(UUID name, Point position) {
		this.name = name;
		this.position = position;
		direction = Direction.NORTH;
	}
	
	public UUID getName() {
		return name;
	}
	
	public Point getPosition() {
		return position;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "RobotInfo [name=" + name + ", position=" + position + "]";
	}

	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return (name + "," + (int)position.getX() + "," + (int)position.getY());
	}

}
