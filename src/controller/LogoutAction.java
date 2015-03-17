package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.TrialConnection;
import view.Application;

public class LogoutAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public LogoutAction() 
	{
		putValue(Action.NAME, "Log Off");
		putValue(Action.SHORT_DESCRIPTION, "Log-out Application");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_L));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		TrialConnection tc = new TrialConnection();
		tc.logout(Application.getMainFrame().getCurrentUser());
		Application.getMainFrame().dispose();
		
		new Application();
	}
}
