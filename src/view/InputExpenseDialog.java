package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class InputExpenseDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private CreateBudgetAccount panel;
	
	public InputExpenseDialog(MainFrame parent)
	{
		 super(parent, "New Budget Account", true);
		 setSize(500, 245);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new CreateBudgetAccount(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public boolean isSuccess()
	{
		return panel.isSuccess();
	}
}
