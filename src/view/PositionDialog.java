package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class PositionDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private InputPositionPanel panel;
	
	public PositionDialog(MainFrame parent)
	{
		 super(parent, "New Position", true);
		 setSize(500, 130);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new InputPositionPanel(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
