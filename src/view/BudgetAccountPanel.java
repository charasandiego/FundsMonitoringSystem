package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.TrialConnection;

public class BudgetAccountPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private TrialConnection triCon =  null;
	
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel model;
	
	private Vector dataVector;
	private Vector columnVector;
	

	private JPopupMenu popup;
	private JMenuItem item;
	private Point p;
	
	public BudgetAccountPanel()
	{
		setBackground(Color.WHITE);
		
		initComponents();
		addComponents();
		initListener();
	}
	
	public void update()
	{
		dataVector.clear();
		initData();
		model.setDataVector(dataVector, columnVector);
		model.fireTableDataChanged();
		table.updateUI();
	}
	
	private void initData()
	{
		triCon = new TrialConnection();
		ResultSet rs = triCon.accessTrustAccounts();
	
		try 
		{
			while (rs.next())
			{
				Vector lineData = new Vector();
				lineData.add((String) rs.getObject("account_name"));
				Double x = rs.getDouble("amount");
				DecimalFormat df = new DecimalFormat("#.##");
				lineData.add("" + df.format(x));
				String benefactor = "";
				if (rs.getObject("benefactor") != null)
				{
					benefactor = (String) rs.getObject("benefactor");
				}
				lineData.add(benefactor);
				lineData.add((String) rs.getObject("account_usage"));
				dataVector.add(lineData);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void initComponents()
	{
		
		popup = new JPopupMenu();
		item = new JMenuItem("Delete");
		
		columnVector = new Vector();
		columnVector.add("Name");
		columnVector.add("Amount");
		columnVector.add("Benefactor");
		columnVector.add("Usage");
		
		dataVector = new Vector();
		initData();
		
		model = new DefaultTableModel(dataVector, columnVector);
		table = new JTable(model)
		{
			public void setValueAt(Object aValue, int row, int column)
			{
				super.setValueAt(aValue, row, column);
				String name = (String) table.getValueAt(row, 0);
				String amt = (String) table.getValueAt(row, column);
				
				triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Update", "Row " + row + " of Trust Account");
				triCon.updateTrustAccount(name, Double.parseDouble(amt));
			}
			
			public boolean isCellEditable(int row, int column)
			{
				if (column == 1)
				{
					return true;
				}
				
				return false;
			}
		};
		
		scroll = new JScrollPane(table);
		scroll.setBounds(0, 0, 1095, 642);
	}
	
	private void addComponents()
	{
		double[][] size = 
		{
			{5, TableLayout.FILL, 5},
			{5, TableLayout.FILL, 5}
		};
		
		setLayout(new TableLayout(size));
		setBackground(new Color(200, 221, 242));
		
		add(scroll, "1,1");
	}
	
	private void initListener()
	{
		table.addMouseListener(new MouseAdapter() 
		{	
			public void mousePressed(MouseEvent e)
			{
				p = e.getPoint();
			}
		});
		
		item.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int row = table.rowAtPoint(p);
				
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?" 
						,"Delete Row", JOptionPane.WARNING_MESSAGE
		                , JOptionPane.OK_CANCEL_OPTION); 
					
				if (choice == 0)
				{
					String name = (String) table.getValueAt(row, 0);
					triCon.deleteTrustAccount(name);
					triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Delete", "Row " + row + " of Trust Account");
						
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					tempModel.removeRow(row);			
				}
			}
		});
		
		popup.add(item);
		table.setComponentPopupMenu(popup);
	}
}
