/**
 * 
 */
package JohnSwift;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import JobInput.JobProcessor;
import Networking.Puppet;
import Networking.RobotLobby;
import Objects.AllPuppets;
import Objects.AllRobots;
import Objects.WarehouseMap;
import Objects.Sendable.CompleteReport;
import Objects.Sendable.MoveReport;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import routeExecution.RouteExecution;
import warehouseInterface.GridMap;
import warehouseInterface.JobTable;
import warehouseInterface.RobotTable;
import warehouseInterface.Statistics;

/**
 * @author georgesabourin
 */
public class RunMe extends Thread{
	
	private RouteExecution theExecutor;
	private boolean alive = true;
	
	public RunMe(){
		//// JobSelection (Fran & Brendan) -- Process the items
        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        //JobProcessor.getAllItems()
		
        
//        Puppet johnCena = new Puppet("John Cena", "00165308E5A7");
//        AllPuppets.addPuppet(johnCena);        
//        Puppet alfonso = new Puppet("Alfonso", "00165308DA58");
//        AllPuppets.addPuppet(alfonso);
//        Puppet tay = new Puppet("TayTay", "0016531AF6E5");
//        AllPuppets.addPuppet(tay);
		
        setUpWarehouse();
        
		AllRobots.addRobot(new RobotInfo("test", new Point(1,1)));		
	}
	
	/**
	 * Keeps the server running.
	 */
	@Override
	public void run(){
		while(alive ){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				out("Sleep failed");
			}
			
			checkCommands();			
		}
	}
	
	/**
	 * Checks through all the puppets, to see if they've sent any commands.
	 */
	private void checkCommands(){
		ArrayList<Puppet> puppets = AllPuppets.getPuppets();
		for(Puppet pup : puppets){
			SendableObject comm = null;
 			
 		    while((comm = pup.popCommand()) != null)
 		    {
 		    	if(comm instanceof MoveReport){
 		    		theExecutor.addMoveReport(pup.getName(), comm);
 		    	}
 		    	else if(comm instanceof CompleteReport){
 		    		theExecutor.addCompleteReport(pup.getName(), comm);
 		    	}
 		    	else if(comm instanceof RobotInfo){
 		    		AllRobots.addRobot((RobotInfo)comm);
 		    	}
 		    }
		}
		try {
			Thread.sleep(400);
		}
		catch (InterruptedException e) {
			out("Sleep failed in the lobby");
		}
	}

	/**
	 * Adds a robot to the puppets list.
	 * @param pup
	 */
	public void addRobot(Puppet pup){
		AllPuppets.addPuppet(pup);
	}
	
	/**
	 * Checks if a robot by the given name already exists.
	 * @param name The name
	 * @return Whether there or not.
	 */
	public boolean checkExists(String name){
		return AllPuppets.checkExist(name);
	}
	
	/**
	 * Sets up Artur's warehouse.
	 */
	private void setUpWarehouse(){
		WarehouseMap grid = new WarehouseMap(12, 8, "res/drops.csv");
        
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
		
        grid.setObstacles(new Point[] { new Point(1, 2), new Point(1, 3), new Point(1, 4), new Point(1, 5), new Point(1, 6), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(4, 5), new Point(4, 6), new Point(7, 2), new Point(7, 3), new Point(7, 4), new Point(7, 5), new Point(7, 6), new Point(10, 2), new Point(10, 3), new Point(10, 4), new Point(10, 5), new Point(10, 6) });

        theExecutor = new RouteExecution(1, grid);
		theExecutor.start();
	}
	
	public static void main(String[] args) {
		RunMe everything = new RunMe();
		everything.start();
	}

	// Helper command
	private void out(Object n) {
		System.out.println(""+n);
	}
}
