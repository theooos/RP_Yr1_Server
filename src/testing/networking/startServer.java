package testing.networking;

import java.util.ArrayList;

import Objects.AllPuppets;
import Objects.AllRobots;
import Objects.Sendable.CompleteReport;
import Objects.Sendable.MoveReport;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import networking.Puppet;
import warehouseInterface.JobTable;

public class startServer extends Thread {

	private boolean alive;

	public startServer() {
		//// Puppets
		Puppet tay = new Puppet("TayTay", "0016531AF6E5");
		AllPuppets.addPuppet(tay);
		//	Puppet johnCena = new Puppet("John Cena", "00165308E5A7");
		//	AllPuppets.addPuppet(johnCena);  

		//// Creating Puppet
		//Puppet alfonso = new Puppet("Alfonso", "00165308DA58");
		//AllPuppets.addPuppet(alfonso);

		alive = true;
	}

	private void checkCommands() {
		ArrayList<Puppet> puppets = AllPuppets.getPuppets();

		for(Puppet pup : puppets) {
			SendableObject comm = null; 			
			while((comm = pup.popCommand()) != null)
			{
				if(comm instanceof SingleTask){
					//routeExec.addMoveReport(pup.name(), (MoveReport)comm);
					//System.out.println("GOT REPORT: " + ((MoveReport) comm).toString());
					System.out.println("GOT SINGLETASK! SENDING TO JUNIT CLASS");
					JUnit_network.getItemBack(comm);
				}
				else if(comm instanceof CompleteReport){
					//routeExec.addCompleteReport(pup.name(), comm);
					JobTable.updateStatus(AllRobots.getRobot(pup.name()).currJob.getJobID(), "Completed");
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
	 * Helper command to print out objects for debugging
	 * @param n Object to print
	 */
	private void out(Object n) {
		System.out.println(""+n);
	}

}
