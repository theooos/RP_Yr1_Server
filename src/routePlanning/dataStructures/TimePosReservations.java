package routePlanning.dataStructures;

import java.awt.Point;
import java.util.Vector;

public class TimePosReservations {
	
	private Vector<Reservation> reservations = new Vector<Reservation>();
	private int lastReservedTime;//For Optimal WHCA* i.e. checking all reserved time windows ( +1 potentially) but not beyond
	private int firstReservedTime;//Used to deduce if robots are waiting to move i.e. currentTime + 1 < firstReservedTime

	public TimePosReservations(){
		
	}
	
	public String toString()
	{
		return reservations.toString();
	}
	
	public void addReservation(int time, Point node) {
		reservations.add(new Reservation(time, node));
	}
	
	/**
	 * Returns true if the given node is reserved at the given time
	 * @param node
	 * @param time
	 * @return
	 */
	public boolean isReserved(Point node, int time){
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).getNode().equals(node) && reservations.get(i).getTime() == time) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the node reserved at the given time or null if there is no node reserved at the given time
	 * @param time
	 * @return
	 */
	public Point getReservedNode(int time){
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).getTime() == time) {
				return reservations.get(i).getNode();
			}
		}
		
		return null;
	}
	
	public void setGreatestReservedTime(int lastReservedTime) {
		this.lastReservedTime = lastReservedTime;
	}
	
	public int getLastReservedTime() {
		return lastReservedTime;
	}
	
	public void setFirstReservedTime(int firstReservedTime) {
		this.firstReservedTime = firstReservedTime;
	}
	
	public int getFirstReservedTime() {
		return firstReservedTime;
	}
}
