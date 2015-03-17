package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.PositionDialog;

public class PositionAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public PositionAction() 
	{
		putValue(Action.NAME, "Position");
		putValue(Action.SHORT_DESCRIPTION, "New Position");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		PositionDialog dialog = new PositionDialog(Application.getMainFrame());
		dialog.setVisible(true);
	}
}
