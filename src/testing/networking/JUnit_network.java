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

public class JUnit_network {

	Boolean alive;
	public static SingleTask dummyReturn;

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
		String R_a = "Alfonso";
		
		// Send the object
		
		/*
		while(!AllRobots.checkExists(R_a)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		AllPuppets.send(R_tay, dummyTask);
		System.out.println("TASK SENT");
		
		
		// Waits for the object to be returned
		while(dummyReturn == null) {
			try {
				Thread.sleep(100);
				System.out.println("WAITING FOR OBJECT");
				//AllPuppets.send(R_tay, dummyTask);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Sleeps only the thread, where as delay delays the whole code
		}
		
		System.out.println("FINISHED");
		//System.out.println(dummyReturn.toString());
		
		assertTrue(dummyTask.toString().equals(dummyReturn.toString()));
		assertTrue(dummyTask.equals(dummyReturn));
		System.out.println(dummyTask.toString());
		System.out.println(dummyReturn.toString());
		System.out.println("WHOSE TAYLOR SWIFT ANYWAY?");

	}

}
