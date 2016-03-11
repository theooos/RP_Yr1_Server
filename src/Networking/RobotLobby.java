package Networking;

import java.util.ArrayList;

import Objects.AllPuppets;
import Objects.Sendable.CompleteReport;
import Objects.Sendable.MoveReport;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;

public class RobotLobby extends Thread {

	private boolean alive = true;

	public RobotLobby() {
		
	}
	
	@Override
	public void run(){
		while(alive){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				out("Sleep failed");
			}
			
			checkCommands();			
		}
	}
	
	// TODO This when objects are sorted and I know where things are going
	synchronized private void checkCommands(){
		ArrayList<Puppet> puppets = AllPuppets.getPuppets();
		for(Puppet pup : puppets){
			SendableObject comm = null;
 			
 		    while((comm = pup.popCommand()) != null)
 		    {
 		    	if(comm instanceof MoveReport || comm instanceof CompleteReport){
 		    		// Do blah
 		    	}
 		    	else if(comm instanceof SingleTask){
 		    		// Do blah
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
	synchronized public void addRobot(Puppet pup){
		AllPuppets.addPuppet(pup);
	}
	
	synchronized public boolean checkExists(String name){
		return AllPuppets.checkExist(name);
	}
	
	/**
	 * Kills the lobby.
	 */
	public void shutdown(){
		alive = false;
	}

	// Helper command
	private void out(Object n) {
		System.out.println(""+n);
	}
}