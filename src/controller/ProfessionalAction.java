package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.InputProfessionalDialog;

public class ProfessionalAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public ProfessionalAction() 
	{
		putValue(Action.NAME, "Professional Service Expense");
		putValue(Action.SHORT_DESCRIPTION, "Input Professional Service Expense");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		InputProfessionalDialog dialog = new InputProfessionalDialog(Application.getMainFrame());
		dialog.setVisible(true);
		if (dialog.isSuccess())
		{
			Application.getMainFrame().setSelectedView(1, 1);
		}
	}
}
