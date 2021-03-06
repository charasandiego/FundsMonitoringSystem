package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
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

public class LoginPanel extends JPanel
{
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel model;
	
	private Vector dataVector;
	private Vector columnVector;
	
	private TrialConnection tc = null;
	
	private JPopupMenu popup;
	private JMenuItem item;
	
	public LoginPanel()
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
		tc = new TrialConnection();
		
		ResultSet rs = tc.accessLogIn();
		
		try
		{
			while (rs.next())
			{
				Vector lineData = new Vector();
				lineData.add((String)rs.getObject("user"));
				lineData.add((String)rs.getObject("login"));
				if (rs.getObject("logout") == null)
				{
					lineData.add("Still Logged In");
				}
				else
				{
					lineData.add((String)rs.getObject("logout"));
				}
				
				dataVector.add(lineData);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void initComponents()
	{
		popup = new JPopupMenu();
		item = new JMenuItem("Delete All");
		
		columnVector = new Vector();
		columnVector.add("User");
		columnVector.add("Login");
		columnVector.add("Logout");
		
		dataVector = new Vector();
		initData();
		
		model = new DefaultTableModel(dataVector, columnVector);
		table = new JTable(model)
		{
			public boolean isCellEditable(int row, int column)
			{
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
		item.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all data?" 
		                ,"Delete All", JOptionPane.WARNING_MESSAGE
		                , JOptionPane.OK_CANCEL_OPTION);
				
				if (choice == 0)
				{
					tc.deleteAllLogIn();
					tc.createActivity(Application.getMainFrame().getCurrentUser(), "Delete All", "Login History");
					
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					int rowCount = tempModel.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--)
					{
						String logout = (String) table.getValueAt(i, 2);
						if (!logout.equals("Still Logged In"))
						{
							tempModel.removeRow(i);
						}
					}
				}
			}
		});
		
		popup.add(item);
		table.setComponentPopupMenu(popup);
	}
}
