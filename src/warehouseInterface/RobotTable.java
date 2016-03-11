package warehouseInterface;

import Objects.AllRobots;
import Objects.Direction;
import Objects.Sendable.RobotInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

public class RobotTable
{
	private static DefaultTableModel tableModel;
	private static JPanel panel;

	public static JPanel draw()
	{
		panel = new JPanel(new BorderLayout());
		tableModel = new DefaultTableModel(new String[] {"Robot", "Status"}, 0);
		JTable table = Display.createTable(tableModel);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewRobotInfo(AllRobots.getRobot((String) table.getValueAt(table.getSelectedRow(), 0))));
		popupMenu.add(viewInfo);
		table.setComponentPopupMenu(popupMenu);

		panel.add(new JScrollPane(table), BorderLayout.CENTER);

		RobotInfo robot1 = new RobotInfo("Tay Tay", new Point(5, 1)), robot2 = new RobotInfo("Alfonso", new Point(9, 5), Direction.EAST), robot3 = new RobotInfo("John Cena", new Point(1, 7), Direction.SOUTH);
		addRobot(robot1);
		AllRobots.addRobot(robot1);
		addRobot(robot2);
		AllRobots.addRobot(robot2);
		addRobot(robot3);
		AllRobots.addRobot(robot3);
		return panel;
	}

	private static void viewRobotInfo(RobotInfo robot)
	{
		JOptionPane.showMessageDialog(panel, "Name: " + robot.getName() + "\nPosition: (" + robot.getPosition().x + ", " + robot.getPosition().y + ")");
	}

	private static void addRobot(RobotInfo robot)
	{
		tableModel.addRow(new Object[] { robot.getName(), "Ready" });
	}

	private static void removeRobot(RobotInfo robot)
	{
		for(int i = 0; i < tableModel.getRowCount(); i++)
			if(tableModel.getValueAt(i, 0).equals(robot.getName()))
			{
				tableModel.removeRow(i);
				break;
			}
	}

	public static void updateStatus(String robot, String status)
	{
		for(int i = 0; i < tableModel.getRowCount(); i++)
		{
			if(tableModel.getValueAt(i, 0).equals(robot))
			{
				tableModel.setValueAt(status, i, 1);
				break;
			}
		}
	}
}