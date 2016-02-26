package Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientReceiver extends Thread {

	private boolean alive = true;
	private DataInputStream fromServer;
	private ArrayList<Object> commands = new ArrayList<Object>();

	public ClientReceiver(DataInputStream fromServer) {
		this.fromServer = fromServer;
	}

	/**
	 * Forever trying to read two messages at a time from the server, to deduce
	 * new commands.
	 */
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
				figureType(classTitle, command);				
				
			} catch (IOException e) {
				out("MyListener noticed the server died. Shutting everything down.");
				System.exit(3);
			} catch (NullPointerException e) {
				// Stops .readUTF from throwing errors.
			}
		}
	}

	// TODO This bad boy. Re-instantiates correct object types and adds to appropriate list.
	private synchronized void figureType(String cT, String command) {
		
		switch(cT){
			case "Move":
				// Move moveObj = gson.fromJson(command, Move.class);
				// addComm(moveObj);
				// Creates a move object, adds to to the right list, etc...
		}
		
	}
	
	/**
	 * Ends the thread.
	 */
	public void shutdown(){
		alive = false;
	}

	// ************************ ARRAYLIST ACCESSORS **************************
	
	/**
	 * Only done so that access to the commandsForInterface ArrayList is always
	 * synchronised.
	 * 
	 * @param comm The command to add.
	 */
	synchronized private void addComm(Object comm) {
		commands.add(comm);
	}
	
	/**
	 * Takes the next message from the message list.
	 * @return
	 */
	synchronized public Object popCommand() {
		if (commands.isEmpty()) {
			return null;
		} else {
			Object comm = commands.get(0);
			commands.remove(0);
			return comm;
		}
	}

	// ******************** HELPER COMMANDS **********************
	
	private void out(Object n) {
		System.out.println("" + n);
	}
}
