package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import view.Application;
import view.CreateUser;
import view.InputExpenseDialog;

public class NewUserAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;

	public NewUserAction() 
	{
		putValue(Action.NAME, "New User");
		putValue(Action.SHORT_DESCRIPTION, "Input New User");
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_N));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		CreateUser createUser = new CreateUser();
		createUser.setVisible(true);
	}
}
