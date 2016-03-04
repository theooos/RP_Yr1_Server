package warehouseInterface;

import Objects.AllRobots;
import Objects.Direction;
import Objects.Sendable.RobotInfo;
import javax.swing.*;
import java.awt.*;

public class GridMap extends JPanel
{
	private static boolean[][] grid;

	public GridMap()
	{
		super();
		grid = new boolean[12][8];
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
				grid[i][j] = true;
		for(int i = 2; i < 4; i++)
			for(int j = 2; j < 6; j++)
				grid[i][j] = false;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth(), height = getHeight(), xScale = width / 13, yScale = height / 9;
		g2d.clearRect(0, 0, width, height);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(0, 0, width, height); // pretty black border :)
		g2d.setStroke(new BasicStroke(1));
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
			{
				int x = (i + 1) * xScale, y = (j + 1) * yScale, x1 = (i + 2) * xScale, y1 = (j + 2) * yScale;
				if(grid[i][j])
				{
					g2d.setColor(Color.BLACK);
					g2d.fillOval(x - 3, y - 3, 6, 6);
					if(j < grid[0].length - 1 && grid[i][j + 1]) g2d.drawLine(x, y, x, y1);
					if(i < grid.length - 1 && grid[i + 1][j]) g2d.drawLine(x, y, x1, y);
				}
				else
				{
					g2d.setColor(Color.RED);
					g2d.fillOval(x - 3, y - 3, 6, 6);
					if(j < grid[0].length - 1 && !grid[i][j + 1]) g2d.drawLine(x, y, x, y1);
					if(i < grid.length - 1 && !grid[i + 1][j] && ((grid[i][j - 1] && !grid[i][j + 1]) || (!grid[i][j - 1] && grid[i][j + 1]))) g2d.drawLine(x, y, x1, y); //only draw red horizontal lines on the edges of the obstacles
				}
			}
		g2d.setStroke(new BasicStroke(3));
		for(RobotInfo robot : AllRobots.getAllRobots())
		{
			if(robot.getDirection() == Direction.NORTH || robot.getDirection() == Direction.SOUTH)
				g2d.drawRect((robot.getPosition().x + 1) * xScale - 8, (robot.getPosition().y + 1) * yScale - 15, 16, 30);
			else
				g2d.drawRect((robot.getPosition().x + 1) * xScale - 15, (robot.getPosition().y + 1) * yScale - 8, 30, 16);
		}
	}
}
