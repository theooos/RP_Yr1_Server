package jobSelection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import JobInput.JobProcessor;
import Objects.Item;
import Objects.Job;
import Objects.Sendable.SingleTask;

public class Features {

    public Map<Integer, Predicate<Job>> predicates;

    public Features() {

        predicates = new HashMap<Integer, Function<Collection<Job>, Long>>();
        predicates.put(NumOfTasks.ONETOTWO, j -> j.getNumOfTasks() >= 1 && j.getNumTasks() <= 2);
        predicates.put(NumOfTasks.THREETOFOUR,  -> j.getNumOfTasks() >= 3 && j.getNumOfTasks() <= 4);
        predicates.put(NumOfTasks.ONETOTWO, j -> j.getNumOfTasks() >= 5);

        predicates.put(TotalItems.ONETOFOUR, j -> totalNumItems(j) >= 1 && totalNumItems(j) <= 4)
        predicates.put(TotalItems.FIVETOEIGHT, j -> totalNumItems(j) >= 5 && totalNumItems(j) <= 8);
        predicates.put(TotalItems.NINETOTWELVE, j -> totalNumItems(j) >= 9 && totalNumItems(j) <= 12);
        predicates.put(TotalItems.THIRTEENPLUS, j -> totalNumItems(j) >= 13);
       
        predicates.put(HighestRewardItem.ZEROTOFIVE, j -> highestReward(j) >= 0 && highestReward(j) <= 5);
        predicates.put(HighestRewardItem.SIXTOTEN, j -> highestReward(j) >= 6 && highestReward(j) <= 10);
        predicates.put(HighestRewardItem.ELEVENTOFIFTEEN, j -> highestReward(j) >= 11 && highestReward(j) <= 15);
        predicates.put(HighestRewardItem.SIXTEENPLUS, j -> highestReward(j) >= 16);
        
        predicates.put(HeaviestItem.ZEROTOONE, j -> heaviestItem(j) >= 0 && heaviestItem(j) <= 1);
        predicates.put(HeaviestItem.TWOTOTHREE, j -> heaviestItem(j) >= 2 && j -> heaviestItem(j) <= 3);
        predicates.put(HeaviestItem.FOURPLUS, j -> heaviestItem(j) >= 4);
        
        predicates.put(HighestQuantity.ONETOTWO, j -> highestQuantity(j) >= 1 && highestQuantity(j) <= 2);
        predicates.put(HighestQuantity.THREETOFOUR, j -> highestQuantity(j) >= 3 && highestQuantity(j) <= 4);
        predicates.put(HighestQuantity.FIVETOSIX, l -> j -> highestQuantity(j) >= 5 && highestQuantity(j) <= 6);
        predicates.put(HighestQuantity.SEVENPLUS, l -> j -> highestQuantity(j) >= 7);
    }

    public static class NumOfTasks {
        
        public static int ONETOTWO = 0;
        public static int THREETOFOUR = 1;
        public static int FIVEPLUS = 2;

    }

    public static class TotalItems {

        public static int ONETOFOUR = 3;
        public static int FIVETOEIGHT = 4;
        public static int NINETOTWELVE = 5;
        public static int THIRTEENPLUS = 6;

    }
    
    public int totalNumItems(Job job){
    	
    	int quantity = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		quantity = quantity + task.getQuantity();
    		
    	}
    	
    	return quantity;
    }

    
    public static class HighestRewardItem {
        
        public static int ZEROTOFIVE = 7;
        public static int SIXTOTEN = 8;
        public static int ELEVENTOFIFTEEN = 9;
        public static int SIXTEENPLUS = 10;

    }
    
    public double highestReward(Job job){
    	
    	double reward = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(JobProcessor.getItem(task.getItemID()).getReward() > reward){
    			 
    			reward = JobProcessor.getItem(task.getItemID()).getReward();
    		 }
    		
    		
    	}
    	
    	return reward;
    }
    
    

    public static class HeaviestItem {

        public static int ZEROTOONE = 11;
        public static int TWOTOTHREE = 12;
        public static int FOURPLUS = 13;

    }
    
    public double heaviestItem(Job job){
    	
    	double heaviest = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(JobProcessor.getItem(task.getItemID()).getWeight() > heaviest){
    			 
    			heaviest = JobProcessor.getItem(task.getItemID()).getWeight();
    		 }
    		
    		
    	}
    	
    	return heaviest;
    	
    }

    public static class HighestQuantity {

        public static int ONETOTWO = 14;
        public static int THREETOFOUR = 15;
        public static int FIVETOSIX = 16;
        public static int SEVENPLUS = 17;

    }
    
    public double highestQuantity(Job job){
    	
    	double quantity = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(task.getQuantity() > quantity){
    			 
    			quantity = task.getQuantity();
    		 }
    		
    		
    	}
    	
    	return quantity;

    }
}
