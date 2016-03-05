package JobInput;
import java.awt.Point;

import Objects.Direction;

public class Test {

	public static void main(String[] args) {

        WarehouseMap map = new WarehouseMap(10, 10, "res/drops.csv");

        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

	}
	
}
