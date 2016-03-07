package Testing.JobSelection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import JobInput.*;
import Objects.Item;
import Objects.Job;

public class JUnit_FileRead {

	@Before
	public void setUp() throws Exception {

        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

	}

	@Test
	public void test() {
		// Testing the jobs
		//assertTrue(processor.getAllJobs().size() == 100); // Only 100 jobs in the demo test file
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(0).get().getItemID().equals("ba"));
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(0).get().getQuantity() == 2);
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(1).get().getItemID().equals("bi"));
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(1).get().getQuantity() == 3);
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(2).get().getItemID().equals("cb"));
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(2).get().getQuantity() == 3);
		
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(0).get().getItemID().equals("af"));
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(0).get().getQuantity() == 2);
		
		// Testing the items
		assertTrue(JobProcessor.getAllItems().size() == 30); // All 30 in the given file
		assertTrue(JobProcessor.getAllItems().get("aa").getLocation().getX() == 5);
		assertTrue(JobProcessor.getAllItems().get("aa").getLocation().getY() == 3);
		assertTrue(JobProcessor.getAllItems().get("aa").getReward() == 12.78);
		assertTrue(JobProcessor.getAllItems().get("aa").getWeight() == 0.36);
	}

}
