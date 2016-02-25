package warehouseInterface;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame
{
	public Display()
	{
		super("Warehouse Interface");
		setLayout(new GridLayout(2, 2));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(800, 600);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new Display();
	}
}
