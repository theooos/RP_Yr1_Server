package routePlanning.tests;
import static org.junit.Assert.assertEquals;

import java.util.Vector;

import routePlanning.dataStructures.Map;
import routePlanning.dataStructures.Node;
import routePlanning.pathFinding.PathFinding;
import routePlanning.virtualRobot.Robot;

public class Test {

	private Map map = new Map(10, 10);
	
	public void DrawMap() {
		for (int j = 0; j < map.getHeight(); j++){
			for (int i = 0; i < map.getWidth(); i++){
				System.out.print(map.getNode(i, j).status + "  ");
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		
		test.map.getNode(6, 4).status = Node.WALL;
		test.map.getNode(5, 4).status = Node.WALL;
		test.map.getNode(4, 4).status = Node.WALL;
		test.map.getNode(3, 4).status = Node.WALL;
		
		test.map.getNode(7, 4).status = Node.WALL;
		test.map.getNode(7, 5).status = Node.WALL;
		test.map.getNode(7, 6).status = Node.WALL;
		test.map.getNode(7, 7).status = Node.WALL;
		
		test.map.getNode(6, 7).status = Node.WALL;
		test.map.getNode(5, 7).status = Node.WALL;
		test.map.getNode(4, 7).status = Node.WALL;
		test.map.getNode(3, 7).status = Node.WALL;
		
		int time = 0;
		
		Robot robot0 = new Robot(0, test.map);
		Robot robot1 = new Robot(1, test.map);
		
		PathFinding pathFinding = new PathFinding(test.map);
		pathFinding.addRobot(robot0);
		pathFinding.addRobot(robot1);
		Vector<Integer> pathSequence;// = pathFinding.GetPath(startNode, endNode, time, robot);
		
		Node startNode = test.map.getNode(1, 0);
		Node endNode = test.map.getNode(1, 2);
		
		pathSequence = pathFinding.GetPath(startNode, endNode, time, robot0);
		robot0.SetUpPath(startNode, pathSequence, pathFinding.getTimePosReservations());
		assertEquals(2, pathSequence.size());
		
		startNode = test.map.getNode(0, 1);
		endNode = test.map.getNode(2, 1);
		
		pathSequence = pathFinding.GetPath(startNode, endNode, time, robot1);
		robot1.SetUpPath(startNode, pathSequence, pathFinding.getTimePosReservations());
		assertEquals(4, pathSequence.size());
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			robot0.goToNextNode();
			robot1.goToNextNode();
			test.DrawMap();
			
			time++;
			System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
		}
	}

}
