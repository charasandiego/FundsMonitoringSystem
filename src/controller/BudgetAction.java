package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.InputExpenseDialog;

public class BudgetAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public BudgetAction() 
	{
		putValue(Action.NAME, "Budget Account");
		putValue(Action.SHORT_DESCRIPTION, "Input Budget Account");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_B));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		InputExpenseDialog dialog = new InputExpenseDialog(Application.getMainFrame());
		dialog.setVisible(true);
		if (dialog.isSuccess())
		{
			Application.getMainFrame().setSelectedView(2, 1);
		}
	}
}
