package jobSelection;

import Objects.Job;
import jobInput.JobProcessor;

public class Test {

    public static void main(String[] args) {

    	JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

        Probability p = new Probability(JobProcessor.getAllJobs().values());

        Job j = JobProcessor.getJob(10300);

        System.out.println(p.getFeatures(j));
        System.out.println(p.probabilityCancelled(j));
        System.out.println(1 - p.probabilityCancelled(j));
    }

}
