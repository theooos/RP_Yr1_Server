package jobSelection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import JobInput.JobProcessor;
import Objects.Item;
import Objects.Job;
import Objects.Sendable.SingleTask;

public class Features {

    public Map<Integer, Function<Collection<Job>, Long>> functions;

    public Features() {

        functions = new HashMap<Integer, Function<Collection<Job>, Long>>();
        functions.put(NumOfTasks.ONETOTWO, l -> l.stream().filter(j -> j.getNumOfTasks() >= 1).filter(j -> j.getNumOfTasks() <= 2).count());
        functions.put(NumOfTasks.THREETOFOUR, l -> l.stream().filter(j -> j.getNumOfTasks() >= 3).filter(j -> j.getNumOfTasks() <= 4).count());
        functions.put(NumOfTasks.ONETOTWO, l -> l.stream().filter(j -> j.getNumOfTasks() >= 5).count());

        functions.put(TotalItems.ONETOFOUR, l -> l.stream().filter(j -> totalNumItems(j) >= 1).filter(j -> totalNumItems(j) <= 4).count());
        functions.put(TotalItems.FIVETOEIGHT, l -> l.stream().filter(j -> totalNumItems(j) >= 5).filter(j -> totalNumItems(j) <= 8).count());
        functions.put(TotalItems.NINETOTWELVE, l -> l.stream().filter(j -> totalNumItems(j) >= 9).filter(j -> totalNumItems(j) <= 12).count());
        functions.put(TotalItems.THIRTEENPLUS, l -> l.stream().filter(j -> totalNumItems(j) >= 13).count());
       
        functions.put(HighestRewardItem.ZEROTOFIVE, l -> l.stream().filter(j -> highestReward(j) >= 0).filter(j -> highestReward(j) <= 5).count());
        functions.put(HighestRewardItem.SIXTOTEN, l -> l.stream().filter(j -> highestReward(j) >= 6).filter(j -> highestReward(j) <= 10).count());
        functions.put(HighestRewardItem.ELEVENTOFIFTEEN, l -> l.stream().filter(j -> highestReward(j) >= 11).filter(j -> highestReward(j) <= 15).count());
        functions.put(HighestRewardItem.SIXTEENPLUS, l -> l.stream().filter(j -> highestReward(j) >= 16).count());
        
        functions.put(HeaviestItem.ZEROTOONE, l -> l.stream().filter(j -> heaviestItem(j) >= 0).filter(j -> heaviestItem(j) <= 1).count());
        functions.put(HeaviestItem.TWOTOTHREE, l -> l.stream().filter(j -> heaviestItem(j) >= 2).filter(j -> heaviestItem(j) <= 3).count());
        functions.put(HeaviestItem.FOURPLUS,l -> l.stream().filter(j -> heaviestItem(j) >= 4).count());
        
        functions.put(HighestQuantity.ONETOTWO, l -> l.stream().filter(j -> highestQuantity(j) >= 1).filter(j -> highestQuantity(j) <= 2).count());
        functions.put(HighestQuantity.THREETOFOUR, l -> l.stream().filter(j -> highestQuantity(j) >= 3).filter(j -> highestQuantity(j) <= 4).count());
        functions.put(HighestQuantity.FIVETOSIX, l -> l.stream().filter(j -> highestQuantity(j) >= 5).filter(j -> highestQuantity(j) <= 6).count());
        functions.put(HighestQuantity.SEVENPLUS, l -> l.stream().filter(j -> highestQuantity(j) >= 7).count());
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
