package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.ChangePassword;

public class ChangePasswordAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public ChangePasswordAction() 
	{
		putValue(Action.NAME, "Change Password");
		putValue(Action.SHORT_DESCRIPTION, "Changes Password");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_C));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String username = Application.getMainFrame().getCurrentUser();
		
		ChangePassword changePassword = new ChangePassword(username);
		changePassword.setVisible(true);
	}
}
