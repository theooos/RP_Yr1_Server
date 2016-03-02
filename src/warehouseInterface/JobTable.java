package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class JobTable extends JPanel
{
	private JTable table;
	private DefaultTableModel tableModel;

	public JobTable()
	{
		super(new BorderLayout());

		createTable();

		JPanel buttons = new JPanel(new BorderLayout());
		JButton addJob = new JButton("Add a job");
		addJob.addActionListener(e -> addJob());
		buttons.add(addJob, BorderLayout.CENTER);
		JButton refresh = new JButton("Refresh");
		buttons.add(refresh, BorderLayout.EAST);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewJobInfo((int) table.getValueAt(table.getSelectedRow(), 0)));
		JMenuItem cancelJob = new JMenuItem("Cancel this job");
		cancelJob.addActionListener(e -> cancelJob((int) table.getValueAt(table.getSelectedRow(), 0)));
		popupMenu.add(viewInfo);
		popupMenu.add(cancelJob);
		table.setComponentPopupMenu(popupMenu);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}

	private void addJob()
	{
		Random random = new Random();
		tableModel.addRow(new Object[] { random.nextInt(20000), random.nextInt(100), "Robot " + (tableModel.getRowCount() + 1), "Executing" });
	}

	private void cancelJob(int jobID)
	{
		tableModel.removeRow(table.getSelectedRow());
		JOptionPane.showMessageDialog(this, "Job " + jobID + " cancelled.");
	}

	private void viewJobInfo(int jobID)
	{
		JOptionPane.showMessageDialog(this, "Nothing here...");
	}

	private void createTable()
	{
		tableModel = new DefaultTableModel(new String[] {"Job ID", "Reward", "Robot", "Status"}, 0);
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