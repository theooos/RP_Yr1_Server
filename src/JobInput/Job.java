package JobInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a job to be carried out.
 */
public class Job {

    public static List<Job> currentJobs = new ArrayList<Job>();

    private int jobID;
    private List<Task> tasks;
    
    /**
     * Create an empty job with an ID.
     * @param jobID The job ID.
     */
    public Job(int jobID) {
        this.jobID = jobID;
        this.items = new ArrayList<JobItem>();
    }

    /**
     * Create a job from a job ID and job items.
     * @param jobID The job ID.
     * @param items The items in the job.
     */
    public Job(int jobID, List<JobItem> items) {
        this.jobID = jobID;
        this.items = items;

        calculateRewardPerItem();
        calculateRewardPerWeight();
    }

    /**
     * Add an item to the list of job items.
     * @param item The job item to be added.
     */
    public void addTask(Task t) {
        tasks.add(t);

        calculateRewardPerItem();
        calculateRewardPerWeight();
    }

    /**
     * Add an item to the list of job items.
     * @param item The item to be added.
     * @param qty The amount of the item needed.
     */
    public void addTask(Item item, int qty) {
        tasks.add(new Task(item, qty));

        calculateRewardPerItem();
        calculateRewardPerWeight();
    }

    /**
     * Calculate the reward for this job per item.
     * @return The reward per item.
     */
    public double rewardPerItem() {
        
        double reward = 0f;

        for(int i = 0; i < tasks.size(); i++) {
            Item item = tasks.get(i).getItem();
            reward += item.getReward() * item.getQuantity(); 
        }

        return (reward / (double) items.size());

    }

    /**
     * Calculate the reward for this job per weight.
     * @return The reward per weight.
     */
    public double rewardPerWeight() {

        double reward = 0f;
        double weight = 0f;

        for(int i = 0; i < items.size(); i++) {
            Item item = tasks.get(i).getItem();
            reward += item.getReward() * item.getQuantity();
            weight += item.getWeight() * item.getQuantity();
        }

        return (reward / weight);
    }

}
