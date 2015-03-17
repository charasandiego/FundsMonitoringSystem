package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

public class InputFacultyProfile extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private JTextField facultyName;
	private JTextField salary;
	private JTextField remarks;
	
	private JComboBox rankCbx;
	private JComboBox positionCbx;
	
	private TrialConnection tc;
	
	private boolean success = false;
	
	public InputFacultyProfile(final InputFacultyDialog dialog)
	{
		setBackground(Color.WHITE);
		
		tc = new TrialConnection();
		
		facultyName = new JTextField();
		facultyName.setColumns(10);
		
		positionCbx = new JComboBox(new DefaultComboBoxModel(getPositions()));
		positionCbx.addItemListener(new ItemListener()
		{	
			public void itemStateChanged(ItemEvent ie)
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
				{
					String post = (String) positionCbx.getSelectedItem();
					String rank = (String) rankCbx.getSelectedItem();
					if (rank != null && !rank.equals(""))
					{
						ResultSet rs = tc.accessSalary(post, rank);
						try
						{
							boolean entered = false;
							while (rs.next())
							{
								entered = true;
								salary.setText(""+((Double) rs.getObject("salary")));
							}
							
							if (!entered)
							{
								salary.setText("");
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		rankCbx = new JComboBox(new DefaultComboBoxModel(getRank()));
		rankCbx.addItemListener(new ItemListener()
		{	
			public void itemStateChanged(ItemEvent ie)
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
				{
					String post = (String) positionCbx.getSelectedItem();
					String rank = (String) rankCbx.getSelectedItem();
					if (post != null && !post.equals(""))
					{
						ResultSet rs = tc.accessSalary(post, rank);
						try
						{
							boolean entered = false;
							while (rs.next())
							{
								entered = true;
								salary.setText(""+((Double) rs.getObject("salary")));
							}
							
							if (!entered)
							{
								salary.setText("");
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		salary = new JTextField();
		salary.setColumns(10);
		salary.setEditable(false);
		
		remarks = new JTextField();
		remarks.setColumns(10);
		
		JLabel lblCreateNewBudget = new JLabel("New Faculty Member");
		lblCreateNewBudget.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblAccountName = new JLabel("Name:");
		
		JLabel lblrank = new JLabel("Position:");
		
		JLabel lblBenefactor = new JLabel("Rank:");
		
		JLabel lblRestrictions = new JLabel("Salary:");
		
		JLabel lblRemarks = new JLabel("Remarks:");
		
		final JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = facultyName.getText();
				String pos = (String)positionCbx.getSelectedItem();
				String ran = (String)rankCbx.getSelectedItem();
				String sal = salary.getText();
				String rem = remarks.getText();
				
				if (name.length() == 0 || pos.length() == 0 || ran.length() == 0 || sal.length() == 0)
				{
					JOptionPane.showMessageDialog(btnNewButton, "Insufficient Data");
					return;
				}
				
				if (rem.length() == 0)
				{
					rem = " ";
				}
				
				if (sal.length() < 3)
				{
					sal = sal + ".00";
				}
				else if (sal.charAt(sal.length() - 3) != '.')
				{
					sal = sal + ".00";
				}
				
				int rk = Integer.parseInt(ran);
				Double totSal = 0.00;
				Double mul = 0.01;
				for (int x = sal.length() - 1; x >= 0; x--)
				{
					if  (sal.charAt(x) == '.')
					{
						continue;
					}
					else if (sal.charAt(x) > '9' || sal.charAt(x) < '0')
					{
						JOptionPane.showMessageDialog(btnNewButton, "Salary amount invalid");
						return;
					}
					totSal = totSal + (sal.charAt(x) - '0') * mul;
					mul = mul * 10;
				}
				
				tc.createActivity(Application.getMainFrame().getCurrentUser(), "New Input", "Faculty Profile");
				tc.createFacultyProfile(name, pos, rk, totSal, rem);		
				JOptionPane.showMessageDialog(btnNewButton, "Account successfully created");
				
				success = true;
				dialog.dispose();
			}
		});
		
		
		double[][] size1 = 
			{
				{TableLayout.FILL, 120, TableLayout.FILL},
				{5, TableLayout.FILL, 5}
			};
			JPanel buttonPanel = new JPanel(new TableLayout(size1));
			buttonPanel.setBackground(Color.WHITE);
			buttonPanel.add(btnNewButton,   "1,1");
			
		double[][] size = 
			{
				{15, TableLayout.MINIMUM, 5, TableLayout.FILL, 15},
				{10, 25, 10, 25, 10, 25, 10, 25, 10, 25, TableLayout.FILL, 35, 5}
			};
			
			setLayout(new TableLayout(size));
			
			add(lblAccountName, "1,1");
			add(facultyName,    "3,1");
			add(lblrank,      	"1,3");
			add(positionCbx,   	"3,3");
			add(lblBenefactor,  "1,5");
			add(rankCbx,     	"3,5");
			add(lblRestrictions,"1,7");
			add(salary,   		"3,7");
			add(lblRemarks, 	"1, 9");
			add(remarks, 		"3, 9");
			add(buttonPanel,    "1,11,3,11");
	}

	public boolean isSuccess()
	{
		return success;
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
