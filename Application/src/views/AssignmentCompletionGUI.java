package views;

import java.io.FileReader;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import models.Problem;

/**
 * Class to display a list of assignments for an instructor.
 */
public class AssignmentCompletionGUI extends JFrame{
	private JPanel contentPane = new JPanel();
	private JScrollPane scroll;

	public AssignmentCompletionGUI(String fileName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);
		setTitle("WebWork");

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
		ArrayList<Problem> problems = getAllProblems(fileName);
		int totalNoProblems = problems.size();
		Collections.shuffle(problems);

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
				selectedAnswers save = new selectedAnswers(fileName,totalNoProblems,p.getProblemString(), a);
				answer.addActionListener(save); 
				
				// On button press, actionlistener event e = problemID,answer pressed
				answer.setActionCommand(p.getProblemID() + "," + a);
				contentPane.add(answer);
				questionGroup.add(answer);
			}
		}

		scroll = new JScrollPane(contentPane);
		c.add(scroll);
		setSize(600, 400);
		setVisible(true);
	}

	public String getAssignmentName(String fileName) {
		String result = "";
		int upTo = fileName.indexOf(".csv");

		for (int i = 0; i < upTo; i++) {
			result += fileName.charAt(i);
		}

		return result;
	}

	public ArrayList<Problem> getAllProblems(String fileName) {
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
		} catch (IOException e) {
			e.printStackTrace();
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}
}

class selectedAnswers implements ActionListener{
	String fileName;
	String answer;
	String questionNo;
	int totalNoProblems;
	HashMap<String, String> answersInfo;
	ArrayList<String> infoTitle;
	
	public selectedAnswers(String fileName, int totalNoProblems, String questionNo, String answer) {
		this.fileName = fileName.substring(0, fileName.length()-4) + "Answers.csv";
		this.totalNoProblems = totalNoProblems;
		this.questionNo = questionNo;
		this.answer = answer;
		this.answersInfo = new HashMap<String, String>();
		this.infoTitle = new ArrayList<String>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			saveAnswers();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void saveAnswers() throws IOException{		
		
		//System.out.print(answersInfo.get(questionTitle));
		try {
			File tmpDir = new File(fileName);
			boolean exists = tmpDir.exists();
			if(exists) {
				String questionTitle = "Question " + questionNo + " Answer";
				answersInfo.put(questionTitle, answer);
				csvFileContent();
			}
			else {
				fileInitialization();
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fileInitialization() {			
		answersInfo.put("StudentID", null);
		infoTitle.add("StudentID");
		for (int i = 1; i <= totalNoProblems; i++) {
			String title = "Question " + i + " Answer";
			infoTitle.add(title);
			answersInfo.put(title, null);
		}
		infoTitle.add("Average Mark");
		answersInfo.put("Average Mark", null);
		infoTitle.add("Number of Tries");
		answersInfo.put("Number of Tries", null);
		infoTitle.add("Time (in seconds)");
		answersInfo.put("Time (in seconds)", null);
		infoTitle.add("Final Mark");
		answersInfo.put("Final Mark", null);
		answersInfo.put("Question " + questionNo + " Answer", answer);
		
		// store user's selected answer
		String questionTitle = "Question " + questionNo + " Answer";
		answersInfo.put(questionTitle, answer);

		try {
			csvFileTitles();
			csvFileContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void csvFileTitles() throws IOException {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bf = new BufferedWriter(fw);
			StringBuilder sb = new StringBuilder();
			// print titles
			for (int j = 0; j < infoTitle.size(); j++ ) {
				sb.append(infoTitle.get(j));
				if(j < infoTitle.size()) {
					sb.append(",");
				}
				else if (j == infoTitle.size()-1){
					sb.append("\n");
				}
			}
			bf.write(sb.toString());
			bf.close();
			fw.close();
	
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	public void csvFileContent() throws IOException{
		
		try {
			
			StringBuilder sb = new StringBuilder();
			FileReader file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			sb.append(reader.readLine() + '\n');
			reader.close();
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bf = new BufferedWriter(fw);

			for(int k = 0; k < infoTitle.size(); k++) {
				String key = (String)infoTitle.get(k);
				sb.append(answersInfo.get(key));
				if(k < answersInfo.size()-1) {
					sb.append(",");
				}
				else {
					sb.append("\n");
				}
			}
			bf.write(sb.toString());
			bf.close();
			fw.close();
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
	}

	

}