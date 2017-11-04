package views;

import controllers.AssignmentCreator;
import models.Problem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class AssignmentEditingGUI extends JFrame {
	File file;
	private int problemID = 1;
	private ArrayList<Problem> problems = new ArrayList<>();
	private JPanel contentPane;
	private JTextField txtOptionA;
	private JTextField txtOptionB;
	private JTextField txtOptionC;
	private JTextField txtOptionD;
	private JTextField txtAssignmentNum;
	
	/**
	 * Create the frame.
	 */
	public AssignmentEditingGUI(File file) {
		this.file = file;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		File assignmentFile;
		
		//Name of assignment to be edited
		JLabel lblNewAssignment = new JLabel("Assignment");
		lblNewAssignment.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblNewAssignment.setBounds(62, 45, 377, 71);
		lblNewAssignment.setSize(lblNewAssignment.getPreferredSize());
		contentPane.add(lblNewAssignment);
		
		//JTextField to edit assignment number.
		JTextField txtAssignmentNum = new JTextField();
		txtAssignmentNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtAssignmentNum.setFont(new Font("Segoe UI Light", Font.PLAIN, 52)); // Only allow numbers and restrict to 2 characters.
		txtAssignmentNum.setBounds(lblNewAssignment.getWidth() + 70, 60, 60, 55);
		contentPane.add(txtAssignmentNum);
		
		//Due date
		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDueDate.setBounds(62, 139, 72, 14);
		lblDueDate.setSize(lblDueDate.getPreferredSize());
		contentPane.add(lblDueDate);
		
		//List of months for drop down list
		String[] monthStrings = { "January", "February", "March", "April",
				"May", "June", "July", "August",
				"September", "October", "November", "December" };
		//Month drop down list
		JComboBox monthDropDown = new JComboBox(monthStrings);
		monthDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		monthDropDown.setBounds(62, 164, 91, 27);
		monthDropDown.setSize(monthDropDown.getPreferredSize());
		contentPane.add(monthDropDown);
		
		//List of days for drop down list
		String[] days = new String[31];
		for (int i = 0; i < 31; i++) {
			days[i] = Integer.toString(i + 1);
		}
		//Day drop down list
		JComboBox dateDropDown = new JComboBox(days);
		dateDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		dateDropDown.setBounds(189, 164, 46, 27);
		dateDropDown.setSize(dateDropDown.getPreferredSize());
		contentPane.add(dateDropDown);
		
		//List of years for drop down list
		String[] yearStrings = { "2017", "2018", "2019"};
		//Year drop down list
		JComboBox yearDropDown = new JComboBox(yearStrings);
		yearDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		yearDropDown.setBounds(260, 164, 64, 27);
		yearDropDown.setSize(yearDropDown.getPreferredSize());
		contentPane.add(yearDropDown);
		
		//Problems
		JLabel lblProblems = new JLabel("Problems");
		lblProblems.setFont(new Font("Segoe UI Light", Font.PLAIN, 38));
		lblProblems.setBounds(62, 220, 155, 51);
		lblProblems.setSize(lblProblems.getPreferredSize());
		contentPane.add(lblProblems);
		
		//Problems subpanel
		JPanel problemPanel = new JPanel();
		problemPanel.setBounds(62, 295, 765, 312);
		contentPane.add(problemPanel);
		problemPanel.setLayout(null);
		
		//Problem mini label
		JLabel lblProblem = new JLabel("Problem: ");
		lblProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblProblem.setBounds(24, 21, 56, 21);
		lblProblem.setSize(lblProblem.getPreferredSize());
		problemPanel.add(lblProblem);
		
		//Options mini label
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblOptions.setBounds(24, 102, 56, 21);
		lblOptions.setSize(lblOptions.getPreferredSize());
		problemPanel.add(lblOptions);
		
		//Retrieve all problems from the assignment.
		ArrayList<ArrayList<String>> data = getAssignmentQData(this.file);
		int qsize = data.get(1).size(); // Does not work if there are no questions.
		String[] questions = new String[qsize];
		for(int i = 0; i < qsize; i++) {
			questions[i] = data.get(0).get(i) + ". " + data.get(1).get(i);
		}
		JComboBox cbProblems = new JComboBox(questions);
		cbProblems.setBounds(24, 42, 720, 55);
		cbProblems.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		cbProblems.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateOptions(cbProblems, data, txtOptionA, txtOptionB, txtOptionC, txtOptionD);
			}
			
		});
		
		problemPanel.add(cbProblems);


		
		txtOptionA = new JTextField();
		txtOptionA.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionA.setText("Option A");
		txtOptionA.setBounds(24, 134, 286, 30);
		problemPanel.add(txtOptionA);
		txtOptionA.setColumns(10);
		//Text area to type choice B
		txtOptionB = new JTextField();
		txtOptionB.setText("Option B");
		txtOptionB.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionB.setColumns(10);
		txtOptionB.setBounds(24, 175, 286, 30);
		problemPanel.add(txtOptionB);
		//Text area to type choice C
		txtOptionC = new JTextField();
		txtOptionC.setText("Option C");
		txtOptionC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionC.setColumns(10);
		txtOptionC.setBounds(24, 216, 286, 30);
		problemPanel.add(txtOptionC);
		//Text area to type choice D
		txtOptionD = new JTextField();
		txtOptionD.setText("Option D");
		txtOptionD.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionD.setColumns(10);
		txtOptionD.setBounds(24, 257, 286, 30);
		problemPanel.add(txtOptionD);

		
		updateOptions(cbProblems, data,txtOptionA, txtOptionB, txtOptionC, txtOptionD);
		
		//Which solution is correct radio buttons
		//Need to fetch the old solution choice
		JLabel lblSolution = new JLabel("Solution");
		lblSolution.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSolution.setBounds(321, 102, 56, 21);
		lblSolution.setSize(lblSolution.getPreferredSize());
		problemPanel.add(lblSolution);
		//Choice A
		JRadioButton option_A_radio = new JRadioButton("");
		option_A_radio.setSelected(true);
		option_A_radio.setBounds(336, 134, 23, 30);
		problemPanel.add(option_A_radio);
		//Choice B
		JRadioButton option_B_radio = new JRadioButton("");
		option_B_radio.setBounds(336, 175, 23, 30);
		problemPanel.add(option_B_radio);
		//Choice C
		JRadioButton option_C_radio = new JRadioButton("");
		option_C_radio.setBounds(336, 216, 23, 30);
		problemPanel.add(option_C_radio);
		//Choice D
		JRadioButton option_D_radio = new JRadioButton("");
		option_D_radio.setBounds(336, 257, 23, 30);
		problemPanel.add(option_D_radio);
		//Group
		ButtonGroup bg = new ButtonGroup();
		bg.add(option_A_radio);
		bg.add(option_B_radio);
		bg.add(option_C_radio);
		bg.add(option_D_radio);
		
		//Edit problem button
		JButton btnEditProblem = new JButton("Save This Problem");
		btnEditProblem.setBackground(Color.LIGHT_GRAY);
		btnEditProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnEditProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//String problemString = textArea.getText();
				ArrayList<String> options = new ArrayList<>();
				options.add(txtOptionA.getText());
				options.add(txtOptionB.getText());
				options.add(txtOptionC.getText());
				options.add(txtOptionD.getText());
				
				String solution;
				
				if (option_A_radio.isSelected()) {
					solution = txtOptionA.getText();
				} else if (option_B_radio.isSelected()) {
					solution = txtOptionB.getText();
				} else if (option_C_radio.isSelected()) {
					solution = txtOptionC.getText();
				} else {
					solution = txtOptionD.getText();
				}
				
				//Problem newProblem = new Problem(problemID, problemString, options, solution);
				//problems.add(newProblem);
				problemID++;
				
				// Reset items
				lblProblem.setText("Problem " + problemID);
				//textArea.setText("");
				txtOptionA.setText("Option A");
				txtOptionB.setText("Option B");
				txtOptionC.setText("Option C");
				txtOptionD.setText("Option D");
				option_A_radio.setSelected(true);
			}
		});
		btnEditProblem.setBounds(521, 630, 190, 40);
		contentPane.add(btnEditProblem);
		
		//Save the new edited assignment
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnSave.setBackground(new Color(51, 204, 153));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String month = Integer.toString(monthDropDown.getSelectedIndex() + 1);
			    String date = dateDropDown.getSelectedItem().toString();
			    String year = yearDropDown.getSelectedItem().toString();
			    
			    String fileName = "Assignment" + txtAssignmentNum.getText() + ".csv";
			    
				AssignmentCreator.initializeFile(fileName, month+"/"+date+"/"+year);
				
				int numSuccessfullyAdded = 0;
				for (Problem problem : problems) {
					if(AssignmentCreator.addProblem(fileName, problem)) {
						numSuccessfullyAdded++;
					}
				}
				
				if (numSuccessfullyAdded == problems.size()) {
					JOptionPane.showMessageDialog(null, "Assignment edited successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "There was a problem saving one or more problems.");
				}
				
				setVisible(false);
				dispose(); // Destroy the JFrame object
			}
		});
		btnSave.setBounds(720, 630, 107, 40);
		contentPane.add(btnSave);
		
	}
	
	/**
	 * Returns ArrayList of ArrayList of Assignment Question data.
	 * @param file
	 * @return
	 */
	private ArrayList<ArrayList<String>> getAssignmentQData(File file) {
		
		ArrayList<String> orderedQInfo1 = new ArrayList<>();
		ArrayList<String> orderedQInfo2 = new ArrayList<>();
		ArrayList<String> orderedQInfo3 = new ArrayList<>();
		ArrayList<String>orderedQInfo4 = new ArrayList<>();
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		data.add(orderedQInfo1);
		data.add(orderedQInfo2);
		data.add(orderedQInfo3);
		data.add(orderedQInfo4);
		String line;
		String[] placeHolder = new String[4];
		
    	try {
    		FileReader fr = new FileReader(file);
    		BufferedReader br = new BufferedReader(fr);
    		br.readLine();
    		line = br.readLine();
    		while(line != null) {
    			placeHolder = line.split(",");
    			data.get(0).add(placeHolder[0]);
    			data.get(1).add(placeHolder[1]);
    			data.get(2).add(placeHolder[2]);
    			data.get(3).add(placeHolder[3]);
    			line = br.readLine();
    		}
    		br.close();
			fr.close();
    	} catch (IOException e) {
    		e.printStackTrace();
        }
    	
    	System.out.println(data);
    	return data;

	}
	
	/**
	 * Updates the JTextField option components for the corresponding
	 * problem. 
	 * WARNING: Raises NullPointerException if the assignment has no problems.
	 * NOTE: prevent user from making an "empty" assignment.
	 * @param questions: JComboBox that contains all questions to select from
	 * @param data: This assignments data
	 * @param optionA: JTextField first option
	 * @param optionB: JTextField second option
	 * @param optionC: JTextField third option
	 * @param optionD: JTextField fourth option
	 */
	private void updateOptions(JComboBox<String> questions, ArrayList<ArrayList<String>> data,JTextField optionA, JTextField optionB, JTextField optionC, JTextField optionD) {

		String selectedID = ((String) questions.getSelectedItem()).substring(0, 1); // ID of problem.
		String options[] = (data.get(3).get(Integer.parseInt(selectedID) - 1)).split("\\|");

		optionA.setText(options[0]);
		optionB.setText(options[1]);
		optionC.setText(options[2]);
		optionD.setText(options[3]);		
	}
		
}
