package JobInput;

/**
 * Represents an item and  needed.
 */
public class Item {

    private final int ID;
    private final int x;
    private final int y;
    private final double reward;
    private final double weight;

    
    public Item(int ID, int x, int y, double reward, double weight) {
        
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.weight = weight;

    }

    public int getID() {
        return ID;
    }

    
    public int getX() {
        return x;
    }
    
    public int getY(){
    	return y;
    }
    
    public double getReward(){
    	return reward;
    }
    
    public double getWeight(){
    	return weight;
    }
    

}
