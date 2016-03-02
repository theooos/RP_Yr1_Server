package warehouseInterface;

import javax.swing.*;
import java.awt.*;

public class GridMap extends JComponent
{
	private RobotTable robotTable;
	private boolean[][] grid;

	public GridMap(RobotTable robotTable/*, WarehouseMap warehouseMap*/)
	{
		super();
		this.robotTable = robotTable;
		grid = new boolean[12][8];
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
				grid[i][j] = true;
		grid[1][1] = false;
		grid[1][2] = false;
		grid[2][1] = false;
		grid[2][2] = false;
		grid[2][3] = false;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth(), height = getHeight(), scaleX = width / 12, scaleY = height / 8;
		g2d.clearRect(0, 0, width, height);
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j])
				{
					g2d.setColor(Color.BLACK);
					g2d.fillOval(i * scaleX + 10, j * scaleY + 15, 6, 6);
					if(j < grid[0].length - 1)
						if(grid[i][j + 1])
							g2d.drawLine(i * scaleX + 13, j * scaleY + 18, i * scaleX + 13, (j + 1) * scaleY + 18);
					if(i < grid.length - 1)
						if(grid[i + 1][j])
							g2d.drawLine(i * scaleX + 13, j * scaleY + 18, (i + 1) * scaleX + 13, j * scaleY + 18);
				}
				else
				{
					g2d.setColor(Color.RED);
					g2d.fillOval(i * scaleX + 10, j * scaleY + 15, 6, 6);
					if(j < grid[0].length - 1)
						if(!grid[i][j + 1])
							g2d.drawLine(i * scaleX + 13, j * scaleY + 18, i * scaleX + 13, (j + 1) * scaleY + 18);
					if(i < grid.length - 1)
						if(!grid[i + 1][j])
							g2d.drawLine(i * scaleX + 13, j * scaleY + 18, (i + 1) * scaleX + 13, j * scaleY + 18);
				}
			}

	}
}
