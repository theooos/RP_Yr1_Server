package testing.jobSelection;

import static org.junit.Assert.assertTrue;

import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

import Objects.Job;
import jobInput.JobProcessor;
import jobSelection.Selection;

/**
 * Test scripts: Test that a priorityQueue of the top 100 priority jobs can be created
 * Links in with JUnit_FileRead as it runs the file reading classes first, so that test must run successfuly before you know this is a valid test pass
 */
public class JUnit_PriorityQueue {
	
	PriorityQueue<Job> priorityQueue;

	/**
	 * Performs the file processing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		// Performs processing of files so all job data is stored
		JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
		JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

		// Calls selection to create the priority queue
		priorityQueue = Selection.createQueue();	
	}

	/**
	 * Performs the tests, asserting that every next value is lower than the one before it
	 */
	@Test
	public void test() {
		
		// While the queue isn't empty, iterate through and pop off one item, then peek another to compare
		while(priorityQueue.size() > 1) {

			// Job removed from queue
			Job jobHead = priorityQueue.remove(); // Get the job
			int id = jobHead.getJobID();
			double rpi = jobHead.rewardPerItem(); // Get the reward per item for the job
			double rpd = jobHead.rewardPerDistance(); // Get the reward per distance for the job
			double canP = jobHead.getCancellationProb().getProbs()[0]; // Get the cancellation probability of the job
		
			Integer totalValueHead = (int) ((rpi * 100 + rpd * 50) * (1-canP) / 2);
			//System.out.println(totalValueHead + " - ID: " + id); // DEBUG PRINT OUT TO GET JOB ID
			
			// Peeked job (not removed) used for a comparison
			Job jobPeek = priorityQueue.peek();
			double rpi2 = jobPeek.rewardPerItem(); 
			double rpd2 = jobPeek.rewardPerDistance();
			double canP2 = jobPeek.getCancellationProb().getProbs()[0];
			
			Integer totalValuePeek = (int) ((rpi2 * 100 + rpd2 * 50) * (1-canP2) / 2);
			
			////////////
			// Asserting that the item on the head should have a value that is greater than or equal to the next item
			System.out.println(totalValueHead + "<- Head... Peek(Head+1) -> " + totalValuePeek); // DEBUG PRINT OUT TO TEST COMPARISON
			assertTrue(totalValueHead >= totalValuePeek);
		}
		
	}

}
