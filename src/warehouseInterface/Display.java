package warehouseInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JFrame
{
	public Display()
	{
		super("Warehouse Interface");
		setLayout(new GridLayout(2, 2));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(800, 600);

		JobTable jobTable = new JobTable();
		RobotTable robotTable = new RobotTable();
		GridMap gridMap = new GridMap(robotTable);

		add(jobTable);
		add(gridMap);
		add(robotTable);
		setVisible(true);
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
		return table;
	}

	public static void main(String[] args)
	{
		new Display();
	}
}
