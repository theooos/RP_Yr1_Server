package Objects;

import java.util.ArrayList;
import java.util.UUID;

import Objects.Sendable.RobotInfo;

public class AllRobots {

	private static ArrayList<RobotInfo> robots = new ArrayList<RobotInfo>();
	
	public synchronized static ArrayList<RobotInfo> getAllRobots(){
		return robots;
	}
	
	public synchronized static RobotInfo getRobot(UUID name){
		for(RobotInfo robot : robots){
			if(robot.getName().equals(name)) return robot;
		}
		return null;
	}

	public synchronized static void addRobot(RobotInfo newBot){
		robots.add(newBot);
	}
	
	public synchronized static boolean checkExists(UUID name){
		for(RobotInfo robot : robots){
			if(robot.getName().equals(name)) return true;
		}
		return false;
	}
	
	public synchronized static void modifyRobot(UUID name, RobotInfo newInfo){
		ArrayList<RobotInfo> newList = new ArrayList<RobotInfo>();
		boolean robotFound = false;
		
		for(RobotInfo robot : robots){
			if(robot.getName().equals(name)){
				newList.add(newInfo);
				robotFound = true;
			}
			else{
				newList.add(robot);
			}
		}
		
		robots = newList;
		if(!robotFound) throw new IllegalArgumentException("Robot " + name + " not found");
	}
	
	public synchronized static void removeRobot(UUID name){
		ArrayList<RobotInfo> newList = new ArrayList<RobotInfo>();
		boolean robotFound = false;
		
		for(RobotInfo robot : robots){
			if(robot.getName().equals(name)){
				robotFound = true;
			}
			else{
				newList.add(robot);
			}
		}
		
		robots = newList;
		if(!robotFound) throw new IllegalArgumentException("Robot " + name + " not found");
	}
}
