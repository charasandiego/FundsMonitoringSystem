package view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.TrialConnection;

import com.ibm.icu.text.SimpleDateFormat;

import controller.AboutAction;
import controller.BudgetAction;
import controller.ChangePasswordAction;
import controller.ExitAction;
import controller.FacultyAction;
import controller.GeneralFundAction;
import controller.LogoutAction;
import controller.NewUserAction;
import controller.PositionAction;
import controller.ProfessionalAction;
import controller.RankAction;
import controller.SalaryAction;

public class MainFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private String currentUser;
	private int currentType;
	
	private TrialConnection tc;
	
	private JMenuBar mainMenu;
	private JMenu fileMenu;
	private JMenu configMenu;
	private JMenu helpMenu;
	
	//File
	private JMenuItem newUserItem;
	private JMenuItem changePWItem;
	private JMenuItem logOffMenu;
	private JMenuItem exitMenu;
	
	//Configuration
	private JMenuItem budgetMenu;
	private JMenuItem genFundMenu;
	private JMenuItem profServiceMenu;
	private JMenuItem facultyMemberMenu;
	private JMenuItem positionMenu;
	private JMenuItem rankMenu;
	private JMenuItem salaryMenu;
	
	//Help
	private JMenuItem aboutMenu;
	
	private JPanel navigationPanel;
	private JButton fundBtn;
	private JButton facultyBtn;
	private JButton activityBtn;
	private JButton loginBtn;
	private JButton extendedBtn;
	
	private JPanel infoPanel;
	private JLabel userLbl;
	private JLabel titleLbl;
	private JLabel timerLbl;
	
	private JPanel centerPanel;
	private ViewFundsPanel viewPanel;
	private FacultyProfilePanel facultyPanel;
	private ActivityPanel activityPanel;
	private LoginPanel loginPanel;
	private ExtendedPanel extendedPanel;
	
	private JPanel mainPanel;
		
	private int selectedPanel = 1;

	public MainFrame(String username, int type)
	{
		setTitle("Funds Monitoring System");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		currentUser = username;
		currentType = type;
		
		initComponents();
		addComponents();
		
		setJMenuBar(mainMenu);
	}
	
	public String getCurrentUser()
	{
		return currentUser;
	}

	private void addComponents()
	{
		double[][] size = 
		{
			{200, TableLayout.FILL},
			{35, TableLayout.FILL}
		};
		
		mainPanel = new JPanel(new TableLayout(size));
		mainPanel.add(infoPanel, "0,0,1,0");
		mainPanel.add(navigationPanel, "0,1");
		mainPanel.add(centerPanel, "1,1");
		
		setContentPane(mainPanel);
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				new TrialConnection().logout(getCurrentUser());
			}
		});
	}
	
	private void initComponents()
	{
		initMenus();
		initInfoPanel();
		initCenterPanel();
		initNavigationComponent();
	}
	
	private void initNavigationComponent()
	{	
		fundBtn = new JButton("<HTML>View Funds</HTML>");
		fundBtn.addActionListener(this);
		fundBtn.setHorizontalAlignment(SwingConstants.CENTER);
		fundBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		
		facultyBtn = new JButton("<HTML><CENTER>View Faculty<BR>Profile</HTML>");
		facultyBtn.addActionListener(this);
		facultyBtn.setHorizontalAlignment(SwingConstants.CENTER);
		facultyBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		
		extendedBtn = new JButton("<HTML><CENTER>View Extended<BR>General Fund</HTML>");
		extendedBtn.addActionListener(this);
		extendedBtn.setHorizontalAlignment(SwingConstants.CENTER);
		extendedBtn.setFont(new Font("Arial", Font.PLAIN, 20));

		loginBtn = new JButton("<HTML><CENTER>View Login<BR>History</HTML>");
		loginBtn.addActionListener(this);
		loginBtn.setHorizontalAlignment(SwingConstants.CENTER);
		loginBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		
		activityBtn = new JButton("<HTML><CENTER>View Activity<BR>Logs</HTML>");
		activityBtn.addActionListener(this);
		activityBtn.setHorizontalAlignment(SwingConstants.CENTER);
		activityBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		
		if (currentType != 0)
		{
			activityBtn.setEnabled(false);
			loginBtn.setEnabled(false);
		}
		
		double[][] size = 
		{
			{5, TableLayout.FILL, 5},
			{15, 60, 10, 60, 10, 60, 10, 60, 10, 60, TableLayout.FILL}
		};
		
		navigationPanel = new JPanel(new TableLayout(size));
		navigationPanel.setBackground(SystemColor.activeCaption);
		navigationPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		navigationPanel.add(fundBtn, "1,1");
		navigationPanel.add(facultyBtn, "1,3");
		navigationPanel.add(extendedBtn, "1, 5");
		navigationPanel.add(activityBtn, "1, 7");
		navigationPanel.add(loginBtn, "1, 9");
	}
	
	private void initInfoPanel()
	{
		userLbl = new JLabel("Welcome, " + currentUser + '!');
		userLbl.setFont(new Font("Arial", Font.PLAIN, 20));
		
		titleLbl = new JLabel("Department of Computer Science");
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setFont(new Font("Arial", Font.BOLD, 24));
		
		final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd,yyyy HH:mm");
		
		timerLbl = new JLabel();
		timerLbl.setText(dateFormat.format(new Date()));
		timerLbl.setFont(new Font("Arial", Font.BOLD, 16));
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() 
		{	
			public void run() 
			{
				timerLbl.setText(dateFormat.format(new Date()));
			}
		}, 0, 60 * 1000);
		
		double[][] size = 
		{
			{5, TableLayout.MINIMUM, TableLayout.FILL, TableLayout.MINIMUM, 5},
			{5, 25, 5}
		};
		
		infoPanel = new JPanel(new TableLayout(size));
		infoPanel.add(userLbl, "1,1");
		infoPanel.add(titleLbl, "2,1");
		infoPanel.add(timerLbl, "3,1");
	}
	
	private void initCenterPanel()
	{
		viewPanel = new ViewFundsPanel();
		facultyPanel = new FacultyProfilePanel();
		activityPanel = new ActivityPanel();
		loginPanel = new LoginPanel();
		extendedPanel = new ExtendedPanel();
		
		double[][] size = 
		{
			{5, TableLayout.FILL, 5},
			{5, TableLayout.FILL, 5}
		};
		
		centerPanel = new JPanel(new TableLayout(size));
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		centerPanel.add(viewPanel, "1,1");
	}
	
	private void initMenus()
	{
		newUserItem = new JMenuItem("New User");
		newUserItem.setAction(new NewUserAction());
		
		if (currentType != 0)
		{
			newUserItem.setEnabled(false);
		}
		
		changePWItem = new JMenuItem("Change Password");
		changePWItem.setAction(new ChangePasswordAction());
		
		logOffMenu = new JMenuItem("Log Off");
		logOffMenu.setAction(new LogoutAction());
		
		exitMenu = new JMenuItem("Exit");
		exitMenu.setAction(new ExitAction());
		
		fileMenu = new JMenu("File");
		fileMenu.add(newUserItem);
		fileMenu.add(changePWItem);
		fileMenu.addSeparator();
		fileMenu.add(logOffMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitMenu);
		
		budgetMenu = new JMenuItem("Budget Account");
		budgetMenu.setAction(new BudgetAction());
		
		genFundMenu = new JMenuItem("General Fund Expense");
		genFundMenu.setAction(new GeneralFundAction());
		
		profServiceMenu = new JMenuItem("Professional Service Expense");
		profServiceMenu.setAction(new ProfessionalAction());
		
		facultyMemberMenu = new JMenuItem("Faculty Member");
		facultyMemberMenu.setAction(new FacultyAction());
		
		positionMenu = new JMenuItem("Position");
		positionMenu.setAction(new PositionAction());
		
		rankMenu = new JMenuItem("Rank");
		rankMenu.setAction(new RankAction());
		
		salaryMenu = new JMenuItem("Salary");
		salaryMenu.setAction(new SalaryAction());
		
		if (currentType != 0)
		{
			facultyMemberMenu.setEnabled(false);
		}
		
		aboutMenu = new JMenuItem("About");
		aboutMenu.setAction(new AboutAction());
		
		configMenu = new JMenu("Configure");
		configMenu.add(budgetMenu);
		configMenu.add(genFundMenu);
		configMenu.add(profServiceMenu);
		configMenu.addSeparator();
		configMenu.add(facultyMemberMenu);
		configMenu.addSeparator();
		configMenu.add(positionMenu);
		configMenu.add(rankMenu);
		configMenu.add(salaryMenu);
		
		
		helpMenu = new JMenu("Help");
		helpMenu.add(aboutMenu);
		
		mainMenu = new JMenuBar();
		mainMenu.add(fileMenu);
		mainMenu.add(configMenu);
		mainMenu.add(helpMenu);
	}

	public void actionPerformed(ActionEvent e)
	{
		activityPanel.update();
		
		if (e.getSource() == fundBtn)
		{
			if(selectedPanel == 1)
			{
				return;
			}
			selectedPanel = 1;
			centerPanel.removeAll();
			centerPanel.revalidate();
			centerPanel.repaint();
			
			centerPanel.add(viewPanel, "1,1");
			centerPanel.revalidate();
			centerPanel.repaint();
		}
		else if (e.getSource() == facultyBtn)
		{
			if(selectedPanel == 2)
			{
				return;
			}
			selectedPanel = 2;
			centerPanel.removeAll();
			centerPanel.revalidate();
			centerPanel.repaint();
			
			centerPanel.add(facultyPanel, "1,1");
			centerPanel.revalidate();
			centerPanel.repaint();
		}
		
		else if (e.getSource() == activityBtn)
		{
			if (selectedPanel == 3)
			{
				return;
			}
			selectedPanel = 3;
			centerPanel.removeAll();
			centerPanel.revalidate();
			centerPanel.repaint();
			
			centerPanel.add(activityPanel, "1,1");
			centerPanel.revalidate();
			centerPanel.repaint();
		}
		else if (e.getSource() == loginBtn)
		{
			if (selectedPanel == 4)
			{
				return;
			}
			selectedPanel = 4;
			centerPanel.removeAll();
			centerPanel.revalidate();
			centerPanel.repaint();
			
			centerPanel.add(loginPanel, "1,1");
			centerPanel.revalidate();
			centerPanel.repaint();
		}
		else if (e.getSource() == extendedBtn)
		{
			if (selectedPanel == 5)
			{
				return;
			}
			selectedPanel = 5;
			centerPanel.removeAll();
			centerPanel.revalidate();
			centerPanel.repaint();
			
			centerPanel.add(extendedPanel, "1,1");
			centerPanel.revalidate();
			centerPanel.repaint();
		}
	}
	
	public void setSelectedView(int idx, int view)
	{
		if (view == 1)
		{
			viewPanel.setSelectedView(idx);
			
			extendedPanel.update();
			
			if (selectedPanel != 1)
			{
				fundBtn.doClick();
			}
		}
		else if (view == 2)
		{
			facultyPanel.update();
			
			if (selectedPanel != 2)
			{
				facultyBtn.doClick();
			}
		}
		else if (view == 3)
		{
			activityPanel.update();
			
			if (selectedPanel != 3)
			{
				activityBtn.doClick();
			}
		}
		else if (view == 4)
		{
			loginPanel.update();
			
			if (selectedPanel != 4)
			{
				loginBtn.doClick();
			}
		}
		else
		{
			extendedPanel.update();
			
			if (selectedPanel != 5)
			{
				extendedBtn.doClick();
			}
		}
	}
	
	public void fixActivity()
	{
		if (selectedPanel == 3)
		{
			activityPanel.update();
		}
	}
}
