package warehouseInterface;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame
{
	public Display()
	{
		super("Warehouse Interface");
		setLayout(new GridLayout(2, 2));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(800, 600);

		JobTable jobTable = new JobTable();
		RobotTable robotTable = new RobotTable();
		GridMap gridMap = new GridMap(robotTable);

		add(jobTable);
		add(gridMap);
		add(robotTable);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new Display();
	}
}
