package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class Statistics
{
	private static DefaultTableModel tableModel;

	public static JPanel draw()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JLabel title = new JLabel("Warehouse Interface");
		title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		tableModel = new DefaultTableModel(new String[] { "Revenue made", "Jobs done", "Jobs cancelled" }, 1);
		JTable table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		for(int i = 0; i < 3; i++)
			table.setValueAt(0, 0, i); //set default values for stats

		panel.add(title);
		panel.add(new JScrollPane(table));
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(new JLabel(new ImageIcon("res/1.1 Logo.png")));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		return panel;
	}

	public static void increaseRevenue(double amount)
	{

	}

	public static void jobDone()
	{

	}

	public static void jobCancelled()
	{

	}
}
