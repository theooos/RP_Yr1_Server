package JobInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Create a job from a list of tasks.
     * @param tasks The tasks in the job.
     */
    public Job(List<Task> tasks) {
        this.tasks = tasks;

        rewardPerItem();
        rewardPerWeight();
    }

    /**
     * Add an item to the list of job items.
     * @param item The job item to be added.
     */
    public void addTask(Task t) {
        tasks.add(t);

        rewardPerItem();
        rewardPerWeight();
    }

    /**
     * Add an item to the list of job items.
     * @param item The item to be added.
     * @param qty The amount of the item needed.
     */
    public void addTask(Item item, int qty) {
        tasks.add(new Task(item, qty));

        rewardPerItem();
        rewardPerWeight();
    }

    /**
     * Calculate the reward for this job per item.
     * @return The reward per item.
     */
    public double reward() {
        
        int numOfItems = 0;
        double reward = 0f;

        for(int i = 0; i < tasks.size(); i++) {
            Item item = tasks.get(i).getItem();
            numOfItems += tasks.get(i).getQuantity();
            reward += item.getReward() * tasks.get(i).getQuantity(); 
        }

        return (reward / (double) tasks.size());

    }

    /**
     * Calculate the reward for this job per weight.
     * @return The reward per weight.
     */
    public double rewardPerWeight() {

        double reward = 0f;
        double weight = 0f;

        for(int i = 0; i < tasks.size(); i++) {
            Item item = tasks.get(i).getItem();
            reward += item.getReward() * tasks.get(i).getQuantity();
            weight += item.getWeight() * tasks.get(i).getQuantity();
        }

        return (reward / weight);
    }

}
