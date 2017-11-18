package views;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controllers.Authenticator;
import models.Problem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class RegisterInstructorInfo extends JFrame {
	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JPasswordField registrationCodeField;
	private final String  accessCode = "abc";
	private boolean isInstructor;
	private String email;
	private String password;

	/**
	 * @param isInstructor
	 *            true if it's a instructor, false otherwise
	 * @param email
	 *            user's email address
	 * @param password
	 *            user's input password
	 */
	public RegisterInstructorInfo(boolean isInstructor, String email, String password) {
		
		this.isInstructor = isInstructor;
		this.email = email;
		this.password = password;
		
		// set up the frame
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 250);
		// set up the panel
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		Border border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);
		
		// set title
		JLabel title = new JLabel("Personal Information");
		title.setFont(new Font("Segoe UI", Font.BOLD, 14));
		title.getPreferredSize();
		title.setBounds(60, 30, 160, 25);
		contentPane.add(title);
		
		// set label as "first name"
		JLabel firstName = new JLabel("First Name");
		firstName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstName.setBounds(10, 70, 80, 25);
		contentPane.add(firstName);
		
		// set the input text box for the label "first name"
		firstNameField = new JTextField();
		firstNameField.setBounds(100, 70, 160, 25);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);
		firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstNameField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// the Jlabel "first name" will change color if the user input invalid content
				
		firstNameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				validateNameInput(firstName, firstNameField, "First Name");
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validateNameInput(firstName, firstNameField, "First Name");
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		// set the second label as "last name"
		JLabel lastName = new JLabel("Last Name");
		lastName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastName.setBounds(10, 100, 80, 25);
		contentPane.add(lastName);
		
		// set up the text box for label "last name"
		lastNameField = new JTextField();
		lastNameField.setBounds(100, 100, 160, 25);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastNameField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// the Jlabel "last name" will change color if the user input an invalid content
	    // i.e. the input type is number
		lastNameField.getDocument().addDocumentListener(new DocumentListener()
	    {
	      @Override
	      public void removeUpdate(DocumentEvent e)
	      {
	    	  	validateNameInput(lastName, lastNameField, "Last Name");
	      }

	      @Override
	      public void insertUpdate(DocumentEvent e)
	      {
	    	  		validateNameInput(lastName, lastNameField, "Last Name");	  	
	      }

	      @Override
	      public void changedUpdate(DocumentEvent e) {} // Not needed for plain-text fields

	  });
		
		// set label for passcode
		JLabel registrationCode = new JLabel("Passcode");
		registrationCode.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		registrationCode.setBounds(10, 130, 100, 25);
		contentPane.add(registrationCode);
		
		registrationCodeField = new JPasswordField();
		registrationCodeField.setColumns(10);
		registrationCodeField.setBounds(100, 130, 160, 25);
		registrationCodeField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(registrationCodeField);
		
		JButton closeButton = new JButton("close");
		closeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    dispose();
			  } 
			} );
		closeButton.setBounds(10, 160, 80, 25);
		contentPane.add(closeButton);
		
		JButton registerButton = new JButton("Submit");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerValidation();		
			}
		});
		registerButton.setBounds(180, 160, 80, 25);
		contentPane.add(registerButton);
		setVisible(true);
		
	}
	
	/**
	 * validate the user's registration information and create an account and a profile for the user if the user enter 
	 * valid first name, last name and correct passcode. Error message will show up on a popup window if registration failed
	 */
	public void registerValidation() {
		boolean typeCheck = checkNameInput(firstNameField.getText()) && checkNameInput(lastNameField.getText());
		boolean accessPermission = false;
		boolean validRegister = false;
		if (registrationCodeField .getText().equals(accessCode)) {
			accessPermission = true;
			if(typeCheck) {
				validRegister = Authenticator.register(isInstructor, email, password, firstNameField.getText(),
						lastNameField.getText());
			}	
			if (validRegister) {
				int input = JOptionPane.showOptionDialog(null, "Registration successful!", "Confirmation",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					dispose();
				}		
			} else {	
					if(!typeCheck) {
						int input = JOptionPane.showOptionDialog(null, "name has to be in letters!", "Error",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
						if (input == JOptionPane.OK_OPTION) {
							clearTextFields();
						}	
					}
					else {
						JOptionPane.showMessageDialog(RegisterInstructorInfo.this, "Something went wrong!");	
					}								
			}
		}else {
			if(!accessPermission) {
				int input = JOptionPane.showOptionDialog(null, "Access Denied!", "Error",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					clearTextFields();
				}
			}
		}

	}
	
	/**
	 * change the name label to red with a star at the corner when the user input a invalid name
	 * change it back to the original when the input is valid again
	 * @param label
	 * @param name
	 * @param labelText
	 */
	public void validateNameInput(JLabel label, JTextField name, String labelText) {
		String text = name.getText();
		boolean valid = checkNameInput(text);
		if (valid) {
			// studentID.setForeground(getBackground());
			label.setText(labelText);
			label.setForeground(Color.BLACK);
		} else {
			label.setForeground(Color.RED);
			label.setText(labelText + "*");
		}
	}
	
	
	/**
	 * @param name
	 * @return true when the string is alphabetic string, false otherwise
	 */
	public boolean checkNameInput(String name) {
		// set up regex pattern
		String namePattern = "^[a-zA-Z]+$";
		Pattern r = Pattern.compile(namePattern);
		Matcher m = r.matcher(name);

		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public void clearTextFields(){
		firstNameField.setText("");
		lastNameField.setText("");
		registrationCodeField.setText("");
	}
	


}

