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

    public Features() {

        predicates = new HashMap<Integer, Predicate<Job>>();
        
        /*
        predicates.put(NumOfTasks.ONETOTWO, j -> j.getNumOfTasks() >= 1 && j.getNumOfTasks() <= 2);
        predicates.put(NumOfTasks.THREETOFOUR, j -> j.getNumOfTasks() >= 3 && j.getNumOfTasks() <= 4);
        predicates.put(NumOfTasks.ONETOTWO, j -> j.getNumOfTasks() >= 5);
		*/

        predicates.put(TotalReward.ZEROTOFORTY,  j -> totalReward(j) >= 0 && totalReward(j) < 40);
        predicates.put(TotalReward.FORTYTOEIGHTY,  j -> totalReward(j) >= 40 && totalReward(j) < 80);
        predicates.put(TotalReward.EIGHTYTOONEHUNDREDANDTWENTY,  j -> totalReward(j) >= 80 && totalReward(j) < 120);
        predicates.put(TotalReward.ONEHUNDREDANDTWENTYTOONEHUNDREDANDSIXTY,  j -> totalReward(j) >= 120 && totalReward(j) < 160);
        predicates.put(TotalReward.ONEHUNDREDANDSIXTYPLUS,  j -> totalReward(j) >= 160);
        
        /*
        predicates.put(TotalItems.ONETOFOUR, j -> totalNumItems(j) >= 1 && totalNumItems(j) <= 4);
        predicates.put(TotalItems.FIVETOEIGHT, j -> totalNumItems(j) >= 5 && totalNumItems(j) <= 8);
        predicates.put(TotalItems.NINETOTWELVE, j -> totalNumItems(j) >= 9 && totalNumItems(j) <= 12);
        predicates.put(TotalItems.THIRTEENPLUS, j -> totalNumItems(j) >= 13);
        */
        
        predicates.put(TotalWeight.ZEROTOTEN,  j -> totalWeight(j) >= 0 && totalWeight(j) < 10);
        predicates.put(TotalWeight.TENTOTWENTY,  j -> totalWeight(j) >= 10 && totalWeight(j) < 20);
        predicates.put(TotalWeight.TWENTYTOTHIRTY,  j -> totalWeight(j) >= 20 && totalWeight(j) < 30);
        predicates.put(TotalWeight.THIRTYTOFORTY,  j -> totalWeight(j) >= 30  && totalWeight(j) < 40);
        predicates.put(TotalWeight.FORTYPLUS,  j -> totalWeight(j) >= 40);
        
       
        /*
        predicates.put(HighestRewardItem.ZEROTOFIVE, j -> highestReward(j) >= 0 && highestReward(j) <= 5);
        predicates.put(HighestRewardItem.SIXTOTEN, j -> highestReward(j) >= 6 && highestReward(j) <= 10);
        predicates.put(HighestRewardItem.ELEVENTOFIFTEEN, j -> highestReward(j) >= 11 && highestReward(j) <= 15);
        predicates.put(HighestRewardItem.SIXTEENPLUS, j -> highestReward(j) >= 16);
        */
        
        /*
        predicates.put(HeaviestItem.ZEROTOONE, j -> heaviestItem(j) >= 0 && heaviestItem(j) <= 1);
        predicates.put(HeaviestItem.TWOTOTHREE, j -> heaviestItem(j) >= 2 && heaviestItem(j) <= 3);
        predicates.put(HeaviestItem.FOURPLUS, j -> heaviestItem(j) >= 4);
        */
        
        predicates.put(HighestQuantity.ONETOTWO, j -> highestQuantity(j) >= 1 && highestQuantity(j) <= 2);
        predicates.put(HighestQuantity.THREETOFOUR, j -> highestQuantity(j) >= 3 && highestQuantity(j) <= 4);
        predicates.put(HighestQuantity.FIVETOSIX, j -> highestQuantity(j) >= 5 && highestQuantity(j) <= 6);
        predicates.put(HighestQuantity.SEVENPLUS, j -> highestQuantity(j) >= 7);
    }

    public static class NumOfTasks {
        
        public static int ONETOTWO = 0;
        public static int THREETOFOUR = 1;
        public static int FIVEPLUS = 2;

    }

    /*
    public static class TotalItems {

        public static int ONETOFOUR = 3;
        public static int FIVETOEIGHT = 4;
        public static int NINETOTWELVE = 5;
        public static int THIRTEENPLUS = 6;

    }
    */
    
    public static class TotalReward {

        public static int ZEROTOFORTY = 3;
        public static int FORTYTOEIGHTY = 4;
        public static int EIGHTYTOONEHUNDREDANDTWENTY = 5;
        public static int ONEHUNDREDANDTWENTYTOONEHUNDREDANDSIXTY = 6;
        public static int ONEHUNDREDANDSIXTYPLUS = 7;

    }
    
    public static class TotalWeight {

        public static int ZEROTOTEN = 8;
        public static int TENTOTWENTY = 9;
        public static int TWENTYTOTHIRTY = 10;
        public static int THIRTYTOFORTY = 11;
        public static int FORTYPLUS = 12;

    }
    
    public static class HighestQuantity {

        public static int ONETOTWO = 13;
        public static int THREETOFOUR = 14;
        public static int FIVETOSIX = 15;
        public static int SEVENPLUS = 16;

    }
    
    public static double totalReward(Job job){
    	
    	double reward = 0.0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(!task.getItemID().equals("dropOff"))
    		 {
    			reward = reward + JobProcessor.getItem(task.getItemID()).getReward();
    			 	
    		 }	
    	}
    	
    	return reward;
    }
    
    public static double totalWeight(Job job){
    	
    	double weight = 0.0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		
    		 if(!task.getItemID().equals("dropOff"))
    		 {
    			 weight = weight + JobProcessor.getItem(task.getItemID()).getWeight();
    			 	
    		 }	
    	}
    	
    	return weight;
    }
    
    /*
    public int totalNumItems(Job job){
    	
    	int quantity = 0;
    	
    	for(SingleTask task : job.getTasks())
    	{
    		quantity = quantity + task.getQuantity();
    		
    	}
    	
    	return quantity;
    }
	*/
    
    /*
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
    		
    		 if(!task.getItemID().equals("dropOff"))
    		 {
    			 
    			 if(JobProcessor.getItem(task.getItemID()).getReward() > reward){
    				 reward = JobProcessor.getItem(task.getItemID()).getReward();
    			 }	
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
    
    */

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
