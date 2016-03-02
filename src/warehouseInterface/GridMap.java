package warehouseInterface;

import Objects.RobotInfo;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GridMap extends JPanel implements Observer
{
	private boolean[][] grid;
	private RobotTable robotTable;

	public GridMap(RobotTable robotTable)
	{
		super();
		this.robotTable = robotTable;
		grid = new boolean[12][8];
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
				grid[i][j] = true;
		grid[1][1] = false;
		grid[2][2] = false;
		grid[2][3] = false;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth(), height = getHeight(), xScale = width / 13, yScale = height / 9;
		g2d.clearRect(0, 0, width, height);
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
					if(i < grid.length - 1 && !grid[i + 1][j]) g2d.drawLine(x, y, x1, y);
				}
			}
		g2d.setColor(new Color(0, 0, 128, 128));
		for(RobotInfo robot : robotTable.getRobots())
		{
			g2d.fillRect((robot.getPosition().x + 1) * xScale - 8, (robot.getPosition().y + 1) * yScale - 15, 16, 30);
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		repaint();
	}
}
