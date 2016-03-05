package warehouseInterface;

import JobInput.JobProcessor;
import Objects.AllRobots;
import Objects.Job;
import Objects.Sendable.RobotInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

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

		JButton addJob = new JButton("Add a job");
		addJob.addActionListener(e -> new AddJob());

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem viewInfo = new JMenuItem("Information");
		viewInfo.addActionListener(e -> viewJobInfo((int) activeJobs.getValueAt(activeJobs.getSelectedRow(), 0)));
		JMenuItem cancelJob = new JMenuItem("Cancel this job");
		cancelJob.addActionListener(e -> cancelJob((int) activeJobs.getValueAt(activeJobs.getSelectedRow(), 0)));
		popupMenu.add(viewInfo);
		popupMenu.add(cancelJob);
		activeJobs.setComponentPopupMenu(popupMenu);
		activeJobs.getColumnModel().getColumn(0).setMaxWidth(60);
		activeJobs.getColumnModel().getColumn(0).setPreferredWidth(60);
		activeJobs.getColumnModel().getColumn(1).setMaxWidth(60);
		activeJobs.getColumnModel().getColumn(1).setPreferredWidth(60);
		activeJobs.getColumnModel().getColumn(2).setMaxWidth(100);
		activeJobs.getColumnModel().getColumn(2).setPreferredWidth(100);

		panel.add(new JScrollPane(activeJobs), BorderLayout.CENTER);
		panel.add(addJob, BorderLayout.SOUTH);
		return panel;
	}

	public static void addJob(int job, String reward, UUID robot)
	{
		tableModel.addRow(new Object[] { job, reward, robot, "Waiting for job confirmation"});
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
