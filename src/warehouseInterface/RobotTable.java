package warehouseInterface;

import Objects.Direction;
import Objects.RobotInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class RobotTable extends JPanel
{
	private final DefaultTableModel tableModel;
	private final JTable table;
	private ArrayList<RobotInfo> robots;
	private GridMap gridMap;

	public RobotTable()
	{
		super(new BorderLayout());
		tableModel = new DefaultTableModel(new String[] {"Robot", "Status"}, 0);
		table = Display.createTable(tableModel);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewRobotInfo(robots.get(table.getSelectedRow())));
		JMenuItem move = new JMenuItem("Move");
		move.addActionListener(e -> moveRobot(robots.get(table.getSelectedRow()), new Point(new Random().nextInt(12), new Random().nextInt(8))));
		popupMenu.add(viewInfo);
		table.setComponentPopupMenu(popupMenu);

		add(new JScrollPane(table), BorderLayout.CENTER);
		robots = new ArrayList<>();
		addRobot(new RobotInfo(UUID.randomUUID(), new Point(5, 1)));
		addRobot(new RobotInfo(UUID.randomUUID(), new Point(9, 5), Direction.EAST));
		addRobot(new RobotInfo(UUID.randomUUID(), new Point(1, 7), Direction.SOUTH));
	}

	private void viewRobotInfo(RobotInfo robot)
	{
		JOptionPane.showMessageDialog(this, "Name: " + robot.getName() + "\nPosition: (" + robot.getPosition().x + ", " + robot.getPosition().y + ")");
	}

	private void addRobot(RobotInfo robot)
	{
		robots.add(robot);
		tableModel.addRow(new Object[] { robot.getName(), "Ready" });
	}

	public RobotInfo getRobot(UUID robotID)
	{
		for(RobotInfo robot : robots)
			if(robot.getName().equals(robotID))
				return robot;
		return null;
	}

	public ArrayList<RobotInfo> getRobots()
	{
		return robots;
	}

	public void setGridMap(GridMap gridMap)
	{
		this.gridMap = gridMap;
	}

	public void moveRobot(RobotInfo robot, Point point)
	{
		//robot.setPosition(point);
		gridMap.repaint();
	}
}
