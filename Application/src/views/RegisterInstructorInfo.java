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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;



public class RegisterInstructorInfo extends JFrame {
	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JLabel inviteCodeLabel;
	private JPasswordField inviteCodeField;
	private String inviteCode;
	private String email;
	private String password;
	private boolean firstTimeSetup;

	/**
         * Class for student's to register an account into WebWork
	 * @param email
	 *            user's email address
	 * @param password
	 *            user's input password
	 */
	public RegisterInstructorInfo(String email, String password) {
            
		this.firstTimeSetup = isFirstAdmin();
		this.email = email;
		this.password = password;
		
		// Change gui according to firstTimeSetup
		int xPosition = 0;
		if(firstTimeSetup) {
			xPosition = 20;
		}
                
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300 + xPosition, 280);
                
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
		//title.setLocation(contentPane.getWidth()/2 - title.getWidth()/2, 30);
		title.setBounds((300 + xPosition)/2 - 100, 30, 200, 25);
		contentPane.add(title);
		
		JLabel firstName = new JLabel("First Name");
		firstName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		firstName.setBounds(10, 70, 90, 30);
		contentPane.add(firstName);
		
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
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lastName = new JLabel("Last Name");
		lastName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastName.setBounds(10, 110, 90, 30);
		contentPane.add(lastName);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(100 + xPosition, 110, 170, 30);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lastNameField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// the Jlabel "last name" will change color if the user input an invalid content
                // i.e. the input type is number
                lastNameField.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void removeUpdate(DocumentEvent e){
                                validateNameInput(lastName, lastNameField, "Last Name");
                        }

                        @Override
                        public void insertUpdate(DocumentEvent e){
                                validateNameInput(lastName, lastNameField, "Last Name");	  	
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {}
                });
		
		// Only on first time setup, allow instructor to set an invitecode
		if(firstTimeSetup) {
			inviteCodeLabel = new JLabel("Set Passcode");
		}else {
			inviteCodeLabel = new JLabel("Passcode");
		}
		
		inviteCodeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		inviteCodeLabel.setBounds(10, 150, 100 + xPosition, 30);
		contentPane.add(inviteCodeLabel);
		
		inviteCodeField = new JPasswordField();
		inviteCodeField.setColumns(10);
		inviteCodeField.setBounds(100 + xPosition, 150, 170, 30);
		inviteCodeField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		contentPane.add(inviteCodeField);
		
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
				if(firstTimeSetup) {				
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
	 * Writes inviteCode string to inviteCode.csv
	 * @param inviteCode to be written
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
	public void registerValidation() {
		boolean typeCheck = isAlphabetical(firstNameField.getText()) && isAlphabetical(lastNameField.getText());
		boolean accessPermission = false;
		boolean validRegister = false;
		String inviteCodeLabel = new String(inviteCodeField.getPassword());
		if (inviteCodeLabel.equals(inviteCode)) {
			accessPermission = true;
			if (typeCheck) {
				validRegister = Authenticator.register(true, email, password, firstNameField.getText(),
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
         * Changes Jlabel label to a red foreground with an asterix symbol iff 
         * JTextField name contains a non-alphabetical name. If JTextField name 
         * is alphabetical, change Jlabel label to a black foreground with standard text.
	 * 
	 * @param label
	 * @param name
	 * @param labelText
	 */
	public void validateNameInput(JLabel label, JTextField name, String labelText) {
                // Check if JTextField name is alphabetical
		if (isAlphabetical(name.getText())) {
			// If so, label behaves normally
			label.setText(labelText);
			label.setForeground(Color.BLACK);
		} else {
                        // If not, label turns red, add * symbol
			label.setText(labelText + "*");
			label.setForeground(Color.RED);
		}
	}

	/**
         * Returns true if s is an alphabetical string.
         * 
	 * @param s is a string that will be checked for non-alphabetical characters
	 * @return true if s only contains characters from alphabet
	 */
	public boolean isAlphabetical(String s) {
		// Setup RegEx
		String namePattern = "^[a-zA-Z]+$";
		Pattern r = Pattern.compile(namePattern);
                
                // Check if name matches RegEx
		Matcher m = r.matcher(s);
		if(m.matches()) {
			return true;
		} else {
			return false;
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
