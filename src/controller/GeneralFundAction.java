package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.InputGeneralFundDialog;

public class GeneralFundAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public GeneralFundAction() 
	{
		putValue(Action.NAME, "General Fund Expense");
		putValue(Action.SHORT_DESCRIPTION, "Input General Fund Expense");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_G));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		InputGeneralFundDialog dialog = new InputGeneralFundDialog(Application.getMainFrame());
		dialog.setVisible(true);
		if (dialog.isSuccess())
		{
			Application.getMainFrame().setSelectedView(0, 1);
		}
	}
}
