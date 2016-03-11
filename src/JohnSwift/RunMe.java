/**
 * 
 */
package JohnSwift;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import JobInput.JobProcessor;
import Objects.AllRobots;
import Objects.WarehouseMap;
import Objects.Sendable.RobotInfo;
import routeExecution.RouteExecution;
import warehouseInterface.GridMap;
import warehouseInterface.JobTable;
import warehouseInterface.RobotTable;
import warehouseInterface.Statistics;

/**
 * @author georgesabourin
 *
 */
public class RunMe {

	/**
	 * 
	 */
	public RunMe() {
		// TODO Auto-generated constructor stub
	}


	public static void main(String[] args) {
		// Some stuff will go here, pending it works, which it likely won't.
		
		
		//// JobSelection (Fran & Brendan) -- Process the items
        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        //JobProcessor.getAllItems()
		
        /*
        // Local Server (Theo) -- Starting the server
        Server server = new Server();
        server.start();
		Puppet testPup = new Puppet("Alfonso", "00165308DA58");
		server.addRobot(testPup);
		*/
		
        
        /////
        WarehouseMap grid = new WarehouseMap(12, 8, "res/drops.csv");
        /////
        
        // WarehouseInterface (Artur) -- Create and launch the interface
		JFrame frame = new JFrame("Warehouse Interface");
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);

		frame.add(JobTable.draw());
		frame.add(GridMap.createGrid(grid));
		frame.add(RobotTable.draw());
		frame.add(Statistics.draw());
		frame.setVisible(true);
		
        /////
        
      
        grid.setObstacles(new Point[] { new Point(1, 2), new Point(1, 3), new Point(1, 4), new Point(1, 5), new Point(1, 6), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(4, 5), new Point(4, 6), new Point(7, 2), new Point(7, 3), new Point(7, 4), new Point(7, 5), new Point(7, 6), new Point(10, 2), new Point(10, 3), new Point(10, 4), new Point(10, 5), new Point(10, 6) });


		
		AllRobots.addRobot(new RobotInfo("test", new Point(1,1)));
		
		RouteExecution theExecutor = new RouteExecution(1, grid);
		
		
		
		theExecutor.start();
		
		

		
	}

}
