package JobInput;

import java.util.ArrayList;
import java.util.List;
import java.util.HasMap;
import java.util.Map;

/**
 * Represents a job to be carried out.
 */
public class Job {

    public static Map<Integer, Job> currentJobs = new HashMap<Integer, Job>();

    private List<Task> tasks;
    
    /**
     * Create an empty job.
     */
    public Job() {
        this.items = new ArrayList<JobItem>();
    }

    /**
     * Create a job from a list of tasks.
     * @param tasks The tasks in the job.
     */
    public Job(List<Task> tasks) {
        this.tasks = tasks;

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
        
        int numOfItems = 0;
        double reward = 0f;

        for(int i = 0; i < tasks.size(); i++) {
            Item item = tasks.get(i).getItem();
            numOfItems += items.getQuantity();
            reward += item.getReward() * item.getQuantity(); 
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

        for(int i = 0; i < items.size(); i++) {
            Item item = tasks.get(i).getItem();
            reward += item.getReward() * item.getQuantity();
            weight += item.getWeight() * item.getQuantity();
        }

        return (reward / weight);
    }

}
