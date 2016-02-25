package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import lejos.util.Delay;

public class ClientConnection extends Thread {

	boolean alive = true;
	
	public ClientConnection() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(){
		establishConnection();
		
		while(alive){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				out("Couldn't sleep thread");
			}
		}
		
		closeConnection();
	}

	// TODO This
	public DataOutputStream getToServer(){
		return null;
	}
	
	// TODO This
	public DataInputStream getFromServer(){
		return null;
	}

	public void shutdown(){
		alive = false;
	}
	
	private void out(Object n) {
		System.out.println(""+n);
	}
	
	// ********************* ALL DEFAULT CODE FROM MARTIN HERE *****************************
    
    // Error code convention for System.exit():
    static final int badUsage                = 1;
    static final int ioError                 = 2;
    static final int connectionBroke         = 3;
    static final int couldNotCloseConnection = 4;
    static final int unknownHost             = 5;
    
    // Helper method:
//    private Socket getSocket() throws UnknownHostException, IOException, IllegalArgumentException
//    {
//    	if (args.length != 3) throw new IllegalArgumentException("Usage: java Client portNumber hostName");
//
//        String serverName = args[2];
//        int serverPort = 0;
//
//        try {
//            serverPort = Integer.parseInt(args[1]);
//        }
//        catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Second argument must be an integer");
//        }
//
//        return new Socket(serverName, serverPort);
//    }
//
//
//    // Exits if fails:
    private void establishConnection() {
//        try {
//            this.serverSocket = getSocket();
//        }
//        catch (IllegalArgumentException e) {
//            System.err.println(e);
//            System.err.println("Usage: java Client serverName serverPort");
//            System.exit(badUsage);
//        }
//        catch (UnknownHostException e) {
//            System.err.println("Unknown host");
//            System.err.println(e);
//            System.exit(unknownHost);
//        } 
//        catch (IOException e) {
//            System.err.println("IO error while connecting to server");
//            System.err.println(e);
//            System.exit(ioError);
//        } 
//
//        try {
//            this.toServer = new DataOutputStream(this.serverSocket.getOutputStream());
//            this.fromServer = new DataInputStream(this.serverSocket.getInputStream());
//        } 
//        catch (IOException e) {
//            System.err.println("connectionBroke");
//            System.err.println(e);
//            System.exit(connectionBroke);
//        }
    }
//
//
    private void closeConnection() {
//        try {
//            toServer.close();   
//            fromServer.close();
//            serverSocket.close();
//        }
//        catch (IOException e) {
//            System.err.println("Couldn't gracefully close connection");
//            System.err.println(e);
//            System.exit(couldNotCloseConnection);
//        }
    }
}
