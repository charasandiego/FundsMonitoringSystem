package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class SalaryDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private InputSalaryPanel panel;
	
	public SalaryDialog(MainFrame parent)
	{
		 super(parent, "New Salary", true);
		 setSize(500, 170);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new InputSalaryPanel(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
