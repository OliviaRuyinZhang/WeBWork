package views;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controllers.Authenticator;
import models.Problem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class LoginRegisterGUI extends JFrame {
	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JCheckBox chckbxInstructor;
	
	/**
	 * Clears all text fields.
	 */
	public void clearTextFields(){
		emailField.setText("");
		passwordField.setText("");
		chckbxInstructor.setSelected(false);
	}

	/**
	 * @return: String in email text box
	 */
	public String getEmail(){
		return emailField.getText();
	}

	/**
	 * @return: String in Password text box
	 */
	public String getPassword(){
		return new String(passwordField.getPassword());
	}
	
	/**
	 * @return: true if user checked instructor box
	 */
	public boolean getIsInstructor(){
		return chckbxInstructor.isSelected();
	}

	/**
	 * Create the frame.
	 */
	public LoginRegisterGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);
		
		JLabel lblWelcomeTo = new JLabel("Welcome to");
		lblWelcomeTo.setFont(new Font("Segoe UI Light", Font.PLAIN, 34));
		lblWelcomeTo.setBounds(365, 73, 171, 56);
		lblWelcomeTo.setSize(lblWelcomeTo.getPreferredSize());
		contentPane.add(lblWelcomeTo);
		
		JLabel lblWebwork = new JLabel("WeBWorK");
		lblWebwork.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblWebwork.setBounds(337, 115, 227, 56);
		lblWebwork.setSize(lblWebwork.getPreferredSize());
		contentPane.add(lblWebwork);
		
		JLabel lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblEmailAddress.setBounds(306, 267, 138, 16);
		contentPane.add(lblEmailAddress);
		
		emailField = new JTextField();
		emailField.setBounds(306, 294, 287, 35);
		contentPane.add(emailField);
		emailField.setColumns(10);
		emailField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		emailField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		emailField.setUI(new JTextFieldHintUI("student@mail.utoronto.ca", Color.gray)); 
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblPassword.setBounds(306, 350, 138, 16);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(306, 377, 287, 35);
		passwordField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			btnLogin.setBorderPainted(false);
		}
		btnLogin.setFocusPainted(false);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//This checks if authentication is right as well as if user is an instructor
				if((Authenticator.authenticate(getEmail(), getPassword())) && (Authenticator.isInstructor(getEmail()))){
					JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Login successful! Welcome instructor.");
					//Creates an InstructorListingGUI
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								InstructorListingGUI frame = new InstructorListingGUI();
								frame.setVisible(true);
								setVisible(false);
								dispose(); // Destroy the JFrame object
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				//If not an instructor
				else if((Authenticator.authenticate(getEmail(), getPassword())) && (Authenticator.isInstructor(getEmail()) == false)){
					JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Login successful! Welcome student.");
				}else{
					JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Invalid email address or password!");
				}
				clearTextFields();
			}
		});
		btnLogin.setBounds(329, 465, 115, 32);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			btnRegister.setBorderPainted(false);
		}
		btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnRegister.setFocusPainted(false);
		btnRegister.setBackground(Color.LIGHT_GRAY);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validRegister = Authenticator.register(getIsInstructor(), getEmail(), getPassword());
				if(validRegister){
					JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Registration successful!");
				}else{
					JOptionPane.showMessageDialog(LoginRegisterGUI.this, "Something went wrong!");
				}
				clearTextFields();
			}
		});
		btnRegister.setBounds(454, 465, 115, 32);
		contentPane.add(btnRegister);
		
		chckbxInstructor = new JCheckBox("Instructor");
		chckbxInstructor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		chckbxInstructor.setBackground(Color.WHITE);
		chckbxInstructor.setBounds(395, 425, 130, 23);
		contentPane.add(chckbxInstructor);
	}
}
