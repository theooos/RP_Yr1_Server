package Objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import JobInput.JobProcessor;
import Objects.Sendable.SingleTask;

/**
 * Represents a job to be carried out.
 */
public class Job {

	private List<SingleTask> tasks;
    private boolean cancelled;
    private float cancellationProb;

	/**
	 * Create an empty job.
	 */
	public Job() {
		this.tasks = new ArrayList<>();
        this.cancelled = false;
        this.cancellationProb = 0.0f;
	}

	/**
	 * Create a job from a list of tasks.
	 * @param tasks The tasks in the job.
	 */
	public Job(List<SingleTask> tasks) {
		this.tasks = tasks;
        this.cancelled = false;
        this.cancellationProb = 0.0f;
	}

	/**
	 * Add a task to the list of tasks.
	 * @param t The task to be added.
	 */
	public void addTask(SingleTask t) {
		tasks.add(t);
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

	public List<SingleTask> getTasks()
	{
		return tasks;
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
        return tasks.size();
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
			Item item = JobProcessor.getItem(task.getItemID());
			numOfItems += task.getQuantity();
			reward += item.getReward() * task.getQuantity();
		}

		return (reward / (double) numOfItems);

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
			Item item = JobProcessor.getItem(task.getItemID());
			reward += item.getReward() * task.getQuantity();
			weight += item.getWeight() * task.getQuantity();
		}

		return (reward / weight);
	}
	
    
    /**
     * calculate total weight of job
     * @return totalweight the total weight of the task
     */
     public double getTotalWeight() {
    	
    	double totalweight = 0.0;
    	
    	for (int i = 0; i < tasks.size(); i++){
    		Item item = tasks.get(i).getItem();
    		totalweight = totalweight + (item.getWeight() * tasks.get(i).getQuantity());
    	}
    	
    	return totalweight;
    }
    
	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Job [tasks=" + tasks + "]";
	}   

}
