package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class AboutDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private AboutPanel panel;
	
	public AboutDialog(MainFrame parent)
	{
		 super(parent, "About", true);
		 setSize(500, 245);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new AboutPanel(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
