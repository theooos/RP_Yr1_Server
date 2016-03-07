package Networking;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Objects.Sendable.Move;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Puppet extends Thread {
	
	private NXTInfo info;
	private DataInputStream fromRobot;
	private DataOutputStream toRobot;
	private PuppetListener listener;
	
	public Puppet(String name, String macAddress) {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			info = new NXTInfo(NXTCommFactory.BLUETOOTH, name, macAddress);
			connect(nxtComm);
			
			listener = new PuppetListener(fromRobot);
			listener.start();
			
			Thread.sleep(5000);
			
			Move testMove = new Move('f',new Point(2,3));
			send(testMove);
			SingleTask testTask = new SingleTask("Something", 42);
			send(testTask);
		}
		catch (NXTCommException | InterruptedException e) {
			out("Failed to connect with: " + name);
		}
	}
	
//	@Override
//	public void run(){
//		while(listener.isAlive()){
//			
//			SendableObject comm = null;
// 			
// 		    while((comm = listener.popCommand()) != null)
// 		    {
// 		    	if(comm instanceof Move){
// 		    		// Do blah
// 		    	}
// 		    	else if(comm instanceof SingleTask){
// 		    		// Do blah
// 		    	}
// 		    }
//			
//			try {
//				Thread.sleep(200);
//			}
//			catch (InterruptedException e) {
//				out("Sleep failed on robot: " + info.name);
//			}
//		}
//		out("Listener died, so " + info.name + " has stopped running.");
//	}
	
	public void send(SendableObject command) {
		String dissolve = command.parameters();
		out("Dissolve: " + dissolve);
		try {
			toRobot.writeUTF(dissolve);
			toRobot.flush();
			out("Wrote dissolve.");			
		} catch (IOException e) {
			out("Sending " + dissolve + " failed");
		}
	}
	
	public SendableObject popCommand(){
		return listener.popCommand();
	}
	
	// **************** ADAPTED FROM NICK'S HELPER CODE ******************
	
	private boolean connect(NXTComm _comm) throws NXTCommException {
		if (_comm.open(info)) {
			fromRobot = new DataInputStream(_comm.getInputStream());
			toRobot = new DataOutputStream(_comm.getOutputStream());
		}
		return isConnected();
	}
	
	public boolean isConnected() {
		return toRobot != null;
	}

	// Helper command
	private void out(Object n) {
		System.out.println(""+n);
	}
}