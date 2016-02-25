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
		
		ClientConnection con = new ClientConnection(args);
		con.start();
		
		DataOutputStream out = con.getToServer();
		while (out == null) out = con.getToServer();
	    this.sender = new MySender(out);
	    sender.start();
	    
	    DataInputStream in = con.getFromServer();
	    while (in == null) in = con.getFromServer();
    	this.listener = new MyListener(in);
	    listener.start();
	}

	public static void main(String[] args) {
		new Client(args);
	}

}
