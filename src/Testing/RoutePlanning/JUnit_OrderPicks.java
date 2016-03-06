/**
 * 
 */
package Testing.RoutePlanning;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Objects.Item;
import Objects.SingleTask;

// TO BE UPDATED AT A LATER DATE, DUE TO CONFUSION ON DATA TYPES


/**
 * @author georgesabourin
 *
 */
public class JUnit_OrderPicks {
	
	private OrderPicks plan;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		// Setting up items (ID, x, y, reward, weight)
		Item a = new Item(7, 7, 7.0, 7.0);
		Item b = new Item(4, 4, 5.0, 5.0);
		Item c = new Item(5, 5, 3.0, 3.0);
		
		// Setting up Tasks (Item, quantity)
		SingleTask aa = new SingleTask("aa", 5);
		SingleTask bb = new SingleTask("bb", 2);
		SingleTask cc = new SingleTask("cc", 3);
		
		// ArrayList of Tasks
		ArrayList<SingleTask> tasks = new ArrayList<SingleTask>();
		tasks.add(aa);
		tasks.add(bb);
		// Drop-off point
		ArrayList<Point2D> dropOff = new ArrayList<Point2D>();
		Point2D point = new Point2D.Double(3, 3);
		dropOff.add(point);
		
		// Create the plan
		plan = new OrderPicks(tasks, dropOff);
		
		
	}

	@Test
	public void test() {
		
		ArrayList<SingleTask> sortedOrders = plan.getOrder();

		assert(sortedOrders.get(0).getItem().getID() == "bb");
		assert(sortedOrders.get(1).getItem().getID() == "aa");
		assert(sortedOrders.get(1).getItem().getID() == "cc");
		
	}

}
