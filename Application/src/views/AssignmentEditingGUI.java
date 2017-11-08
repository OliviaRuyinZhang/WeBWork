package views;

import controllers.AssignmentCreator;
import controllers.ExtractData;
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
	private int selectedPID;
	private ArrayList<Problem> problems = new ArrayList<>();
	private JPanel contentPane;
	private JTextField txtOptionA;
	private JTextField txtOptionB;
	private JTextField txtOptionC;
	private JTextField txtOptionD;
	private JTextField txtAssignmentNum;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File file = new File("Assignment1.csv");
					AssignmentEditingGUI a = new AssignmentEditingGUI(file);
					a.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	
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
		
		//Retrieve all problems from the assignment.
		ArrayList<ArrayList<String>> data = ExtractData.getAssignmentQData(this.file);
		String[] info = ExtractData.getAssignmentInfo(this.file.getName());
		
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

		// All due date related info.
		String[] currDueDate = info[1].split("\\/");
		
		//List of months for drop down list
		String[] monthStrings = { "January", "February", "March", "April",
				"May", "June", "July", "August",
				"September", "October", "November", "December" };
		//Month drop down list
		int monthInd = Integer.parseInt(currDueDate[0]) - 1;
		JComboBox<String> monthDropDown = new JComboBox<>(monthStrings);
		monthDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		monthDropDown.setBounds(62, 164, 91, 27);
		monthDropDown.setSize(monthDropDown.getPreferredSize());
		monthDropDown.setSelectedItem(monthStrings[monthInd]);
		contentPane.add(monthDropDown);
		
		//List of days for drop down list
		String[] days = new String[31];
		for (int i = 0; i < 31; i++) {
			days[i] = Integer.toString(i + 1);
		}
		//Day drop down list
		String day = currDueDate[1];
		JComboBox<String> dateDropDown = new JComboBox<>(days);
		dateDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		dateDropDown.setBounds(189, 164, 46, 27);
		dateDropDown.setSize(dateDropDown.getPreferredSize());
		dateDropDown.setSelectedItem(day);
		contentPane.add(dateDropDown);
		
		String year = currDueDate[2];
		//List of years for drop down list
		String[] yearStrings = { "2017", "2018", "2019"};
		//Year drop down list
		JComboBox<String> yearDropDown = new JComboBox<>(yearStrings);
		yearDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		yearDropDown.setBounds(260, 164, 64, 27);
		yearDropDown.setSize(yearDropDown.getPreferredSize());
		yearDropDown.setSelectedItem(year);
		contentPane.add(yearDropDown);
		
		//Problems
		int pid;
		String question;
		String solution;
		for(int i = 0; i < data.get(0).size(); i++) { // Every subindex has same num of elements.
			pid = Integer.parseInt(data.get(0).get(i));
			question = data.get(1).get(i);
			solution = data.get(2).get(i);
			String[] splitOptions = data.get(3).get(i).split("\\|");
			ArrayList<String> options = new ArrayList<>();
			for(int j = 0; j < splitOptions.length; j++) {
				options.add(splitOptions[j]);
			}
			problems.add(new Problem(pid, question, options, solution));
		}
		
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
		

		int qsize = problems.size(); // Does not work if there are no questions.
		String[] questions = new String[qsize];
		for(Problem prob: problems) {
			questions[prob.getProblemID() - 1] = prob.getProblemString();
		}
		
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
				
		//Which solution is correct radio buttons
		//Need to fetch the old solution choice
		JLabel lblSolution = new JLabel("Solution");
		lblSolution.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSolution.setBounds(321, 102, 56, 21);
		lblSolution.setSize(lblSolution.getPreferredSize());
		problemPanel.add(lblSolution);
		//Choice A
		JRadioButton option_A_radio = new JRadioButton("");
		option_A_radio.setBounds(336, 134, 23, 30);
		option_A_radio.setSelected(false);
		problemPanel.add(option_A_radio);
		//Choice B
		JRadioButton option_B_radio = new JRadioButton("");
		option_B_radio.setBounds(336, 175, 23, 30);
		option_B_radio.setSelected(false);
		problemPanel.add(option_B_radio);
		//Choice C
		JRadioButton option_C_radio = new JRadioButton("");
		option_C_radio.setBounds(336, 216, 23, 30);
		option_C_radio.setSelected(false);
		problemPanel.add(option_C_radio);
		//Choice D
		JRadioButton option_D_radio = new JRadioButton("");
		option_D_radio.setBounds(336, 257, 23, 30);
		option_D_radio.setSelected(false);
		problemPanel.add(option_D_radio);
		
		//Group
		ButtonGroup bg = new ButtonGroup();
		bg.add(option_A_radio);
		bg.add(option_B_radio);
		bg.add(option_C_radio);
		bg.add(option_D_radio);
		
		JComboBox<String> cbProblems = new JComboBox<String>(questions);
		cbProblems.setBounds(54, 42, 700, 55);
		cbProblems.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		cbProblems.setEditable(true);
		cbProblems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int solI = updateOptions(cbProblems,txtOptionA, txtOptionB, txtOptionC, txtOptionD);
				updateSolutionSelection(solI, option_A_radio, option_B_radio, option_C_radio, option_D_radio);

			}
		});
		
		problemPanel.add(cbProblems);
		
		int solI = updateOptions(cbProblems,txtOptionA, txtOptionB, txtOptionC, txtOptionD);
		updateSolutionSelection(solI, option_A_radio, option_B_radio, option_C_radio, option_D_radio);

		
		
		//Edit problem button
		JButton btnSaveProblem = new JButton("Save This Problem");
		btnSaveProblem.setBackground(Color.LIGHT_GRAY);
		btnSaveProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnSaveProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//String problemString = textArea.getText();
				ArrayList<String> options = new ArrayList<>();
				options.add(txtOptionA.getText());
				options.add(txtOptionB.getText());
				options.add(txtOptionC.getText());
				options.add(txtOptionD.getText());
			}
		});
		btnSaveProblem.setBounds(521, 630, 190, 40);
		contentPane.add(btnSaveProblem);
		
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
	 * [EDIT]: Returns index of solution in options.
	 * WARNING: Raises NullPointerException if the assignment has no problems.
	 * NOTE: prevent user from making an "empty" assignment.
	 * @param questions: JComboBox that contains all questions to select from
	 * @param data: This assignments data
	 * @param optionA: JTextField first option
	 * @param optionB: JTextField second option
	 * @param optionC: JTextField third option
	 * @param optionD: JTextField fourth option
	 * @return 
	 */
	private int updateOptions(JComboBox<String> questions, JTextField optionA, 
			JTextField optionB, JTextField optionC, JTextField optionD) {

		String selectedProblem = ((String) questions.getSelectedItem());
		for(int i = 0; i < problems.size();i++) {
			if(problems.get(i).getProblemString().equals(selectedProblem)) {
				selectedPID = i;
			}
		}
		
		ArrayList<String> options = problems.get(selectedPID).getOptions();

		optionA.setText(options.get(0));
		optionB.setText(options.get(1));
		optionC.setText(options.get(2));
		optionD.setText(options.get(3));	
		
		String sol = problems.get(selectedPID).getSolution();
		int j = 0;
		while (j < problems.get(selectedPID).getOptions().size()) {
			if(problems.get(selectedPID).getOptions().get(j).equals(sol)) {
				return j;
			}
			j++;
		}
		
		return j - 1; // Returns last index if no solution was found.
			
	}
	
	public void updateSolutionSelection(int solIndex, JRadioButton rbA, JRadioButton rbB, JRadioButton rbC, JRadioButton rbD) {
		if(solIndex == 0){
			rbA.setSelected(true);
		} else if (solIndex == 1) {
			rbB.setSelected(true);
		} else if (solIndex == 2) {
			rbC.setSelected(true);
		} else if (solIndex == 3) {
			rbD.setSelected(true);
		}
	}
		
}
