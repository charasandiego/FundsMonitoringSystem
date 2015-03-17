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


public class InputGeneralFundExpense extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private TrialConnection tc = new TrialConnection();
	
	private JTextField payee;
	private JTextField particulars;
	private JTextField amount;
	private JTextField totalAmount;

	private boolean success = false;

	/**
	 * Create the panel.
	 */
	public InputGeneralFundExpense(final InputGeneralFundDialog dialog) 
	{
		setBackground(Color.WHITE);
		
		payee = new JTextField();
		payee.setColumns(10);
		
		particulars = new JTextField();
		particulars.setColumns(10);
		
		amount = new JTextField();
		amount.setColumns(10);
		
		totalAmount = new JTextField();
		totalAmount.setColumns(10);
		
		JLabel lblCreateNewBudget = new JLabel("Input Expense from General Fund");
		lblCreateNewBudget.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblAccountName = new JLabel("Payee:");
		
		JLabel lblAmount = new JLabel("Particulars:");
		
		JLabel lblBenefactor = new JLabel("Amount:");
		
		JLabel lblRestrictions = new JLabel("Total Amount:");
		
		final JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String payeeName = payee.getText();
				String part = particulars.getText();
				String amt = amount.getText();
				String totalAmt = totalAmount.getText();
				
				if (payeeName.length() == 0 || part.length() == 0 || amt.length() == 0 || totalAmt.length() == 0)
				{
					JOptionPane.showMessageDialog(btnNewButton, "Insufficient Data");
					return;
				}
				
				if (amt.length() < 3)
				{
					amt = amt + ".00";
				}
				else if (amt.charAt(amt.length() - 3) != '.')
				{
					amt = amt + ".00";
				}
				
				if (totalAmt.length() < 3)
				{
					totalAmt = totalAmt+ ".00";
				}
				else if (totalAmt.charAt(totalAmt.length() - 3) != '.')
				{
					totalAmt = totalAmt + ".00";
				}
						
				Double bud = 0.00;
				Double mul = 0.01;
				for (int x = amt.length() - 1; x >= 0; x--)
				{
					if  (amt.charAt(x) == '.' && x == amt.length() - 3)
					{
						continue;
					}
					else if (amt.charAt(x) > '9' || amt.charAt(x) < '0')
					{
						JOptionPane.showMessageDialog(btnNewButton, "Amount invalid");
						return;
					}
					bud = bud + (amt.charAt(x) - '0') * mul;
					mul = mul * 10;
				}
				
				tc.createGeneralFund(payeeName, part, bud, bud);
				tc.createActivity(Application.getMainFrame().getCurrentUser(), "New Input", "General Fund");
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
			{10, 25, 10, 25, 10, 25, 10, 25, TableLayout.FILL, 25, 10}
		};
		
		setLayout(new TableLayout(size));
		
		add(lblAccountName, "1,1");
		add(payee,    "3,1");
		add(lblAmount,      "1,3");
		add(particulars,      	"3,3");
		add(lblBenefactor,  "1,5");
		add(amount,     "3,5");
		add(lblRestrictions,"1,7");
		add(totalAmount,   "3,7");
		add(buttonPanel,    "1,9,3,9");
	}

	public boolean isSuccess()
	{
		return success;
	}
}
