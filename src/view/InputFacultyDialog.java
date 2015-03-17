package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class InputFacultyDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private InputFacultyProfile panel;
	
	public InputFacultyDialog(MainFrame parent)
	{
		 super(parent, "New Faculty Member", true);
		 setSize(500, 255);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new InputFacultyProfile(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public boolean isSuccess()
	{
		return panel.isSuccess();
	}
}
