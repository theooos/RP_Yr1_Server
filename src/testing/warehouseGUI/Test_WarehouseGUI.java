package testing.warehouseGUI;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Objects.WarehouseMap;
import Objects.Sendable.RobotInfo;
import jobInput.JobProcessor;
import routeExecution.RouteExecution;
import warehouseInterface.GridMap;
import warehouseInterface.JobTable;
import warehouseInterface.RobotTable;
import warehouseInterface.Statistics;

/**
 * VISUAL TEST
 * Test that jobs and robots appear, and that you can view job info/cancel a job.
 * NOTE: TAKES ABOUT HALF A MINUTE TO APPEAR, PLEASE WAIT
 */
public class Test_WarehouseGUI {
	
	// Creates the map
	public static WarehouseMap map = new WarehouseMap("res/drops.csv");

	public static void main(String args[]) {
		
		// Process job files
		JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
		JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

		// Creating and loading the interface
		JFrame frame = new JFrame("Warehouse Interface - 1.1");
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);

		RouteExecution routeExec = new RouteExecution(1, map);
		routeExec.start();
		
		frame.add(JobTable.draw(routeExec));
		frame.add(GridMap.createGrid(map));
		frame.add(RobotTable.draw());
		frame.add(Statistics.draw());
		frame.setVisible(true);
		
		// Adding dummy jobs
		JobTable.addJob(10001, "cake", "TayTay");
		JobTable.addJob(10002, "sweets", "John Cena");
		JobTable.addJob(10003, "donuts", "Alfonso");
		JobTable.addJob(10004, "donuts", "TayTay");
		JobTable.addJob(10005, "cake", "John Cena");
		JobTable.addJob(10006, "sweets", "Alfonso");
		
		// Creating dummy robot info
		RobotInfo tt = new RobotInfo("TayTay");
		RobotInfo jc = new RobotInfo("John Cena");
		RobotInfo af = new RobotInfo("Alfonso");
		
		// Adding dummy robots
		RobotTable.addRobot(tt);
		RobotTable.draw();
		RobotTable.addRobot(jc);
		RobotTable.addRobot(af);
		
		// Adding dummy statistics
		Statistics.increaseRevenue(1989);
		Statistics.jobDone();
		Statistics.jobDone();
		Statistics.jobDone();
		Statistics.jobCancelled();
		
		// Updating status of a job
		JobTable.updateStatus(10002, "Shake it off");
		
		RobotTable.updateStatus("TayTay", "TEST");
		
	}

	
	
}
