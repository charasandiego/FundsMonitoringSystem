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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.TrialConnection;

public class GeneralFundPanel extends JPanel
{
	private TrialConnection triCon =  null;
	
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel model;
	
	private Vector dataVector;
	private Vector columnVector;
	
	private boolean change = false;
	
	private JPopupMenu popup;
	private JMenuItem item;
	private Point p = null;

	public GeneralFundPanel()
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
		
		ResultSet rs = triCon.accessGeneralFund();
		try 
		{
			while (rs.next())
			{
				Vector lineData = new Vector();
				lineData.add((String) rs.getObject("payee"));
				lineData.add((String) rs.getObject("particulars"));
				Double x = rs.getDouble("amount");
				DecimalFormat df = new DecimalFormat("#.##");
				lineData.add("" + df.format(x));
			    Double x2 = rs.getDouble("totalAmount");
				DecimalFormat df2 = new DecimalFormat("#.##");
				lineData.add("" + df2.format(x2));
				
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
		columnVector.add("Particulars");
		columnVector.add("Amount");
		columnVector.add("Total Amount");
		
		dataVector = new Vector();
		initData();
		
		model = new DefaultTableModel(dataVector, columnVector);
		table = new JTable(model)
		{
			public void setValueAt(Object aValue, int row, int column)
			{
				if (row == 0 && column != 3 && !change)
				{
					JOptionPane.showMessageDialog(this, "Can't edit this row!");
				}
				else
				{
					super.setValueAt(aValue, row, column);
					String name = (String) table.getValueAt(row, 0);
					String amt = (String) table.getValueAt(row, 2);
					String totalAmt = (String) table.getValueAt(row, 3);
					
					if (row != 0)
					{
						triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Update", "Row " + row + " of General Fund");
					}
					
					triCon.updateGeneralFund(name, Double.parseDouble(amt), Double.parseDouble(totalAmt));
				}
			}
			
			public boolean isCellEditable(int row, int column)
			{
				if (column == 2 || column == 3)
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
				if (SwingUtilities.isRightMouseButton(e))
				{
					p = e.getPoint();
				}
			}
		});
		
		item.addMouseListener(new MouseAdapter() 
		{	
			public void mousePressed(MouseEvent e)
			{
				int row = table.rowAtPoint(p);
				
				if (row == 0)
				{
					JOptionPane.showMessageDialog(table, "Can't delete this row!");
				}
				
				else
				{
					int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?" 
			                ,"Delete Row", JOptionPane.WARNING_MESSAGE
			                , JOptionPane.OK_CANCEL_OPTION); 
					
					if (choice == 0)
					{
						Double totalAmount = Double.parseDouble((String) table.getValueAt(0, 2));
						Double amount = Double.parseDouble((String) table.getValueAt(row, 2));
						
						DecimalFormat df = new DecimalFormat("#.##");
						String newAmount = "" + df.format(totalAmount - amount);
						
						if (newAmount.charAt(newAmount.length() - 1) == '0' && newAmount.length() > 1)
						{
							newAmount = newAmount.substring(0, newAmount.length() - 2);
						}
						else if (newAmount.length() > 1)
						{
							if (newAmount.charAt(newAmount.length() - 2) == '.')
							{
								newAmount = newAmount + '0';
							}
						}
						
						String name = (String) table.getValueAt(row, 0);
						triCon.deleteGeneralFund(name);
						triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Delete", "Row " + row + " of General Fund");
						
						change = true;
						table.setValueAt(newAmount, 0, 2);
						change = false;
						
						DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
						tempModel.removeRow(row);
					}
				}
			}
		});
		popup.add(item);
		table.setComponentPopupMenu(popup);
	}

}
