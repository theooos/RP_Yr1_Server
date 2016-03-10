package routePlanning.dataStructures;

import java.awt.Point;
import java.util.Vector;

public class TimePosReservations {
	
	Vector<Reservation> reservations = new Vector<Reservation>();

	public TimePosReservations(){
		
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
}
