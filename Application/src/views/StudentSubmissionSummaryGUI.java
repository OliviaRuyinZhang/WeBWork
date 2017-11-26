package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;
import models.Problem;

public class StudentSubmissionSummaryGUI extends JFrame {
	protected static final int PANEL_GAP = 100; //the constant gap in between all questions
	private JPanel contentPane;
	private String email;
	private File file;
	
	public StudentSubmissionSummaryGUI(File file, String email){
		this.email = email;
		this.file = file;		
		
		//main content pane
		setResizable(false);
		setSize(600,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		
		//scroll bar
		JScrollPane scroll = new JScrollPane(contentPane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		scroll.setBounds(0, 0, 600, 600);
		getContentPane().add(scroll);
		contentPane.setLayout(null);
		
		//done button
		JButton btnDone = new JButton("Done");
		btnDone.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnDone.setFocusPainted(false);
		btnDone.setBackground(Color.decode("#F0F0F0"));
		btnDone.setBounds(480, 50, 80, 20);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					StudentListingGUI frame = new StudentListingGUI(email);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					hideSummary();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnDone);
		
		//summary label
		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblSummary.setBounds(20, 18, 350, 70);
		lblSummary.setSize(lblSummary.getPreferredSize());
		contentPane.add(lblSummary);
		
		//setting the check mark label
		Icon checkMark = new ImageIcon("resources/check_mark.png");
		JLabel lblCheck;
		//setting the x mark label
		Icon xMark = new ImageIcon("resources/cross_mark.png");
		JLabel lblX;
		ArrayList<Problem> problems = getProblems(file.getName());
		ArrayList<Boolean> summary = checkRightWrong(problems);
		int ycoord = 0;
		JPanel problemPanel = new JPanel();
		//create a panel for each question
		for (int i = 0; i < problems.size(); i++) {
			problemPanel = new JPanel();
			problemPanel.setLayout(null);
			problemPanel.setBackground(Color.decode("#F0F0F0"));
			problemPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			problemPanel.setBounds(12, ycoord + PANEL_GAP, 555, 85);
			ycoord += PANEL_GAP;
			addToProblemPanel(problemPanel, problems.get(i));
			//if student got it right, check mark is shown
			if (summary.get(i)) {
				lblCheck = new JLabel(checkMark);
				lblCheck.setBounds(500, 20, 50, 50);
				problemPanel.add(lblCheck);
			} else { //if student got it wrong, x mark is shown
				lblX = new JLabel(xMark);
				lblX.setBounds(500, 20, 50, 50);
				problemPanel.add(lblX);
			}
			contentPane.add(problemPanel);
		}
		
		contentPane.setPreferredSize(new Dimension(600, 600 + problemPanel.getHeight()));
	}
	
	/**
	 * Hides the summary panel
	 */
	private void hideSummary() {
		this.setVisible(false);
	}
	
	/**
	 * Checks if the students answer is right or wrong with the corresponding problems' solution
	 * @param ArrayList<Problem> p which is the ArrayList of Problem objects to be used in
	 * comparing between right/wrong
	 * @return ArrayList<Boolean> where it holds if student got right/wrong (true/false respectively)
	 * to each question where each index is the respective question
	 */
	private ArrayList<Boolean> checkRightWrong(ArrayList<Problem> p) {
		ArrayList<Boolean> summary = new ArrayList<>();
		String fileName = this.file.getName();
		String submissionName = fileName.substring(0, fileName.indexOf(".")) + "Submission.csv";
		try {
			FileReader fr = new FileReader(submissionName);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			String stuNum = ExtractData.getStudentID(this.email);
			String line = br.readLine();
			String[] info = line.split(",");
			//read until desired student number is found
			while ((line = br.readLine()) != null) {
				info = line.split(",");
				if (info[0].equals(stuNum)) {
					break;
				}
			}
			br.close();
			fr.close();
			for (int i = 0; i < p.size(); i++) {
				//if solution matches students answer
				if(p.get(i).getSolution().equals(info[i+1])){
					summary.add(true);
				} else {
					summary.add(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return summary;
	}
		
	/**
	 * Adds question data to the designated panel to be shown
	 * @param JPanel panel to which question info is to be added to
	 * @param Problem p the problem object
	 */
	private void addToProblemPanel(JPanel panel, Problem p) {
		
		//show the question
		JTextArea txtProblem = new JTextArea(p.getProblemString());
		txtProblem.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtProblem.setEditable(false);
		txtProblem.setLineWrap(true);
		txtProblem.setWrapStyleWord(true);
		txtProblem.setSize(txtProblem.getPreferredSize());
		
		//scroll
		JScrollPane scroll = new JScrollPane(txtProblem, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(15, 14, 480, 60);
		panel.add(scroll);
	}
	
	/**
	 * Gathers the questions from the file given and puts them into an ArrayList of Problem objects
	 * @param String fileName
	 * @return ArrayList<Problem> containing all the questions and their info
	 */
	private ArrayList<Problem> getProblems(String fileName) {
		ArrayList<Problem> result = new <Problem>ArrayList();
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			// Skip first cell
			String line = br.readLine();
			// Read all questions
			while ((line = br.readLine()) != null) {
				// Get line
				String[] problemLine = line.split(",");
				// Get question answers into an array list
				ArrayList<String> options = new <Problem>ArrayList();
				for (String s : problemLine[3].split("\\|")) {
					options.add(s);
				}
				// Add new question to results
				result.add(new Problem(Integer.parseInt(problemLine[0]), problemLine[1], options, problemLine[2]));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}