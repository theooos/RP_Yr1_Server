package Networking;

/**
 * The class to instantiate.
 * @author theo
 *
 */
public class Server extends Thread {
	
	private boolean isAlive = true;
	private RobotLobby lobby = new RobotLobby();
	
	public Server() {
		lobby.start();
		Puppet testPup = new Puppet("Alfonso", "00165308DA58");
//		lobby.addRobot(testPup);
	}
	
	@Override
	public void run(){
		while(isAlive){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				out("Server sleep failed.");
			}
		}
	}
	
	public boolean serverAlive(){
		return isAlive;
	}
	
	// Helper command
	private void out(Object n) {
		System.out.println(""+n);
	}
	
	public static void main(String[] args){
		Server serv = new Server();
		serv.start();
	}

}
