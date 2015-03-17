package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.InputFacultyDialog;
import view.SalaryDialog;

public class SalaryAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public SalaryAction() 
	{
		putValue(Action.NAME, "Salary");
		putValue(Action.SHORT_DESCRIPTION, "New Salary");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		SalaryDialog dialog = new SalaryDialog(Application.getMainFrame());
		dialog.setVisible(true);
	}
}
