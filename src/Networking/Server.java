package Networking;

public class Server extends Thread {
	
	private boolean isAlive = true;
	
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
	
	
	public void send(String comm){
		// TODO The sending bit.
		System.out.println(comm);
	}
	
	public boolean serverAlive(){
		return isAlive;
	}
}
