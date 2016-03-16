package warehouseInterface;

import JobInput.JobProcessor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display
{
	public static void main(String[] args)
	{
//		JFrame frame = new JFrame("Warehouse Interface");
//		frame.setLayout(new GridLayout(2, 2));
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		frame.setResizable(false);
//		frame.setSize(800, 600);
//
//		frame.add(JobTable.draw());
//		frame.add(GridMap.createGrid());
//		frame.add(RobotTable.draw());
//		frame.add(Statistics.draw());
//		frame.setVisible(true);

//		JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
//		JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");
	}

	public static JTable createTable(DefaultTableModel tableModel)
	{
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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so we can't select more than one row
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFocusable(false);
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
		return table;
	}
}
