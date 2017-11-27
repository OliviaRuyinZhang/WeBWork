package views;

import javax.swing.BorderFactory;

import controllers.Authenticator;


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

import java.awt.Color;

import javax.swing.JPasswordField;
import javax.swing.JButton;

public class RegisterInstructorInfo extends RegisterGUI {
	private JLabel inviteCodeLabel;
	private JPasswordField inviteCodeField;
	private String inviteCode;

	private boolean firstTimeSetup;

	/**
	 * Class for student's to register an account into WebWork
	 * 
	 * @param email
	 *            user's email address
	 * @param password
	 *            user's input password
	 */
	public RegisterInstructorInfo(String email, String password) {
		super(email, password);
		this.firstTimeSetup = isFirstAdmin();
		
		
		// Change gui according to firstTimeSetup
		int xPosition = 0;
		if (firstTimeSetup) {
			xPosition = 20;
		}

		setSize(300 + xPosition, 280);
		firstNameField.setBounds(100 + xPosition, 70, 170, 30);
		lastNameField.setBounds(100 + xPosition, 110, 170, 30);

		// Only on first time setup, allow instructor to set an invitecode
		if (firstTimeSetup) {
			inviteCodeLabel = new JLabel("Set Passcode");
		} else {
			inviteCodeLabel = new JLabel("Passcode");
		}

		inviteCodeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		inviteCodeLabel.setBounds(10, 150, 100 + xPosition, 30);
		contentPane.add(inviteCodeLabel);

		inviteCodeField = new JPasswordField();
		inviteCodeField.setColumns(10);
		inviteCodeField.setBounds(100 + xPosition, 150, 170, 30);
		inviteCodeField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(inviteCodeField);

		JButton registerButton = new JButton("Submit");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if the instructor is admin Instructor and try to register for the first time,
				// store the passcode he set
				if (firstTimeSetup) {
					inviteCode = new String(inviteCodeField.getPassword());
					writeInviteCode(inviteCode);
					firstTimeSetup = false;
				} else {
					// if the instructor is not admin then retrieve the access code
					try {
						readInviteCode();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
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
	 * Writes inviteCode string to inviteCode.csv
	 * 
	 * @param inviteCode
	 *            to be written
	 */
	public void writeInviteCode(String inviteCode) {
		try {
			FileWriter fw = new FileWriter("inviteCode.csv");
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(inviteCode);
			bf.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function retrieve the inviteCode from inviteCode.csv file
	 * 
	 * @throws IOException
	 */
	public void readInviteCode() throws IOException {
		File filepath = new File("inviteCode.csv");
		if (filepath.exists()) {
			try {
				FileReader fr = new FileReader("inviteCode.csv");
				BufferedReader reader = new BufferedReader(fr);
				inviteCode = reader.readLine();
				reader.close();
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Returns true, if there is no existing admins in user.csv
	 * 
	 * @return true when there is no other admins in user.csv
	 */
	public boolean isFirstAdmin() {
		boolean r = true;
		// If users.csv exists, check everyline for a pre-existing
		// admin
		if (new File("users.csv").exists()) {
			try {
				FileReader fr = new FileReader("users.csv");
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				while ((line = br.readLine()) != null) {
					String[] user_info = line.split(",");
					if (user_info[0].equalsIgnoreCase("true")) {
						r = false;
						break;
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * validate the user's registration information and create an account and a
	 * profile for the user if the user enter valid first name, last name and
	 * correct passcode. Error message will show up on a popup window if
	 * registration failed
	 */
	public void register() {	
		boolean typeCheck = isAlphabetical(firstNameField.getText()) && isAlphabetical(lastNameField.getText());
		boolean validRegister = false;
		boolean accessPermission = false;
		
		if (typeCheck) {
			validRegister = Authenticator.register(true, email, password, firstNameField.getText(),
					lastNameField.getText());
		}
		
		String inviteCodeLabel = new String(inviteCodeField.getPassword());
		if (inviteCodeLabel.equals(inviteCode)) {
			accessPermission = true;
			registerValidation(validRegister, typeCheck, false);
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
	 * Clears all text fields.
	 */
	public void clearTextFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		inviteCodeField.setText("");
	}
}