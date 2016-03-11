/**
 * 
 */
package JohnSwift;

import JobInput.JobProcessor;
import Networking.Puppet;
import Networking.Server;
import routeExecution.RouteExecution;

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
		
		
		// JobSelection (Fran & Brendan) -- Process the items
        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
		
        // Local Server (Theo) -- Starting the server
        Server server = new Server();
        server.start();
		Puppet johnCena = new Puppet("John Cena", "00165308E5A7");
		server.addRobot(johnCena);        
//        Puppet alfonso = new Puppet("Alfonso", "00165308DA58");
//        server.addRobot(alfonso);
		Puppet tay = new Puppet("TayTay", "0016531AF6E5");
		server.addRobot(tay);
		
		RouteExecution theExecutor = new RouteExecution();
		theExecutor.start();
		
		
        /*
        // WarehouseInterface (Artur) -- Create and launch the interface
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
		*/
	}

}
