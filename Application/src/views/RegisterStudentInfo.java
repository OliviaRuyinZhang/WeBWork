package views;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.xml.internal.ws.util.StringUtils;

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
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class RegisterStudentInfo extends JFrame {
	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField studentIDField;
	private String pattern = "\\d+";
	private JLabel studentID;
	private boolean isInstructor;
	private String email;
	private String password;

	public RegisterStudentInfo(boolean isInstructor, String email, String password) {
		
		this.isInstructor = isInstructor;
		this.email = email;
		this.password = password;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 250);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		Border border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);

		JLabel title = new JLabel("Personal Information");
		title.setFont(new Font("Segoe UI", Font.BOLD, 14));
		title.getPreferredSize();
		title.setBounds(60, 30, 160, 25);
		contentPane.add(title);

		JLabel firstName = new JLabel("First Name");
		firstName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstName.setBounds(10, 70, 80, 25);
		contentPane.add(firstName);

		firstNameField = new JTextField();
		firstNameField.setBounds(100, 70, 160, 25);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);
		firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstNameField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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
		lastName.setBounds(10, 100, 80, 25);
		contentPane.add(lastName);

		lastNameField = new JTextField();
		lastNameField.setBounds(100, 100, 160, 25);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastNameField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

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
		studentID.setBounds(10, 130, 100, 25);
		contentPane.add(studentID);

		studentIDField = new JTextField();
		studentIDField.setColumns(10);
		studentIDField.setBounds(100, 130, 160, 25);
		studentIDField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(studentIDField);

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
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeButton.setBounds(10, 190, 80, 25);
		contentPane.add(closeButton);

		JButton registerButton = new JButton("Submit");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		registerButton.setBounds(180, 190, 80, 25);
		contentPane.add(registerButton);
		setVisible(true);

	}
	
	public void registerValidation() {
		boolean typeCheck = checkNameInput(firstNameField.getText()) && checkNameInput(lastNameField.getText())
				&& checkIDInput(studentIDField.getText());
		boolean validRegister = false;
		if (typeCheck) {
			validRegister = Authenticator.register(isInstructor, email, password, firstNameField.getText(),
					lastNameField.getText(), studentIDField.getText());
		} else {
			if ((checkNameInput(firstNameField.getText()) == false
					|| checkNameInput(lastNameField.getText()) == false) && checkIDInput(studentIDField.getText()) == false) {
				int input = JOptionPane.showOptionDialog(null, "Name has to be in letters,\n StudentID has to be integers!", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					clearTextFields();
				}
			} else {
				if (checkIDInput(studentIDField.getText()) == false) {
					int input = JOptionPane.showOptionDialog(null, "StudentID has to be integer numbers!",
							"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,
							null);
					if (input == JOptionPane.OK_OPTION) {
						clearTextFields();
					}
				} else {
						int input = JOptionPane.showOptionDialog(null, "Name has to be in letters!", "Error",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
						if (input == JOptionPane.OK_OPTION) {
							clearTextFields();
						}
					}

				}
			}
		
		if (validRegister) {
			int input = JOptionPane.showOptionDialog(null, "Registration successful!", "Confirmation",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if (input == JOptionPane.OK_OPTION) {
				dispose();
			}
		} else {
			if (typeCheck) {
				JOptionPane.showMessageDialog(RegisterStudentInfo.this, "Something went wrong!");
			}
		}
	}

	public void validateStudentIDInput() {
		String text = studentIDField.getText();
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);

		if (m.matches()) {
			// studentID.setForeground(getBackground());
			studentID.setText("StudentID");
			studentID.setForeground(Color.BLACK);
		} else {
			studentID.setForeground(Color.RED);
			studentID.setText("StudentID*");
		}
	}

	public void validateNameInput(JLabel label, JTextField name, String labelText) {
		String namePattern = "^[a-zA-Z]+$";
		String text = name.getText();
		Pattern r = Pattern.compile(namePattern);
		Matcher m = r.matcher(text);

		if (m.matches()) {
			// studentID.setForeground(getBackground());
			label.setText(labelText);
			label.setForeground(Color.BLACK);
		} else {
			label.setForeground(Color.RED);
			label.setText(labelText + "*");
		}
	}

	public boolean checkNameInput(String name) {
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
		studentIDField.setText("");
	}

	public boolean checkIDInput(String studentID) {

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(studentID);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
