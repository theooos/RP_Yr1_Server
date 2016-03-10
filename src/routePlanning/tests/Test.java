package routePlanning.tests;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Vector;

import Objects.Direction;
import Objects.WarehouseMap;
import Objects.Sendable.RobotInfo;
import routePlanning.pathFinding.PathFinding;
import routePlanning.pathFinding.SimpleDistance;

/**
 * Contains my own tests and as a result guidance for usage of route planning classes & methods
 * @author Szymon
 *
 */
public class Test {

	private WarehouseMap map = new WarehouseMap(10, 10);
	private List<Point> obstacles;
	private RobotInfo stationaryRobot;
	private Point stationaryRobotPoint;

	public boolean DrawMap(Point robot1Location, Point robot2Location) {
		if(robot1Location == null || robot2Location == null)
			return true;//path complete
		
		for (int j = 0; j < 10; j++){
			for (int i = 0; i < 10; i++){
				if(i == robot1Location.x && j == robot1Location.y){
					System.out.print("  R1  ");
					continue;
				}
				else if (i == robot2Location.x && j == robot2Location.y){
					System.out.print("  R2  ");
					continue;
				}
				else if (i == stationaryRobotPoint.x && j == stationaryRobotPoint.y){
					System.out.print("  R3  ");
					continue;
				}
				boolean isObstacle = map.isObstacle(i, j);
				if(isObstacle){
					System.out.print("      ");
					continue;
				}
				System.out.print(isObstacle + " ");
			}
			System.out.println();
			System.out.println();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		
		test.obstacles = new ArrayList<Point>();
		test.obstacles.add(new Point(6, 4));
		test.obstacles.add(new Point(5, 4));
		test.obstacles.add(new Point(4, 4));
		test.obstacles.add(new Point(3, 4));
		
		test.obstacles.add(new Point(7, 4));
		test.obstacles.add(new Point(7, 5));
		test.obstacles.add(new Point(7, 6));
		test.obstacles.add(new Point(7, 7));
		
		test.obstacles.add(new Point(6, 7));
		test.obstacles.add(new Point(5, 7));
		test.obstacles.add(new Point(4, 7));
		test.obstacles.add(new Point(3, 7));
		
		test.map.setObstacles(test.obstacles);

		int time = 0;
		
		RobotInfo robot0 = new RobotInfo(0, test.map);
		RobotInfo robot1 = new RobotInfo(1, test.map);
		test.stationaryRobot = new RobotInfo(2, test.map);
		
		PathFinding pathFinding = new PathFinding(test.map);
		pathFinding.addRobot(robot0);
		pathFinding.addRobot(robot1);
		pathFinding.addRobot(test.stationaryRobot);
		Vector<Direction> pathSequence;// = pathFinding.GetPath(startNode, endNode, time, robot);
		
		test.stationaryRobot.SetUpStationary(new Point(4, 6));
		test.stationaryRobotPoint = new Point(4, 6);
		
		Point startNode = new Point(1, 0);
		Point endNode = new Point(6, 5);
		
		//Simple distance example
		SimpleDistance simpleDistance = new SimpleDistance(test.map);
		System.out.println("SimpleDistance No of steps: " + simpleDistance.GetDistnace(startNode, endNode));
		
		pathSequence = pathFinding.GetPath(startNode, endNode, time, robot0);
		robot0.SetUpPath(startNode, pathSequence, pathFinding.getTimePosReservations());
		
		startNode = new Point(1, 1);
		endNode = new Point(6, 6);
		
		pathSequence = pathFinding.GetPath(startNode, endNode, time, robot1);
		robot1.SetUpPath(startNode, pathSequence, pathFinding.getTimePosReservations());
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(test.DrawMap(robot0.goToNextNode(), robot1.goToNextNode())){
				break;//Path Complete
			}
			
			time++;
			System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
		}
	}

}
