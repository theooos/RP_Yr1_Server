import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import JobInput.JobProcessor;
import Objects.Job;
import Objects.Sendable.SingleTask;

public class Selection { 
	
	private final Collection<Job> joblist;
	
	/**
	 * Selection class
	 * @param joblist the unordered list of jobs
	 */
	//pass the selection class a list of jobs (JobProcessor.getAllJobs.values())
	public Selection(){
		
		this.joblist = JobProcessor.getAllJobs().values();
	
	}
	
	
	
	/**
	 * createQueue method 
	 * @param joblist
	 * @return jobQueue the priority queue
	 */
	public PriorityQueue<Job> createQueue(List<Job> joblist){
	
		PriorityQueue<Job> jobQueue = new PriorityQueue<Job>(100, new Comparator<Job>() {
	
			@Override
			public int compare(Job job1, Job job2) {
	
				
				return ((Integer) ( (int)   (job1.rewardPerItem()*100))).compareTo( (Integer) ( (int) (job2.rewardPerItem()*100) ) );
		
			}
		});
			
		//add to queue
		for(Job j : joblist) {
			
			if(j.getTotalWeight()<50){
				jobQueue.add(j);
			}
			
		}
		
		return jobQueue;
	};	
	

}	
		


