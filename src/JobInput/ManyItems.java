package JobInput;

import java.util.ArrayList;
import java.util.List;

public class ManyItems {
	
	//public static List<ManyItems> currentItems = new ArrayList<ManyItems>();
	
	private int itemID;
	private List<Item> items;
	
	public ManyItems(int itemID){
		
		this.itemID = itemID;
		this.items = new ArrayList<Item>();
		
	}
	
	//add an item to list of items
    public void addItem(){//int itemID, int x, int y, double reward, double weight) {
        items.add(Item(itemID));
    }

	

}
