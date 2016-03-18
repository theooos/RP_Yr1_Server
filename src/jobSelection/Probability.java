package jobSelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import Objects.Job;

public class Probability {

    public double[] probs;
    public double[][] conProbs;
    private List<Job> trainingSet;

    public Probability(Collection<Job> trainingSet) {

        probs = new double[Features.predicates.size()];
        conProbs = new double[Features.predicates.size()][Features.predicates.size()];

        this.trainingSet = new ArrayList<Job>(trainingSet);
        
        populateProbs();

    }

    public void populateProbs() {
        
        for(int i = 0; i < probs.length; i++) {
            
            probs[i] = (float)trainingSet.stream()
                                  .filter(Features.predicates.get(i))
                                  .count() / (float)trainingSet.size();
      
            for(int j = 0; j < probs.length; j++) {
                conProbs[i][j] = (float)trainingSet.stream()
                                    .filter(Features.predicates.get(i))
                                    .filter(Features.predicates.get(j))
                                    .count() /
                              (float)trainingSet.stream()
                                    .filter(Features.predicates.get(j))
                                    .count();
         
            }
        }

    }

    public List<Integer> getFeatures(Job j) {

        List<Integer> featuresOfJob = new ArrayList<Integer>();

        for(int i = 0; i < Features.predicates.size(); i++) {
            if(i != Features.Cancelled.NO && i != Features.Cancelled.YES && Features.predicates.get(i).test(j))
                featuresOfJob.add(i);
        }

        return featuresOfJob;

    }
    
    public double probabilityCancelled(Job j) {
    	
    	double prob = 0d;
    	
    	List<Integer> features = getFeatures(j);
    	
    	double pGivenFeatures = 1d;
    	for(Integer f : features) {
    		double p = conProbs[f][Features.Cancelled.YES];
    		pGivenFeatures *= (p == 0.0d ? 0.001d : p);
    	}
    	
    	 
    	prob = pGivenFeatures * probs[Features.Cancelled.YES];
    	
        int count = 0;
        for(int i = 0; i < trainingSet.size(); i++) {
        
        	Job theJob = trainingSet.get(i);
        	boolean all = true;
        	
        	for(int f = 0; f < features.size(); f++) {
        		Predicate<Job> curPred = Features.predicates.get(features.get(f));
        		if(!curPred.test(theJob))
        			all = false;
     
        	}
        	
        	if(all)
        		count += 1;
        	
        }
    	
    	
    	double probOfAllFeatures = (double)count / (double)trainingSet.size();
    	
    	prob = prob / probOfAllFeatures;
    	
    	return prob;
    	
    }

}
