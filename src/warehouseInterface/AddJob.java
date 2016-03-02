package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddJob extends JFrame
{
	public AddJob(JobTable jobTable)
	{
		super("Add a new job");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(640, 480);

		DefaultTableModel tableModel = new DefaultTableModel(new String[] { "Job ID", "Tasks", "Reward" }, 0);
		JTable table = new JTable(tableModel) {
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

		JButton ok = new JButton("OK");
		ok.addActionListener(e -> dispose());

		JPanel comp = new JPanel(new BorderLayout());
		comp.add(new JScrollPane(table), BorderLayout.CENTER);
		comp.add(ok, BorderLayout.SOUTH);

		add(comp);
		setVisible(true);
	}
}
