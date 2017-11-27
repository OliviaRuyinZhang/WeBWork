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

public class RegisterStudentInfo extends RegisterGUI {
	private JTextField studentIDField;
	private String pattern = "\\d+";
	private JLabel studentID;

	/**
	 * Class for student's to register an account into WebWork
	 * 
	 * @param email
	 *            user's email address
	 * @param password
	 *            user's input password
	 */
	public RegisterStudentInfo(String email, String password) {
		super(email, password);

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

		JButton registerButton = new JButton("Submit");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		registerButton.setBounds(180, 205, 100, 25);
		registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		registerButton.setBackground(Color.LIGHT_GRAY);
		contentPane.add(registerButton);
		setVisible(true);
	}

	/**
	 * Validate the user's registration information and create an account and a
	 * profile for the user if the user enter valid first name, last name and
	 * passcode. Error message will show up on a popup window if registration failed
	 */
	public void register() {
		boolean isStudent = false;
		boolean typeCheck = isAlphabetical(firstNameField.getText()) && isAlphabetical(lastNameField.getText())
				&& checkIDInput(studentIDField.getText());
		boolean validRegister = false;
		if (typeCheck) {
			validRegister = Authenticator.register(false, email, password, firstNameField.getText(),
					lastNameField.getText(), studentIDField.getText());
		}

		if (checkIDInput(studentIDField.getText()) == false) {
			int input = JOptionPane.showOptionDialog(null, "StudentID has to be integer numbers!", "Error",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if (input == JOptionPane.OK_OPTION) {
				clearTextFields(studentIDField);
				clearTextFields(firstNameField);
				clearTextFields(lastNameField);
			}
			typeCheck = true;
			isStudent = true;
		}
		registerValidation(validRegister, typeCheck, isStudent);
	}

	/**
	 * change the student id label to red with a star at the corner when the user
	 * input a invalid student id change it back to the original when the input is
	 * valid again
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
	 * @param studentID
	 * @return true when the input studentID is a numeric string, false otherwise
	 */
	public boolean checkIDInput(String studentID) {
		// Set up regex pattern.
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(studentID);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

}