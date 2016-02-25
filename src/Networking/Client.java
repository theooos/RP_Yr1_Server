package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class Client {

	private ClientReceiver receiver;
	private ClientSender sender;
	
	private UUID name;
	
	public Client(String[] args) {
		name = UUID.randomUUID();
		
		// TODO Set up connection properly.
		ClientConnection con = new ClientConnection();
		con.start();
		
		DataOutputStream out = con.getToServer();
		while (out == null) out = con.getToServer();
	    this.sender = new ClientSender(out);
	    sender.start();
	    
	    DataInputStream in = con.getFromServer();
	    while (in == null) in = con.getFromServer();
    	this.receiver = new ClientReceiver(in);
	    receiver.start();
	    
	    // TODO Once objects are set up, use a name object.
	    send(name);
	    
 		while (receiver.isAlive()) {			
 			
 		   	// Goes through all available instructions and executes them.
 			Object comm = null;
 			
 		    while((comm = receiver.popCommand()) != null)
 		    {
 		    	String cT = comm.getClass().getName();
 		    	
 		    	if(cT.equals("Move") || cT.equals("Something")){
 		    		// Give it to the motion.
 		    	}
 		    	else if(cT.equals("Somethingelse") || cT.equals("Test")){
 		    		// Give it to the interface.
 		    	}
 		    }
 		}
     	out("MyClient no longer running");
	}
	
	/**
	 * Sends a message to the sender.
	 * @param comm
	 */
	public void send(Object comm){
    	sender.send(comm);
    }

	public static void main(String[] args) {
		new Client(args);
	}
	
	// ********************** HELPER METHODS *********************

	private void out(Object n) {
		System.out.println(""+n);
	}
}
