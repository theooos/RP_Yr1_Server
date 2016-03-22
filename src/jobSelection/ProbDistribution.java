package jobSelection;

import java.util.Collection;
import java.util.function.Predicate;

import jobInput.JobProcessor;

/**
 * 
 * prob distribution class
 * calculates the probability distribution using predicates
 *	
 */
public class ProbDistribution {

    private float[] probabilities;

    /**
     * constructor
     * @param probabilities array of probabilities
     */
    public ProbDistribution(float[] probabilities) {
        this.probabilities = probabilities;
    }

    /**
     * is normalised method
     * @return boolean do probs add up to 1?
     */
    public boolean isNormalised() {
        return (sumOfProbs() == 1);
    }

    /**
     * normalise method
     */
    public void normalise() {
        float multiplier = 1.0f / sumOfProbs();
        for(int i = 0; i < probabilities.length; i++) {
            probabilities[i] *= multiplier;
        }
    }
    
    /**
     * get probs method
     * @return probabilities the array of probs
     */
    public float[] getProbs() {
    	return probabilities;
    }

    private float sumOfProbs() {
        float sum = 0.0f;
        for(int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i]; 
        }
        return sum;
    }

    /**
     * calculate the probability distribution
     * @param col collection 
     * @param p predicate
     * @return probability distribution
     */
    public static <T> ProbDistribution calculateProbDistr(Collection<T> col, Predicate<T> p) {
        
        float[] prob = new float[2];
        int numSatisfyingP = (int) col
        						.stream()
        						.filter(p)
        						.count();
        prob[0] = (float)numSatisfyingP / (float)JobProcessor.getAllJobs().size();
        prob[1] = 1 - prob[0];
        
        return new ProbDistribution(prob);

    }

    /**
     * to string method
     * @return s string of probabilities
     */
    // For debugging purposes.
    @Override
    public String toString() {
        String s = "[";
        for(int i = 0; i < probabilities.length; i++) {
            if(i == probabilities.length - 1) {
                s += probabilities[i] + "]";
            } else {
                s += probabilities[i] + ",";
            }
        }
        return s;
    }

}
