package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import java.util.Date;

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


/*
 * Feedback Sending class
 */
public class feedbackGUI extends JFrame{
	
	private JPanel contentPane;
	private JTextArea txtfeedback;
	private JTextArea txtSubjectTitle;
	private Date date;
	
	public feedbackGUI() {
		
		setResizable(true);
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
	    
		JLabel lblRemark = new JLabel("<html>Feedback</html>");
		lblRemark.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		contentPane.add(lblRemark, gbc);
		
		gbc.gridy++;
		
	    txtSubjectTitle = new JTextArea();
	    txtSubjectTitle.setFont(new Font("Segoe/ UI", Font.PLAIN, 15));
	    txtSubjectTitle.setLineWrap(true);
	    txtSubjectTitle.setWrapStyleWord(true);
		Border titleBorder = BorderFactory.createLineBorder(Color.BLACK);
		txtSubjectTitle.setBorder(BorderFactory.createCompoundBorder(titleBorder,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		txtSubjectTitle.setUI(new JTextFieldHintUI("Subject Line", Color.gray)); 
		JScrollPane titleScroll = new JScrollPane(txtSubjectTitle, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		titleScroll.setPreferredSize(new Dimension(300,50));
		
		titleScroll.getHorizontalScrollBar().setUnitIncrement(16);
		contentPane.add(titleScroll, gbc);
		
		gbc.gridy++;
				
		txtfeedback = new JTextArea();
		txtfeedback.setFont(new Font("Segoe/ UI", Font.PLAIN, 15));
		txtfeedback.setLineWrap(true);
		txtfeedback.setWrapStyleWord(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		txtfeedback.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    
	    
		JScrollPane scroll = new JScrollPane(txtfeedback, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
				date = new Date();
				saveFeedback();
				JOptionPane.showMessageDialog(feedbackGUI.this, "Thanks for your feedback!");
				dispose();
			}	
		});
		buttonPanel.add(btnSubmit,gbc);
		
		contentPane.add(buttonPanel, gbc);
		
		
				
		add(contentPane);
		
	}
	
	/**
	 * @return String subjectLine
	 */
	private String getSubjectLine() {
		return txtSubjectTitle.getText().toString();
	}
	
	/**
	 * @return String feedback content
	 */
	private String getContent() {
		return txtfeedback.getText().toString();
	}
	
	/**
	 * @return String feedback's submission date
	 */
	private String getDate() {
		String pattern = "dd/MM/yyyy";
		String dateInString = new SimpleDateFormat(pattern).format(date);
		return dateInString;
	}
	
	
	/**
	 * write student feedback and submission date into a file
	 */
	private void saveFeedback() {
		try {
			String file = "studentFeedback.csv";
			File filePath = new File(file);
			

			String tempLine;
			StringBuilder sb = new StringBuilder();

			// if the answerSubmission csv file does not exist
			if (filePath.exists() == false) {
				FileWriter fw = new FileWriter(file);
				BufferedWriter bf = new BufferedWriter(fw);
				sb.append(getSubjectLine());
				sb.append(",");
				sb.append(getContent());
				sb.append(",");
				sb.append(getDate());
				sb.append("\n");
				bf.write(sb.toString());
				bf.close();
				fw.close();
			}

			// if the file exists
			else {
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);
				
				while ((tempLine = reader.readLine()) != null) {
					sb.append(tempLine);
					sb.append("\n");
				}
				sb.append(getSubjectLine());
				sb.append(",");
				sb.append(getContent());
				sb.append(",");
				sb.append(getDate());
				sb.append("\n");
				reader.close();
				FileWriter fw = new FileWriter(file);
				BufferedWriter bf = new BufferedWriter(fw);
				bf.write(sb.toString());
				bf.close();
				fw.close();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}