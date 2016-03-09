package warehouseInterface;

import JobInput.WarehouseMap;
import Objects.AllRobots;
import Objects.Direction;
import Objects.Sendable.RobotInfo;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class GridMap
{
	private static JPanel panel;
	private static final int GRID_WIDTH = 12, GRID_HEIGHT = 8;

	public static JPanel createGrid()
	{
		WarehouseMap grid = new WarehouseMap(GRID_WIDTH, GRID_HEIGHT, "res/drops.csv");
		ArrayList<Point> obstacles = new ArrayList<>();
		for(int i = 1; i < 11; i++)
			for(int j = 2; j < 7; j++)
				if(i == 1 || i == 4 || i == 7 || i == 10)
					obstacles.add(new Point(i, j));
		grid.setObstacles(obstacles);
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				int width = getWidth(), height = getHeight(), xScale = width / 13, yScale = height / 9;
				g2d.clearRect(0, 0, width, height);
				g2d.setStroke(new BasicStroke(10));
				g2d.drawRect(0, 0, width, height); // pretty black border :)
				g2d.setStroke(new BasicStroke(1));
				for(int i = 0; i < GRID_WIDTH; i++)
					for(int j = 0; j < GRID_HEIGHT; j++)
					{
						int x = (i + 1) * xScale, y = (j + 1) * yScale, x1 = (i + 2) * xScale, y1 = (j + 2) * yScale;
						if(!grid.isObstacle(i, j))
						{
							g2d.setColor(Color.BLACK);
							g2d.fillOval(x - 3, y - 3, 6, 6);
							if(j < GRID_HEIGHT - 1 && !grid.isObstacle(i, j + 1)) g2d.drawLine(x, y, x, y1);
							if(i < GRID_WIDTH - 1 && !grid.isObstacle(i + 1, j)) g2d.drawLine(x, y, x1, y);
						}
						else
						{
							g2d.setColor(Color.RED);
							g2d.fillOval(x - 3, y - 3, 6, 6);
							if(j < GRID_HEIGHT - 1 && grid.isObstacle(i, j + 1)) g2d.drawLine(x, y, x, y1);
							if(i < GRID_WIDTH - 1 && grid.isObstacle(i + 1, j) && ((!grid.isObstacle(i, j - 1) && grid.isObstacle(i, j + 1)) || (grid.isObstacle(i, j - 1) && !grid.isObstacle(i, j + 1)))) g2d.drawLine(x, y, x1, y); //only draw red horizontal lines on the edges of the obstacles
						}
					}
				g2d.setStroke(new BasicStroke(3));
				for(RobotInfo robot : AllRobots.getAllRobots())
				{
					if(robot.getDirection() == Direction.NORTH || robot.getDirection() == Direction.SOUTH)
					{
						g2d.drawRect((robot.getPosition().x + 1) * xScale - 8, (robot.getPosition().y + 1) * yScale - 15, 16, 30);
						if(robot.getDirection() == Direction.NORTH)
							g2d.drawLine((robot.getPosition().x + 1) * xScale, (robot.getPosition().y + 1) * yScale - 15, (robot.getPosition().x + 1) * xScale, (robot.getPosition().y + 1) * yScale - 25);
						else
							g2d.drawLine((robot.getPosition().x + 1) * xScale, (robot.getPosition().y + 1) * yScale + 15, (robot.getPosition().x + 1) * xScale, (robot.getPosition().y + 1) * yScale + 25);
					}
					else
					{
						g2d.drawRect((robot.getPosition().x + 1) * xScale - 15, (robot.getPosition().y + 1) * yScale - 8, 30, 16);
						if(robot.getDirection() == Direction.EAST)
							g2d.drawLine((robot.getPosition().x + 1) * xScale + 15, (robot.getPosition().y + 1) * yScale, (robot.getPosition().x + 1) * xScale + 25, (robot.getPosition().y + 1) * yScale);
						else
							g2d.drawLine((robot.getPosition().x + 1) * xScale - 15, (robot.getPosition().y + 1) * yScale, (robot.getPosition().x + 1) * xScale - 25, (robot.getPosition().y + 1) * yScale);
					}
				}
			}
		};
		return panel;
	}

	public static void refresh()
	{
		panel.repaint();
	}
}
