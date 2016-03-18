package main;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import routeExecution.RouteExecution;
import warehouseInterface.GridMap;
import warehouseInterface.JobTable;
import warehouseInterface.RobotTable;
import warehouseInterface.Statistics;
import Objects.AllPuppets;
import Objects.AllRobots;
import Objects.WarehouseMap;
import Objects.Sendable.CompleteReport;
import Objects.Sendable.Move;
import Objects.Sendable.MoveReport;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import jobInput.JobProcessor;
import networking.Puppet;

/**
 * The main class to run all the server side code
 * -- Merged with Server.java and RobotLobby.java to save difficulties with communicating amongst the other server sections.
 */
public class RunServer extends Thread {

	public static WarehouseMap map;
	private RouteExecution routeExec;
	private boolean alive = true;

	public RunServer() {
		
		//// JobSelection (Fran & Brendan) -- Process the items
		JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
		JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

		//// Testing puppets, uncomment for experimentation
		      
		//        Puppet tay = new Puppet("TayTay", "0016531AF6E5");
		//        AllPuppets.addPuppet(tay);
		
		//// Creating Puppet
		//Puppet alfonso = new Puppet("Alfonso", "00165308DA58");
		//AllPuppets.addPuppet(alfonso);
		
		Puppet johnCena = new Puppet("John Cena", "00165308E5A7");
		AllPuppets.addPuppet(johnCena);  
		
		//// Setting up the WarehouseInterface (Artur)
		setUpWarehouse();


		/* Starts up new robot testing class. (*HAS NOT BEEN TESTED WITH ROBOTS*).
		 * If the above 3 puppets are commented out, then it won't have the puppets to talk to.
		 * 1. Add the bots to your computer in your system bluetooth devices.
		 * 2. Ensure you have all the NXJ shite set up on your pc. (Don't use a mac. Linux OK).
		 * 3. Uncomment the above puppets.
		 * 4. Turn on robots and have them started up before you start the server.
		 * 5. Once all robots are on, start the server.
		 * 6. Cross all your fingers.
		 */
		//        RobotMovementSuite testSuite = new RobotMovementSuite();
		//        testSuite.start(); 
	}

	
	/**
	 * Keeps the server running.
	 */
	@Override
	public void run() {
		while(alive) {
			checkCommands();	
			try {
				// Sleeping to allow others access to AllRobots.
				Thread.sleep(400);
			} catch (InterruptedException e) {
				out("RunMe run() sleep failed");
			}
			// Checks puppets for data.
			//checkCommands();			
		}
	}

	/**
	 * Checks through all the puppets, to see if they've sent any commands.
	 * If they have, then it executes them accordingly.
	 */
	private void checkCommands() {
		ArrayList<Puppet> puppets = AllPuppets.getPuppets();

		for(Puppet pup : puppets) {
			SendableObject comm = null; 			
			while((comm = pup.popCommand()) != null)
			{
				if(comm instanceof MoveReport){
					routeExec.addMoveReport(pup.getName(), (MoveReport)comm);
				}
				else if(comm instanceof CompleteReport){
					routeExec.addCompleteReport(pup.getName(), comm);
				}
				else if(comm instanceof RobotInfo){
					/* Will only be called when a robot has started up, and had it's
					 * input given to it by the operator.
					 */
					AllRobots.addRobot((RobotInfo)comm);
				}
			}
		}
	}

	/**
	 * Sets up Artur's warehouse.
	 */
	private void setUpWarehouse(){
		map = new WarehouseMap("res/drops.csv");

		JFrame frame = new JFrame("Warehouse Interface - 1.1");
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);

		frame.add(JobTable.draw());
		frame.add(GridMap.createGrid(map));
		frame.add(RobotTable.draw());
		frame.add(Statistics.draw());
		frame.setVisible(true);

		// This shouldn't be hardcoded, change later
		map.setObstacles(new Point[] { new Point(1, 2), new Point(1, 3), new Point(1, 4), new Point(1, 5), new Point(1, 6), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(4, 5), new Point(4, 6), new Point(7, 2), new Point(7, 3), new Point(7, 4), new Point(7, 5), new Point(7, 6), new Point(10, 2), new Point(10, 3), new Point(10, 4), new Point(10, 5), new Point(10, 6) });

		//// Start RoutePlanning & RouteExecution (Szymon & Maria)
		routeExec = new RouteExecution(1, map);
		routeExec.start();
	}

	/**
	 * Helper command to print out objects for debugging
	 * @param n Object to print
	 */
	private void out(Object n) {
		System.out.println(""+n);
	}
	
	/**
	 * Main method to run everything
	 * @param args
	 */
	public static void main(String[] args) {
		RunServer everything = new RunServer();
		everything.start();
	}
}
