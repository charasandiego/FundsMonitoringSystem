package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.TrialConnection;

public class InputProfessionalServiceExpense extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private TrialConnection triCon = null;

	private ResultSet rs = null;
	private ResultSet mainRS = null;
	private JTextField others;

	private JComboBox comboBox;
	
	private boolean success = false;

	/**
	 * Create the panel.
	 */
	public InputProfessionalServiceExpense(final InputProfessionalDialog dialog)
	{
		setBackground(Color.WHITE);
		
		triCon = new TrialConnection();
		rs = triCon.accessFacultyProfile();
		
		triCon = new TrialConnection();
		mainRS = triCon.accessFacultyProfile();
		
		comboBox = new JComboBox();
		
		others = new JTextField();
		others.setColumns(10);
		
		JLabel lblCreateNewBudget = new JLabel("Input Professional Service Expense");
		lblCreateNewBudget.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblAmount = new JLabel("Faculty Name:");
		
		JLabel lblBenefactor = new JLabel("Others:");
		
		String[] names = new String[100];
		try {
			int key = 0;
			
			while (rs.next())
			{
				String temp = (String) rs.getObject("faculty");
				names[key] = temp;
				key++;
			}
			
			key = 0;
			triCon = new TrialConnection();
			rs = triCon.accessFacultyProfile();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		comboBox.setModel(new DefaultComboBoxModel(names));
		add(comboBox);
		
		final JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String oth = others.getText();
				int faculty = comboBox.getSelectedIndex();
				
				if (oth.length() == 0 || faculty == -1)
				{
					JOptionPane.showMessageDialog(btnNewButton, "Insufficient Data");
					return;
				}
				
				if (oth.length() < 3)
				{
					oth = oth + ".00";
				}
				else if (oth.charAt(oth.length() - 3) != '.')
				{
					oth = oth + ".00";
				}
				
				Double bud = 0.00;
				Double mul = 0.01;
				for (int x = oth.length() - 1; x >= 0; x--)
				{
					if  (oth.charAt(x) == '.' && x == oth.length() - 3)
					{
						continue;
					}
					else if (oth.charAt(x) > '9' || oth.charAt(x) < '0')
					{
						JOptionPane.showMessageDialog(btnNewButton, "Amount invalid");
						return;
					}
					bud = bud + (oth.charAt(x) - '0') * mul;
					mul = mul * 10;
				}
				
				String name = "";
				String position  = "";
				int rank = 1;
				Double salary = 0.00;
				int index = comboBox.getSelectedIndex();
				int ctr = 0;
				
				try
				{
					
					while (mainRS.next())
					{
						if (index == ctr)
						{
							name = (String) mainRS.getObject("faculty");
							position = (String) mainRS.getObject("position");
							rank = mainRS.getInt("rank");
							salary = mainRS.getDouble("salary");
							
							break;
						}
						else
						{
							ctr++;
						}
					}
					
				}catch(Exception ef) {}
				
				triCon = new TrialConnection();
				mainRS = triCon.accessFacultyProfile();
				
				triCon.createActivity(Application.getMainFrame().getCurrentUser(), "New Input", "Professional Service Expense");
				triCon.createProfessionalServices(name, position, rank, salary, bud);		
				JOptionPane.showMessageDialog(btnNewButton, "Account successfully created");
				
				success = true;
				dialog.dispose();
			}
		});
		
		double[][] size1 = 
		{
			{TableLayout.FILL, 120, TableLayout.FILL},
			{TableLayout.FILL}
		};
		JPanel buttonPanel = new JPanel(new TableLayout(size1));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(btnNewButton,   "1,0");
		
		double[][] size = 
		{
			{15, TableLayout.MINIMUM, 5, TableLayout.FILL, 15},
			{10, 25, 10, 25, TableLayout.FILL, 25, 10}
		};
		
		setLayout(new TableLayout(size));
		
		add(lblAmount, 		"1,1");
		add(comboBox,    	"3,1");
		add(lblBenefactor,  "1,3");
		add(others,      	"3,3");
		add(buttonPanel,    "1,5,3,5");
	}

	public boolean isSuccess() 
	{
		return success;
	}
}
