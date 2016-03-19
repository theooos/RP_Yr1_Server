package testing.routePlanning;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import Objects.Direction;
import Objects.WarehouseMap;
import Objects.Sendable.RobotInfo;
import routePlanning.pathFinding.PathFinding;
import rp.robotics.mapping.MapUtils;

public class JUnit_RoutePlanning {

	private PathFinding pathFinder;
	private RobotInfo taytay;
	private WarehouseMap map; 
	private int width;
	private int height;

	@Before
	public void setUp() throws Exception {
		// Gets the size of the final map
		rp.robotics.mapping.GridMap nicksMap = MapUtils.createRealWarehouse();
		width = nicksMap.getXSize();
		height = nicksMap.getYSize();

		// Initializes our map and pathfinder
		map = new WarehouseMap("res/drops.csv");
		pathFinder = new PathFinding(map);

		// Creates dummy robot
		taytay = new RobotInfo("TayTay");
	}

	public void printMap() {
		System.out.println("| 0| 1| 2| 3| 4| 5| 6| 7|");
		System.out.println(".........................");
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				//grid[i][j] = nicksMap.isObstructed(i, j);
				if(map.isObstacle(i, j)) {
					System.out.print("| X");
				}
				else {
					System.out.print("|  ");
				}
			}
			System.out.println("");
			System.out.println("_________________________");
		}
	}
	
	@Test
	public void test() {
		////////////// 
		
		// x is x, y is y
		
		Point start = new Point(0,0);
		Point end = new Point(5,5);
		System.out.println(pathFinder.GetPath(start, end, 0, taytay));

		Vector<Direction> path1 = pathFinder.GetPath(new Point(7,1), new Point(6,6), 0, taytay);
		assertEquals(path1.size(), 6);
		
		Vector<Direction> path2 = pathFinder.GetPath(new Point(1,4), new Point(8,7), 0, taytay);
		assertEquals(path2.size(), 10);
		
		//System.out.println(path2.size());
		
		printMap();


	}

}
