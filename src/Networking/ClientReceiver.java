package Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;

public class ClientReceiver extends Thread {

	private boolean alive = true;
	private DataInputStream fromServer;
	private ArrayList<Object> commandsForInterface = new ArrayList<Object>();
	private ArrayList<Object> commandsForMovement = new ArrayList<Object>();

	public ClientReceiver(DataInputStream fromServer) {
		this.fromServer = fromServer;
	}

	@Override
	public void run() {
		while (alive) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				System.out.println("INTERRUPTED.");
			}
			
			try {
				String classTitle = fromServer.readUTF();
				String command = fromServer.readUTF();
				figureRecipient(classTitle, command);				
				
			} catch (IOException e) {
				out("MyListener noticed the server died. Shutting everything down.");
				System.exit(3);
			} catch (NullPointerException e) {
				// Stops .readUTF from throwing errors.
			}
		}
	}

	// TODO This bad boy. Re-instantiates correct object types and adds to appropriate list.
	private synchronized void figureRecipient(String cT, String command) {
		Gson gson = new Gson();
		
		switch(cT){
			case "Move":
				// Move moveObj = gson.fromJson(command, Move.class);
				// addInterfaceComm(moveObj);
				// Creates a move object, adds to to the right list, etc...
		}
		
	}
	
	private void shutdown(){
		alive = false;
	}

	// ************************ ARRAYLIST ACCESSORS **************************
	
	/**
	 * Only done so that access to the commandsForInterface ArrayList is always
	 * synchronised.
	 * 
	 * @param comm The command to add.
	 */
	synchronized private void addInterfaceComm(Object comm) {
		commandsForInterface.add(comm);
	}
	
	/**
	 * Only done so that access to the commandsForMovement ArrayList is always
	 * synchronised.
	 * @param comm The command to add.
	 */
	synchronized private void addMovementComm(Object comm) {
		commandsForMovement.add(comm);
	}
	
	/**
	 * Takes the next message from the message list.
	 * @return
	 */
	synchronized public Object popInterfaceCommand() {
		if (commandsForInterface.isEmpty()) {
			return null;
		} else {
			Object comm = commandsForInterface.get(0);
			commandsForInterface.remove(0);
			return comm;
		}
	}
	
	/**
	 * Takes the next message from the message list.
	 * @return
	 */
	synchronized public Object popMovementCommand() {
		if (commandsForMovement.isEmpty()) {
			return null;
		} else {
			Object comm = commandsForMovement.get(0);
			commandsForMovement.remove(0);
			return comm;
		}
	}	

	// ******************** HELPER COMMANDS **********************
	
	private void out(Object n) {
		System.out.println("" + n);
	}
}
