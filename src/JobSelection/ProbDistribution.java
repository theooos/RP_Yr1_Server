package JobSelection;

public class ProbDistribution {

    private float[] probabilities;

    public ProbDistribution(float[] probabilities) {
        this.probabilities = probabilities;
    }

    public boolean isNormalised() {
        return (sumOfProbs() == 1);
    }

    public void normalise() {
        float multiplier = 1.0f / sumOfProbs();
        for(int i = 0; i < probabilities.length; i++) {
            probabilities[i] *= multiplier;
        }
    }

    private float sumOfProbs() {
        float sum = 0.0f;
        for(int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i]; 
        }
        return sum;
    }

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
