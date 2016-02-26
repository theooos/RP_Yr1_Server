package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RobotTable extends JPanel
{
	private DefaultTableModel tableModel;
	private JTable table;

	public RobotTable()
	{
		super(new BorderLayout());

		createTable();

		JPanel buttons = new JPanel(new BorderLayout());
		JButton addRobot = new JButton("Connect a robot");
		addRobot.addActionListener(e -> connectRobot());
		JButton refresh = new JButton("Refresh");
		buttons.add(addRobot, BorderLayout.CENTER);
		buttons.add(refresh, BorderLayout.EAST);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewRobotInfo((String) table.getValueAt(table.getSelectedRow(), 0)));
		JMenuItem disconnect = new JMenuItem("Disconnect");
		disconnect.addActionListener(e -> disconnectRobot((String) table.getValueAt(table.getSelectedRow(), 0)));
		popupMenu.add(viewInfo);
		popupMenu.add(disconnect);
		table.setComponentPopupMenu(popupMenu);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}

	private void disconnectRobot(String robot)
	{
		tableModel.removeRow(table.getSelectedRow());
		JOptionPane.showMessageDialog(this, robot + " disconnected.");
	}

	private void viewRobotInfo(String robot)
	{
		JOptionPane.showMessageDialog(this, "Nothing here...");
	}

	private void connectRobot()
	{
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
