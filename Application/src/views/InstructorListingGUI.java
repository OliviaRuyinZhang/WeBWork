package views;

import java.util.Date;

import java.io.FileReader;
import java.io.FileWriter;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;

/**
 * Class to display a list of assignments for an instructor.
 */
public class InstructorListingGUI extends JFrame{
	private static final String firstNameText = null;
	private JPanel contentPane;
	private JPanel listAssignmentsPanel;
	private List<File> assignments;
	private boolean beforeDeadline;
	


	
	private String email;
	public InstructorListingGUI(String email) {
		this.email = email;

		setResizable(false); // Temporarily until we add a scroll bar.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("WebWork");
		contentPane = new JPanel();
		JScrollPane scroll = new JScrollPane(contentPane, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(scroll);
		contentPane.setLayout(null);
		
		// Welcome label.
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		lblWelcome.setBounds(62, 20, 80, 50);
		lblWelcome.setSize(lblWelcome.getPreferredSize());
		contentPane.add(lblWelcome);
	
		
		// User's Name label.
		//System.out.print(ExtractData.getFirstName(email));
		JLabel lblName = new JLabel(ExtractData.getFirstName(email));
		lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblName.setBounds(62, 45, 350, 70);
		lblName.setSize(lblName.getPreferredSize());
		contentPane.add(lblName);
		
		// Released Assignments Panel
		listAssignmentsPanel = new JPanel();
		listAssignmentsPanel.setBackground(Color.WHITE);
		contentPane.add(listAssignmentsPanel);
		listAssignmentsPanel.setLayout(null);
		
		displayAssignments();
				
		setSize(900, 700);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Displays components regarding the AssignmentListingGUI 
	 * window.
	 */
	public void displayAssignments() {
		// Every existing assignment copied into an ArrayList.
		assignments = gatherExistingAssignments();
		
		// today's date
		Date today = new Date();
		
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
								}
								
								@Override
								public void windowClosed(WindowEvent e) {
									showListingFrame();
									resetAssignmentListing();
									displayAssignments();
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
		listAssignmentsPanel.add(btnAddAssignment);
		
		
		/*
		 * Released Assignments Section
		 */
	
		// Released label.
		JLabel lblReleased = new JLabel("Released");
		lblReleased.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblReleased.setBounds(0, 0, 350, 70);
		lblReleased.setSize(lblReleased.getPreferredSize());
		listAssignmentsPanel.add(lblReleased);
		
		// Make a JPanel for every existing assignment.
		int i = 0;
		for(File file: assignments) {
			JPanel assignReleasedPanel = new JPanel();
			assignReleasedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			assignReleasedPanel.setLayout(null);
			String fileName = file.getName();
			
			String[] info = ExtractData.getAssignmentInfo(fileName);
			
			// Check due date
			String[] dueDate = info[2].split("/");
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Integer.parseInt(dueDate[2]), Integer.parseInt(dueDate[0]) - 1, Integer.parseInt(dueDate[1])); 
		    Date due = calendar.getTime();
		    beforeDeadline = (due.compareTo(today) > 0);
		    
			if(info[0].equals("Released") && beforeDeadline) {

				assignReleasedPanel.setBounds(0, 55 + i, 765, 85);
				assignReleasedPanel.setBackground(Color.decode("#F0F0F0"));
				assignReleasedPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
				addToAssignmentPanel(true, assignReleasedPanel, file);
				
				i += 90;
				
			}
			//assignmentPanel.setLayout(null);
			listAssignmentsPanel.add(assignReleasedPanel);
			
		}
		
		/*
		 * Unreleased Assignments Section
		 */

		// Unreleased label.
		JLabel lblUnreleased = new JLabel("Unreleased");
		lblUnreleased.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblUnreleased.setSize(lblUnreleased.getPreferredSize());
		lblUnreleased.setBounds(0,  75 + i , lblUnreleased.getWidth(), 
				lblUnreleased.getHeight());
		
		listAssignmentsPanel.add(lblUnreleased);
		
		for(File file: assignments) {
			JPanel assignUnreleasedPanel = new JPanel();
			assignUnreleasedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			assignUnreleasedPanel.setLayout(null);
			String fileName = file.getName();
			String[] info = ExtractData.getAssignmentInfo(fileName);
			
			// check due date
			String[] dueDate = info[2].split("/");
			//System.out.printf("%s", Arrays.toString(dueDate));
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Integer.parseInt(dueDate[2]), Integer.parseInt(dueDate[0]) - 1, Integer.parseInt(dueDate[1])); 
		    Date due = calendar.getTime();
		    beforeDeadline = (due.compareTo(today) > 0);
		    
			if(info[0].equals("Unreleased") && beforeDeadline) {

				assignUnreleasedPanel.setBounds(0, 130 + i, 765, 85);
				assignUnreleasedPanel.setBackground(Color.decode("#F0F0F0"));
				assignUnreleasedPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());

				addToAssignmentPanel(false, assignUnreleasedPanel, file);

				// Set y for the next assignment panel.
				i += 90;
			}
			listAssignmentsPanel.add(assignUnreleasedPanel);
			
		}
		
		// Closed Assignment label.
		JLabel lblClosed = new JLabel("Closed");
		lblClosed.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblClosed.setBounds(0, 160+i, lblClosed.getWidth(), 
				lblClosed.getHeight());
		lblClosed.setSize(lblClosed.getPreferredSize());
		listAssignmentsPanel.add(lblClosed);
		
		
		for(File file: assignments) {	
		    JPanel closedAssignmentPanel  = new JPanel();
		    closedAssignmentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			closedAssignmentPanel.setLayout(null);
			String fileName = file.getName();
			String[] info = ExtractData.getAssignmentInfo(fileName);
			
			// Check due date
			String[] dueDate = info[2].split("/");
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Integer.parseInt(dueDate[2]), Integer.parseInt(dueDate[0]) - 1, Integer.parseInt(dueDate[1])); 
		    Date due = calendar.getTime();
		    
		    // Make a JPanel for every closed assignment
			if(due.compareTo(today) < 0) {
				closedAssignmentPanel.setBounds(0, 215 + i, 765, 85);
				closedAssignmentPanel.setBackground(Color.decode("#F0F0F0"));
				closedAssignmentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				
				addToClosedAssignmentPanel(closedAssignmentPanel, file);

				// Set y for the next assignment panel.
				i += 90;
			}
			listAssignmentsPanel.add(closedAssignmentPanel);
			
		}
	
		listAssignmentsPanel.setBounds(62, 145, 765, 250 + i);
		
		JButton btnRemark = new JButton("Remark");
		btnRemark.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnRemark.setFocusPainted(false);
		btnRemark.setBackground(Color.decode("#f1c40f"));
		btnRemark.setBounds(467, 10, 101, 35);
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
	 * Adds interactive JButtons to each listed assignment. One button either
	 * releases or unreleased an assignment, and the other handles editing
	 * an assignment. 
	 * 
	 * @param released: boolean Flag whether assignment is released or not.
	 * @param panel: JPanel for each component being added.
	 * @param file: The assignment's file. 
	 * @param position: The current position of the last displayed assignment panel.
	 */
	private void addToAssignmentPanel(boolean released, JPanel panel, File file) {
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

			// Create toggle unrelease button.
			JButton unReleaseButton = new JButton("Unrelease");
			unReleaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
			unReleaseButton.setBounds(640, 26, 100, 35);
			unReleaseButton.setFocusPainted(false);
			unReleaseButton.setBackground(Color.decode("#EC7063"));
			unReleaseButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			updateStatus changeSatus = new updateStatus(file, info[0], this);
			unReleaseButton.addActionListener(changeSatus);

			// Add to the panel.
			panel.add(unReleaseButton);
		} else {
			
			// Create toggle release button. 
			JButton releaseButton = new JButton("Release");
			releaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
			releaseButton.setBounds(640, 26, 100, 35);
			releaseButton.setBackground(new Color(51, 204, 153));
			releaseButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			updateStatus changeSatus = new updateStatus(file, info[0], this);
			releaseButton.addActionListener(changeSatus);
			
			// Add to the panel.
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
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						showListingFrame();
						resetAssignmentListing();
						displayAssignments();
					}					
					
				});

				aeg.setVisible(true);
				aeg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			}
			
		});
		
		// Add to the panel.
		panel.add(editAssignmentButton);
		
		
	}
	
	private void addToClosedAssignmentPanel(JPanel panel, File file) {
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
	}
	

	
	/**
	 * Sets visibility of this InstructorListingGUI
	 * to false.
	 */
	public void hideListingFrame() {
		this.setVisible(false);
	}
	
	/**
	 * Sets visibility of this InstructorListingGUI
	 * to true.
	 */
	public void showListingFrame() {
		this.setVisible(true);
	}
	
	/**
	 * Removes all components in listAssignmentsPanel.
	 */
	public void resetAssignmentListing() {
		listAssignmentsPanel.removeAll();
		listAssignmentsPanel.revalidate();
		listAssignmentsPanel.repaint();
	}
	
	
	/**
	 * Return an ArrayList of assignment files from the user's
	 * default project directory. 
	 * 
	 * @return ArrayList of files.
	 */
	private ArrayList<File> gatherExistingAssignments(){
		
		ArrayList<File> assignments = new ArrayList<>();
		Pattern pattern = Pattern.compile("Assignment+(\\d)*.csv");
	    Matcher matcher;
	    
	    File[] files = new File(".").listFiles(); // All files in current directory.
	    for(File file: files) {
	    	if(file.isFile()) {
	    		matcher = pattern.matcher(file.getName());
	    		if(matcher.find()) { // If file name matches the regex expression in pattern.
	    			assignments.add(file);
	    		}
	    	}
	    }  
	    return assignments;
	}
    
    /**
     * Returns the mean value of the student grades for the given assignment
     * @param fileName: String name of the assignment's csv file(i.e. Assignment1.csv, Assignment2.csv etc)
     */
	public double getMean(String fileName) throws IOException {
		String file = fileName.substring(0, fileName.length() - 4) + "Submission.csv";
		File filePath = new File(file);
	
		double sumOfFianlGrade = 0.0;
		int numberOfStudents = 0;
		double mean;
		
		
		// if the answerSubmission csv file exist
		if (filePath.exists() == true) {	
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			String tempLine = reader.readLine();
			try {
				while((tempLine = reader.readLine()) != null) {
					String[] individualAnswerInfo = tempLine.split(","); 
					
					int size = individualAnswerInfo[6].length();
					boolean isNumber = true;
					for(int i = 0; i < size ; i++) {
						 if (!Character.isDigit(individualAnswerInfo[6].charAt(i))) {
					           isNumber = false;
					       }
					}
					if(isNumber) {
						sumOfFianlGrade += Double.parseDouble(individualAnswerInfo[6]);
						numberOfStudents += 1;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader.close();
		}
		mean = sumOfFianlGrade/numberOfStudents;
		return mean;
	}

}



/*
 * Class that handles the released buttons.
 */
class updateStatus implements ActionListener{
	private File csvFile;
	private String status;
	private InstructorListingGUI board;
	public updateStatus(File file, String originalStatus, InstructorListingGUI board) {
		this.csvFile = file;
		this.status = originalStatus;
		this.board = board;
	}

	public void actionPerformed(ActionEvent e) {
		updateStatusInfo(csvFile, status, board);
	}
	
	/**
	 * Changes the Released/Unreleased flag string in the corresponding assignment
	 * csv file.
	 * 
     * @param file: The file object which we want to change release status
	 * @param originalStatus: the assignment's original status
	 */
	private void updateStatusInfo(File file, String originalStatus, JFrame board) {	
		
		String replacedtext;
        try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String oldtext = "";
			String temptext = "";
			oldtext = reader.readLine() + '\n';
			while((temptext = reader.readLine()) != null) {
				oldtext += temptext + '\n';
				temptext = "";
			}
			reader.close();
			if(originalStatus.equals("Unreleased")) {
				replacedtext = oldtext.replaceFirst(originalStatus, "Released");
			}
			else {
				replacedtext = oldtext.replaceFirst(originalStatus, "Unreleased");
			}
			FileWriter writer = new FileWriter(file);
			writer.write(replacedtext);
			writer.close();
			
			this.board.resetAssignmentListing();
			this.board.displayAssignments();
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}

class fileSaveAs implements ActionListener{
	public JButton exportButton;
	public String fileName;
	public String currentDirectory;
	public boolean fileFound = false;
	public fileSaveAs(JButton exportButton, String fileName){
		this.fileName = fileName.substring(0,fileName.length() - 4);
		this.exportButton = exportButton;
		getFileCurrentDirectory();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
		if(fileFound == true) {
			// start at user/home directory
			fc.setCurrentDirectory(new java.io.File("user.home"));
			// show directories only
	        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

        // get the absolute path of the user's selected directory and copy submission result to the indicated path
        if (fileFound == true && fc.showOpenDialog(exportButton) == JFileChooser.APPROVE_OPTION) {
        		String destinatedDirectory = fc.getSelectedFile().getAbsolutePath() + "/"+ fileName + "Submission.csv";
        		String message = "save to " + fc.getSelectedFile().getName() + " sucessfully!";
            try {
            		Files.copy(Paths.get(currentDirectory), Paths.get(destinatedDirectory), StandardCopyOption.REPLACE_EXISTING);
            		JOptionPane.showMessageDialog(null, message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }else {
        		JOptionPane.showMessageDialog(null, "No Submission File");
        }
	
	}
	
	public void getFileCurrentDirectory() {
		
		Pattern pattern = Pattern.compile(fileName + "Submission.csv");
		Matcher matcher;
		File[] files = new File(".").listFiles(); // All files in current directory.
		for (File file : files) {
			if (file.isFile()) {
				matcher = pattern.matcher(file.getName());
				if (matcher.find()) { // If file name matches the regex expression in pattern.
					fileFound = true;
					this.currentDirectory = file.getAbsolutePath();
				}
			}
		}
		
	}

}