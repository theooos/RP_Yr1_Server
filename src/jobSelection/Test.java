package jobSelection;

import java.util.PriorityQueue;

import Objects.Job;
import jobInput.JobProcessor;

public class Test {

    public static void main(String[] args) {

    	JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
        
        Probability p = new Probability(JobProcessor.getAllJobs().values(), JobProcessor.getAllItems().values());	
        
        PriorityQueue<Job> q = Selection.createQueue();
        
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        
        for(Job j : JobProcessor.getAllJobs().values()) {
        	j.setCancellationProb(p.probabilityCancelled(j));
        }
System.out.println();
        q = Selection.createQueue();
        
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        System.out.println(q.remove().rewardPerItem());
        
    }

}
