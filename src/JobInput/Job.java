package JobInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a job to be carried out.
 */
public class Job {

    public static List<Job> currentJobs = new ArrayList<Job>();

    private int jobID;
    private List<JobItem> items;
    
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
    }

    /**
     * Add an item to the list of job items.
     * @param item The job item to be added.
     */
    public void addItem(JobItem item) {
        items.add(item);
    }

    /**
     * Add an item to the list of job items.
     * @param item The item to be added.
     * @param qty The amount of the item needed.
     */
    public void addItem(Item item, int qty) {
        items.add(new JobItem(item, qty));
    }

}
