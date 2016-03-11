package warehouseInterface;

import Objects.AllRobots;
import Objects.Direction;
import Objects.Sendable.RobotInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
		return panel;
	}

	private static void viewRobotInfo(RobotInfo robot)
	{
		JOptionPane.showMessageDialog(panel, "Name: " + robot.getName() + "\nPosition: (" + robot.getPosition().x + ", " + robot.getPosition().y + ")");
	}

	public static void addRobot(RobotInfo robot)
	{
		tableModel.addRow(new Object[] { robot.getName(), "Ready" });
	}

	public static void removeRobot(RobotInfo robot)
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
