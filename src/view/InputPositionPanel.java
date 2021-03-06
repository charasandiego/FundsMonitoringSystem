package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.TrialConnection;

public class InputPositionPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private TrialConnection tc;
	
	private boolean success = false;
	
	private JLabel nameLbl;
	private JLabel descLbl;
	
	private JTextField nameFld;
	private JTextField descFld;
	
	private JButton submitBtn;
	
	private PositionDialog positionDialog;

	public InputPositionPanel(PositionDialog positionDialog)
	{
		this.positionDialog = positionDialog;
		
		initComponents();
		addComponents();
	}
	
	private void initComponents()
	{
		tc = new TrialConnection();
		nameLbl = new JLabel("Position");
		descLbl = new JLabel("Description");
		
		nameFld = new JTextField();
		nameFld.setText("");
		descFld = new JTextField();
		descFld.setText("");
		
		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (nameFld.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Insufficient Data");
				}
				else
				{
					String desc = "";
					if (!descFld.getText().equals(""))
					{
						desc = descFld.getText();
					}
					tc.createPosition(nameFld.getText(), desc);
					positionDialog.dispose();
				}
			}
		});
	}
	
	private void addComponents()
	{
		double[][] size1 = 
		{
			{TableLayout.FILL, 120, TableLayout.FILL},
			{TableLayout.FILL}
		};
		JPanel buttonPanel = new JPanel(new TableLayout(size1));
		buttonPanel.add(submitBtn,   "1,0");
		
		double[][] size = 
		{
			{5, TableLayout.MINIMUM, 5, TableLayout.FILL, 5},
			{5, TableLayout.FILL, 5, TableLayout.FILL, 10, TableLayout.FILL, 5}
		};
		
		setLayout(new TableLayout(size));
		add(nameLbl, "1,1");
		add(nameFld, "3,1");
		add(descLbl, "1,3");
		add(descFld, "3,3");
		add(buttonPanel, "1,5,3,5");
	}
}
