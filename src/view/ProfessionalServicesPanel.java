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

public class ProfessionalServicesPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel model;
	
	private Vector dataVector;
	private Vector columnVector;
	
	private boolean change = false;	
	private JPopupMenu popup;
	private JMenuItem item;
	
	private Point p = null;
	private TrialConnection triCon;

	public ProfessionalServicesPanel()
	{
		setBackground(Color.WHITE);
		
		initComponents();
		addComponents();
		initListener();
	}
	
	public void updateData()
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
		ResultSet rs = triCon.accessProfessionalServices();
		try 
		{
			while (rs.next())
			{
				Vector lineData = new Vector();
				lineData.add((String) rs.getObject("faculty"));
				lineData.add((String) rs.getObject("position"));
				int rank = rs.getInt("rank");
				lineData.add("" + rank);
				
				Double x = rs.getDouble("salary");
				DecimalFormat df = new DecimalFormat("#.##");
				String salary = "" + df.format(x);
				if (salary.charAt(salary.length() - 1) == '0' && salary.length() > 1)
				{
					salary = salary.substring(0, salary.length() - 2);
				}
				else if (salary.length() > 1)
				{
					if (salary.charAt(salary.length() - 2) == '.')
					{
						salary = salary + '0';
					}
				}
				lineData.add(salary);
				
				x = rs.getDouble("others");
				df = new DecimalFormat("#.##");
				String others = "" + df.format(x);
				if (others.charAt(others.length() - 1) == '0' && others.length() > 1)
				{
					others = others.substring(0, others.length() - 2);
				}
				else if (others.length() > 1)
				{
					if (others.charAt(others.length() - 2) == '.')
					{
						others = others + '0';
					}
				}
				lineData.add(others);
		
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
		columnVector.add("Position");
		columnVector.add("Rank");
		columnVector.add("Salary");
		columnVector.add("Others");
		
		dataVector = new Vector();
		initData();
		
		model = new DefaultTableModel(dataVector, columnVector);
		table = new JTable(model)
		{
			public void setValueAt(Object aValue, int row, int column)
			{
				if (row == 0 && !change)
				{
					JOptionPane.showMessageDialog(this, "Can't edit this row!");
				}
				else
				{
					super.setValueAt(aValue, row, column);
					String name = (String) table.getValueAt(row, 0);
					String others = (String) table.getValueAt(row, column);
					
					if (row != 0)
					{
						triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Update", "Row " + row + " of Professional Service Expense");
					}
					
					triCon.updateProfessionalServices(name, Double.parseDouble(others));
				}
			}				
			
			public boolean isCellEditable(int row, int column)
			{
				if (column == 4)
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

						Double totalSalary = Double.parseDouble((String) table.getValueAt(0, 3));
						Double totalOthers = Double.parseDouble((String) table.getValueAt(0, 4));
						Double salary = Double.parseDouble((String) table.getValueAt(row, 3));
						Double others = Double.parseDouble((String) table.getValueAt(row, 4));
						
						DecimalFormat df = new DecimalFormat("#.##");
						
						String newSalary = "" + df.format(totalSalary - salary);
						String newOthers = "" + df.format(totalOthers - others);
						
						if (newSalary.charAt(newSalary.length() - 1) == '0' && newSalary.length() > 1)
						{
							newSalary = newSalary.substring(0, newSalary.length() - 2);
						}
						else if (newSalary.length() > 1)
						{
							if (newSalary.charAt(newSalary.length() - 2) == '.')
							{
								newSalary = newSalary + '0';
							}
						}
						
						if (newOthers.charAt(newOthers.length() - 1) == '0' && newOthers.length() > 1)
						{
							newOthers = newOthers.substring(0, newOthers.length() - 2);
						}
						else if (newOthers.length() > 1)
						{
							if (newOthers.charAt(newOthers.length() - 2) == '.')
							{
								newOthers = newOthers + '0';
							}
						}
						
						String name = (String) table.getValueAt(row, 0);
						triCon.deleteProfessionalServices(name);
						triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Delete", "Row " + row + " of Professional Service Expense");
						
						change = true;
						table.setValueAt(newSalary, 0, 3);
						table.setValueAt(newOthers, 0, 4);
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
