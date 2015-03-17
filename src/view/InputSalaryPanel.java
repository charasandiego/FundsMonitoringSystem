package view;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.TrialConnection;

public class InputSalaryPanel extends JPanel
{
	
private static final long serialVersionUID = 1L;
	
	private TrialConnection tc;
	
	private boolean success = false;
	
	private JLabel nameLbl;
	private JLabel rankLbl;
	private JLabel salaryLbl;
	
	private JComboBox positionBox;
	private JComboBox rankBox;

	private JTextField salaryFld;
	
	private JButton submitBtn;
	
	private SalaryDialog salaryDialog;

	public InputSalaryPanel(SalaryDialog salaryDialog)
	{
		this.salaryDialog = salaryDialog;
		
		initComponents();
		addComponents();
	}
	
	private void initComponents()
	{
		tc = new TrialConnection();
		nameLbl = new JLabel("Position");
		rankLbl = new JLabel("Rank");
		salaryLbl = new JLabel("Salary");
		
		positionBox = new JComboBox(new DefaultComboBoxModel(getPositions()));
		rankBox = new JComboBox(new DefaultComboBoxModel(getRank()));
		salaryFld = new JTextField();
		
		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (positionBox.getSelectedIndex() == -1 || rankBox.getSelectedIndex() == -1 || salaryFld.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Insufficient Data");
				}
				else
				{
					String sal = salaryFld.getText();
					/*boolean hasDot = false;
					int ctr = 0;
					for (int x = 0; x < sal.length(); x++)
					{
						if (sal.charAt(x) > '9' || sal.charAt(x) < '0')
						{
							if (sal.charAt(x) == '.' && !hasDot)
							{
								hasDot = true;
								continue;
							}
							
							JOptionPane.showMessageDialog(null, "Invalid Input");
							return;
						}
						
						if (hasDot)
						{
							ctr++;
							
							if (ctr > 2)
							{
								JOptionPane.showMessageDialog(null, "Invalid Input");
								return;
							}
						}
						
					}
					
					if (ctr == 0)
					{
						sal = sal + ".00";
					}
					else if(ctr == 1)
					{
						sal = sal + '0';
					}
					*/
					tc.createActivity(Application.getMainFrame().getCurrentUser(), "Update", "Faculty Salary");
					tc.createSalary((String)positionBox.getSelectedItem(), (String)rankBox.getSelectedItem(), Double.parseDouble(sal));
					Application.getMainFrame().fixActivity();
					salaryDialog.dispose();
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
			{5, TableLayout.FILL, 5, TableLayout.FILL, 5, TableLayout.FILL, 10, TableLayout.FILL, 5}
		};
		
		setLayout(new TableLayout(size));
		add(nameLbl, "1,1");
		add(positionBox, "3,1");
		add(rankLbl, "1,3");
		add(rankBox, "3,3");
		add(salaryLbl, "1, 5");
		add(salaryFld, "3, 5");
		add(buttonPanel, "1,7,3,7");
	}
	
	private Vector getPositions()
	{
		Vector list = new Vector();
		list.add("");
		ResultSet rs = tc.accessPosition();
		try
		{
			while (rs.next())
			{
				if (!list.contains((String) rs.getObject("name")))
				{
					list.add((String) rs.getObject("name"));
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	private Vector getRank()
	{
		Vector list = new Vector();
		list.add("");
		ResultSet rs = tc.accessRank();
		try
		{
			while (rs.next())
			{
				if (!list.contains("" + ((Integer) rs.getObject("rank"))))
				{
					list.add("" + ((Integer) rs.getObject("rank")));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
}
