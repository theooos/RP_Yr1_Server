package testing.warehouseMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Objects.Direction;
import Objects.WarehouseMap;
import rp.robotics.mapping.MapUtils;

/**
 * Test scripts: Test that the map for the warehouse is correctly generated
 */
public class JUnit_map {

	private WarehouseMap map;
	private boolean[][] grid;
	private int gridWidth;
	private int gridHeight;
	private Point dropOff;
	private Point free;

	/**
	 * Performs the map creation/parsing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		map = new WarehouseMap("res/drops.csv");

		//// Import the map created by Nick and set the obstacles
		rp.robotics.mapping.GridMap nicksMap = MapUtils.createRealWarehouse();

		gridWidth = nicksMap.getXSize();
		gridHeight = nicksMap.getYSize();

		grid = new boolean[gridWidth][gridHeight];

		// Set obstacles
		for(int i = 0; i < gridWidth; i++) {
			for(int j = 0; j < gridHeight; j++) {
				grid[i][j] = nicksMap.isObstructed(i, j);
			}
		}
		//////

		// Get a singular dropoff location
		List<Point> dropPoints = map.getDropoffPoints();
		dropOff = map.getDropoffPoints().get(0);

	}

	/**
	 * Performs the tests, asserting certain points on th emap
	 */
	@Test
	public void test() {

		// Test that asserts the obstacles in the WarehouseMap are the same as in Nick's defined map
		for(int x = 0; x < gridWidth; x++) {
			for(int y = 0; y < gridHeight; y++) {
				assertEquals(map.isObstacle(x, y), grid[x][y]);
				if(grid[x][y]) {
					free = new Point(x, y);
				}
			}
		}

		// Test the drop-off point is correct, should currently be 4,7
		assertEquals(dropOff, new Point(4,7));

		// Test positioning
		Point test = new Point(5,5);

		assertTrue(map.getLeftNode(test).equals(new Point(4, 5)));
		assertTrue(map.getRightNode(test).equals(new Point(6, 5)));
		assertTrue(map.getAboveNode(test).equals(new Point(5, 4)));
		assertTrue(map.getBelowNode(test).equals(new Point(5, 6)));

		// Test freeing a position
		assertTrue(map.isObstacle(free)); // Should be obstacle there
		map.freePosition(free); // Free up position
		assertFalse(map.isObstacle(free)); // Should no longer be an obstacle

		// Test adding an obstacle (same one as removed)
		map.addObstacle(free);
		assertTrue(map.isObstacle(free));

		// Testing distance to the wall --- Should be 0 as it's an obstacle
		assertTrue(map.distanceToWall(free, Direction.NORTH) == 0);
		assertTrue(map.distanceToWall(test, Direction.NORTH) == 5); // Should hopefully be 5 if there are no obstacles in the way

	}

}
