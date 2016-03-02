package JobInput;
import java.awt.Point;

public class Test {

	public static void main(String[] args) {

        WarehouseMap map = new WarehouseMap(10, 10);
        map.addObstacle(new Point(0, 0));
        System.out.println(map.distanceToWall(new Point(0, 5), Direction.NORTH));
		
	}
	
}
