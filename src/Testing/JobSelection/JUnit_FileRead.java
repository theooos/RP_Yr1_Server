package Testing.JobSelection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import JobInput.JobProcessor;
import Objects.Item;
import Objects.Job;

public class JUnit_FileRead {
	
	private JobProcessor processor;

	@Before
	public void setUp() throws Exception {

		processor = new JobProcessor();

		processor.processBothFiles("/Users/georgesabourin/Documents/eworkspace/rp-1.1/res/JobInput/items.csv", "/Users/georgesabourin/Documents/eworkspace/rp-1.1/src/JobInput/locations.csv");

		processor.processJobFile("/Users/georgesabourin/Documents/eworkspace/rp-1.1/res/JobInput/jobs_testing(w8).csv");

	}

	@Test
	public void test() {
		// Testing the jobs
		assert(processor.getAllJobs().size() == 100); // Only 100 jobs in the demo test file
		assert(processor.getAllJobs().get(10000).getTaskAtIndex(0).get().getItemID().equals("ba"));
		assert(processor.getAllJobs().get(10000).getTaskAtIndex(0).get().getQuantity() == 2);
		assert(processor.getAllJobs().get(10000).getTaskAtIndex(1).get().getItemID().equals("bi"));
		assert(processor.getAllJobs().get(10000).getTaskAtIndex(1).get().getQuantity() == 3);
		assert(processor.getAllJobs().get(10000).getTaskAtIndex(1).get().getItemID().equals("cb"));
		assert(processor.getAllJobs().get(10000).getTaskAtIndex(1).get().getQuantity() == 3);
		
		assert(processor.getAllJobs().get(10015).getTaskAtIndex(1).get().getItemID().equals("aj"));
		assert(processor.getAllJobs().get(10015).getTaskAtIndex(1).get().getQuantity() == 2);
		
		// Testing the items
		assert(processor.getAllItems().size() == 30); // All 30 in the given file
		assert(processor.getAllItems().get("aa").getX() == 5);
		assert(processor.getAllItems().get("aa").getY() == 3);
		assert(processor.getAllItems().get("aa").getReward() == 12.78);
		assert(processor.getAllItems().get("aa").getWeight() == 0.36);
	}

}
