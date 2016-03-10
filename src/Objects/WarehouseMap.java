//package JobInput;
package Objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import JobInput.JobProcessor;
import Objects.Direction;

/**
 * Represents the warehouse map.
 */
public class WarehouseMap {

    // TRUE = OBSTACLE, FALSE = EMPTY
    private boolean[][] grid;
    private int gridWidth; 
    private int gridHeight;
    private List<Point> dropoffs;

    /**
     * Create a new warehouse map.
     * @param gridWidth The warehouse width.
     * @param gridHeight The warehouse height.
     * @param doFile The file containing the drop off locations.
     */
    public WarehouseMap(int gridWidth, int gridHeight, String doFile) {
        
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        grid = new boolean[gridWidth][gridHeight];

        // Read dropoff locations
        Optional<List<String>> dos = JobProcessor.readFile(doFile);
        
        if(!dos.isPresent()) {
            throw new IllegalArgumentException("Could not create dropoff points from the file.");
        }
        dropoffs = new ArrayList<Point>();
        for(String doPoints : dos.get()) {
        	if(doPoints.equals("") || doPoints.equals(" "))
        		continue;
            String[] doArr = doPoints.replaceAll("\\s","")
.split(",");
            Point p = new Point(Integer.parseInt(doArr[0]), Integer.parseInt(doArr[1]));
            dropoffs.add(p);
        }
            
    }

    /**
     * Set the obstacles from a list of points.
     * @param obstacles The list of obstacles.
     */
    public void setObstacles(Point[] obstacles) {
        
        for(int i = 0; i < obstacles.length; i++)
            grid[(int) obstacles[i].getX()][(int) obstacles[i].getY()] = true;

    }

    /**
     * Add a single obstacle to the warehouse.
     * @param o The obstacle.
     */
    public void addObstacle(Point o) {
        grid[(int) o.getX()][(int) o.getY()] = true;
    }

    /**
     * Check if a given position is an obstacle.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return If the position is an obstacle or not.
     */
    public boolean isObstacle(int x, int y) {
        return grid[x][y];
    }

    /**
     * Check if a given position is an obstacle.
     * @param p The point to check.
     * @return If the position is an obstacle or not.
     */
    public boolean isObstacle(Point p) {
        return grid[(int) p.getX()][(int) p.getY()];
    }

    /**
     * Get the drop off points in the warehouse.
     * @return The drop off locations.
     */
    public List<Point> getDropoffPoints() {
        return dropoffs;
    }

    /**
     * Calculate the distance to the wall at a given point and heading.
     * @param p The position.
     * @param heading The direction to check.
     * @return The number of grid positions to a wall.
     */
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

    /**
     * Free a position (remove an obstacle).
     * @param pos The position to free.
     */
    public void freePosition(Point pos) {
        grid[(int) pos.getX()][(int) pos.getY()] = false;
    }

    /**
     * Reset the entire grid to empty.
     */
    public void resetGrid() {
        for(int i = 0; i < gridWidth; i++)
            for(int j = 0; j < gridHeight; j++)
                grid[i][j] = false;        
    }

}
