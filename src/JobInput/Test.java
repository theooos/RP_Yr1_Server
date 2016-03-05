package JobInput;
import java.awt.Point;
import Objects.Direction;

public class Test {

	public static void main(String[] args) {

        WarehouseMap map = new WarehouseMap(10, 10, "res/drops.csv");
        map.addObstacle(new Point(0, 0));
        System.out.println(map.distanceToWall(new Point(0, 5), Direction.NORTH));

        JobProcessor processor = new JobProcessor();

        processor.processItemFiles("res/items.csv", "res/locations.csv");
        processor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
		
	}
	
}
