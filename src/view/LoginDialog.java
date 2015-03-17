package view;

import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.TrialConnection;

public class LoginDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	
	private JLabel iconLbl;
	private JLabel usernameLbl;
	private JLabel passwordLbl;
	
	private JTextField usernameFld;
	private JPasswordField passwordFld;
	
	private JPanel buttonPanel;
	private JButton okBtn;
	private JButton cancelBtn;
	
	private int check = 1;
	private int userType  = 0;
	private String userName = "";
	
	public LoginDialog()
	{
		setModal(true);
		setTitle("Log-in to Funds Monitoring System");
		setSize(425, 160);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		initComponents();
		addComponents();
	}
	
	private void addComponents()
	{
		double[][] size = 
		{
			{5, 115, 5, TableLayout.MINIMUM, 5, TableLayout.FILL, 5},
			{10, 25, 10, 25, TableLayout.FILL, 25, 10}
		};
		
		mainPanel = new JPanel(new TableLayout(size));
		mainPanel.add(iconLbl,     "1,1,1,5");
		mainPanel.add(usernameLbl, "3,1");
		mainPanel.add(usernameFld, "5,1");
		mainPanel.add(passwordLbl, "3,3");
		mainPanel.add(passwordFld, "5,3");
		mainPanel.add(buttonPanel, "3,5,5,5");
		
		setContentPane(mainPanel);
	}
	
	private void initComponents()
	{
		iconLbl = new JLabel(new ImageIcon("C:\\Eclipse\\Workspace\\FundsMonitoringSystem\\image\\lock.png"));
		
		usernameLbl = new JLabel("User Name");
		usernameLbl.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		usernameFld = new JTextField();
		usernameFld.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		passwordFld = new JPasswordField();
		passwordFld.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		okBtn = new JButton("Submit");
		okBtn.addActionListener(this);
		okBtn.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		cancelBtn.setFont(new Font("Helvetica", Font.PLAIN, 16));
		
		double[][] size = 
		{
			{TableLayout.FILL, 100, 10, 100, TableLayout.FILL},
			{TableLayout.FILL}
		};
		
		buttonPanel = new JPanel(new TableLayout(size));
		buttonPanel.add(okBtn, "1,0");
		buttonPanel.add(cancelBtn, "3,0");
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == okBtn)
		{
			TrialConnection tc = new TrialConnection();
			String pw = passwordFld.getText();
			userName = usernameFld.getText();
			check = tc.checkPassword(userName, pw);
			
			if(check == 0)
			{
				userType = tc.checkUserType(userName);
				tc.createLogin(userName);
				
				EventQueue.invokeLater(new Runnable() 
				{
					public void run() 
					{
						try 
						{
							setVisible(false);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Incorrect user name or password!");
			}
		}
		else if (e.getSource() == cancelBtn)
		{
			System.exit(0);
		}
	}
	
	public int getCheck() 
	{
		return check;
	}

	public int getUserType() 
	{
		return userType;
	}

	public String getUserName() 
	{
		return userName;
	}
}
