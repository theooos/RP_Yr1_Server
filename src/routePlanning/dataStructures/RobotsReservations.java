package routePlanning.dataStructures;

import java.awt.Point;
import java.util.Vector;

import Objects.Sendable.RobotInfo;

/*
 * TODO Dispose of obsolete reservations
 */
public class RobotsReservations {
	private static Vector<RobotInfo> robots = new Vector<RobotInfo>(0);
	
	public RobotsReservations() {
		
	}
	
	public static void AddRobot(RobotInfo robot){
		robots.add(robot);
	}
	
	public static boolean IsReserved(Point node, int time){
		for (int i = 0; i < robots.size(); i++){
			if (robots.get(i).IsReserved(node, time))
				return true;
		}
		
		return false;
	}
	
	public static boolean isReservedForStopping(Point node, int time){
		for (int i = 0; i < robots.size(); i++){
			if (robots.get(i).isReservedForStopping(node, time))
				return true;
		}
		
		return false;
	}
	
	public static int getGreatestReservedTime(){
		int largestReservedTime = 0;
		int tempLastReservedTime = 0;
		for (int i = 0; i < robots.size(); i++){
			tempLastReservedTime = robots.get(i).getLastReservedTime();
			if (largestReservedTime < tempLastReservedTime)
				largestReservedTime = tempLastReservedTime;
		}
		
		return largestReservedTime;
	}
}
