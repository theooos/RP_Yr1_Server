package JobSelection;

import JobInput.JobProcessor;
import Objects.Job;

public class Test {

    public static void main(String[] args) {

    	JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        
        ProbDistribution prob = ProbDistribution.calculateProbDistr(JobProcessor.getAllJobs().values(), Job::cancelled);
        System.out.println(prob);
        
    }

}
