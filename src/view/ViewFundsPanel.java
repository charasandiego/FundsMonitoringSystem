package view;

import info.clearthought.layout.TableLayout;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ViewFundsPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane;
	
	private GeneralFundPanel generalFundPanel;
	private ProfessionalServicesPanel profServicesPanel;
	private BudgetAccountPanel budgetAccountPanel;
	/**
	 * Create the panel.
	 */
	public ViewFundsPanel() 
	{
		generalFundPanel = new GeneralFundPanel();
		profServicesPanel = new ProfessionalServicesPanel();
		budgetAccountPanel = new BudgetAccountPanel();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Arial", Font.PLAIN, 18));
		
		tabbedPane.addTab("General Fund", generalFundPanel);
		tabbedPane.addTab("Professional Service", profServicesPanel);
		tabbedPane.addTab("Trust Account", budgetAccountPanel);
		
		double[][] size = 
		{
			{TableLayout.FILL},
			{TableLayout.FILL}
		};

		setLayout(new TableLayout(size));
		add(tabbedPane, "0,0");
	}
	
	public void setSelectedView(int idx)
	{
		if (idx == 0)
		{
			generalFundPanel.update();
		}
		else if (idx == 1)
		{
			profServicesPanel.updateData();
		}
		else if (idx == 2)
		{
			budgetAccountPanel.update();
		}
		
		tabbedPane.setSelectedIndex(idx);
	}
}


