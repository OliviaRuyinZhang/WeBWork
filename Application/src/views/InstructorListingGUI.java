package views;

import java.util.Date;

import java.io.FileReader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.ExportButton;
import controllers.ExtractData;
import controllers.StatusButton;

/**
 * Class to display a list of assignments for an instructor.
 */
public class InstructorListingGUI extends ListingGUI{
	
	private Date today;
	private ArrayList<File> assignments;
	private ArrayList<File> releasedFiles;
	private ArrayList<File> unreleasedFiles;
	private ArrayList<File> closedFiles;
	
	public InstructorListingGUI(String email) {
		super(email);
		timer.start();
	}
	
	/**
	 * Displays components regarding the AssignmentListingGUI 
	 * window.
	 */
	protected void displayAssignments() {
		today = new Date();
		assignments = gatherExistingAssignments();
		releasedFiles = new ArrayList<File>();
		unreleasedFiles = new ArrayList<File>();
		closedFiles = new ArrayList<File>();
		
				
		JButton btnAddAssignment = new JButton("+ Add Assignment");
		btnAddAssignment.setBounds(585, 10, 179, 35);
		btnAddAssignment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnAddAssignment.setFocusPainted(false);
		btnAddAssignment.setBackground(Color.decode("#B2BABB"));
		btnAddAssignment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Creates an InstructorListingGUI
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AssignmentCreationGUI frame = new AssignmentCreationGUI();
							frame.addWindowListener(new WindowAdapter() {
								@Override
								public void windowOpened(WindowEvent e) {
									hideListingFrame();
									timer.stop();
								}
								@Override
								public void windowClosed(WindowEvent e) {
									showListingFrame();
									resetAssignmentListing();
									displayAssignments();
									timer.start();
								}					
							});
							frame.setVisible(true);
							frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				});
			}
		});
		this.listAssignmentsPanel.add(btnAddAssignment);
		
		Calendar calendar = Calendar.getInstance();
		Date due;
		String[] info, dueDate;
		boolean beforeDeadline;
		// Sets up the ArrayList sections.
		for(File assignment: assignments) {
			info = ExtractData.getAssignmentInfo(assignment.getName());
			dueDate = info[2].split("/");
			calendar.set(Integer.parseInt(dueDate[2]), Integer.parseInt(dueDate[0]) - 1, Integer.parseInt(dueDate[1])); 
			due = calendar.getTime();
			beforeDeadline = (due.compareTo(today) > 0);
			if(info[0].equals("Released") && beforeDeadline) {
				releasedFiles.add(assignment);
			} else if(info[0].equals("Unreleased") && beforeDeadline) {
				unreleasedFiles.add(assignment);
			} else {
				// Then before after deadline.
				closedFiles.add(assignment);
			}
		}
		
		/*
		 * Released Assignments Section
		 */
	
		// Released label.
		JLabel lblReleased = new JLabel("Released");
		lblReleased.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblReleased.setBounds(0, 0, lblReleased.getWidth(), lblReleased.getHeight());
		lblReleased.setSize(lblReleased.getPreferredSize());
		listAssignmentsPanel.add(lblReleased);
		
		int yPos = INITIAL_SECTION_Y;
		yPos += addSection(true, true, releasedFiles, yPos);
		
		/*
		 * Unreleased Assignments Section
		 */
		
		// Unreleased label.
		JLabel lblUnreleased = new JLabel("Unreleased");
		lblUnreleased.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblUnreleased.setSize(lblUnreleased.getPreferredSize());
		lblUnreleased.setBounds(0, yPos, lblUnreleased.getWidth(), 
				lblUnreleased.getHeight());
		
		listAssignmentsPanel.add(lblUnreleased);
		yPos += INITIAL_SECTION_Y;
		yPos = addSection(true, false, unreleasedFiles, yPos);
		
		/*
		 * Closed Assignments Section
		 */
		
		// Closed Assignment label.
		JLabel lblClosed = new JLabel("Closed");
		lblClosed.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblClosed.setBounds(0, yPos, lblClosed.getWidth(), 
				lblClosed.getHeight());
		lblClosed.setSize(lblClosed.getPreferredSize());
		listAssignmentsPanel.add(lblClosed);
		
		yPos += INITIAL_SECTION_Y;
		yPos = addSection(false, true, closedFiles, yPos);
			
	
		listAssignmentsPanel.setBounds(62, 145, 765, yPos);
		
		JButton btnSearchStudent = new JButton("Search Student");
		btnSearchStudent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnSearchStudent.setFocusPainted(false);
		btnSearchStudent.setBackground(Color.decode("#5D8AA8"));
		btnSearchStudent.setBounds(440, 10, 135, 35);
		btnSearchStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					DisplayMarksGUI frame = new DisplayMarksGUI();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		listAssignmentsPanel.add(btnSearchStudent);
		
		JButton btnRemark = new JButton("Remark");
		btnRemark.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnRemark.setFocusPainted(false);
		btnRemark.setBackground(Color.decode("#f1c40f"));
		btnRemark.setBounds(329, 10, 101, 35);
		btnRemark.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Creates an InstructorRemarkGUI
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							InstructorRemarkGUI frame = new InstructorRemarkGUI();
							frame.setVisible(true);
							frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}
				});
			}
		});
		listAssignmentsPanel.add(btnRemark);
		
		contentPane.setPreferredSize(new Dimension(900, 150  + listAssignmentsPanel.getHeight()));
		
	}
	
	/**
	 * Return the y-position that indicates where
	 * the next section will start adding assignment.
	 * @param boolean beforeDeadline
	 * @param boolean isReleased 
	 * @param ArrayList<File> assignmentSection section that will be added to the
	 * 				content panel.
	 * @param int currYPos where to start added to.
	 * @return int y-position
	 */
	private int addSection(boolean beforeDeadline, boolean isReleased, ArrayList<File> assignmentSection, int currYPos) {
		int yPos = currYPos;
		
		for(File file: assignmentSection) {
			JPanel assignmentPanel = new JPanel();
			assignmentPanel.setLayout(null);
			assignmentPanel.setBackground(Color.decode("#F0F0F0"));
			assignmentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			assignmentPanel.setBounds(0, yPos, 765, 85);

			// Adds other components to each individual panel.
			if(beforeDeadline) {
				addToStatusAssignmentPanel(isReleased, assignmentPanel, file);
			} else {
				addToClosedAssignmentPanel(assignmentPanel, file);
			}
			yPos += ASSIGN_PANEL_GAP;
			listAssignmentsPanel.add(assignmentPanel); // Adds to the main assignments panel.
		}
		return yPos;
	}
	
	/**
	 * Adds interactive JButtons to each listed assignment. One button either
	 * releases or unreleased an assignment, and the other handles editing
	 * an assignment. 
	 * 
	 * @param released: boolean Flag whether assignment is released or not.
	 * @param panel: JPanel for each component being added.
	 * @param file: The assignment's file. 
	 */
	private void addToStatusAssignmentPanel(boolean released, JPanel panel, File file) {
		String fileName = file.getName();
		String[] info = ExtractData.getAssignmentInfo(fileName);
		
		JLabel lblAssignment = new JLabel(fileName.replaceFirst("[.][^.]+$", "")); // Strips the .csv extension.
		lblAssignment.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		lblAssignment.setBounds(50, -3, 350, 70);
		panel.add(lblAssignment);
		
		JLabel lblDeadline = new JLabel("Due " + info[2]);
		lblDeadline.setFont(new Font("Segoe UI Regular", Font.PLAIN, 13));
		lblDeadline.setBounds(50, 22, 350, 70);
		lblDeadline.setBackground(Color.BLACK);
		panel.add(lblDeadline);
		
		if(released) {
			JButton unReleaseButton = new StatusButton("Unrelease", file);
			unReleaseButton.setBackground(Color.decode("#EC7063"));
			panel.add(unReleaseButton);
		} else {
			JButton releaseButton = new StatusButton("Release", file);
			releaseButton.setBackground(new Color(51, 204, 153));
			panel.add(releaseButton);
		}
		// Create edit assignment button.
		JButton editAssignmentButton = new JButton("Edit");
		editAssignmentButton.setHorizontalTextPosition(SwingConstants.CENTER);
		editAssignmentButton.setBounds(500, 26, 100, 35);
		editAssignmentButton.setFocusPainted(false);
		editAssignmentButton.setBackground(Color.decode("#B2BABB"));
		editAssignmentButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		editAssignmentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AssignmentEditingGUI aeg = new AssignmentEditingGUI(file);
				aeg.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						hideListingFrame();
						timer.stop();
					}
					@Override
					public void windowClosed(WindowEvent e) {
						showListingFrame();
						timer.start();
					}					
				});
				aeg.setVisible(true);
				aeg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		panel.add(editAssignmentButton);
	}
	
	private void addToClosedAssignmentPanel(JPanel panel, File file) {
		String fileName = file.getName();
		String[] info = ExtractData.getAssignmentInfo(fileName);
		double assignmentAvg = 0;
		
		//this gets the average mark of the given assignment
		
		assignmentAvg = getMean(fileName);
		
		// Label to display the average mark of the assignment.
		JLabel lblAverage = new JLabel ("Avg: " + assignmentAvg + "%");
		lblAverage.setFont(new Font("Segoe UI Regular", Font.BOLD, 14));
		lblAverage.setBounds(150, 22, 350, 70);
		lblAverage.setBackground(Color.BLACK);
		panel.add(lblAverage);

		
		JLabel lblAssignment = new JLabel(fileName.replaceFirst("[.][^.]+$", "")); // Strips the .csv extension.
		lblAssignment.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		lblAssignment.setBounds(50, -3, 350, 70);
		panel.add(lblAssignment);
		
		JLabel lblDeadline = new JLabel("Due " + info[2]);
		lblDeadline.setFont(new Font("Segoe UI Regular", Font.PLAIN, 13));
		lblDeadline.setBounds(50, 22, 350, 70);
		lblDeadline.setBackground(Color.BLACK);
		panel.add(lblDeadline);
		
		JButton exportButton = new ExportButton(file.getName());
		panel.add(exportButton);
	}
	
	private void hideListingFrame() {
		this.setVisible(false);
	}

	private void showListingFrame() {
		this.setVisible(true);
	}
    
    /**
     * Returns the mean value of the student grades for the given assignment
     * @param fileName: String name of the assignment's csv file(i.e. Assignment1.csv, Assignment2.csv etc)
     */
	private double getMean(String fileName) {
		String submissionName = fileName.substring(0, fileName.length() - 4) + "Submission.csv";
		File submissionFile = new File(submissionName);
	
		double sumOfFinalGrade = 0.0;
		int numberOfStudents = 0;
		double mean = 0.0;

		// if the answerSubmission csv file exist
		if (submissionFile.exists()) {	
			
			try {
				FileReader fr = new FileReader(submissionFile);
				BufferedReader buf = new BufferedReader(fr);
				String tempLine = buf.readLine();
				
				while((tempLine = buf.readLine()) != null) {
					String[] individualAnswerInfo = tempLine.split(","); 

					int size = individualAnswerInfo[individualAnswerInfo.length-1].length();

					boolean isNumber = true;
					for(int i = 0; i < size ; i++) {
						 if (!Character.isDigit(individualAnswerInfo[individualAnswerInfo.length-1].charAt(i))) {
					           isNumber = false;
					       }
					}
					if(isNumber) {
						sumOfFinalGrade += Double.parseDouble(individualAnswerInfo[individualAnswerInfo.length-1]);
						numberOfStudents += 1;
					}
				}
				buf.close();
				fr.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			mean = sumOfFinalGrade/numberOfStudents;
		}
		return mean;
	}
}