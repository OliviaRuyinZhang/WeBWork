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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
public class AssignmentCompletionGUI extends JFrame implements ActionListener{
	private JPanel contentPane = new JPanel();
	private JScrollPane scroll;

	public AssignmentCompletionGUI(String fileName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);
		setTitle("WebWork");
                
		Container c = getContentPane();
		contentPane.setSize(600,400);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(getGridLayoutCount(fileName),1));
                
                // Assignment Label
		JLabel lblAssignment = new JLabel(getAssignmentName(fileName));
		lblAssignment.setFont(new Font("Segoe UI Light", Font.PLAIN, 30));
		lblAssignment.setSize(lblAssignment.getPreferredSize());
		contentPane.add(lblAssignment);
                
		// Shuffle problems
		ArrayList<Problem> problems = getAllProblems(fileName);
		Collections.shuffle(problems);
                
		//Add problems to gui
		for (Problem p: problems){

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
			for (String a: p.getOptions()){
				JRadioButton answer = new JRadioButton(a);
				answer.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
				answer.setSize(answer.getPreferredSize());
				answer.addActionListener(this);
				// On button press, actionlistener event e = problemID,answer pressed
				answer.setActionCommand(p.getProblemID() + "," + a);
				contentPane.add(answer);
				questionGroup.add(answer);
			}
		}

        scroll = new JScrollPane(contentPane);
        c.add(scroll);
        setSize(600,400);
        setVisible(true);
        }
         
		public String getAssignmentName(String fileName){
			String result = "";
			int upTo = fileName.indexOf(".csv");

			for (int i = 0; i < upTo; i++){
				result += fileName.charAt(i);
			}

			return result;
		}
        
		public ArrayList<Problem> getAllProblems(String fileName){
			ArrayList<Problem> result = new <Problem>ArrayList();

			try {
				FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);
				// Skip first cell
				String line = br.readLine();
				// Read all questions
				while((line = br.readLine()) != null){
					// Get line
					String[] problemLine = line.split(",");
					// Get question answers into an arraylist
					ArrayList<String> options = new <Problem>ArrayList();
					for (String s:problemLine[3].split("\\|")){
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
        
		public int getGridLayoutCount(String fileName){
			// 1 For assignment name at least
			int count = 1;

			try {
				FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);
				// Skip first cell
				String line = br.readLine();
				// Read all questions
				while((line = br.readLine()) != null){
					// Add one blank line + question + 4 answers
					count += 6;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return count;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}
}

