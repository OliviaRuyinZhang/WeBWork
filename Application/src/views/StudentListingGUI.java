package views;

import java.io.FileReader;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Class to display a list of assignments for an instructor.
 */
public class StudentListingGUI extends JFrame{
	private static final Color GREEN = null;
	private JPanel contentPane;
	private JScrollBar scroll;
	private JPanel listAssignmentsPanel;
	private List<File> assignments;

	public StudentListingGUI(String email) {
		setResizable(true); // Temporarily until we add a scroll bar.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);
		setTitle("WebWork");
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		// Welcome label.
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		lblWelcome.setBounds(62, 20, 80, 50);
		lblWelcome.setSize(lblWelcome.getPreferredSize());
		contentPane.add(lblWelcome);
	
		
		// User's Name label.
		JLabel lblName = new JLabel("Insert Name");
		lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblName.setBounds(62, 45, 350, 70);
		lblName.setSize(lblName.getPreferredSize());
		contentPane.add(lblName);
		
		// Every existing assignment copied into an ArrayList.
		assignments = gatherExistingAssignments();
		

		/*
		 * Released Assignments Section
		 */
		
		// Released Assignments Panel
		listAssignmentsPanel = new JPanel();
		listAssignmentsPanel.setBackground(Color.WHITE);
		contentPane.add(listAssignmentsPanel);
		listAssignmentsPanel.setLayout(null);
		
		// Released label.
		JLabel lblReleased = new JLabel("Open");
		lblReleased.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblReleased.setBounds(0, 0, 350, 70);
		lblReleased.setSize(lblReleased.getPreferredSize());
		listAssignmentsPanel.add(lblReleased);
		
		JLabel lblAssignment;
		JLabel lblDeadline;
		// Make a JPanel for every existing assignment.
		int i = 0;
		for(File file: assignments) {
			JPanel assignReleasedPanel = new JPanel();
			assignReleasedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			assignReleasedPanel.setLayout(null);
			String fileName = file.getName();
			String[] info = getAssignmentInfo(fileName);
			if(info[0].equals("Released")) {
				assignReleasedPanel.setBounds(0, 55 + i, 765, 85);
				assignReleasedPanel.setBackground(Color.decode("#F0F0F0"));
				assignReleasedPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				
				lblAssignment = new JLabel(fileName.replaceFirst("[.][^.]+$", "")); // Strips the .csv extension.
				lblAssignment.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
				lblAssignment.setBounds(50, -3, 350, 70);
				assignReleasedPanel.add(lblAssignment);
				
				lblDeadline = new JLabel("Due " + info[2]);
				lblDeadline.setFont(new Font("Segoe UI Regular", Font.PLAIN, 13));
				lblDeadline.setBounds(50, 22, 350, 70);
				lblDeadline.setBackground(Color.BLACK);
				assignReleasedPanel.add(lblDeadline);
				
				i += 90;
				
			}
			//assignmentPanel.setLayout(null);
			listAssignmentsPanel.add(assignReleasedPanel);
			
		}
		
		listAssignmentsPanel.setBounds(62, 145, 765, 150 + i);
		contentPane.add(listAssignmentsPanel);
	
	}
	
	/**
	 * Return an ArrayList of assignment files from the user's
	 * default project directory. 
	 * 
	 * @return ArrayList of files.
	 */
	public static ArrayList<File> gatherExistingAssignments(){
		
		ArrayList<File> assignments = new ArrayList<>();
		Pattern pattern = Pattern.compile("Assignment*\\d");
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
     * Returns a String array of the assignment
     * information, located in it's respective
     * assignment csv file.
     * 
     * [(Un)released/Due-date/date of creation]
     * 
     * @param fileName: String name of the assignment's csv file.
     */
    public String[] getAssignmentInfo(String fileName) {
    	
    	String[] info = new String[3];
    	
    	try {
    		FileReader fr = new FileReader(fileName);
    		BufferedReader br = new BufferedReader(fr);
    		// Check first cell for unreleased
    		String line = br.readLine();
    		info = line.split(",");
    		br.close();
    		fr.close();
    		
    	} catch (IOException e) {
    		e.printStackTrace();
        }
    	
    	return info;

    }
	
}