package routePlanning.virtualRobot;

import java.util.Vector;

import Objects.Direction;
import routePlanning.dataStructures.GeneralProtocol;
import routePlanning.dataStructures.Map;
import routePlanning.dataStructures.Node;
import routePlanning.dataStructures.Reservation;
import routePlanning.pathFinding.TimePosReservations;

public class Robot {
	private int robotID;
	private Map map;
	private Node currentNode;
	private Vector<Direction> pathSequence;
	private TimePosReservations timePosReservations;
	private int pathSequenceProgress = 0;//Index of the next move
	
	/**
	 * After robot stops at goal and remains stationary(no jobs) it becomes an obstacle and has the node it resides at reserved until the next move
	 */
	private boolean stopped;
	
	
	public Robot(int robotID, Map map){
		this.robotID = robotID;
		this.map = map;
	}
	
	public void SetUpPath(Node currentNode){
		this.currentNode = currentNode;
		stopped = true;
	}
	
	public void SetUpPath(Node currentNode, Vector<Direction> pathSequence, TimePosReservations timePosReservations){
		this.currentNode = currentNode;
		this.pathSequence = pathSequence;
		this.timePosReservations = timePosReservations;
		stopped = false;
	}

	///////////////////////////////////////////////////////////////////Time & Pos Reservations///////////////////////////////////////////////////////////////////////////
	public boolean IsReserved(Node node, int time){
		if (timePosReservations == null)
			return false;//object created yet SetUp Method hasnt been called on this instance
		if (stopped)
			return node.equals(currentNode);// stopped e.g. No reservations yet, therefore return true if the current node is being checked for reservations as this robot is occupying it
		return timePosReservations.isReserved(node, time);
	}
	
	public void goToNextNode(){
		if (stopped)
			return;//End of path
		
		switch (pathSequence.get(pathSequenceProgress++)) {
		case NORTH:
			moveUp();
			break;

		case SOUTH:
			moveDown();	
			break;
					
		case EAST:
			moveRight();
			break;
			
		case WEST:
			moveLeft();
			break;
		}
		
		if (pathSequenceProgress == pathSequence.size())
			stopped = true;
	}
	
	private void move(Node destinationNode){
		currentNode.status = Node.GOALPATH;
		currentNode = destinationNode;
		currentNode.status = " " + robotID + " ";
	}
	
	public void moveUp() {
		move(map.getAboveNode(currentNode));
	}
	
	public void moveDown() {
		move(map.getBelowNode(currentNode));
	}
	
	public void moveLeft() {
		move(map.getLeftNode(currentNode));
	}
	
	public void moveRight() {
		move(map.getRightNode(currentNode));
	}
	
	public int getID(){
		return robotID;
	}
}
