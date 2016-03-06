package Networking;

import java.util.ArrayList;
import java.util.UUID;

import Objects.Sendable.Move;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;

public class RobotLobby extends Thread {

	private boolean alive = true;
	private ArrayList<Puppet> puppets = new ArrayList<Puppet>();

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
		for(Puppet pup : puppets){
			SendableObject comm = null;
 			
 		    while((comm = pup.popCommand()) != null)
 		    {
 		    	if(comm instanceof Move){
 		    		// Do blah
 		    	}
 		    	else if(comm instanceof SingleTask){
 		    		// Do blah
 		    	}
 		    }
		}
		try {
			Thread.sleep(200);
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
		puppets.add(pup);
	}
	
	synchronized public boolean checkExists(UUID name){
		for(Puppet pup : puppets){
			if(pup.getName().equals(name)) return true;
		}
		return false;
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
