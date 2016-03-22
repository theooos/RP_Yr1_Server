package testing.networking;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Objects.AllPuppets;
import Objects.AllRobots;
import Objects.Sendable.SingleTask;
import lejos.util.Delay;
import networking.PuppetListener;

/**
 * Test scripts: Test that a bluetooth connection can be made with a robot then an object is sent down and returned
 * Code validates that the object sent down (SingleTask) is sent back and contains all the same parameters since it's stripped and rebuilt.
 */
public class JUnit_network {

	Boolean alive;
	public static SingleTask dummyReturn;

	/**
	 * A static method to return the item sent back by the robot
	 * @param obj The object sent back
	 */
	public static void getItemBack(Object obj) {
		SingleTask dummyReturn = (SingleTask) obj;
	}

	@Test
	public void test() {

		// Starts server side thread
		startServer netTest = new startServer();
		netTest.start();
		
		// Creates dummy task to send 
		SingleTask dummyTask = new SingleTask("TARDIS", 1963, new Point(3,3));
		
		// Robot name
		String R_tay = "TayTay";
		String R_a = "Alfonso";
		String R_jc = "John Cena";
		
		// Send the object
		AllPuppets.send(R_tay, dummyTask); // Change name to the appropriate robot used for testing
		System.out.println("DUMMY OBJECT SENT");
		
		// Waits for the object to be returned
		while(dummyReturn == null) {
			try {
				Thread.sleep(500);
				System.out.println("WAITING FOR OBJECT");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // Sleeps only the thread, where as delay delays the whole code
		}
		
		System.out.println("\n FINISHED");
		
		System.out.println("Item sent: " + dummyReturn.toString());
		System.out.println("Item returned: " + dummyReturn.toString());
		
		// Asserting that the above are both equal to eachother
		assertTrue(dummyTask.toString().equals(dummyReturn.toString()));
		
		// Should hopefully end the server, so the robot end should stop and reboot too since connection is lost.
		netTest.end();

	}

}
