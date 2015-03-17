package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.InputFacultyDialog;

public class FacultyAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public FacultyAction() 
	{
		putValue(Action.NAME, "Faculty Member");
		putValue(Action.SHORT_DESCRIPTION, "New Faculty Member");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_F));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		InputFacultyDialog dialog = new InputFacultyDialog(Application.getMainFrame());
		dialog.setVisible(true);
		
		if (dialog.isSuccess())
		{
			Application.getMainFrame().setSelectedView(2, 2);
		}
	}
}
