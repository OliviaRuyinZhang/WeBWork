package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controllers.ExtractData;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class RemarkRequestGUI extends JFrame{

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemarkRequestGUI frame = new RemarkRequestGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private File file;
	private String studentEmail;
	private JPanel contentPane;
	private JTextArea txtRemarkReason;
	
	public RemarkRequestGUI(File file, String studentEmail) {
		this.file = file;
		this.studentEmail = studentEmail;
		
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
	    
		JLabel lblRemark = new JLabel("<html>Please explain why you <br>would like a remark request:");
		lblRemark.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		contentPane.add(lblRemark, gbc);
		
		gbc.gridy++;
				
		txtRemarkReason = new JTextArea();
		txtRemarkReason.setFont(new Font("Segoe/ UI", Font.PLAIN, 15));
		txtRemarkReason.setLineWrap(true);
		//txtRemarkReason.setWrapStyleWord(true);
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
			}	
		});
		buttonPanel.add(btnSubmit,gbc);
		
		contentPane.add(buttonPanel, gbc);
		
		
				
		add(contentPane);
		
	}
	
	private String getRemarkReasonString() {
		return txtRemarkReason.getText().toString();
	}
	
	private void formatEmail() {
		String studentId = ExtractData.getStudentID(this.studentEmail);
		
		ArrayList<ArrayList<String>> questions = ExtractData.getAssignmentQData(this.file);
		HashMap<String,String> submission = ExtractData.getAssignmentSubmissionDetails(this.file.getName(), studentId);
		StringBuilder sb = new StringBuilder();
		
	}
	
	public void sendRemarkRequest() {
		// Recipient's email ID needs to be mentioned.
		final String to = "julian.b@live.ca";
		
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
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject("Remark Request for " + "" + "from " + "");
			
			message.setText(getRemarkReasonString() + "\n\n"
					+ "============== Assignment Details ================"
					+ "\n\n");

			
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
