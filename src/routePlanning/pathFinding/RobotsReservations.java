package routePlanning.pathFinding;

import java.util.Vector;

import routePlanning.dataStructures.Node;
import routePlanning.virtualRobot.Robot;

public class RobotsReservations {
	private static Vector<Robot> robots = new Vector<Robot>(0);
	
	public RobotsReservations() {
		
	}
	
	public static void AddRobot(Robot robot){
		robots.add(robot);
	}
	
	public static boolean IsReserved(Node node, int time){
		for (int i = 0; i < robots.size(); i++){
			if (robots.get(i).IsReserved(node, time))
				return true;
		}
		
		return false;
	}
}
