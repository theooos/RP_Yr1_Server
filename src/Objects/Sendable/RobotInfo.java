package Objects.Sendable;

import java.awt.Point;
import java.util.Vector;

import Objects.Direction;
import Objects.GlobalClock;
import Objects.Job;
import Objects.WarehouseMap;
import routePlanning.dataStructures.TimePosReservations;

/**
 * SHARED OBJECTS Used to represent information about a single robot
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
	public boolean goingToDropOff;
	public boolean waitingForMoveReport;
	// public boolean waitingForCompleteReport;
	public boolean hasMoved;
	public boolean hasCompletedTask;
	public boolean finishedDroppingItems;
	public boolean hasATask;
	public boolean functioning;
	public Point nextRobotLocation;
	public Direction nextDir;
	public Point originalGoal;

	/////////////////////////////////////////////////////////////////////////////////// Route
	/////////////////////////////////////////////////////////////////////////////////// Planning///////////////////////////////////////////////////////////////////////////////////
	private TimePosReservations timePosReservations;

	/////////////////////////////////////////////////////////////////////////////////// Route
	/////////////////////////////////////////////////////////////////////////////////// Planning-DEBUg///////////////////////////////////////////////////////////////////////////////////
	private int robotID;
	private WarehouseMap map;
	private Vector<Direction> pathSequence;
	private int pathSequenceProgress = 0;// Index of the next move

	public RobotInfo(int robotID, WarehouseMap map, Point position) {
		this.robotID = robotID;
		this.map = map;
		this.position = position;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// By default, all robots start facing 'north' (which is west IRL)
	public RobotInfo(String name) {
		this.name = name;
		this.position = new Point(1, 1);
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
		functioning = true;
		isDoingJob = false;
		pickingUp = false;
		droppingOff = false;
		waitingForMoveReport = false;
		hasMoved = false;
		hasCompletedTask = false;
		finishedDroppingItems = false;
		hasATask = false;
		goingToDropOff = false;
		// waitingForCompleteReport=false;
	}

	/**
	 * Get the name of the roobt
	 * 
	 * @return Name of the robot
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get current position of the robot
	 * 
	 * @return Current location of the robot
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Set position of the robot
	 * 
	 * @param position
	 *            Point where the robot is currently located
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * Get the current direction of the robot
	 * 
	 * @return Direction the robot is facing
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the robot
	 * 
	 * @param direction
	 *            The direction the robot is currently facing
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
	 * 
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return ("RobotInfo," + name + "," + (int) position.getX() + "," + (int) position.getY() + "," + direction);
	}

	/////////////////////////////////////////////////////////////////////////////////// Setters
	/////////////////////////////////////////////////////////////////////////////////// Route
	/////////////////////////////////////////////////////////////////////////////////// Planning///////////////////////////////////////////////////////////////////////////////////
	/**
	 * SetUp a stationary robot(no jobs allocated)
	 * 
	 * @param position
	 */
	public void SetUpStationary(Point position) {
		setStopped();
	}

	public void SetUpPath(Vector<Direction> pathSequence, TimePosReservations timePosReservations) {

		System.out.println(name + " SetUpPath pathSequence " + pathSequence + " timePosReservations " + timePosReservations);
		this.pathSequence = pathSequence;
		pathSequenceProgress = 0;
		this.timePosReservations = timePosReservations;// timePosReservations
														// contains
														// firstReservedTime
														// other than
														// Integer.MAX_VALUE so
														// this robot is not
														// stopped
	}

	/////////////////////////////////////////////////////////////////// Getters
	/////////////////////////////////////////////////////////////////// Time &
	/////////////////////////////////////////////////////////////////// Pos
	/////////////////////////////////////////////////////////////////// Reservations///////////////////////////////////////////////////////////////////////////
	public boolean IsReserved(Point node, int time) {
		int stoppedStatus = getStopped(time);
		if (stoppedStatus > -1) {
			/*if(timePosReservations != null){
				System.out.println("IsReserved: robot " + name +" stopped status " + stoppedStatus +" Node 1,4 ,time " + time + " returns " + node.equals(timePosReservations.getReservedNode(timePosReservations.getLastReservedTime())));
				System.out.println("Time pos reservations " + timePosReservations);
			}*/
				switch (stoppedStatus) {
			case 0:
				return node.equals(position);// stopped e.g. No reservations
												// yet, therefore return true if
												// the current node is being
												// checked for reservations as
												// this robot is occupying it
			case 1:
				return node.equals(position);
			case 2:
				return node.equals(timePosReservations.getReservedNode(timePosReservations.getLastReservedTime()));
			}
		}
		if(node.getX() == 1 && node.getY() == 4)
		{
			System.out.println("IsReserved: robot " + name +" stoppedStatus == -1 Node 1,4 ,time " + time);
		}
		return timePosReservations.isReserved(node, time);
	}

	/**
	 * Caller wants to stop at the given node at the given time, return boolean
	 * indicating if he will cut off my path
	 * 
	 * @param node
	 * @param time
	 * @return
	 */
	public boolean isReservedForStopping(Point node, int time) {
		if (timePosReservations == null)
			return false;
		if (node.equals(timePosReservations.getReservedNode(timePosReservations.getLastReservedTime()))) {// caller
																											// wants
																											// to
																											// go
																											// to
																											// the
																											// node
																											// I
																											// am
																											// currently
																											// travelling
																											// towards
																											// and
																											// will
																											// stop
																											// at
			// This node is not reserved before I get there e.g. time == 16, but
			// caller is closer so he is checking it at time 15 as he will get
			// there in less time
			// , so return false because the caller will crash with me my
			// destination node otherwise
			return true;
		} else {// Caller wants to potentially stop along my path to my goal
				// node so he can only stop there after I have passed that
				// point, not before!
			for (int t = time; t < timePosReservations.getLastReservedTime(); t++) {// dont
																					// need
																					// <=
																					// in
																					// condition
																					// since
																					// I
																					// have
																					// already
																					// checked
																					// above
																					// if
																					// this
																					// is
																					// a
																					// common
																					// destination
																					// node
				if (node.equals(timePosReservations.getReservedNode(t))) // If I
																			// will
																			// be
																			// at
																			// that
																			// node
																			// when
																			// the
																			// caller
																			// wants
																			// to
																			// stop
																			// there
																			// or
																			// after
																			// he
																			// stops
																			// (before
																			// reaching
																			// my
																			// dest.)
																			// do
																			// not
																			// let
																			// him
																			// block
																			// my
																			// way
					return true;
			}
		}
		return false;
	}

	/**
	 * No reservations initialised yet OR the reservations have been placed in
	 * the future e.g. pathFinding determined it is more optimal (less travel)
	 * to wait for other robots to move out of the way, than go around or
	 * explicitly stopped using SetUpStationary()
	 * 
	 * @param time
	 *            time at which Im checking if the robot is stopped
	 * @return -1 - not stopped, 0 - no reservations exist, 1 - waiting to move,
	 *         2 - reached destination, waiting for next a task from java.awt.Point[x=1,y=5] to java.awt.Point[x=1,y=4]
IsReserved:stoppedStatus == -1 Node 1,4 ,time 34
IsReserved, stopped status2 Node 1,4 ,time 34
	 */
	public int getStopped(int time) {
		if (timePosReservations == null)
			return 0;
		else if (time + 1 < timePosReservations.getFirstReservedTime())
			return 1;
		else if (time > timePosReservations.getLastReservedTime())
			return 2;
		return -1;
	}

	public void setStopped() {
		if (getStopped(GlobalClock.getCurrentTime()) == -1) // if
															// timePosReservations
															// == null then wont
															// try to call
															// setFirstReservedTime
															// and the null
															// condition itself
															// signifies being
															// stopped
			timePosReservations.setFirstReservedTime(Integer.MAX_VALUE);
	}

	public int getLastReservedTime() {
		return timePosReservations == null ? 0 : timePosReservations.getLastReservedTime();// If
																							// no
																							// reservations
																							// yet
																							// e.g.
																							// robots
																							// added
																							// and
																							// calculating
																							// path
																							// before
																							// (setting
																							// path
																							// i.e.
																							// adding
																							// reservations)
	}

	/////////////////////////////////////////////////////////////////////////////////// Route
	/////////////////////////////////////////////////////////////////////////////////// Planning-DEBUg///////////////////////////////////////////////////////////////////////////////////
	public int getFirstReservedTime() {// DELETE AFTER DEBUG!
		return timePosReservations == null ? 0 : timePosReservations.getFirstReservedTime();// If
																							// no
																							// reservations
																							// yet
																							// e.g.
																							// robots
																							// added
																							// and
																							// calculating
																							// path
																							// before
																							// (setting
																							// path
																							// i.e.
																							// adding
																							// reservations)
	}

	public Vector<Direction> getPathSequence() {// DELETE AFTER DEBUG!
		return pathSequence;
	}

	/**
	 * 
	 * @return returns null if the robot will not move
	 */
	public Point goToNextNode() {
		if (pathSequence == null || getStopped(GlobalClock.getCurrentTime()) > -1) {
			return null;// Please do not move me Im stopped
		}

		if (pathSequenceProgress == pathSequence.size()) {
			setStopped();
			return null;// End of path
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
			return null;// Should not happen
		}
	}

	private Point move(Point destinationNode) {
		// position.status = Node.GOALPATH;
		return position = destinationNode;
		// position.status = " " + robotID + " ";
	}

	public Point moveUp() {
		return move(map.getAboveNode(position));
	}

	public Point moveDown() {
		return move(map.getBelowNode(position));
	}

	public Point moveLeft() {
		return move(map.getLeftNode(position));
	}

	public Point moveRight() {
		return move(map.getRightNode(position));
	}

	public int getID() {
		return robotID;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}