package Networking;

import java.util.ArrayList;

public class PuppetListener extends Thread {

	ArrayList<Object> commands = new ArrayList<Object>();
	
	public PuppetListener(){
		// TODO Auto-generated constructor stub
	}

	/**
	 * Waits for a message from the robot is associated with, and adds it to the ArrayList commands.
	 */
	@Override
	public void run(){
		
	}
}
