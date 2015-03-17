package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class InputGeneralFundDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private InputGeneralFundExpense panel;
	
	public InputGeneralFundDialog(MainFrame parent)
	{
		 super(parent, "New General Fund", true);
		 setSize(500, 245);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new InputGeneralFundExpense(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public boolean isSuccess()
	{
		return panel.isSuccess();
	}
}
