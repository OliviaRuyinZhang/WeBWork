package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controllers.RegisterValidation;

public abstract class RegisterGUI extends JFrame implements RegisterValidation {
	public JPanel contentPane;
	public JTextField firstNameField;
	public JTextField lastNameField;
	public Border border;
	public String email;
	public String password;

	public RegisterGUI(String email, String password) {
		this.email  = email;
		this.password = password;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 280);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);

		JLabel title = new JLabel("Personal Information");
		title.setFont(new Font("Segoe UI", Font.BOLD, 15));
		title.getPreferredSize();
		// title.setLocation(contentPane.getWidth()/2 - title.getWidth()/2, 30);
		title.setBounds((300) / 2 - 100, 30, 200, 25);
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
		firstNameField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

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
			}
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
		lastNameField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

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
			}
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
	}
	
	public void registerValidation(boolean validRegister, boolean typeCheck, boolean isStudent) {
		
		if (validRegister) {
			int input = JOptionPane.showOptionDialog(null, "Registration successful!", "Confirmation",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if (input == JOptionPane.OK_OPTION) {
				dispose();
			}
		} else {
			if (!typeCheck) {
				int input = JOptionPane.showOptionDialog(null, "Name has to be in letters!", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					clearTextFields();
				}
			} else {
				if(!isStudent) {
					JOptionPane.showMessageDialog(null, "This email has already been registered.");
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
		boolean valid = isAlphabetical(text);
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
	public boolean isAlphabetical(String name) {
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

}
