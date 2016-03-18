package Objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import JobInput.JobProcessor;
import Objects.Sendable.SingleTask;
import main.Test;
import routePlanning.orderPicks.OrderPicks;

/**
 * SHARED OBJECTS
 * Represents a Job to be carried out
 */
public class Job {

	private List<SingleTask> tasks;
	private boolean cancelled;
	private boolean ordered;
	private float cancellationProb;
	private Map<String, Item> items;
	private int jobid;
	private int distanceToTravel;

	/**
	 * Create an empty job
	 * @param jobid The ID of the job
	 * @param items The items to add to the job
	 */
	public Job(int jobid, Map<String, Item> items) {
		this.tasks = new ArrayList<>();
		this.cancelled = false;
		this.ordered = false;
		this.cancellationProb = 0.0f;
		this.items = items;
		this.jobid = jobid;
	}

	/**
	 * Create a job with items
	 * @param jobid The ID of the job
	 * @param tasks The tasks in the job.
	 * @param items The items for the job.
	 */
	public Job(int jobid, List<SingleTask> tasks, Map<String, Item> items) {
		this.tasks = tasks;
		this.cancelled = false;
		this.ordered = false;
		this.cancellationProb = 0.0f;
		this.jobid = jobid;
	}

	/**
	 * Get the ID of the job
	 * @return The ID of the job
	 */
	public int getJobID(){
		return jobid;
	}

	/**
	 * Add a task to the list of tasks.
	 * @param t The task to be added.
	 */
	public void addTask(SingleTask t) {
		tasks.add(t);
	}

	/**
	 * Get the list of tasks for the current job
	 * @return The list of tasks
	 */
	public List<SingleTask> getTasks()
	{
		return tasks;
	}

	/**
	 * Add an item to the list of job items.
	 * @param itemID The item id to be added.
	 * @param qty The amount of the item needed.
	 */
	public void addTask(String itemID, int qty, Point location) {
		tasks.add(new SingleTask(itemID, qty, location));
	}

	/**
	 * Get the task at the given index.
	 * @param i The given index.
	 * @return The task at the given index if it exists.
	 */
	public Optional<SingleTask> getTaskAtIndex(int i) {
		if(i >= 0 && i < tasks.size())
			return Optional.of(tasks.get(i));
		else
			return Optional.empty();
	}

	/**
	 * Get the task with the given item ID.
	 * @param itemID The item ID.
	 * @return The task with the given item ID if it exists.
	 */
	public Optional<SingleTask> getTask(String itemID) {
		for(SingleTask task : tasks)
			if(task.getItemID().equals(itemID))
				return Optional.of(task);
		return Optional.empty();
	}

	/**
	 * Set the cancellation probability.
	 * @param p The cancellation probability.
	 */
	public void setCancellationProb(float p) {
		this.cancellationProb = p;
	}

	/**
	 * Get the cancellation probability.
	 * @return The cancellation probability.
	 */
	public float getCancellationProb() {
		return cancellationProb;
	}

	/**
	 * Check if this job is cancelled.
	 * @return If this job is cancelled.
	 */
	public boolean cancelled() {
		return cancelled;
	}

	/**
	 * Cancel this job.
	 */
	public void cancel() {
		cancelled = true;
	}

	/**
	 * Get the number of tasks.
	 * @return The number of tasks.
	 */
	public int getNumOfTasks() {
        int count = 0;
        for(int i = 0; i < tasks.size(); i++) {
            if(!tasks.get(i).getItemID().equals("dropoff"))
                count++;
        }
		return count; 
	}

	/**
	 * Calculate the reward for this job per item.
	 * @return The reward per item.
	 */
	public double rewardPerItem() {

		int numOfItems = 0;
		double reward = 0f;

		for(SingleTask task : tasks)
		{
            if(!task.getItemID().equals("dropoff")) {
                Item item = items.get(task.getItemID());
                //Item item = JobProcessor.getItem(task.getItemID());
                numOfItems += task.getQuantity();
                reward += item.getReward() * task.getQuantity();
            }
		}

		return (reward / (double) numOfItems);

	}

	public double getTotalReward() {

		double reward = 0f;
		for(SingleTask task : tasks) {
            if(!task.getItemID().equals("dropoff")) {
                Item item = items.get(task.getItemID());
                reward += item.getReward() * task.getQuantity();
            }
		}
		return reward;

	}

	/**
	 * Calculate the reward for this job per weight.
	 * @return The reward per weight.
	 */
	public double rewardPerWeight() {

		double reward = 0f;
		double weight = 0f;

		for(SingleTask task : tasks)
		{
            if(!tasks.getItemID.equals("dropoff")) {
                Item item = items.get(task.getItemID());
                reward += item.getReward() * task.getQuantity();
                weight += item.getWeight() * task.getQuantity();
            }
		}

		return (reward / weight);
	}

	/**
	 * calculate total weight of job
	 * @return totalweight the total weight of the task
	 */
	public double getTotalWeight() {

		double totalweight = 0.0;

		for(SingleTask task : tasks)
		{
            if(!tasks.getItemID.equals("dropoff")) {
                Item item = items.get(task.getItemID());
                totalweight = totalweight + (item.getWeight() * task.getQuantity());
            }
		}

		return totalweight;
	}

	public double rewardPerDistance() {

		if(ordered)
			return getTotalReward() / distanceToTravel;
		else {
			OrderPicks op = new OrderPicks(tasks, Test.map.getDropoffPoints(), Test.map); 
			this.tasks = op.orderedItems;
			distanceToTravel = op.getFinalDistance();
			this.ordered = true;
			return getTotalReward() / distanceToTravel;
		}

	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Job [tasks=" + tasks + ", cancelled=" + cancelled + ", ordered=" + ordered + ", cancellationProb="
				+ cancellationProb + ", items=" + items + ", jobid=" + jobid + ", distanceToTravel=" + distanceToTravel
				+ "]";
	}   	
}
