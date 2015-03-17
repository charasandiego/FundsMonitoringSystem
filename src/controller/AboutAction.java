package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.AboutDialog;
import view.Application;
import view.InputExpenseDialog;

public class AboutAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public AboutAction() 
	{
		putValue(Action.NAME, "About");
		putValue(Action.SHORT_DESCRIPTION, "About the Software");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		AboutDialog dialog = new AboutDialog(Application.getMainFrame());
		dialog.setVisible(true);
	}
}
