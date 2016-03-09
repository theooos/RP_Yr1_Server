package Networking;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import Objects.Sendable.Move;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;

public class PuppetListener extends Thread {

	private DataInputStream fromRobot;
	ArrayList<SendableObject> commands = new ArrayList<SendableObject>();
	private boolean alive = true;
	
	public PuppetListener(DataInputStream fromRobot){
		this.fromRobot = fromRobot;
	}

	/**
	 * Waits for a message from the robot is associated with, and adds it to the ArrayList commands.
	 */
	@Override
	public void run(){
		while(alive){
			try {
				String fullComm = fromRobot.readUTF();
				out("Full comm: " + fullComm);
				Object[] splitComm = Splitter.split(fullComm);
				String type = (String) splitComm[0];
				Object[] objParams = new Object[splitComm.length-1];
				for(int i = 1; i < splitComm.length; i++){
					objParams[i-1] = splitComm[i];
				}
				figureType(type, objParams);
			}
			catch (IOException e) {
				out("Connection died, shutting down.");
				alive = false;
			}
		}
	}
	
	private synchronized void figureType(String type, Object[] parameters) {
		type = "Console";
		if(type.equals("Console")){
			String message = "";
			for(Object param : parameters){
				message += param;
			}
			out(message);
		}
		else {
			SendableObject newObj = null;
			if(type.equals("Move")){
				newObj = new Move((Character)parameters[0], new Point((Integer)parameters[1],(Integer)parameters[2]));
			}
			else if(type.equals("SingleTask")){
				newObj = new SingleTask((String) parameters[0], (Integer) parameters[1]);
			}
			
			if(newObj == null){
				out("Error creating new object. Didn't know how to deal with: " + type);
			}
			else {
				addComm(newObj);
			}
		}
	}
	
	// ************************ ARRAYLIST ACCESSORS **************************
	
	/**
	 * Only done so that access to the commandsForInterface ArrayList is always
	 * synchronised.
	 * 
	 * @param comm The command to add.
	 */
	synchronized private void addComm(SendableObject comm) {
		commands.add(comm);
	}
	
	/**
	 * Takes the next message from the message list.
	 * @return
	 */
	synchronized public SendableObject popCommand() {
		if (commands.isEmpty()) {
			return null;
		} else {
			SendableObject comm = commands.get(0);
			commands.remove(0);
			return comm;
		}
	}
	
	// Helper command
	private void out(Object n) {
		System.out.println(""+n);
	}
}
