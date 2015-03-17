package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.TrialConnection;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class LogInWindow extends JFrame {

	private BasicPasswordEncryptor bpe = new BasicPasswordEncryptor();
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JPanel panel2 = new JPanel();
	
	private final TrialConnection tc = new TrialConnection();
	private int type = 0;

	/**
	 * Create the application.
	 */
	public LogInWindow() 
	{
		super("Log-in to Funds Monitoring System");
		initialize();
	}
	
	public JFrame getFrame() 
	{
		return frame;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame.setBounds((getToolkit().getScreenSize().width/2)-255, (getToolkit().getScreenSize().height-600), 450, 145);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel.setBackground(new Color(240, 255, 255));
		
		panel.setBounds(0, 0, 444, 115);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton LogIn = new JButton("Log In");
		LogIn.setBounds(348, 56, 73, 23);
		panel.add(LogIn);
		
		final JPasswordField password = new JPasswordField();
		password.setBounds(293, 28, 128, 20);
		panel.add(password);
		password.setColumns(10);
		
		final JTextField username = new JTextField();
		username.setBounds(148, 28, 128, 20);
		panel.add(username);
		username.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(148, 11, 63, 14);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(293, 11, 82, 14);
		panel.add(lblPassword);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Eclipse\\Workspace\\FundsMonitoring\\src\\main\\lock.png"));
		lblNewLabel.setBounds(10, 0, 128, 106);
		panel.add(lblNewLabel);
		LogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pw = password.getText();
				final String un = username.getText();
				//System.out.println(pw);
				int check = tc.checkPassword(un, pw);
				if(check == 0)
				{
					type = tc.checkUserType(un);
				
					if(type == 0){
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									frame.dispose();
									//SuperAdmin sa = new SuperAdmin(tc, un);
									//sa.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
						});
					}
					else if(type == 1){
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									//Admin ad = new Admin(tc, un);
									//ad.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						frame.setVisible(true);
					}
					else if(type == 2){
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									//Normal no = new Normal(tc, un);
									//no.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						frame.setVisible(true);
					}
					username.setText("");
					password.setText("");
				}
				else if(check == 1){
					JOptionPane.showMessageDialog(frame, "Incorrect username and password");
					username.setText("");
					password.setText("");
					//frame.repaint();
					//return;
				}
				else if(check == 2){
					JOptionPane.showMessageDialog(frame, "Incorrect password");
					username.setText("");
					password.setText("");
					//frame.repaint();
					//return;
				}
				
				
			}
		});
	}
}
