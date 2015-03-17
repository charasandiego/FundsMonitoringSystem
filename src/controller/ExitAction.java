package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class ExitAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public ExitAction() 
	{
		putValue(Action.NAME, "Exit");
		putValue(Action.SHORT_DESCRIPTION, "Exit Application");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_E));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		System.exit(0);
	}
}
