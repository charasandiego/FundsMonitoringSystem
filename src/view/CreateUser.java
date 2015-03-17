package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.TrialConnection;

import org.jasypt.util.password.*;

public class CreateUser extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private JPasswordField passField1;
	private JPasswordField passField2;
	private String usertype = "";
	private TrialConnection tc = null;
	private BasicPasswordEncryptor bpe = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUser frame = new CreateUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateUser()
	{
		setTitle("Create New User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tc = new TrialConnection();
		bpe = new BasicPasswordEncryptor();
		
		userField = new JTextField();
		userField.setBounds(162, 23, 183, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		
		passField1 = new JPasswordField();
		passField1.setBounds(162, 54, 183, 20);
		contentPane.add(passField1);
		passField1.setColumns(10);
		
		passField2 = new JPasswordField();
		passField2.setBounds(162, 85, 183, 20);
		contentPane.add(passField2);
		passField2.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(84, 26, 68, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(84, 57, 68, 14);
		contentPane.add(lblPassword);
		
		JLabel lblPassword_1 = new JLabel("Confirm Password:");
		lblPassword_1.setBounds(38, 88, 123, 14);
		contentPane.add(lblPassword_1);
		
		JLabel lblUserType = new JLabel("User Type:");
		lblUserType.setBounds(82, 125, 68, 14);
		contentPane.add(lblUserType);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String un = userField.getText();
				String pw1 = passField1.getText();
				String pw2 = passField2.getText();
				if(!pw1.equals(pw2) || un.equals("") || pw1.equals("") || usertype.equals("")){
					JOptionPane.showMessageDialog(contentPane, "Invalid input");
					return;
				}
				
				String encryptedPassword = bpe.encryptPassword(pw1);
				System.out.println(encryptedPassword);
				
				if (!tc.createUser(un, encryptedPassword, usertype))
				{
					JOptionPane.showMessageDialog(contentPane, "Username already exists");
				}
				else
				{
					tc.createActivity(Application.getMainFrame().getCurrentUser(), "Created New User", "");
					JOptionPane.showMessageDialog(contentPane, "Account successfully created");
					dispose();
					return;
				}
			}
		});
		btnConfirm.setBounds(86, 211, 89, 23);
		contentPane.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				return;
			}
		});
		btnCancel.setBounds(256, 211, 89, 23);
		contentPane.add(btnCancel);
		
		JRadioButton rdbtnAdminUser = new JRadioButton("Admin User");
		rdbtnAdminUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				usertype = "admin";
			}
		});
		rdbtnAdminUser.setBounds(162, 121, 123, 23);
		contentPane.add(rdbtnAdminUser);
		
		JRadioButton rdbtnNormalUser = new JRadioButton("Normal User");
		rdbtnNormalUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				usertype = "normal";
			}
		});
		rdbtnNormalUser.setBounds(162, 147, 123, 23);
		contentPane.add(rdbtnNormalUser);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnAdminUser);
		bg.add(rdbtnNormalUser);
	}
}
