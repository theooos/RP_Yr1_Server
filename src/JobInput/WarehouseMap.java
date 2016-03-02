package JobInput;

import java.awt.Point;
import java.util.List;

/**
 * Represents the warehouse map.
 */
public class WarehouseMap {
	
	enum Object {
		ROBOT, OBSTACLE, 
	}

    // TRUE = OBSTACLE, FALSE = EMPTY
    private boolean[][] grid;
    private int gridWidth; 
    private int gridHeight;

    public WarehouseMap(int gridWidth, int gridHeight) {
        
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        grid = new boolean[gridWidth][gridHeight];

    }

    public void setObstacles(List<Point> obstacles) {
        for(Point obstacle : obstacles)
            grid[(int) obstacle.getX()][(int) obstacle.getY()] = true;
    }

    public void addObstacle(Point o) {
        grid[(int) o.getX()][(int) o.getY()] = true;
    }

    public boolean isObstacle(int x, int y) {
        return grid[x][y];
    }

    public boolean isObstacle(Point p) {
        return grid[(int) p.getX()][(int) p.getY()];
    }

    public int distanceToWall(Point p, Direction heading) {
        int distance = 0;
        switch(heading) {        
	        case NORTH:
	            for(int i = (int) (p.getY()-1); i >= 0; i--) {
	               if(!grid[(int) p.getX()][i])
	                    distance++;
	                else
	                    break;
	            }
	            return distance;
	
	        case SOUTH:
	            for(int i = (int) (p.getY()+1); i < gridHeight; i++) {
	               if(!grid[(int) p.getX()][i])
	                    distance++;
	                else
	                    break;
	            }
	            return distance;
	
	        case EAST:
	            for(int i = (int) (p.getX()+1); i < gridWidth; i++) {
	               if(!grid[i][(int) p.getY()])
	                    distance++;
	                else
	                    break;
	            }
	            return distance;
	
	        case WEST:
	            for(int i = (int) (p.getX()-1); i >= 0; i--) {
	               if(!grid[i][(int) p.getY()])
	                    distance++;
	                else
	                    break;
	            }
	            return distance;
	            
	        default:
	        	return -1;
        }
    }

    public void freePosition(Point pos) {
        grid[(int) pos.getX()][(int) pos.getY()] = false;
    }

    public void resetGrid() {
        for(int i = 0; i < gridWidth; i++)
            for(int j = 0; j < gridHeight; j++)
                grid[i][j] = false;        
    }

}