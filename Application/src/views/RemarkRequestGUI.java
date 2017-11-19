package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controllers.ExtractData;


/*
 * Remark Request class
 */
public class RemarkRequestGUI extends JFrame{
	
	private File file;
	private String studentEmail;
	private ArrayList<String> instructorEmails;
	private JPanel contentPane;
	private JTextArea txtRemarkReason;
	
	public RemarkRequestGUI(File file, String studentEmail) {
		this.file = file;
		this.studentEmail = studentEmail;
		this.instructorEmails = ExtractData.getInstructorEmails();
		
		setResizable(true); // Temporarily until we add a scroll bar.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,500);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new GridBagLayout());		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.CENTER;
	    gbc.weighty = 1;
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    
		JLabel lblRemark = new JLabel("<html>Please explain the reason<br>for your remark request:</html>");
		lblRemark.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		contentPane.add(lblRemark, gbc);
		
		gbc.gridy++;
				
		txtRemarkReason = new JTextArea();
		txtRemarkReason.setFont(new Font("Segoe/ UI", Font.PLAIN, 15));
		txtRemarkReason.setLineWrap(true);
		txtRemarkReason.setWrapStyleWord(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	    txtRemarkReason.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    
		JScrollPane scroll = new JScrollPane(txtRemarkReason, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(300,300));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.add(scroll, gbc);
		
		gbc.gridy++;

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2, 15, 0));
		buttonPanel.setBackground(Color.WHITE);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCancel.setBounds(640, 26, 100, 35);
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}	
		});
		buttonPanel.add(btnCancel,gbc);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSubmit.setBounds(640, 26, 100, 35);
		btnSubmit.setBackground(new Color(51, 204, 153));
		btnSubmit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Send email to instructors.
				sendRemarkRequest();
				JOptionPane.showMessageDialog(RemarkRequestGUI.this, "Your remark request has been sent.");
				dispose();
			}	
		});
		buttonPanel.add(btnSubmit,gbc);
		
		contentPane.add(buttonPanel, gbc);
		
		
				
		add(contentPane);
		
	}
	
	/**
	 * Getter for the student's email address.
	 * @return String student's email
	 */
	public String getStudentEmail() {
		return this.studentEmail;
	}	
	
	/**
	 * Returns text in the TextArea txtRemarkReason.
	 * @return String text
	 */
	private String getRemarkReasonString() {
		return txtRemarkReason.getText().toString();
	}
	
	/**
	 * Returns the String representation of the subject header
	 * of the email being sent.
	 * @return String subject header
	 */
	private String formatSubject() {
		String studentId = ExtractData.getStudentID(this.studentEmail);
		String fileName = this.file.getName();
		String subject = "Remark Request from student " +  studentId + " for " + 
		fileName.substring(0, fileName.indexOf("."));
		
		return subject;
	}
	
	/**
	 * Returns a string representation of the message in the email
	 * that will be sent.
	 * @return String message
	 */
	private String formatEmail() {
		String studentName = ExtractData.getFirstName(this.studentEmail);
		String studentId = ExtractData.getStudentID(this.studentEmail);
		String fileName = this.file.getName();
		ArrayList<ArrayList<String>> qData = ExtractData.getAssignmentQData(this.file);
		ArrayList<String> questions = qData.get(1);
		ArrayList<String> solutions = qData.get(2);
		HashMap<String,String> submission = ExtractData.getSubmittedAnswers(fileName, studentId);
		String message = "Reason for remark:\n\n" + getRemarkReasonString() + "\n\n"
				+ "============== Assignment Details ================"
				+ "\n\n";
		
		
		message += fileName.substring(0, fileName.indexOf(".")) + "\n\n"
				+ "Student Number: " + studentId + "\n"
						+ "Name: " + studentName + "\n\n";
		
		for(int i = 0; i < questions.size(); i++) {
			message += questions.get(i) + "[Solution: " + solutions.get(i) + "] " + "----- answered: " + submission.get(String.valueOf(i + 1)) + "\n";
			
		}
		
		return message;
		
	}
	
	/**
	 * Sends an email to every registered instructor
	 * via Gmail SMTP server, TLS connection.
	 * Idea and parts of code were taken from citation found below.
	 */
	public void sendRemarkRequest() {
		
		/***************************************************************** 
		 *  Author: mkyoung (modified by Julian Barker)
		 *	Title: JavaMail API – Sending email via Gmail SMTP example
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
			
			for(String email: this.instructorEmails) {
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(email));
				message.setSubject(formatSubject());
				message.setText(formatEmail());
				Transport.send(message);
			}
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
