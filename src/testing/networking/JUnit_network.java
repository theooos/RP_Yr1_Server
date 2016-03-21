package testing.networking;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Objects.AllPuppets;
import Objects.AllRobots;
import Objects.Sendable.SingleTask;
import lejos.util.Delay;
import networking.PuppetListener;

public class JUnit_network {

	Boolean alive;
	SingleTask dummyReturn;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
	
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
		
		// Send the object
		AllPuppets.send(R_tay, dummyTask);
		
		
		// Waits for the object to be returned
		while(dummyReturn.equals(null)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Sleeps only the thread, where as delay delays the whole code
			System.out.println(dummyReturn.toString());
		}
		

	}

}
