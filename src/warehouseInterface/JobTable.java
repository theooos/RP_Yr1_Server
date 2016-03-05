package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JobTable
{
	private static JTable activeJobs;
	private static DefaultTableModel tableModel;
	private static JPanel panel;

	public static JPanel draw()
	{
		panel = new JPanel(new BorderLayout());
		tableModel = new DefaultTableModel(new String[] {"Job ID", "Reward", "Robot", "Status"}, 0);
		activeJobs = Display.createTable(tableModel);

		JPanel buttons = new JPanel(new BorderLayout());
		JButton addJob = new JButton("Add a job");
		addJob.addActionListener(e -> new AddJob());
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

		panel.add(new JScrollPane(activeJobs), BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);
		return panel;
	}

	private static void cancelJob(int jobID)
	{
		tableModel.removeRow(activeJobs.getSelectedRow());
		JOptionPane.showMessageDialog(panel, "Job " + jobID + " cancelled.");
	}

	private static void viewJobInfo(int jobID)
	{
		JOptionPane.showMessageDialog(panel, "Nothing here...");
	}
}
