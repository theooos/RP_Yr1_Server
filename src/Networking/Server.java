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

}
