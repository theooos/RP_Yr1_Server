package main;
import java.util.PriorityQueue;

import Objects.Job;
import Objects.WarehouseMap;
import jobInput.JobProcessor;
import jobSelection.Selection;

public class Test {
	
	public static WarehouseMap map;

    public static void main(String[] args) {

		JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
		JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

        map = new WarehouseMap("res/drops.csv");

        PriorityQueue<Job> q = Selection.createQueue();
        Job firstJob = q.remove();
        System.out.println(firstJob.getJobID());
        System.out.println(firstJob.getTotalReward());
        firstJob = q.remove();
        System.out.println(firstJob.getJobID());
        System.out.println(firstJob.getTotalReward());
        firstJob = q.remove();
        System.out.println(firstJob.getJobID());
        System.out.println(firstJob.getTotalReward());
        
        
    }

}
