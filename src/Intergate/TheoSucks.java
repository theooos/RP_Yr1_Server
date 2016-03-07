package Intergate;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import JobInput.JobProcessor;
import Networking.Server;
import warehouseInterface.GridMap;
import warehouseInterface.JobTable;
import warehouseInterface.RobotTable;
import warehouseInterface.Statistics;

public class TheoSucks {

	public TheoSucks() {
	
		// Fran & Brendan --- JobProcessor: Process item 
        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        
        // FRAN'S STUFF MAY GO HERE WHEN IT WORKS, BUT IDK BECAUSE SHE SUCKS, THEN AGAIN: THEO SUCKS MORE <3xxx
        
        ////
        
        // Theo --- Server: Starting the server
        Server server = new Server();
		
		JFrame frame = new JFrame("Warehouse Interface");
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);

		frame.add(JobTable.draw());
		frame.add(GridMap.createGrid());
		frame.add(RobotTable.draw());
		frame.add(Statistics.draw());
		frame.setVisible(true);
		
		
        
        
	}

}
