package routePlanning.dataStructures;

import java.awt.Point;
import java.util.Vector;

import Objects.Sendable.RobotInfo;

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
}
