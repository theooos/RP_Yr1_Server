package Objects;

import java.util.List;

import Objects.Sendable.SingleTask;

/**
 * SHARED OBJECTS
 * Used to represent a customer order: the currentJobs, the JobID and the items
 */

public class CustomerOrder {
	
	public List<Job> currentJobs;
	private String JobID;
	private List<SingleTask> tasks;

	public CustomerOrder(String JobID) {
		this.JobID = JobID;
	}
	
	public CustomerOrder(String JobID, List<SingleTask> tasks) {
		this.JobID = JobID;
		this.tasks = tasks;
	}
	
	/**
	 * Add a new task to the list of tasks
	 * @param item Item to add
	 */
	public void addTask(SingleTask task) {
		tasks.add(task);
	}
	
	/**
	 * Add a new abstract item to the list of SingleTasks
	 * @param item Item to add
	 * @param quanity Quantity to add
	 */
	public void addTask(String item, int quantity) {
		tasks.add(new SingleTask(item, quantity));
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "CustomerOrder [currentJobs=" + currentJobs + ", JobID=" + JobID + ", tasks=" + tasks + "]";
	}

}
