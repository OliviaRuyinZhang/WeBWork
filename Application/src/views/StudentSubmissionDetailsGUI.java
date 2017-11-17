package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Dimension;

public class StudentSubmissionDetailsGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public StudentSubmissionDetailsGUI(String assignmentNumber, HashMap<String, String> submissionDetails) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 758, 373);
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
		contentPane.add(lblFinalGrade);
		
		JLabel lblActualGrade = new JLabel(submissionDetails.get("Final Mark"));
		lblActualGrade.setSize(new Dimension(324, 51));
		lblActualGrade.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblActualGrade.setBounds(104, 178, 48, 51);
		lblActualGrade.setForeground(Color.decode("#2ECC71"));
		contentPane.add(lblActualGrade);
		
		JLabel lblNumTries = new JLabel("Number of Tries");
		lblNumTries.setSize(new Dimension(324, 51));
		lblNumTries.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblNumTries.setBounds(401, 145, 128, 22);
		contentPane.add(lblNumTries);
		
		JLabel lblActualNumTries = new JLabel(submissionDetails.get("Number of Tries"));
		lblActualNumTries.setSize(new Dimension(324, 51));
		lblActualNumTries.setForeground(Color.GRAY);
		lblActualNumTries.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblActualNumTries.setBounds(441, 178, 31, 51);
		contentPane.add(lblActualNumTries);
		
		JLabel lblTimeSpent = new JLabel("Time Spent");
		lblTimeSpent.setSize(new Dimension(324, 51));
		lblTimeSpent.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblTimeSpent.setBounds(575, 145, 85, 22);
		contentPane.add(lblTimeSpent);
		
		JLabel lblHours = new JLabel("2 hr.");
		lblHours.setSize(new Dimension(324, 51));
		lblHours.setForeground(Color.GRAY);
		lblHours.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		lblHours.setBounds(575, 178, 85, 51);
		contentPane.add(lblHours);
		
		JLabel lblAverageGrade = new JLabel("Average Mark");
		lblAverageGrade.setSize(new Dimension(324, 51));
		lblAverageGrade.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblAverageGrade.setBounds(227, 145, 111, 22);
		contentPane.add(lblAverageGrade);
		
		JLabel label_1 = new JLabel(submissionDetails.get("Average Mark"));
		label_1.setSize(new Dimension(324, 51));
		label_1.setForeground(new Color(46, 204, 113));
		label_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 38));
		label_1.setBounds(258, 178, 48, 51);
		contentPane.add(label_1);
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(603, 278, 111, 35);
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
