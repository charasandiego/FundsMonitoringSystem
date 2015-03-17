package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.InputFacultyDialog;
import view.RankDialog;

public class RankAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public RankAction() 
	{
		putValue(Action.NAME, "Rank");
		putValue(Action.SHORT_DESCRIPTION, "New Rank");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		RankDialog dialog = new RankDialog(Application.getMainFrame());
		dialog.setVisible(true);
	}
}