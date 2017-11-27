package views;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controllers.Authenticator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RegisterStudentInfo extends JFrame {
	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField studentIDField;
	private String pattern = "\\d+";
	private JLabel studentID;
	private String email;
	private String password;

	/**
         * Class for student's to register an account into WebWork
	 * @param email user's email address
	 * @param password user's input password
	 */
	public RegisterStudentInfo(String email, String password) {

		this.email = email;
		this.password = password;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 280);
		contentPane = new JPanel();
		
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		Border border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);

		JLabel title = new JLabel("Personal Information");
		title.setFont(new Font("Segoe UI", Font.BOLD, 15));
		title.getPreferredSize();
		title.setBounds((300/2) - 100, 30, 200, 25);
		contentPane.add(title);

		JLabel firstName = new JLabel("First Name");
		firstName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstName.setBounds(10, 70, 90, 30);
		contentPane.add(firstName);

		firstNameField = new JTextField();
		firstNameField.setBounds(100, 70, 170, 30);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);
		firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstNameField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

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
			} // Not needed for plain-text fields

		});

		JLabel lastName = new JLabel("Last Name");
		lastName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastName.setBounds(10, 110, 90, 30);
		contentPane.add(lastName);

		lastNameField = new JTextField();
		lastNameField.setBounds(100, 110, 170, 30);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastNameField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// the Jlabel "last name" will change color if the user input an invalid content
		// i.e. the input type is number
		lastNameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				validateNameInput(lastName, lastNameField, "Last Name");
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validateNameInput(lastName, lastNameField, "Last Name");
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			} // Not needed for plain-text fields

		});

		studentID = new JLabel("StudentID");
		studentID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		studentID.setBounds(10, 150, 110, 30);
		contentPane.add(studentID);

		studentIDField = new JTextField();
		studentIDField.setColumns(10);
		studentIDField.setBounds(100, 150, 170, 30);
		studentIDField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(studentIDField);

		// the Jlabel "StudentID" will change color if the user input an invalid content
		// the color will change it back if the user input a valid content again
		studentIDField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				validateStudentIDInput();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validateStudentIDInput();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			} // Not needed for plain-text fields

		});

		JButton closeButton = new JButton("Close");
		closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		closeButton.setBackground(Color.LIGHT_GRAY);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeButton.setBounds(10, 205, 100, 25);
		contentPane.add(closeButton);

		JButton registerButton = new JButton("Submit");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
	 * validate the user's registration information and create an account and a profile for the user if the user enter 
	 * valid first name, last name and passcode. Error message will show up on a popup window if registration failed
	 */
	public void registerValidation() {
		// check whether the type of the user's input information is valid
		boolean typeCheck = checkNameInput(firstNameField.getText()) && checkNameInput(lastNameField.getText())
				&& checkIDInput(studentIDField.getText());
		// the variable validRegister will be set to true when we successfully create an account for user
		boolean validRegister = false;
		// if the type of input information is valid, create an account for student 
		if (typeCheck) {
			// create an account
			validRegister = Authenticator.register(false, email, password, firstNameField.getText(),
					lastNameField.getText(), studentIDField.getText());
			// if the account is successfully created, notify user
			if (validRegister) {
				int input = JOptionPane.showOptionDialog(null, "Registration successful!", "Confirmation",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				// close the "personal information" frame for user when user click the "OK" button on the popup window
				if (input == JOptionPane.OK_OPTION) {
					dispose();
				}
			} else {
				// error message if we could not create a account for user or the account already exist 
				if (typeCheck) {
					JOptionPane.showMessageDialog(RegisterStudentInfo.this, "Something went wrong!");
				}
			}
		} else {
			// when the input first/last name and the student id are not valid, show error message
			if ((checkNameInput(firstNameField.getText()) == false || checkNameInput(lastNameField.getText()) == false)
					&& checkIDInput(studentIDField.getText()) == false) {
				int input = JOptionPane.showOptionDialog(null,
						"Name has to be in letters,\n StudentID has to be integers!", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				// clear all input text for user when the user click "OK" button on the popup error window
				if (input == JOptionPane.OK_OPTION) {
					clearTextFields();
				}
			} else {
				// when the input student id is invalid, show error message
				if (checkIDInput(studentIDField.getText()) == false) {
					int input = JOptionPane.showOptionDialog(null, "StudentID has to be integer numbers!", "Error",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if (input == JOptionPane.OK_OPTION) {
						// clear all input text for user when the user click "OK" button on the popup error window
						clearTextFields();
					}
				} else {
					// when the input first/last name is invalid, show error message
					int input = JOptionPane.showOptionDialog(null, "Name has to be in letters!", "Error",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if (input == JOptionPane.OK_OPTION) {
						clearTextFields();
					}
				}

			}
		}

	}

	/**
	 * change the student id label to red with a star at the corner when the user input a invalid student id
	 * change it back to the original when the input is valid again
	 */
	public void validateStudentIDInput() {
		String text = studentIDField.getText();
		boolean valid = checkIDInput(text);

		if (valid) {
			// studentID.setForeground(getBackground());
			studentID.setText("StudentID");
			studentID.setForeground(Color.BLACK);
		} else {
			// set to red 
			studentID.setForeground(Color.RED);
			studentID.setText("StudentID*");
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

	
	public void clearTextFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		studentIDField.setText("");
	}

	/**
	 * @param studentID
	 * @return true when the input studentID is a numeric string, false otherwise
	 */
	public boolean checkIDInput(String studentID) {
		// set up regex pattern
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(studentID);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
