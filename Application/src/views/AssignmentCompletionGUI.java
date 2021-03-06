package views;

import java.io.FileReader;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;

import models.Problem;

/**
 * Class to display a list of assignments for an instructor.
 */
public class AssignmentCompletionGUI extends JFrame implements ActionListener {
	
	private JPanel contentPane = new JPanel();
	private JScrollPane scroll;
	
	private Instant start;

	private HashMap<String, String> answersInfo = new HashMap<String, String>();
	private ArrayList<String> infoTitle = new ArrayList<String>();
	private String fileName;
	private String studentNo;
	
	private boolean hasPreviousSubmission;
	private ArrayList<String> previousSubmission = new <String>ArrayList();

	public AssignmentCompletionGUI(String fileName, String studentNo) {
		ImageIcon icon = new ImageIcon("resources/webwork_icon.png");
		setIconImage(icon.getImage());
		setTitle("WeBWorK | Complete Assignment");
		start = Instant.now();
		
		this.fileName = fileName;
		this.studentNo = studentNo;
		this.hasPreviousSubmission = getPreviousSubmissionStatus(fileName, studentNo);

		// Get prior attempt if applicable
		if (this.hasPreviousSubmission) {
			this.previousSubmission = getPreviousSubmission(fileName, studentNo);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);

		Container c = getContentPane();

		contentPane.setSize(600, 400);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(getGridLayoutCount(fileName), 1));

		// Assignment Label

		JLabel lblAssignment = new JLabel(getAssignmentName(fileName));
		lblAssignment.setFont(new Font("Segoe UI Light", Font.PLAIN, 30));
		lblAssignment.setSize(lblAssignment.getPreferredSize());
		contentPane.add(lblAssignment);

		// Shuffle problems
		ArrayList<Problem> problems = ExtractData.getProblems(fileName);
		int totalNoProblems = problems.size();
		Collections.shuffle(problems);

		// construct assignment answers template
		constructAssignmentAnswersTemplate(totalNoProblems);

		// Add problems to gui
		for (Problem p : problems) {
			// Blank Label
			JLabel lblBlank = new JLabel("");
			lblBlank.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
			lblBlank.setSize(lblAssignment.getPreferredSize());
			contentPane.add(lblBlank);

			// Question
			JLabel lblQuestion = new JLabel(p.getProblemString());
			lblQuestion.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
			lblQuestion.setSize(lblQuestion.getPreferredSize());
			contentPane.add(lblQuestion);

			ButtonGroup questionGroup = new ButtonGroup();

			// Answers
			for (String a : p.getOptions()) {

				JRadioButton answer = new JRadioButton(a);
				answer.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
				answer.setSize(answer.getPreferredSize());

				// save user's selected answer
				answer.addActionListener(this);
				answer.setActionCommand(p.getProblemID() + "," + a);

				// Get prior attempt if applicable
				if (this.hasPreviousSubmission) {
					// Turn on the correct button
					if (a.equals(previousSubmission.get(p.getProblemID()))) {
						answer.setSelected(true);
						this.actionPerformed(
								new ActionEvent(answer, ActionEvent.ACTION_PERFORMED, p.getProblemID() + "," + a));
					}
				}

					contentPane.add(answer);
					questionGroup.add(answer);
				}
			}

			// save & close button
			JButton saveButton = new JButton("Save and Close");
			// close the current Jframe while the main method still running in the back,
			// other opened Jframe will remain open
			saveButton.addActionListener(this);
			saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			saveButton.setFocusPainted(false);
			saveButton.setBackground(Color.decode("#B2BABB"));
			contentPane.add(saveButton);

			// submit & grade button
			JButton submitButton = new JButton("Submit and Grade");
			// increment the number of tries
			submitButton.addActionListener(this);
			submitButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			submitButton.setFocusPainted(false);
			submitButton.setBackground(new Color(51, 204, 153));
			contentPane.add(submitButton);

			scroll = new JScrollPane(contentPane);
			scroll.getVerticalScrollBar().setUnitIncrement(16);
			c.add(scroll);
			setSize(600, 700);
			setVisible(true);
			setLocationRelativeTo(null);
	}
	
	/**
	 * Grades the current student's submission by comparing the submission to the
	 * solutions.
	 * 
	 * @return The student's grade for this submission
	 */
	private double gradeSubmission() {
		ArrayList<Problem> problems = ExtractData.getProblems(fileName);
		double finalGrade = 0;
		
		for (int i = 0; i < problems.size(); i++) {
			String answer = answersInfo.get("Question " + (i + 1) + " Answer");
			
			if (answer == null) {
				return -1;
			} else if (answer.equals(problems.get(i).getSolution())) {
				finalGrade++;
			}
		}
		
		finalGrade = (finalGrade / problems.size()) * 100;
		return Math.round(finalGrade * 100.0) / 100.0; 
	}

	public void constructAssignmentAnswersTemplate(int totalNoProblems) {
		// add titles
		answersInfo.put("StudentID", studentNo);
		infoTitle.add("StudentID");
		// add title for each question
		for (int i = 1; i <= totalNoProblems; i++) {
			String title = "Question " + i + " Answer";
			infoTitle.add(title);
			answersInfo.put(title, null);
		}
		// add titles and set initial value
		infoTitle.add("Average Mark");
		infoTitle.add("Number of Tries");
		infoTitle.add("Time (in seconds)");
		infoTitle.add("Final Mark");
		if(getPreviousSubmissionStatus(fileName, studentNo)){
			for(int i = 0; i < previousSubmission.size(); i++) {
				answersInfo.put(infoTitle.get(i), previousSubmission.get(i));
			}
		}else {
			answersInfo.put("Average Mark", "0");
			answersInfo.put("Number of Tries", "0");
			answersInfo.put("Time (in seconds)", "0");
			answersInfo.put("Final Mark", "0");
		}
		
	}

	public void storeAssignmentAnswers(int questionNo, String answer) {
		// update answer
		answersInfo.put("Question " + questionNo + " Answer", answer);
	}

	public String getAssignmentName(String fileName) {
		return fileName.substring(0, fileName.indexOf("."));
	}

	public boolean getPreviousSubmissionStatus(String fileName, String id) {
		boolean status = false;
		String submissionFileName = getAssignmentName(fileName) + "Submission.csv";

		// Check if exists , then check for studentId
		if ((new File(submissionFileName)).exists()) {
			try {
				FileReader fr = new FileReader(submissionFileName);
				BufferedReader br = new BufferedReader(fr);
				// Skip first cell
				String line = br.readLine();
				// Read all studentIDs
				while ((line = br.readLine()) != null) {
					// Get line
					String[] problemLine = line.split(",");
					// Check if studentID is present
					if (problemLine[0].equals(id))
						status = true;
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public ArrayList<String> getPreviousSubmission(String fileName, String id) {
		ArrayList<String> result = new <String>ArrayList();
		String submissionFileName = getAssignmentName(fileName) + "Submission.csv";

		// Check if exists , then check for studentId
		if ((new File(submissionFileName)).exists()) {
			try {
				FileReader fr = new FileReader(submissionFileName);
				BufferedReader br = new BufferedReader(fr);
				// Skip first cell
				String line = br.readLine();
				// Read all studentIDs
				while ((line = br.readLine()) != null) {
					// Get line
					String[] problemLine = line.split(",");
					// Check if studentID is present
					if (problemLine[0].equals(id)) {
						for (String s : problemLine) {
							result.add(s);
						}
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int getGridLayoutCount(String fileName) {
		// 1 For assignment name at least
		int count = 1;

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			// Skip first cell
			String line = br.readLine();
			// Read all questions
			while ((line = br.readLine()) != null) {
				// Add one blank line + question + 4 answers
				count += 6;
			}
			// add save and submit buttons
			count += 2;
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;

	}
	
	// update answers only but reset everything else back to 0
	public void updateCsvFile() {
		try {
			String file = fileName.substring(0, fileName.length() - 4) + "Submission.csv";
			File filePath = new File(file);

			String tempLine;
			StringBuilder sb = new StringBuilder();

			// if the answerSubmission csv file does not exist
			if (filePath.exists() == false) {
				// write title
				for (int i = 0; i < infoTitle.size(); i++) {
					sb.append(infoTitle.get(i));
					if (i < infoTitle.size()) {
						sb.append(",");
					} else if (i == infoTitle.size() - 1) {
						sb.append("\n");
					}
				}

				// change row
				sb.append("\n");

				// write content
				for (int j = 0; j < infoTitle.size(); j++) {
					sb.append(answersInfo.get(infoTitle.get(j)));
					if (j < infoTitle.size()) {
						sb.append(",");
					} else if (j == infoTitle.size() - 1) {
						sb.append("\n");
					}
				}
				FileWriter fw = new FileWriter(file);
				BufferedWriter bf = new BufferedWriter(fw);
				bf.write(sb.toString());
				bf.close();
				fw.close();
			}

			// if the file exists
			else {
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);
				boolean found = false;
				while ((tempLine = reader.readLine()) != null) {
					String[] individualAnswerInfo = tempLine.split(",");
					// If the studentNo exists in the file.
					if (individualAnswerInfo[0].equals(studentNo)) {
						found = true;
						// update the new selected answer for this student
						for (int i = 0; i < individualAnswerInfo.length; i++) {
							sb.append(answersInfo.get(infoTitle.get(i)));
							if (i < individualAnswerInfo.length - 1) {
								sb.append(",");
							} else if (i == individualAnswerInfo.length - 1) {
								sb.append("\n");
							}
						}
					} // continue to record answers for other students
					else {
						sb.append(tempLine + "\n");
					}
				} // if the studentNo is not in the submission list
				if (found != true) {
					// append this student's answers to the end of the list
					for (int j = 0; j < infoTitle.size(); j++) {
						sb.append(answersInfo.get(infoTitle.get(j)));
						if (j < infoTitle.size()) {
							sb.append(",");
						} else if (j == infoTitle.size() - 1) {
							sb.append("\n");
						}
					}
				}
				reader.close();
				FileWriter fw = new FileWriter(file);
				BufferedWriter bf = new BufferedWriter(fw);
				bf.write(sb.toString());
				bf.close();
				reader.close();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// get the object that currently come through actionListener
		Object source = e.getSource();
		// if the user selects an answer
		if (source instanceof JRadioButton) {
			// get question number and updated answer
			ArrayList<String> modifiedAnswer = new ArrayList<String>(Arrays.asList(e.getActionCommand().split(",")));
			// edit and update the answer
			storeAssignmentAnswers(Integer.parseInt(modifiedAnswer.get(0)), modifiedAnswer.get(1));

		} else if (source instanceof JButton) {
			JButton button = (JButton) source;
			String buttonText = button.getText();

			// if the user clicks the save and close button
			if (buttonText.equals("Save and Close")) {
				long totalTimeTaken = 0;
				
				// get prior attempt if applicable
				if (this.hasPreviousSubmission) {
					totalTimeTaken = Integer.parseInt(this.previousSubmission.get(this.previousSubmission.size() - 2));
				}

				Duration timeElapsed = Duration.between(start, Instant.now());
				answersInfo.put("Time (in seconds)", Long.toString(totalTimeTaken + (timeElapsed.toMillis() / 1000)));
				
				updateCsvFile();
				setVisible(false);
				dispose();
			} // if the user clicks the submit and grade button
			if (buttonText.equals("Submit and Grade")) {
				int numOfTries = 0;
				long totalTimeTaken = 0;
				double averageGrade = 0;
				
				// get prior attempt if applicable
				if (this.hasPreviousSubmission) {
					numOfTries = Integer.parseInt(this.previousSubmission.get(this.previousSubmission.size() - 3));
					totalTimeTaken = Integer.parseInt(this.previousSubmission.get(this.previousSubmission.size() - 2));
					averageGrade = Double.parseDouble(this.previousSubmission.get(this.previousSubmission.size() - 4));
				}
				
				// Stop counting the elapsed time and get the duration
				Duration timeElapsed = Duration.between(start, Instant.now());
				
				// increment the number of tries
				numOfTries += 1;
				
				String file = fileName.substring(0, fileName.length() - 4) + "Submission.csv";
				File filePath = new File(file);
				
				// If a submission file doesn't exist, make one
				if (!filePath.exists()) {
					updateCsvFile();
				}

				double currentSubmissionGrade = gradeSubmission();
				
				if (currentSubmissionGrade < 0) {
					JOptionPane.showMessageDialog(AssignmentCompletionGUI.this, "You need to answer questions to submit!");
				} else {
					averageGrade = (averageGrade + currentSubmissionGrade) / numOfTries;
					averageGrade = Math.round(averageGrade * 100.0) / 100.0;
					if(currentSubmissionGrade > Double.parseDouble(answersInfo.get("Final Mark"))) {
						answersInfo.put("Final Mark", Double.toString(currentSubmissionGrade));
					}	
					answersInfo.put("Number of Tries", Integer.toString(numOfTries));
					answersInfo.put("Average Mark", Double.toString(averageGrade));
					answersInfo.put("Time (in seconds)", Long.toString(totalTimeTaken + (timeElapsed.toMillis() / 1000)));
					
					updateCsvFile();
					
					//after submit and grade button is pressed, opens a summary panel for student to view right/wrong
					try {
						StudentSubmissionSummaryGUI frame = new StudentSubmissionSummaryGUI(new File(fileName), ExtractData.getStudentEmail(studentNo));
						frame.setVisible(true);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					} catch (Exception fe){
						fe.printStackTrace();
					}
					
					setVisible(false);
					dispose();
				}
			}
		}
	}
}
