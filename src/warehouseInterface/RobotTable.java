package warehouseInterface;

import Objects.AllRobots;
import Objects.Direction;
import Objects.Sendable.RobotInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class RobotTable extends JPanel
{
	private final DefaultTableModel tableModel;
	private final JTable table;

	public RobotTable()
	{
		super(new BorderLayout());
		tableModel = new DefaultTableModel(new String[] {"Robot", "Status"}, 0);
		table = Display.createTable(tableModel);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewRobotInfo(AllRobots.getRobot((UUID) table.getValueAt(table.getSelectedRow(), 0))));
		popupMenu.add(viewInfo);
		table.setComponentPopupMenu(popupMenu);

		add(new JScrollPane(table), BorderLayout.CENTER);

		RobotInfo robot1 = new RobotInfo(UUID.randomUUID(), new Point(5, 1)), robot2 = new RobotInfo(UUID.randomUUID(), new Point(9, 5), Direction.EAST), robot3 = new RobotInfo(UUID.randomUUID(), new Point(1, 7), Direction.SOUTH);
		addRobot(robot1);
		AllRobots.addRobot(robot1);
		addRobot(robot2);
		AllRobots.addRobot(robot2);
		addRobot(robot3);
		AllRobots.addRobot(robot3);
	}

	private void viewRobotInfo(RobotInfo robot)
	{
		JOptionPane.showMessageDialog(this, "Name: " + robot.getName() + "\nPosition: (" + robot.getPosition().x + ", " + robot.getPosition().y + ")");
	}

	private void addRobot(RobotInfo robot)
	{
		tableModel.addRow(new Object[] { robot.getName(), "Ready" });
	}

	public RobotInfo getRobot(UUID robotID)
	{
		for(RobotInfo robot : AllRobots.getAllRobots())
			if(robot.getName().equals(robotID))
				return robot;
		return null;
	}
}
