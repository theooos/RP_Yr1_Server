package jobSelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import Objects.Item;
import Objects.Job;
import Objects.Sendable.SingleTask;
import jobInput.JobProcessor;

public class Features {

    public static Map<Integer, Predicate<Job>> predicates;

    static {

        predicates = new HashMap<Integer, Predicate<Job>>();
        
        // ???
        predicates.put(NumOfTasks.ONETOTWO, j -> j.getNumOfTasks() >= 1 && j.getNumOfTasks() <= 2);
        predicates.put(NumOfTasks.THREETOFOUR, j -> j.getNumOfTasks() >= 3 && j.getNumOfTasks() <= 4);
        predicates.put(NumOfTasks.FIVEPLUS, j -> j.getNumOfTasks() >= 5);

        // DEFINITE
        predicates.put(TotalItems.ONETOFOUR, j -> totalNumItems(j) >= 1 && totalNumItems(j) <= 4);
        predicates.put(TotalItems.FIVETOEIGHT, j -> totalNumItems(j) >= 5 && totalNumItems(j) <= 8);
        predicates.put(TotalItems.NINETOTWELVE, j -> totalNumItems(j) >= 9 && totalNumItems(j) <= 12);
        predicates.put(TotalItems.THIRTEENPLUS, j -> totalNumItems(j) >= 13);
       
        // REPLACE WITH TOTAL ORDER REWARD
        predicates.put(HighestRewardItem.ZEROTOFIVE, j -> highestReward(j) >= 0 && highestReward(j) < 5);
        predicates.put(HighestRewardItem.FIVETOTEN, j -> highestReward(j) >= 5 && highestReward(j) < 10);
        predicates.put(HighestRewardItem.TENTOFIFTEEN, j -> highestReward(j) >= 10 && highestReward(j) < 15);
        predicates.put(HighestRewardItem.FIFTEENPLUS, j -> highestReward(j) >= 15);
        
        // REPLACE WITH TOTAL ORDER WEIGHT?
        predicates.put(HeaviestItem.ZEROTOPOINTFIVE, j -> heaviestItem(j) >= 0 && heaviestItem(j) < 1.5);
        predicates.put(HeaviestItem.POINTFIVETOTHREE, j -> heaviestItem(j) >= 1.5 && heaviestItem(j) < 3);
        predicates.put(HeaviestItem.THREEPLUS, j -> heaviestItem(j) >= 3);
        
        // DEFINITE
        predicates.put(HighestQuantity.ONETOTWO, j -> highestQuantity(j) >= 1 && highestQuantity(j) <= 2);
        predicates.put(HighestQuantity.THREETOFOUR, j -> highestQuantity(j) >= 3 && highestQuantity(j) <= 4);
        predicates.put(HighestQuantity.FIVETOSIX, j -> highestQuantity(j) >= 5 && highestQuantity(j) <= 6);
        predicates.put(HighestQuantity.SEVENPLUS, j -> highestQuantity(j) >= 7);

        predicates.put(Cancelled.YES, Job::cancelled);
        predicates.put(Cancelled.NO, j -> !j.cancelled());
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
    
    public static int totalNumItems(Job job){
    	
    	int quantity = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		quantity = quantity + task.getQuantity();
    		
    	}
    	
    	return quantity;
    }

    
    public static class HighestRewardItem {
        
        public static int ZEROTOFIVE = 7;
        public static int FIVETOTEN = 8;
        public static int TENTOFIFTEEN = 9;
        public static int FIFTEENPLUS = 10;

    }
    
    public static double highestReward(Job job){
    	
    	double reward = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(!task.getItemID().equals("dropOff") && JobProcessor.getItem(task.getItemID()).getReward() > reward){
    			 
    			reward = JobProcessor.getItem(task.getItemID()).getReward();
    		 }
    		
    		
    	}
    	
    	return reward;
    }
    
    

    public static class HeaviestItem {

        public static int ZEROTOPOINTFIVE = 11;
        public static int POINTFIVETOTHREE = 12;
        public static int THREEPLUS = 13;

    }
    
    public static double heaviestItem(Job job){
    	
    	double heaviest = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(JobProcessor.getItem(task.getItemID()).getWeight() > heaviest){
    			 
    			heaviest = JobProcessor.getItem(task.getItemID()).getWeight();
    		 }
    		
    		
    	}
    	
    	return heaviest;
    	
    }

    public static List<Integer> getFeatures(Job job) {
        
        List<Integer> list = new ArrayList<Integer>();
        Set<Entry<Integer, Predicate<Job>>> entrySet = predicates.entrySet();

        for( Map.Entry<Integer, Predicate<Job>> e : entrySet) {
            Integer f = e.getKey();
            Predicate<Job> p = e.getValue(); 
            if(p.test(job))
                list.add(f);
        }

        return list;
        
    }

    public static class HighestQuantity {

        public static int ONETOTWO = 14;
        public static int THREETOFOUR = 15;
        public static int FIVETOSIX = 16;
        public static int SEVENPLUS = 17;

    }
    
    public static double highestQuantity(Job job){
    	
    	double quantity = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(task.getQuantity() > quantity){
    			 
    			quantity = task.getQuantity();
    		 }
    		
    		
    	}
    	
    	return quantity;

    }

    public static class Cancelled {
        
        public static int YES = 18;
        public static int NO = 19;

    }
}
