package JobSelection;

import JobInput.JobProcessor;
import Objects.Job;

public class Test {

    public static void main(String[] args) {

    	JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        
        ProbDistribution prob = ProbDistribution.calculateProbDistr(JobProcessor.getAllJobs().values(), Job::cancelled);
        ProbDistribution prob2 = ProbDistribution.calculateProbDistr(JobProcessor.getAllJobs().values(), j -> (j.getNumberOfTasks() >= 1 && j.getNumberOfTasks() <= 2));
        System.out.println(prob);
        
    }

}
