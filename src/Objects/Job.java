package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a job to be carried out.
 */
public class Job implements Serializable {

    public static List<Job> currentJobs = new ArrayList<Job>();

    private int jobID;
    private List<SingleTask> tasks;
    
    /**
     * Create an empty job with an ID.
     * @param jobID The job ID.
     */
    public Job(int jobID) {
        this.jobID = jobID;
        this.tasks = new ArrayList<SingleTask>();
    }

    /**
     * Create a job from a job ID and job items.
     * @param jobID The job ID.
     * @param items The items in the job.
     */
    public Job(int jobID, List<SingleTask> tasks) {
        this.jobID = jobID;
        this.tasks = tasks;
    }

    /**
     * Add an item to the list of job items.
     * @param item The job item to be added.
     */
    public void addItem(SingleTask task) {
        tasks.add(task);
    }

    /**
     * Add an item to the list of job items.
     * @param item The item to be added.
     * @param qty The amount of the item needed.
     */
    public void addItem(Item item, int qty) {
        tasks.add(new SingleTask(item, qty));
    }

    // toString method for debugging purposes
	@Override
	public String toString() {
		return "Job [jobID=" + jobID + ", tasks=" + tasks + "]";
	}

    
}
