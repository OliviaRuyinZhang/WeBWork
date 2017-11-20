package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.awt.Dimension;
import javax.swing.JTextField;

/**
 * GUI class for instructors to adjust the final marks of student's 
 * assignment submissions.
 */
public class InstructorRemarkGUI extends JFrame {

	private JPanel contentPane;
	private JTextField studentIDField;
	private JLabel lblAssignment;
	private JTextField assignmentField;
	private JLabel lblFinalMark;
	private JTextField finalMarkField;

	/**
	 * Create the frame.
	 */
	public InstructorRemarkGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 389);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);
		
		JLabel lblRemarkAssignment = new JLabel("Remark Assignment");
		lblRemarkAssignment.setSize(new Dimension(493, 51));
		lblRemarkAssignment.setFont(new Font("Segoe UI Light", Font.PLAIN, 38));
		lblRemarkAssignment.setBounds(35, 38, 329, 51);
		lblRemarkAssignment.setSize(lblRemarkAssignment.getPreferredSize());
		contentPane.add(lblRemarkAssignment);
		
		JLabel lblStudentNumber = new JLabel("Student ID");
		lblStudentNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblStudentNumber.setBounds(106, 136, 78, 23);
		contentPane.add(lblStudentNumber);
		
		studentIDField = new JTextField();
		studentIDField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		studentIDField.setColumns(10);
		studentIDField.setBounds(96, 170, 98, 35);
		studentIDField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		studentIDField.setUI(new JTextFieldHintUI("1009857569", Color.gray));
		contentPane.add(studentIDField);		
		
		lblAssignment = new JLabel("Assignment #");
		lblAssignment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblAssignment.setBounds(263, 136, 98, 23);
		contentPane.add(lblAssignment);
		
		assignmentField = new JTextField();
		assignmentField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		assignmentField.setColumns(10);
		assignmentField.setBounds(291, 170, 33, 35);
		assignmentField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		assignmentField.setUI(new JTextFieldHintUI("1", Color.gray));
		contentPane.add(assignmentField);
		
		lblFinalMark = new JLabel("Final Mark");
		lblFinalMark.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblFinalMark.setBounds(412, 136, 98, 23);
		contentPane.add(lblFinalMark);
		
		finalMarkField = new JTextField();
		finalMarkField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		finalMarkField.setColumns(10);
		finalMarkField.setBounds(422, 170, 44, 35);
		finalMarkField.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		finalMarkField.setUI(new JTextFieldHintUI("95", Color.gray));
		contentPane.add(finalMarkField);
		
		JButton btnAdjustMark = new JButton("Adjust Mark");
		btnAdjustMark.setBounds(446, 285, 137, 35);
		btnAdjustMark.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnAdjustMark.setFocusPainted(false);
		btnAdjustMark.setBackground(Color.decode("#f1c40f"));
		btnAdjustMark.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (adjustMark(studentIDField.getText(), assignmentField.getText(), finalMarkField.getText())) {
					String assignment = "Assignment" + assignmentField.getText();
					sendRemarkResult(studentIDField.getText(), assignment,  finalMarkField.getText());
					JOptionPane.showMessageDialog(InstructorRemarkGUI.this, "Grade adjusted successfully!");
					setVisible(false);
					dispose();
				} else {
					JOptionPane.showMessageDialog(InstructorRemarkGUI.this, "Something went wrong! Grade was not adjusted.");
				}
			}
		});
		contentPane.add(btnAdjustMark);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnCancel.setFocusPainted(false);
		btnCancel.setBackground(Color.decode("#B2BABB"));
		btnCancel.setBounds(323, 285, 108, 35);
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		contentPane.add(btnCancel);
		
	}
	
	/**
	 * Adjusts the student's final mark if the assignment exists, the submission file for that
	 * assignment exists, and if the student has a submission for that assignment.
	 * @param studentID The student's ID
	 * @param assignmentNumber The assignment that is to be remarked
	 * @param finalMark The new final mark that is to be updated
	 * @return true if the mark was adjusted, false otherwise
	 */
	private boolean adjustMark(String studentID, String assignmentNumber, String finalMark) {
		// Check to see if assignment actually exists
		File tempFile = new File("Assignment" + assignmentNumber + ".csv");
		if (!tempFile.exists()) {
			return false;
		}
		
		// Check to see if submissions file exists
		tempFile = new File("Assignment" + assignmentNumber + "Submission.csv");
		if (!tempFile.exists()) {
			return false;
		}
		
		String tempLine;
		StringBuilder sb = new StringBuilder();
		
		try {
			FileReader fr = new FileReader("Assignment" + assignmentNumber + "Submission.csv");
			BufferedReader reader = new BufferedReader(fr);
			boolean found = false;
			
			// Go through each line in the submissions file
			while ((tempLine = reader.readLine()) != null) {
				String[] individualAnswerInfo = tempLine.split(",");
				// If the studentID exists in the file.
				if (individualAnswerInfo[0].equals(studentID)) {
					found = true;
					// Update the student's final mark
					for (int i = 0; i < individualAnswerInfo.length; i++) {
						if (i < individualAnswerInfo.length - 1) {
							sb.append(individualAnswerInfo[i]);
							sb.append(",");
						} else if (i == individualAnswerInfo.length - 1) {
							sb.append(finalMark);
							sb.append("\n");
						}
					}
				// Continue to record answers for other students
				} else {
					sb.append(tempLine + "\n");
				}
			}
			
			reader.close();
			
			// If the student was found, write the new contents
			if (found) {
				FileWriter fw = new FileWriter("Assignment" + assignmentNumber + "Submission.csv");
				BufferedWriter bf = new BufferedWriter(fw);
				bf.write(sb.toString());
				bf.close();
				return true;
			} else {
				return false;
			}
				
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Returns the String representation of the subject header
	 * of the email being sent.
	 * @return String subject header
	 */
	private String formatSubject(String studentEmail, String Assignment) {
		String studentId = ExtractData.getStudentID(studentEmail);
		//String fileName = this.file.getName();
		String subject = "Grade Adjusted for " +  studentId + " for " + Assignment;
		//fileName.substring(0, fileName.indexOf("."));
		
		return subject;
	}
	
	/**
	 * Returns a string representation of the message in the email
	 * that will be sent.
	 * @return String message
	 */
	private String formatEmail(String studentID, String Assignment, String finalGrade) {
		String studentName = ExtractData.getFirstName(ExtractData.getStudentEmail(studentID));
		String message = "Remark Result:\n\n";
		message += Assignment + "\n";
		message += "Student Name: " + studentName + "\n";
		message += "Student ID: " + studentID + '\n';
		message += "Final Grade: " +  finalGrade;
		
		return message;
		
	}
	
	/**
	 * Sends an email to student regarding the remark result
	 * via Gmail SMTP server, TLS connection.
	 * Idea and parts of code were taken from citation found below.
	 */
	public void sendRemarkResult(String studentID, String Assignment, String finalGrade) {
		
		/***************************************************************** 
		 *  Author: mkyoung (modified by Julian Barker and Ruyin Zhang)
		 *	Title: JavaMail API � Sending email via Gmail SMTP example
		 *	Date: 2017-11-18
		 *	Type: source code
		 *	https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
		 ****************************************************************/
		
		final String username = "webworkremarks@gmail.com";
		final String password = "workremarks";

		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			String studentEmail = ExtractData.getStudentEmail(studentID);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentEmail));
			message.setSubject(formatSubject(studentEmail, Assignment));
			message.setText(formatEmail(studentID, Assignment, finalGrade));
			Transport.send(message);
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
}
