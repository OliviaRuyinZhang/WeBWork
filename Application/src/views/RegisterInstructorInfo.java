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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;



public class RegisterInstructorInfo extends JFrame {
	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JLabel registrationCode;
	private JPasswordField registrationCodeField;
	private String accessCode;
	private boolean isInstructor;
	private String email;
	private String password;
	private boolean isAdminInstructor;

	/**
	 * @param isInstructor
	 *            true if it's a instructor, false otherwise
	 * @param email
	 *            user's email address
	 * @param password
	 *            user's input password
	 */
	public RegisterInstructorInfo(boolean isInstructor, String email, String password) {
		// check if the register user is adminInstructor or not
		isAdminProf();
		
		this.isInstructor = isInstructor;
		this.email = email;
		this.password = password;
		
		// change label position if the user is an adminProf, else stay the same
		int xPosition = 0;
		if(isAdminInstructor) {
			xPosition = 20;
		}
		// set up the frame
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300 + xPosition, 280);
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
		title.setFont(new Font("Segoe UI", Font.BOLD, 15));
		title.getPreferredSize();
		title.setBounds(70, 30, 200, 25);
		contentPane.add(title);
		
		// set label as "first name"
		JLabel firstName = new JLabel("First Name");
		firstName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstName.setBounds(10, 70, 90, 30);
		contentPane.add(firstName);
		
		// set the input text box for the label "first name"
		firstNameField = new JTextField();
		firstNameField.setBounds(100 + xPosition, 70, 170, 30);
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
		lastName.setBounds(10, 110, 90, 30);
		contentPane.add(lastName);
		
		// set up the text box for label "last name"
		lastNameField = new JTextField();
		lastNameField.setBounds(100 + xPosition, 110, 170, 30);
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
		if(isAdminInstructor) {
			registrationCode = new JLabel("Set Passcode");
		}else {
			registrationCode = new JLabel("Passcode");
		}
		
		registrationCode.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		// set bound 
		registrationCode.setBounds(10, 150, 100 + xPosition, 30);
		contentPane.add(registrationCode);
		
		registrationCodeField = new JPasswordField();
		registrationCodeField.setColumns(10);
		registrationCodeField.setBounds(100 + xPosition, 150, 170, 30);
		registrationCodeField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(registrationCodeField);
		
		JButton closeButton = new JButton("Close");
		closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		closeButton.setBackground(Color.LIGHT_GRAY);
		closeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    dispose();
			  } 
			} );
		closeButton.setBounds(10, 205, 100, 25);
		contentPane.add(closeButton);
		
		JButton registerButton = new JButton("Submit");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if the instructor is admin Instructor and try to register for the first time, store the passcode he set
				if(isAdminInstructor) {				
					accessCode = new String(registrationCodeField.getPassword());
					storeAccessCode(accessCode);
					isAdminInstructor = false;
				}
				else {
					// if the instructor is not admin then retrieve the access code
					try {
						retrieveAccessCode();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				registerValidation();		
			}
		});
		registerButton.setBounds(180, 205, 100, 25);
		registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		registerButton.setBackground(Color.LIGHT_GRAY);
		contentPane.add(registerButton);
		setVisible(true);
		
	}

	/**
	 * This function stores the accessCode that the adminInstructor set to accessCode.csv file 
	 * @param accessCode
	 */
	public void storeAccessCode(String accessCode) {
		try {
			FileWriter fw = new FileWriter("accessCode.csv");
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(accessCode);
			bf.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This function retrieve the accessCode from accessCode.csv file
	 * @throws IOException
	 */
	public void retrieveAccessCode() throws IOException {
		File filepath = new File("accessCode.csv");
		if (filepath.exists()) {
			try {
				FileReader fr = new FileReader("accessCode.csv");
				BufferedReader reader = new BufferedReader(fr);
				// set accessCode value
				accessCode = reader.readLine();
				reader.close();
				fr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * @return true when this is an adminProfessor, otherwise return false
	 */
	public void isAdminProf() {
		isAdminInstructor = true;
		File usersInfo = new File("users.csv");
		if (usersInfo.exists() == true) {
			try {
				FileReader fr = new FileReader("users.csv");
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				while ((line = br.readLine()) != null) {
					String[] user_info = line.split(",");
					if (user_info[0].equalsIgnoreCase("true")) {
						isAdminInstructor = false;
						break;
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * validate the user's registration information and create an account and a
	 * profile for the user if the user enter valid first name, last name and
	 * correct passcode. Error message will show up on a popup window if
	 * registration failed
	 */
	public void registerValidation() {
		boolean typeCheck = checkNameInput(firstNameField.getText()) && checkNameInput(lastNameField.getText());
		boolean accessPermission = false;
		boolean validRegister = false;
		String registrationCode = new String(registrationCodeField.getPassword());
		if (registrationCode.equals(accessCode)) {
			accessPermission = true;
			if (typeCheck) {
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
				if (!typeCheck) {
					int input = JOptionPane.showOptionDialog(null, "name has to be in letters!", "Error",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if (input == JOptionPane.OK_OPTION) {
						clearTextFields();
					}
				} else {
					JOptionPane.showMessageDialog(RegisterInstructorInfo.this, "Something went wrong!");
				}
			}
		} else {
			if (!accessPermission) {
				int input = JOptionPane.showOptionDialog(null, "Access Denied!", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					clearTextFields();
				}
			}
		}

	}

	/**
	 * change the name label to red with a star at the corner when the user input a
	 * invalid name change it back to the original when the input is valid again
	 * 
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

	public void clearTextFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		registrationCodeField.setText("");
	}

}
