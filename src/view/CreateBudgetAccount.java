package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.TrialConnection;

public class CreateBudgetAccount extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private TrialConnection tc = new TrialConnection();
	
	private JTextField accountName;
	private JTextField amount;
	private JTextField restrictions;
	private JTextField benefactor;
	
	private boolean success = false;

	/**
	 * Create the panel.
	 */
	public CreateBudgetAccount(final InputExpenseDialog dialog) 
	{
		setBackground(Color.WHITE);
		
		accountName = new JTextField();
		accountName.setColumns(10);
		
		amount = new JTextField();
		amount.setColumns(10);
		
		restrictions = new JTextField();
		restrictions.setColumns(10);
		
		benefactor = new JTextField();
		benefactor.setColumns(10);
		
		JLabel lblCreateNewBudget = new JLabel("Create New Budget Account");
		lblCreateNewBudget.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblAccountName = new JLabel("Account Name:");
		
		JLabel lblAmount = new JLabel("Amount:");
		
		JLabel lblBenefactor = new JLabel("Benefactor:");
		
		JLabel lblRestrictions = new JLabel("Restrictions:");
		
		final JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				String name = accountName.getText();
				String budget = amount.getText();
				String ben = benefactor.getText();
				String res = restrictions.getText();
				
				if (budget.length() == 0)
				{
					JOptionPane.showMessageDialog(btnNewButton, "Enter Amount");
					return;
				}
				
				if (budget.length() < 3)
				{
					budget = budget + ".00";
				}
				else if (budget.charAt(budget.length() - 3) != '.')
				{
					budget = budget + ".00";
				}
						
				Double bud = 0.00;
				Double mul = 0.01;
				for (int x = budget.length() - 1; x >= 0; x--)
				{
					if  (budget.charAt(x) == '.' && x == budget.length() - 3)
					{
						continue;
					}
					else if (budget.charAt(x) > '9' || budget.charAt(x) < '0')
					{
						JOptionPane.showMessageDialog(btnNewButton, "Amount invalid");
						return;
					}
					bud = bud + (budget.charAt(x) - '0') * mul;
					mul = mul * 10;
				}
				
				if (name.equals(""))
				{
					JOptionPane.showMessageDialog(btnNewButton, "Insufficient input");
					return;
				}
				tc.createActivity(Application.getMainFrame().getCurrentUser(), "New Input", "Trust Account");
				tc.createTrustAccount(name, bud, ben, res);		
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
		
		JLabel lblI = new JLabel("*Leave blank if budget has no restrictions");
		lblI.setForeground(Color.RED);
		lblI.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		double[][] size = 
		{
			{15, TableLayout.MINIMUM, 5, TableLayout.FILL, 15},
			{10, 25, 10, 25, 10, 25, 10, 25, 2, 12, TableLayout.FILL, 25, 10}
		};
		
		setLayout(new TableLayout(size));
		
		add(lblAccountName, "1,1");
		add(accountName,    "3,1");
		add(lblAmount,      "1,3");
		add(amount,      	"3,3");
		add(lblBenefactor,  "1,5");
		add(benefactor,     "3,5");
		add(lblRestrictions,"1,7");
		add(restrictions,   "3,7");
		add(lblI,   		"3,9");
		add(buttonPanel,    "1,11,3,11");
	}

	public boolean isSuccess()
	{
		return success;
	}
}