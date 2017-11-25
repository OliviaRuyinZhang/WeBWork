package views;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;
import models.Feedback;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

/**
 * Class to display all student feedback directed towards the course instructors.
 */
public class InstructorFeedbackGUI extends JFrame {

	private JPanel contentPane;
	protected static final int FEEDBACK_PANEL_GAP = 30;
	protected static final int FEEDBACK_PANEL_HEIGHT = 200;

	/**
	 * Create the frame.
	 */
	public InstructorFeedbackGUI() {
		// Create content pane and set up scroll
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Student Feedback");
		contentPane = new JPanel();
		JScrollPane scroll = new JScrollPane(contentPane, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(scroll);
		contentPane.setLayout(null);

		// Title
		JLabel lblName = new JLabel("Student Feedback");
		lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblName.setBounds(62, 45, 350, 70);
		lblName.setSize(lblName.getPreferredSize());
		this.contentPane.add(lblName);

		// Retrieve all feedback
		ArrayList<Feedback> allFeedback = ExtractData.getFeedback();
		int yPos = 150;

		// If there is feedback, show each feedback in its own box with the subject, date, and message
		if (allFeedback != null) {
			for (Feedback feedback : allFeedback) {
				JPanel feedbackPanel = new JPanel();
				feedbackPanel.setBackground(Color.decode("#F0F0F0"));
				feedbackPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				feedbackPanel.setBounds(62, yPos, 760, FEEDBACK_PANEL_HEIGHT);
				feedbackPanel.setLayout(null);

				JLabel lblSubject = new JLabel(feedback.getSubject()); // Strips the .csv extension.
				lblSubject.setBounds(50, 22, 190, 36);
				lblSubject.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
				lblSubject.setSize(lblSubject.getPreferredSize());
				feedbackPanel.add(lblSubject);

				JLabel lblDate = new JLabel(feedback.getDate());
				lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				lblDate.setBounds(50, 58, 103, 23);
				lblDate.setBackground(Color.BLACK);
				lblDate.setSize(lblDate.getPreferredSize());
				feedbackPanel.add(lblDate);

				JLabel lblMessage = new JLabel();
				lblMessage.setText("<html>"+ feedback.getMessage() +"</html>");
				lblMessage.setVerticalAlignment(SwingConstants.TOP);
				lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				lblMessage.setBounds(50, 96, 604, 69);
				lblMessage.setBackground(Color.BLACK);
				feedbackPanel.add(lblMessage);

				yPos += feedbackPanel.getHeight() + FEEDBACK_PANEL_GAP;
				contentPane.add(feedbackPanel);
			}
			contentPane.setPreferredSize(new Dimension(900, 150  + allFeedback.size()*(FEEDBACK_PANEL_HEIGHT+FEEDBACK_PANEL_GAP)));
		// If there isn't any feedback, show a message instead
		} else {
			JLabel lblNoFeedback = new JLabel();
			lblNoFeedback.setText("You have no feedback to view!");
			lblNoFeedback.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			lblNoFeedback.setBounds(330, 300, 604, 69);
			lblName.setSize(lblName.getPreferredSize());
			lblNoFeedback.setBackground(Color.BLACK);
			contentPane.add(lblNoFeedback);
		}
		
		setSize(900, 700);
		setLocationRelativeTo(null);
	}
}
