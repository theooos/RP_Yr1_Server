package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class JobTable extends JPanel
{
	private JTable activeJobs;
	private DefaultTableModel tableModel;

	public JobTable()
	{
		super(new BorderLayout());

		tableModel = new DefaultTableModel(new String[] {"Job ID", "Reward", "Robot", "Status"}, 0);
		activeJobs = Display.createTable(tableModel);

		JPanel buttons = new JPanel(new BorderLayout());
		JButton addJob = new JButton("Add a job");
		addJob.addActionListener(e -> new AddJob(this));
		buttons.add(addJob, BorderLayout.CENTER);
		JButton refresh = new JButton("Refresh");
		buttons.add(refresh, BorderLayout.EAST);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewJobInfo((int) activeJobs.getValueAt(activeJobs.getSelectedRow(), 0)));
		JMenuItem cancelJob = new JMenuItem("Cancel this job");
		cancelJob.addActionListener(e -> cancelJob((int) activeJobs.getValueAt(activeJobs.getSelectedRow(), 0)));
		popupMenu.add(viewInfo);
		popupMenu.add(cancelJob);
		activeJobs.setComponentPopupMenu(popupMenu);

		add(new JScrollPane(activeJobs), BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}

	private void cancelJob(int jobID)
	{
		tableModel.removeRow(activeJobs.getSelectedRow());
		JOptionPane.showMessageDialog(this, "Job " + jobID + " cancelled.");
	}

	private void viewJobInfo(int jobID)
	{
		JOptionPane.showMessageDialog(this, "Nothing here...");
	}
}
