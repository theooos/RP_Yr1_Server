package Networking;

import java.util.ArrayList;
import java.util.UUID;

public class RobotLobby extends Thread {

	private boolean alive = true;
	private ArrayList<Puppet> puppets = new ArrayList<Puppet>();

	public RobotLobby() {
		// TODO Auto-generated constructor stub
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
//			Object comm = pup.nextCommand();
//			if(comm instanceof Move){
//				blah
//			}
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
