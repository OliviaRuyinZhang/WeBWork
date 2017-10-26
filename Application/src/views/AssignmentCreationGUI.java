package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class AssignmentCreationGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtOptionA;
	private JTextField txtOptionB;
	private JTextField txtOptionC;
	private JTextField txtOptionD;

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
		setBounds(100, 100, 900, 731);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
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
		
		String[] monthStrings = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		
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
		dateDropDown.setBounds(174, 164, 46, 27);
		dateDropDown.setSize(dateDropDown.getPreferredSize());
		contentPane.add(dateDropDown);
		
		String[] yearStrings = { "2017", "2018", "2019"};
		
		JComboBox yearDropDown = new JComboBox(yearStrings);
		yearDropDown.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		yearDropDown.setBounds(226, 164, 64, 27);
		yearDropDown.setSize(yearDropDown.getPreferredSize());
		contentPane.add(yearDropDown);
		
		JLabel lblProblems = new JLabel("Problems");
		lblProblems.setFont(new Font("Segoe UI Light", Font.PLAIN, 38));
		lblProblems.setBounds(62, 220, 155, 51);
		lblProblems.setSize(lblProblems.getPreferredSize());
		contentPane.add(lblProblems);
		
		JPanel problemPanel = new JPanel();
		problemPanel.setBounds(62, 295, 765, 312);
		contentPane.add(problemPanel);
		problemPanel.setLayout(null);
		
		JLabel lblProblem = new JLabel("Problem");
		lblProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblProblem.setBounds(24, 21, 56, 21);
		lblProblem.setSize(lblProblem.getPreferredSize());
		problemPanel.add(lblProblem);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblOptions.setBounds(24, 102, 56, 21);
		lblOptions.setSize(lblOptions.getPreferredSize());
		problemPanel.add(lblOptions);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 53, 718, 38);
		problemPanel.add(textArea);
		
		txtOptionA = new JTextField();
		txtOptionA.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionA.setText("Option A");
		txtOptionA.setBounds(24, 134, 286, 30);
		problemPanel.add(txtOptionA);
		txtOptionA.setColumns(10);
		
		txtOptionB = new JTextField();
		txtOptionB.setText("Option B");
		txtOptionB.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionB.setColumns(10);
		txtOptionB.setBounds(24, 175, 286, 30);
		problemPanel.add(txtOptionB);
		
		txtOptionC = new JTextField();
		txtOptionC.setText("Option C");
		txtOptionC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionC.setColumns(10);
		txtOptionC.setBounds(24, 216, 286, 30);
		problemPanel.add(txtOptionC);
		
		txtOptionD = new JTextField();
		txtOptionD.setText("Option D");
		txtOptionD.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOptionD.setColumns(10);
		txtOptionD.setBounds(24, 257, 286, 30);
		problemPanel.add(txtOptionD);		
		
		JLabel lblSolution = new JLabel("Solution");
		lblSolution.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSolution.setBounds(321, 102, 56, 21);
		lblSolution.setSize(lblSolution.getPreferredSize());
		problemPanel.add(lblSolution);
		
		JRadioButton option_A_radio = new JRadioButton("");
		option_A_radio.setSelected(true);
		option_A_radio.setBounds(336, 134, 23, 30);
		problemPanel.add(option_A_radio);
		
		JRadioButton option_B_radio = new JRadioButton("");
		option_B_radio.setBounds(336, 175, 23, 30);
		problemPanel.add(option_B_radio);
		
		JRadioButton option_C_radio = new JRadioButton("");
		option_C_radio.setBounds(336, 216, 23, 30);
		problemPanel.add(option_C_radio);
		
		JRadioButton option_D_radio = new JRadioButton("");
		option_D_radio.setBounds(336, 257, 23, 30);
		problemPanel.add(option_D_radio);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(option_A_radio);
		bg.add(option_B_radio);
		bg.add(option_C_radio);
		bg.add(option_D_radio);
		
		
		JButton btnAddProblem = new JButton("+ Add Problem");
		btnAddProblem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnAddProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAddProblem.setBounds(689, 232, 138, 40);
		contentPane.add(btnAddProblem);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnCreate.setBackground(new Color(51, 204, 153));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCreate.setBounds(720, 630, 107, 40);
		contentPane.add(btnCreate);
	}
}
