package Objects.Sendable;

import Objects.Direction;

import java.awt.Point;
import java.util.UUID;

public class RobotInfo implements SendableObject {

	private UUID name;
	private Point position;
	private Direction direction;

	//By default, all robots start facing 'north' (which is west IRL)
	public RobotInfo(UUID name, Point position) {
		this.name = name;
		this.position = position;
		direction = Direction.NORTH;
	}

	public RobotInfo(UUID name, Point position, Direction direction) {
		this.name = name;
		this.position = position;
		this.direction = direction;
	}
	
	public UUID getName() {
		return name;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
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
