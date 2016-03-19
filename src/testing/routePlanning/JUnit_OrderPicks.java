/**
 * 
 */
package testing.routePlanning;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Objects.WarehouseMap;
import Objects.Sendable.SingleTask;
import jobInput.JobProcessor;
import routePlanning.orderPicks.OrderPicks;

/**
 * Test scripts: Test that OrderPicks orders the tasks within a job, and that the dropoff point is able to be read
 */
public class JUnit_OrderPicks {
	
	private OrderPicks order;
	private List<SingleTask> taskList;
	private List<Point> dropPoints;
	private WarehouseMap map;
	
	private Point dropOff;
	

	/**
	 * Performs the file processing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		// Performs processing of files so all job data is stored
		JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
		JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
		
		// Gets a list of tasks from a random job
		taskList = JobProcessor.getAllJobs().remove(12455).getTasks();
		System.out.println(taskList); // PRINTS OUT TASKLIST
		
		// Creates the map, gets the drop off point
		map = new WarehouseMap("res/drops.csv");
		dropPoints = map.getDropoffPoints();
		
		// Get singular drop-off point for testing
		dropOff = map.getDropoffPoints().get(0);
		
	}

	/**
	 * Performs the tests, asserting that that dropoff point is correct
	 */
	@Test
	public void test() {
		
		// Drop-off point should currently be 4,7
		assertEquals(dropOff, new Point(4,7));
		
		order = new OrderPicks(taskList, dropPoints, map);
		
		System.out.println(order.orderedItems);
		
	}

}
