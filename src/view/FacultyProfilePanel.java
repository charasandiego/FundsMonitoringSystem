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

public class FacultyProfilePanel extends JPanel 
{
	private static final long serialVersionUID = 1L;

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
	private JPanel panel = new JPanel();
	
	/**
	 * Create the panel.
	 */
	public FacultyProfilePanel() 
	{
		setBackground(new Color(238, 238, 238));
		
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
		ResultSet rs = triCon.accessFacultyProfile();
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
				
				if(rs.getObject("Remarks") != null){
					lineData.add((String)rs.getObject("Remarks"));
				}
				
		
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
		columnVector.add("Remarks");
		
		dataVector = new Vector();
		initData();
		
		model = new DefaultTableModel(dataVector, columnVector);
		table = new JTable(model)
		{
			public void setValueAt(Object aValue, int row, int column)
			{
				String newName = "";
				String newPosition = "";
				int newRank = -1;
				Double newSalary = -1.0;
				String Remarks = "";
				
				String name = (String) table.getValueAt(row, 0);
				super.setValueAt(aValue, row, column);
				
				if (column == 0)
				{
					newName = (String) table.getValueAt(row, 0);
				}
				else if (column == 1)
				{
					newPosition = (String) table.getValueAt(row, 1);
					
				}
				else if (column == 2)
				{
					newRank = Integer.parseInt((String) table.getValueAt(row, 2));
				}
				else if (column == 3)
				{
					newSalary = Double.parseDouble((String) table.getValueAt(row, 3));
				}
				else
				{
					Remarks = (String) table.getValueAt(row, 4);
				}
				triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Update", "Row " + row + " of Faculty Profile");
				triCon.updateFacultyProfile(name, newName, newPosition, newRank, newSalary, Remarks);
			}
			
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		scroll = new JScrollPane(table);
	}
	
	private void addComponents()
	{
		double[][] size = 
		{
			{5, TableLayout.FILL, 5},
			{5, TableLayout.FILL, 5}
		};
		
		setLayout(new TableLayout(size));
		
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
				
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?" 
						,"Delete Row", JOptionPane.WARNING_MESSAGE
		                , JOptionPane.OK_CANCEL_OPTION); 
					
				if (choice == 0)
				{
					String name = (String) table.getValueAt(row, 0);
					triCon.deleteFacultyMember(name);
					triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Delete", "Row " + row + " of Faculty Profile");
						
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					tempModel.removeRow(row);			
				}
			}
		});
		popup.add(item);
		table.setComponentPopupMenu(popup);
		
	}
}
