package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;
import models.Problem;

public class StudentSubmissionSummaryGUI extends JFrame {
	protected static final int PANEL_GAP = 90; //the constant gap inbetween all questions
	private JPanel contentPane;
	
	public StudentSubmissionSummaryGUI(String file){
		
		//scroll bar
		JScrollPane scroll = new JScrollPane(contentPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		
		//main content pane
		setResizable(false);
		setSize (600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(scroll);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//summary label
		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblSummary.setBounds(20, 18, 350, 70);
		lblSummary.setSize(lblSummary.getPreferredSize());
		contentPane.add(lblSummary);
		
		//create a panel for each question
		ArrayList<Problem> problems = new ArrayList<>();
		problems = getProblems(file);
		for (Problem p : problems) {
			int ycoord = 0;
			JPanel problemPanel = new JPanel();
			problemPanel.setLayout(null);
			problemPanel.setBackground(Color.decode("#F0F0F0"));
			problemPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			problemPanel.setBounds(0, ycoord + PANEL_GAP, 600, 85);
			ycoord += PANEL_GAP;
//			addToProblemPanel(problemPanel, fileName);
			contentPane.add(problemPanel);
		}
	}
	
	/**
	 * Adds question data to the designated panel to be shown
	 * @param JPanel panel to which question info is to be added to
	 * @param File file where questions are located
	 */
	private void addToProblemPanel(JPanel panel, File file) {
		ArrayList<ArrayList<String>> qData = ExtractData.getAssignmentQData(file);
		
		//show question number
		for (int i = 0; i < qData.size(); i++) {
			JLabel lblQuestion = new JLabel(qData.get(0).get(i));
			lblQuestion.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
			lblQuestion.setBounds(50, -3, 600, 85);
			panel.add(lblQuestion);
		}
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
				// Get question answers into an arraylist
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
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentSubmissionSummaryGUI frame = new StudentSubmissionSummaryGUI("Assignment1.csv");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
