package warehouseInterface;

import Objects.RobotInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RobotTable extends JPanel
{
	private DefaultTableModel tableModel;
	private JTable table;
	private Objects.RobotInfo[] robots;

	public RobotTable()
	{
		super();

		createTable();

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewRobotInfo(table.getSelectedRow()));
		popupMenu.add(viewInfo);
		table.setComponentPopupMenu(popupMenu);

		add(new JScrollPane(table));
		connectRobot();
	}

	private void viewRobotInfo(int robot)
	{
		JOptionPane.showMessageDialog(this, "Name: " + robots[robot].getName() + "\nPosition: " + robots[robot].getPosition().toString());
	}

	private void connectRobot()
	{
		tableModel.addRow(new Object[] { "Robot " + (tableModel.getRowCount() + 1), "Ready" });
		robots[0] = new RobotInfo();
		tableModel.addRow(new Object[] { "Robot " + (tableModel.getRowCount() + 1), "Ready" });
		tableModel.addRow(new Object[] { "Robot " + (tableModel.getRowCount() + 1), "Ready" });
	}

	private void createTable()
	{
		tableModel = new DefaultTableModel(new String[] {"Robot", "Status"}, 0);
		table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}

			@Override
			public boolean getColumnSelectionAllowed()
			{
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so we can't select more than one job
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(SwingUtilities.isRightMouseButton(e))
				{
					int row = table.rowAtPoint(e.getPoint());
					table.setRowSelectionInterval(row, row);
				}
			}
		});
	}
}
