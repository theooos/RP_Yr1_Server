package jobSelection;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

import JobInput.JobProcessor;
import Objects.Job;

public class Selection { 
	
	public static PriorityQueue<Job> priorityQueue = new PriorityQueue<Job>(100, new Comparator<Job>() {
		
		@Override
		public int compare(Job job1, Job job2) {

            Integer valueJob1 = (int) ((job1.rewardPerItem() * 100 + job1.rewardPerDistance() * 100) / 2);
            Integer valueJob2 = (int) ((job2.rewardPerItem() * 100 + job2.rewardPerDistance() * 100) / 2);
			
            return 1;
	
		}
	});
	
	
	/**
	 * createQueue method 
	 * @param joblist
	 * @return jobQueue the priority queue
	 */
	public static PriorityQueue<Job> createQueue(){
		
		Map<Integer, Job> jobMap = JobProcessor.getAllJobs();
		
		//add to queue
		for(Job j : jobMap.values())
			if(j.getTotalWeight()<50)
				priorityQueue.add(j);

		return priorityQueue;
	};	
	

}	
		


