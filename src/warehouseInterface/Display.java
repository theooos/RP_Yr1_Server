package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Objects.WarehouseMap;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Warehouse Interface");
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);

		WarehouseMap grid = new WarehouseMap("res/drops.csv");
		grid.setObstacles(new Point[] { new Point(1, 2), new Point(1, 3), new Point(1, 4), new Point(1, 5), new Point(1, 6), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(4, 5), new Point(4, 6), new Point(7, 2), new Point(7, 3), new Point(7, 4), new Point(7, 5), new Point(7, 6), new Point(10, 2), new Point(10, 3), new Point(10, 4), new Point(10, 5), new Point(10, 6) });
		
		frame.add(JobTable.draw());
		frame.add(GridMap.createGrid(grid));
		frame.add(RobotTable.draw());
		frame.add(Statistics.draw());
		frame.setVisible(true);

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
