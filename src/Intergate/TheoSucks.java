package Intergate;

import JobInput.JobProcessor;
import Networking.Server;

public class TheoSucks {

	public TheoSucks() {
	
		// Fran & Brendan --- JobProcessor: Process item 
        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        
        // FRAN'S STUFF MAY GO HERE WHEN IT WORKS, BUT IDK BECAUSE SHE SUCKS
        
        ////
        
        // Theo --- Server: Starting the server
        Server server = new Server();
		
	}

}
