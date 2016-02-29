package warehouseInterface;

import javax.swing.*;
import java.awt.*;

public class GridMap extends JComponent
{
	private RobotTable robotTable;

	public GridMap(RobotTable robotTable)
	{
		super();
		this.robotTable = robotTable;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		int width = getWidth(), height = getHeight();
		g.clearRect(0, 0, width, height);
		g.drawLine(0, 0, width, height);
	}
}
