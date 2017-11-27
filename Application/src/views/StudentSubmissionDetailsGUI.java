package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Dimension;

/**
 * GUI class to display the final mark, average mark, number of tries, and time spent
 * for a given student submission.
 */
public class StudentSubmissionDetailsGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public StudentSubmissionDetailsGUI(String assignmentNumber, HashMap<String, String> submissionDetails) {
		ImageIcon icon = new ImageIcon("resources/webwork_icon.png");
		setIconImage(icon.getImage());
		setTitle("WeBWorK | Submission Details");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 977, 373);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAssignment = new JLabel("Assignment " + assignmentNumber + " Details");
		lblAssignment.setBounds(47, 56, 46, 14);
		lblAssignment.setFont(new Font("Segoe UI Light", Font.PLAIN, 38));
		lblAssignment.setSize(lblAssignment.getPreferredSize());
		contentPane.add(lblAssignment);
		
		JLabel lblFinalGrade = new JLabel("Final Mark");
		lblFinalGrade.setSize(new Dimension(324, 51));
		lblFinalGrade.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblFinalGrade.setBounds(89, 145, 95, 22);
		lblFinalGrade.setSize(lblFinalGrade.getPreferredSize());
		contentPane.add(lblFinalGrade);
		
		JLabel lblActualGrade = new JLabel(submissionDetails.get("Final Mark"));
		lblActualGrade.setSize(new Dimension(324, 51));
		lblActualGrade.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblActualGrade.setBounds(95, 178, 48, 51);
		lblActualGrade.setForeground(Color.decode("#2ECC71"));
		lblActualGrade.setSize(lblActualGrade.getPreferredSize());
		contentPane.add(lblActualGrade);
		
		JLabel lblNumTries = new JLabel("Number of Tries");
		lblNumTries.setSize(new Dimension(324, 51));
		lblNumTries.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblNumTries.setBounds(401, 145, 128, 22);
		lblNumTries.setSize(lblNumTries.getPreferredSize());
		contentPane.add(lblNumTries);
		
		JLabel lblActualNumTries = new JLabel(submissionDetails.get("Number of Tries"));
		lblActualNumTries.setSize(new Dimension(324, 51));
		lblActualNumTries.setForeground(Color.GRAY);
		lblActualNumTries.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblActualNumTries.setBounds(441, 178, 31, 51);
		lblActualNumTries.setSize(lblActualNumTries.getPreferredSize());
		contentPane.add(lblActualNumTries);
		
		JLabel lblTimeSpent = new JLabel("Time Spent");
		lblTimeSpent.setSize(new Dimension(324, 51));
		lblTimeSpent.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblTimeSpent.setBounds(675, 145, 85, 22);
		lblTimeSpent.setSize(lblTimeSpent.getPreferredSize());
		contentPane.add(lblTimeSpent);
		
		int timeInSeconds = Integer.parseInt(submissionDetails.get("Time Spent"));
		int numberOfHours = (timeInSeconds) / 3600;
		int numberOfMinutes = ((timeInSeconds % 86400 ) % 3600 ) / 60;
		int numberOfSeconds = ((timeInSeconds % 86400 ) % 3600 ) % 60 ;
		
		JLabel lblHours = new JLabel(numberOfHours + " hr. " + numberOfMinutes + " min. " + numberOfSeconds + " sec.");
		lblHours.setSize(new Dimension(324, 51));
		lblHours.setForeground(Color.GRAY);
		lblHours.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblHours.setBounds(575, 178, 85, 51);
		lblHours.setSize(lblHours.getPreferredSize());
		contentPane.add(lblHours);
		
		JLabel lblAverageGrade = new JLabel("Average Mark");
		lblAverageGrade.setSize(new Dimension(324, 51));
		lblAverageGrade.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblAverageGrade.setBounds(227, 145, 111, 22);
		lblAverageGrade.setSize(lblAverageGrade.getPreferredSize());
		contentPane.add(lblAverageGrade);
		
		JLabel lblActualAverageGrade = new JLabel(submissionDetails.get("Average Mark"));
		lblActualAverageGrade.setSize(new Dimension(324, 51));
		lblActualAverageGrade.setForeground(new Color(46, 204, 113));
		lblActualAverageGrade.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblActualAverageGrade.setBounds(240, 178, 48, 51);
		lblActualAverageGrade.setSize(lblActualAverageGrade.getPreferredSize());
		contentPane.add(lblActualAverageGrade);
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(819, 275, 111, 35);
		btnDone.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnDone.setFocusPainted(false);
		btnDone.setBackground(Color.decode("#B2BABB"));
		btnDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		contentPane.add(btnDone);		
	}
}
