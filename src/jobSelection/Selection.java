package jobSelection;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

import Objects.Job;
import jobInput.JobProcessor;
import main.RunServer;
import routePlanning.orderPicks.OrderPicks;

/**
 * selection class
 * creates and orders priority queue of jobs
 *
 */
public class Selection { 
	
	/**
	 * constructor
	 */
	public static PriorityQueue<Job> priorityQueue = new PriorityQueue<Job>(new Comparator<Job>() {
		
		/**
		 * overwritten compare method
		 * @param job1 first job
		 * @param job2 second job 
		 */
		@Override
		public int compare(Job job1, Job job2) {

	        Integer valueJob1 = (int) ((job1.rewardPerItem() * 100 + job1.rewardPerDistance() * 50) / 2);
            Integer valueJob2 = (int) ((job2.rewardPerItem() * 100 + job2.rewardPerDistance() * 50) / 2);
	
            return valueJob2.compareTo(valueJob1);
		}
	});
	
	
	/**
	 * createQueue method 
	 * @param joblist
	 * @return jobQueue the priority queue
	 */
	public static PriorityQueue<Job> createQueue(){
		
		Map<Integer, Job> jobMap = JobProcessor.getAllJobs();
		//OrderPicks op = new OrderPicks(tasks, RunServer.map.getDropoffPoints(), RunServer.map); 
		//add to queue
		for(Job j : jobMap.values())
			if(j.getTotalWeight()<50 && !j.cancelled() && j.getCancellationProb().getProbs()[0] < 0.5){
 				//System.out.println("job being added to queue: "+ j);
				priorityQueue.add(j);
				
			}
		
		//System.out.println(priorityQueue);
		return priorityQueue;
	};	
	

}	
		


