package routePlanning.pathFinding;

import java.util.Vector;

import routePlanning.dataStructures.Node;
import routePlanning.dataStructures.Reservation;

public class TimePosReservations {
	
	Vector<Reservation> reservations = new Vector<Reservation>();

	public TimePosReservations(){
		
	}
	
	public void addReservation(int time, Node node) {
		reservations.add(new Reservation(time, node));
	}
	
	/**
	 * Returns true if the given node is reserved at the given time
	 * @param node
	 * @param time
	 * @return
	 */
	public boolean isReserved(Node node, int time){
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).getNode().equals(node) && reservations.get(i).getTime() == time) {
				return true;
			}
		}
		
		return false;
	}
}
