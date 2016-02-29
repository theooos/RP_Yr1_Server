package JobInput;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an item and  needed.
 */
public class Item {

   // private final String ID;
    private final int x;
    private final int y;
    private final double reward;
    private final double weight;
    
    public static Map<String, Item> items = new HashMap<String, Item>();
    

    
    public Item(int x, int y, double reward, double weight) {
        
       // this.ID = ID;
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.weight = weight;

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
