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
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(){
		while(isAlive){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean serverAlive(){
		return isAlive;
	}
}
