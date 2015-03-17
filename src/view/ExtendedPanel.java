package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.TrialConnection;

public class ExtendedPanel extends JPanel
{
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel model;
	
	private Vector dataVector;
	private Vector columnVector;

	private TrialConnection triCon;
	
	public ExtendedPanel()
	{
		setBackground(Color.WHITE);
		
		initComponents();
		addComponents();
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
			boolean first = true;
			while (rs.next())
			{
				if (first)
				{
					first = false;
					continue;
				}
				
				Vector lineData = new Vector();
				lineData.add((String) rs.getObject("Particulars"));
				Double x = rs.getDouble("amount");
				DecimalFormat df = new DecimalFormat("#.##");
				lineData.add("" + df.format(x));
				String travel = "";
				if (rs.getObject("travel") != null)
				{
					travel =  "" + rs.getObject("travel");
				}
				lineData.add(travel);
				String insurance = "";
				if (rs.getObject("insurance") != null)
				{
					insurance = "" + rs.getObject("insurance");
				}
				lineData.add(insurance);
				String transportation = "";
				if (rs.getObject("transportation") != null)
				{
					transportation = "" +  rs.getObject("transportation");
				}
				lineData.add(transportation);
				String mail = "";
				if (rs.getObject("mall") != null)
				{
					mail = "" +  rs.getObject("mall");
				}
				lineData.add(mail);
				String comm = "";
				if (rs.getObject("communication") != null)
				{
					comm = "" + rs.getObject("communication");
				}
				lineData.add(comm);
				String office = "";
				if (rs.getObject("officesupplies") != null)
				{
					office = "" + rs.getObject("officesupplies");
				}
				lineData.add(office);
				String is = "";
				if (rs.getObject("industrialsupplies") != null)
				{
					is = "" + rs.getObject("industrialsupplies");
				}
				lineData.add(is);
				String ls = "";
				if (rs.getObject("labsupplies") != null)
				{
					ls = "" + rs.getObject("labsupplies");
				}
				lineData.add(ls);
				String notary = "";
				if (rs.getObject("notary") != null)
				{
					travel = "" + rs.getObject("notary");
				}
				lineData.add(notary);
				String em = "";
				if (rs.getObject("equipment") != null)
				{
					em = "" + rs.getObject("equipment");
				}
				lineData.add(em);
				String bm = "";
				if (rs.getObject("building") != null)
				{
					bm = "" + rs.getObject("building");
				}
				lineData.add(bm);
				String rp = "";
				if (rs.getObject("photocopy") != null)
				{
					rp = "" + rs.getObject("photocopy");
				}
				lineData.add(rp);
				String printing = "";
				if (rs.getObject("printingexpenses") != null)
				{
					printing = "" + rs.getObject("printingexpenses");
				}
				lineData.add(printing);
				String re = "";
				if (rs.getObject("representation") != null)
				{
					re = "" + rs.getObject("representation");
				}
				lineData.add(re);
				
				
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
		columnVector = new Vector();
		columnVector.add("Particulars");
		columnVector.add("Amount");
		columnVector.add("Travel");
		columnVector.add("Insurance");
		columnVector.add("Transportation");
		columnVector.add("Mail");
		columnVector.add("Communication");
		columnVector.add("Office Supplies");
		columnVector.add("Industrial Supplies");
		columnVector.add("Lab Supplies");
		columnVector.add("Notary");
		columnVector.add("Equipment Maintenance");
		columnVector.add("Building Maintenance");
		columnVector.add("Rental Photocopy");
		columnVector.add("Printing Expenses");
		columnVector.add("Representation Expenses");
		
		dataVector = new Vector();
		initData();
		
		model = new DefaultTableModel(dataVector, columnVector);
		table = new JTable(model)
		{
			public void setValueAt(Object aValue, int row, int column)
			{
				super.setValueAt(aValue, row, column);
				String change = (String) table.getValueAt(row, column);
				String amt = (String) table.getValueAt(row, 1);
				
				triCon.createActivity(Application.getMainFrame().getCurrentUser(), "Update", "Row " + row + " of Extended General Fund Panel");
				triCon.extendGeneralFund(Double.parseDouble(amt), Double.parseDouble(change), column - 1);
			}
			
			public boolean isCellEditable(int row, int column)
			{
				if (column == 0 || column == 1)
				{
					return false;
				}
				
				return true;
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
}
