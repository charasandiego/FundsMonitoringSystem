package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

public class RankDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private InputRankPanel panel;
	
	public RankDialog(MainFrame parent)
	{
		 super(parent, "New Rank", true);
		 setSize(500, 130);
		 
		 Dimension parentSize = parent.getSize(); 
		 Point p = parent.getLocation(); 
		 setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		 
		 panel = new InputRankPanel(this);
		 setContentPane(panel);
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
