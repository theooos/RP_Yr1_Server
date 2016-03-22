package testing.jobSelection;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jobInput.JobProcessor;

/**
 * Test scripts: Test that information can be successfully read from files
 * Reading from csv files: jobs (contains the list of jobs), items (contains weights and rewards)
 * locations (location of items) and cancellations (previously cancelled jobs)
 */
public class JUnit_FileRead {

	/**
	 * Performs the processing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

	}

	/**
	 * Performs the tests, asserting that values we know to be true in the passed csv files are correct
	 */
	@Test
	public void test() {
		System.out.println(JobProcessor.getJob(10000));
		// Testing the jobs
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(0).get().getItemID().equals("cc"));
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(0).get().getQuantity() == 1);
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(1).get().getItemID().equals("bf"));
		assertTrue(JobProcessor.getAllJobs().get(10000).getTaskAtIndex(1).get().getQuantity() == 1);
		
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(0).get().getItemID().equals("ab"));
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(0).get().getQuantity() == 1);
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(1).get().getItemID().equals("aj"));
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(1).get().getQuantity() == 3);
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(2).get().getItemID().equals("bf"));
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(2).get().getQuantity() == 3);
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(3).get().getItemID().equals("cc"));
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(3).get().getQuantity() == 1);
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(4).get().getItemID().equals("ce"));
		assertTrue(JobProcessor.getAllJobs().get(10015).getTaskAtIndex(4).get().getQuantity() == 1);
		
		// Testing the items
		assertTrue(JobProcessor.getAllItems().size() == 30); // All 30 in the given file
		assertTrue(JobProcessor.getAllItems().get("aa").getLocation().getX() == 9);
		assertTrue(JobProcessor.getAllItems().get("aa").getLocation().getY() == 1);
		assertTrue(JobProcessor.getAllItems().get("aa").getReward() == 16.02);
		assertTrue(JobProcessor.getAllItems().get("aa").getWeight() == 0.38);
	}

}
