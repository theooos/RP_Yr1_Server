package JobInput;

/**
 * Represents the warehouse map.
 */
public class WarehouseMap {

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
            grid[obstacle.getX()][obstacle.getY()] = true;
    }

    public void addObstacle(Point obstacle) {
        grid[obstacle.getX()][obstacle.getY()] = true;
    }

    public void freePosition(Point pos) {
        grid[pos.getX()][pos.getY()] = false;
    }

    public void resetGrid() {
        for(int i = 0; i < gridWidth; i++) {
            for(int j = 0; j < gridWidth; j++) {
                grid[i][j] = false;        
    }

}
