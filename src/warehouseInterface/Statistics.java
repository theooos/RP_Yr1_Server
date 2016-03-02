package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Statistics extends JPanel
{
	public Statistics()
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JLabel title = new JLabel("Warehouse Interface");
		title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		DefaultTableModel tableModel = new DefaultTableModel(new String[] { "Revenue made", "Jobs done", "Jobs cancelled" }, 1);
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
		table.setValueAt(0, 0, 0); //set default values for stats
		table.setValueAt(0, 0, 1);
		table.setValueAt(0, 0, 2);

		add(title);
		add(new JScrollPane(table));
		setBorder(BorderFactory.createEmptyBorder(0, 30, 150, 30));
	}
}
