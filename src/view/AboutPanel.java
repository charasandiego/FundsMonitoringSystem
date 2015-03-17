package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import info.clearthought.layout.TableLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JLabel lblText;
	
	public AboutPanel(AboutDialog dialog)
	{
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setVisible(true);
		
		lblText = new JLabel("<HTML>Funds Monitoring System by: <BR><BR>Chara Mae San Diego<BR>Patrick Roi Ocampo</HTML>");
		lblText.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		add(lblText, BorderLayout.NORTH);
		
	}
}
