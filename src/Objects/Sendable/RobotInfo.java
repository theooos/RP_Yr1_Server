package Objects.Sendable;

import java.awt.Point;
import java.util.Vector;

import Objects.Direction;
import Objects.Job;
import Objects.WarehouseMap;
import routePlanning.dataStructures.TimePosReservations;

/**
 * SHARED OBJECTS
 * Used to represent information about a single robot
 */
public class RobotInfo implements SendableObject {

	private String name;
	private Point position;
	private Direction direction;
	public Job currJob;
	public int currTaskIndex;
	public boolean pickingUp;
	public boolean isDoingJob;
	public Vector<Direction> directions;
	public int currDirectionsIndex;
	public boolean droppingOff;
	public boolean waitingForMoveReport;
	public boolean hasMoved;
	public boolean hasCompletedTask;
	public boolean finishedDroppingItems;
	public boolean hasATask;
	public boolean functioning;
	public Point nextRobotLocation;
	public Direction nextDir;

	///////////////////////////////////////////////////////////////////////////////////Route Planning///////////////////////////////////////////////////////////////////////////////////
	private Point currentNode;
	private TimePosReservations timePosReservations;

	/**
	 * After robot stops at goal and remains stationary(no jobs) it becomes an obstacle and has the node it resides at reserved until the next move
	 */
	private boolean stopped;

	///////////////////////////////////////////////////////////////////////////////////Route Planning-DEBUg///////////////////////////////////////////////////////////////////////////////////
	private int robotID;
	private WarehouseMap map;
	private Vector<Direction> pathSequence;
	private int pathSequenceProgress = 0;//Index of the next move

	public RobotInfo(int robotID, WarehouseMap map){
		this.robotID = robotID;
		this.map = map;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//By default, all robots start facing 'north' (which is west IRL)
	public RobotInfo(String name) {
		this.name = name;
		this.position = new Point(1,1);
		direction = Direction.NORTH;
	}

	public RobotInfo(String name, Point position) {
		this.name = name;
		this.position = position;
		direction = Direction.NORTH;
	}

	public RobotInfo(String name, Point position, Direction direction) {
		this.name = name;
		this.position = position;
		this.direction = direction;
		functioning=true;
		isDoingJob=false;
		pickingUp=false;
		droppingOff=false;
		waitingForMoveReport=false;
		hasMoved=false;
		hasCompletedTask=false;
		finishedDroppingItems=false;
		hasATask=false;
	}

	/**
	 * Get the name of the roobt
	 * @return Name of the robot
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get current position of the robot
	 * @return Current location of the robot
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Set position of the robot
	 * @param position Point where the robot is currently located
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * Get the current direction of the robot
	 * @return Direction the robot is facing
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the robot
	 * @param direction The direction the robot is currently facing
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "RobotInfo [name=" + name + ", position=" + position + ", direction=" + direction + "]";
	}

	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return ("RobotInfo," + name + "," + (int)position.getX() + "," + (int)position.getY() + "," + direction);
	}

	///////////////////////////////////////////////////////////////////////////////////Route Planning///////////////////////////////////////////////////////////////////////////////////
	/**
	 * SetUp a stationary robot(no jobs allocated)
	 * @param currentNode
	 */
	public void SetUpStationary(Point currentNode){
		this.currentNode = currentNode;
		stopped = true;
	}

	public void SetUpPath(Point currentNode, TimePosReservations timePosReservations){
		this.currentNode = currentNode;
		this.timePosReservations = timePosReservations;
		stopped = false;
	}

	///////////////////////////////////////////////////////////////////Time & Pos Reservations///////////////////////////////////////////////////////////////////////////
	public boolean IsReserved(Point node, int time){
		if (stopped)
			return node.equals(currentNode);// stopped e.g. No reservations yet, therefore return true if the current node is being checked for reservations as this robot is occupying it
		else if (timePosReservations == null)
			return false;//object created yet SetUp Method hasnt been called on this instance
		return timePosReservations.isReserved(node, time);
	}

	///////////////////////////////////////////////////////////////////////////////////Route Planning-DEBUg///////////////////////////////////////////////////////////////////////////////////
	public void SetUpPath(Point currentNode, Vector<Direction> pathSequence, TimePosReservations timePosReservations){
		this.currentNode = currentNode;
		this.pathSequence = pathSequence;
		this.timePosReservations = timePosReservations;
		stopped = false;
	}

	public Point goToNextNode(){
		if (pathSequenceProgress == pathSequence.size()){
			stopped = true;
			return null;//End of path
		}

		switch (pathSequence.get(pathSequenceProgress++)) {
		case NORTH:
			return moveUp();

		case SOUTH:
			return moveDown();	

		case EAST:
			return moveRight();

		case WEST:
			return moveLeft();

		default:
			return null;//Should not happen
		}
	}

	
	/**
	 * Move the roobt
	 * @param destinationNode Destination
	 * @return New location of the robot
	 */
	private Point move(Point destinationNode){
		return currentNode = destinationNode;
	}

	/**
	 * Move the robot up
	 * @return New location of the robot
	 */
	public Point moveUp() {
		return move(map.getAboveNode(currentNode));
	}

	/**
	 * Move the robot down
	 * @return New location of the robot
	 */
	public Point moveDown() {
		return move(map.getBelowNode(currentNode));
	}

	/**
	 * Move the robot left
	 * @return New location of the robot
	 */
	public Point moveLeft() {
		return move(map.getLeftNode(currentNode));
	}

	/**
	 * Move the robot right
	 * @return New location of the robot
	 */
	public Point moveRight() {
		return move(map.getRightNode(currentNode));
	}

	/**
	 * Get the ID of the robot
	 * @return ID of robot
	 */
	public int getID(){
		return robotID;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}