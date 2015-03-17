package view;

import java.awt.BorderLayout; 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.TrialConnection;

import org.jasypt.util.password.*;

public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JTextField currentPassword;
	private JTextField newPassword;
	private JTextField confirmPassword;
	private BasicPasswordEncryptor bpe = null;
	private TrialConnection tc = new TrialConnection();
	private String name = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword("admin");
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
	public ChangePassword(final String username) {
		setTitle("Change Account Password");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		bpe = new BasicPasswordEncryptor();
		name = username;
		
		JLabel lblCurrentPassword = new JLabel("Current Password: ");
		lblCurrentPassword.setBounds(65, 56, 110, 14);
		contentPane.add(lblCurrentPassword);
		
		JLabel lblNewPassword = new JLabel("New Password:");
		lblNewPassword.setBounds(82, 91, 91, 14);
		contentPane.add(lblNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm New Password:");
		lblConfirmPassword.setBounds(33, 128, 142, 14);
		contentPane.add(lblConfirmPassword);
		
		currentPassword = new JPasswordField();
		currentPassword.setBounds(195, 53, 182, 20);
		contentPane.add(currentPassword);
		currentPassword.setColumns(10);
		
		newPassword = new JPasswordField();
		newPassword.setBounds(195, 88, 182, 20);
		contentPane.add(newPassword);
		newPassword.setColumns(10);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setBounds(195, 125, 182, 20);
		contentPane.add(confirmPassword);
		confirmPassword.setColumns(10);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cp = currentPassword.getText();
				String np = newPassword.getText();
				String confirm = confirmPassword.getText();
				
				if(!np.equals(confirm) || cp.equals("") || np.equals("") || confirm.equals("")){
					JOptionPane.showMessageDialog(contentPane, "Invalid input");
					return;
				}
				
				if (!tc.checkPassword(name, cp, np))
				{
					JOptionPane.showMessageDialog(contentPane, "Incorrect password!");
					
					//insert new password into database
				}
				else
				{
					tc.createActivity(username, "Changed Password", "");
					JOptionPane.showMessageDialog(contentPane, "Password successfully changed!");
					dispose();
					return;
				}
			}
		});
		btnNewButton.setBounds(81, 194, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				return;
			}
		});
		btnNewButton_1.setBounds(260, 194, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}
