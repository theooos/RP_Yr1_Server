package jobSelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import Objects.Job;

public class Probability {

    public double[] probs;
    public double[][] conProbs;
    private Collection<Job> trainingSet;

    public Probability(Collection<Job> trainingSet) {

        probs = new double[Features.predicates.size()];
        conProbs = new double[Features.predicates.size()][Features.predicates.size()];

        this.trainingSet = trainingSet;
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
            if(Features.predicates.get(i).test(j))
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
    	for(Iterator<Job> i = trainingSet.iterator(); i.hasNext();) {
    		boolean allFeatures = true;
    		for(int f = 0; f < features.size(); f++) {
    			if(!Features.predicates.get(f).test(i.next())) {
    				allFeatures = false;
    				break;
    			}
    		}
    		if(allFeatures) count++;
    	}
    	
    	double probOfAllFeatures = (double)count / (double)trainingSet.size();
    	
    	prob = prob / probOfAllFeatures;
    	
    	return prob;
    	
    }

}
