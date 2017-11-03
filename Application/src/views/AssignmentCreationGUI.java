package views;

import controllers.AssignmentCreator;
import models.Problem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Dimension;
import javax.swing.SwingConstants;

/**
 * Class to create a new assignment for an instructor.
 */
public class AssignmentCreationGUI extends JFrame {
	private int problemID = 1;
	private ArrayList<Problem> problems = new ArrayList<>();
	private JPanel contentPane;
	private JTextField txtOptionA;
	private JTextField txtOptionB;
	private JTextField txtOptionC;
	private JTextField txtOptionD;
	private JTextField txtAssignmentNum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AssignmentCreationGUI frame = new AssignmentCreationGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AssignmentCreationGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 750);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.decode("#7A7A7A"), 2);
		
		JLabel lblNewAssignment = new JLabel("New Assignment");
		lblNewAssignment.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblNewAssignment.setBounds(62, 45, 377, 71);
		lblNewAssignment.setSize(lblNewAssignment.getPreferredSize());
		contentPane.add(lblNewAssignment);
		
		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDueDate.setBounds(62, 139, 72, 14);
		lblDueDate.setSize(lblDueDate.getPreferredSize());
		contentPane.add(lblDueDate);
		
		JLabel poundSymbol = new JLabel("#");
		poundSymbol.setSize(new Dimension(365, 71));
		poundSymbol.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		poundSymbol.setBounds(725, 45, 36, 71);
		poundSymbol.setSize(poundSymbol.getPreferredSize());
		contentPane.add(poundSymbol);
		
		txtAssignmentNum = new JTextField();
		txtAssignmentNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtAssignmentNum.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		txtAssignmentNum.setText("1");
		txtAssignmentNum.setBounds(771, 51, 56, 59);
		contentPane.add(txtAssignmentNum);
		txtAssignmentNum.setColumns(10);
		
		String[] monthStrings = { "January", "February", "March", "April",
				"May", "June", "July", "August",
				"September", "October", "November", "December" };
		
		JComboBox monthDropDown = new JComboBox(monthStrings);
		monthDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		monthDropDown.setBounds(62, 164, 91, 27);
		monthDropDown.setSize(monthDropDown.getPreferredSize());
		
		contentPane.add(monthDropDown);
		
		String[] days = new String[31];
		for (int i = 0; i < 31; i++) {
			days[i] = Integer.toString(i + 1);
		}
		
		JComboBox dateDropDown = new JComboBox(days);
		dateDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		dateDropDown.setBounds(189, 164, 46, 27);
		dateDropDown.setSize(dateDropDown.getPreferredSize());
		contentPane.add(dateDropDown);
		
		String[] yearStrings = { "2017", "2018", "2019"};
		
		JComboBox yearDropDown = new JComboBox(yearStrings);
		yearDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		yearDropDown.setBounds(260, 164, 64, 27);
		yearDropDown.setSize(yearDropDown.getPreferredSize());
		contentPane.add(yearDropDown);
		
		JLabel lblProblems = new JLabel("Problems");
		lblProblems.setFont(new Font("Segoe UI Light", Font.PLAIN, 38));
		lblProblems.setBounds(62, 220, 155, 51);
		lblProblems.setSize(lblProblems.getPreferredSize());
		contentPane.add(lblProblems);
		
		JPanel problemPanel = new JPanel();
		problemPanel.setBounds(62, 295, 765, 338);
		contentPane.add(problemPanel);
		problemPanel.setLayout(null);
		
		JLabel lblProblem = new JLabel("Problem " + problemID);
		lblProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblProblem.setBounds(24, 21, 56, 21);
		lblProblem.setSize(lblProblem.getPreferredSize());
		problemPanel.add(lblProblem);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblOptions.setBounds(25, 129, 56, 21);
		lblOptions.setSize(lblOptions.getPreferredSize());
		problemPanel.add(lblOptions);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textArea.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		textArea.setBounds(24, 53, 718, 60);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		problemPanel.add(textArea);
		
		txtOptionA = new JTextField(); 
		txtOptionA.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionA.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		txtOptionA.setUI(new JTextFieldHintUI("Option A", Color.gray)); 
		txtOptionA.setBounds(25, 161, 286, 30);
		problemPanel.add(txtOptionA);
		txtOptionA.setColumns(10);
		
		txtOptionB = new JTextField();
		txtOptionB.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionB.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		txtOptionB.setUI(new JTextFieldHintUI("Option B", Color.gray)); 
		txtOptionB.setColumns(10);
		txtOptionB.setBounds(25, 202, 286, 30);
		problemPanel.add(txtOptionB);
		
		txtOptionC = new JTextField();
		txtOptionC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionC.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		txtOptionC.setUI(new JTextFieldHintUI("Option C", Color.gray)); 
		txtOptionC.setColumns(10);
		txtOptionC.setBounds(25, 243, 286, 30);
		problemPanel.add(txtOptionC);
		
		txtOptionD = new JTextField();
		txtOptionD.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionD.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		txtOptionD.setUI(new JTextFieldHintUI("Option D", Color.gray)); 
		txtOptionD.setColumns(10);
		txtOptionD.setBounds(25, 284, 286, 30);
		problemPanel.add(txtOptionD);		
		
		JLabel lblSolution = new JLabel("Solution");
		lblSolution.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSolution.setBounds(322, 129, 56, 21);
		lblSolution.setSize(lblSolution.getPreferredSize());
		problemPanel.add(lblSolution);
		
		JRadioButton option_A_radio = new JRadioButton("");
		option_A_radio.setSelected(true);
		option_A_radio.setBounds(337, 161, 23, 30);
		problemPanel.add(option_A_radio);
		
		JRadioButton option_B_radio = new JRadioButton("");
		option_B_radio.setBounds(337, 202, 23, 30);
		problemPanel.add(option_B_radio);
		
		JRadioButton option_C_radio = new JRadioButton("");
		option_C_radio.setBounds(337, 243, 23, 30);
		problemPanel.add(option_C_radio);
		
		JRadioButton option_D_radio = new JRadioButton("");
		option_D_radio.setBounds(337, 284, 23, 30);
		problemPanel.add(option_D_radio);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(option_A_radio);
		bg.add(option_B_radio);
		bg.add(option_C_radio);
		bg.add(option_D_radio);
		
		
		JButton btnAddProblem = new JButton("+ Add This Problem");
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			btnAddProblem.setBorderPainted(false);
		}
		btnAddProblem.setFocusPainted(false);
		btnAddProblem.setBackground(Color.LIGHT_GRAY);
		btnAddProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnAddProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String problemString = textArea.getText();
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
				
				Problem newProblem = new Problem(problemID, problemString, options, solution);
				problems.add(newProblem);
				problemID++;
				
				// Reset items
				lblProblem.setText("Problem " + problemID);
				textArea.setText("");
				txtOptionA.setText("");
				txtOptionB.setText("");
				txtOptionC.setText("");
				txtOptionD.setText("");
				option_A_radio.setSelected(true);
			}
		});
		
		btnAddProblem.setBounds(521, 660, 190, 40);
		contentPane.add(btnAddProblem);
		
		JButton btnCreate = new JButton("Create");
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			btnCreate.setBorderPainted(false);
		}
		btnCreate.setFocusPainted(false);
		btnCreate.setForeground(Color.BLACK);
		btnCreate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnCreate.setBackground(new Color(51, 204, 153));
		btnCreate.addActionListener(new ActionListener() {
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
					JOptionPane.showMessageDialog(null, "Assignment created successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "There was a problem adding one or more problems.");
				}
				
				setVisible(false);
				dispose(); // Destroy the JFrame object
			}
		});
		btnCreate.setBounds(720, 660, 107, 40);
		contentPane.add(btnCreate);
	}
}
