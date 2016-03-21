package networking;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;

public class Puppet extends Thread {
	
	private NXTInfo info;
	private DataInputStream fromRobot;
	private DataOutputStream toRobot;
	private PuppetListener listener;
	private String name;
	private String macAddress;
	
	public Puppet(String name, String macAddress) {
		this.name = name;
		this.macAddress = macAddress;
		
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			info = new NXTInfo(NXTCommFactory.BLUETOOTH, name, macAddress);
			connect(nxtComm);
			
			listener = new PuppetListener(fromRobot, name);
			listener.start();
			
			RobotInfo info = new RobotInfo(name);
			send(info);
			
			Thread.sleep(5000);
		}
		catch (NXTCommException | InterruptedException e) {
			out("Failed to connect with: " + name);
		}
	}
	
	public void send(SendableObject command) {
		String dissolve = command.parameters();
		try {
			toRobot.writeUTF(dissolve);
			toRobot.flush();
			out("[SENT] " + name + " | " + dissolve);
		} catch (IOException e) {
			out("Sending " + dissolve + " failed");
		}
	}
	
	public SendableObject popCommand(){
		return listener.popCommand();
	}
	
	public String name(){
		return name;
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
