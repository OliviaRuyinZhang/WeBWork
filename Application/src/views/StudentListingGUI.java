package views;
import java.io.FileReader;

import java.awt.Color;

import java.awt.EventQueue;

import java.awt.Dimension;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;

import controllers.ExtractData;

/**
 * Class to display a list of assignments for an instructor.
 */
public class StudentListingGUI extends JFrame{
	private JPanel contentPane;
	private JPanel listAssignmentsPanel;
	private List<File> assignments;
	private boolean beforeDeadline;


	public StudentListingGUI(String email) {
		setResizable(true); // Temporarily until we add a scroll bar.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 731);

		setTitle("WebWork");
	
		contentPane = new JPanel();
		

		JScrollPane scroll = new JScrollPane(contentPane, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(scroll);


		contentPane.setLayout(null);
		//setSize(900, 700);
		//setLocationRelativeTo(null);
		
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
		 * Opened Assignments Section
		 */
		
		// Released Assignments Panel
		listAssignmentsPanel = new JPanel();
		listAssignmentsPanel.setBackground(Color.WHITE);
		contentPane.add(listAssignmentsPanel);
		listAssignmentsPanel.setLayout(null);
		
		// Open label.
		JLabel lblOpen = new JLabel("Open");
		lblOpen.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblOpen.setBounds(0, 0, 350, 70);
		lblOpen.setSize(lblOpen.getPreferredSize());
		listAssignmentsPanel.add(lblOpen);
		
		JLabel lblAssignment;
		JLabel lblDeadline;
		Date today = new Date();

		// Make a JPanel for every existing assignment.
		int i = 0;
		for(File file: assignments) {
			JPanel assignOpenPanel = new JPanel();
			assignOpenPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			assignOpenPanel.setLayout(null);
			String fileName = file.getName();
			String[] info = getAssignmentInfo(fileName);
			
			// Check due date
			String[] dueDate = info[2].split("/");
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Integer.parseInt(dueDate[2]), Integer.parseInt(dueDate[1]), Integer.parseInt(dueDate[0])); 
		    Date due = calendar.getTime();
		    beforeDeadline = (due.compareTo(today) > 0);
		    
			if(info[0].equals("Released") && beforeDeadline) {
				assignOpenPanel.setBounds(0, 55 + i, 765, 85);
				assignOpenPanel.setBackground(Color.decode("#F0F0F0"));
				assignOpenPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				
				lblAssignment = new JLabel(fileName.replaceFirst("[.][^.]+$", "")); // Strips the .csv extension.
				lblAssignment.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
				lblAssignment.setBounds(50, -3, 350, 70);
				assignOpenPanel.add(lblAssignment);
				
				lblDeadline = new JLabel("Due " + info[2]);
				lblDeadline.setFont(new Font("Segoe UI Regular", Font.PLAIN, 13));
				lblDeadline.setBounds(50, 22, 350, 70);
				lblDeadline.setBackground(Color.BLACK);
				assignOpenPanel.add(lblDeadline);
				
				// added an open button
				JButton openButton = new JButton("Open");
				openButton.setHorizontalTextPosition(SwingConstants.CENTER);
				openButton.setBounds(640, 26, 100, 35);
				openButton.setBackground(new Color(51, 204, 153));
				openButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				openButton.addActionListener(new ActionListener() { 
					  public void actionPerformed(ActionEvent e) { 
						    new AssignmentCompletionGUI(fileName, "1002205883");
						  } 
						} );
				assignOpenPanel.add(openButton);
	
				
				i += 90;
				
			}
			listAssignmentsPanel.add(assignOpenPanel);
			
		}
		
		// Closed Assignment label.
		JLabel lblClosed = new JLabel("Closed");
		lblClosed.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblClosed.setBounds(0, 80+i, lblClosed.getWidth(), 
				lblClosed.getHeight());
		lblClosed.setSize(lblClosed.getPreferredSize());
		listAssignmentsPanel.add(lblClosed);
		
		
		for(File file: assignments) {	
		    JPanel assignClosedPanel  = new JPanel();
		    assignClosedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			assignClosedPanel.setLayout(null);
			String fileName = file.getName();
			String[] info = ExtractData.getAssignmentInfo(fileName);
			
			// Check due date
			String[] dueDate = info[2].split("/");
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Integer.parseInt(dueDate[2]), Integer.parseInt(dueDate[1]), Integer.parseInt(dueDate[0])); 
		    Date due = calendar.getTime();
		    
		    // Make a JPanel for every closed assignment
			if(info[0].equals("Released") && due.compareTo(today) < 0) {
				assignClosedPanel.setBounds(0, 130 + i, 765, 85);
				assignClosedPanel.setBackground(Color.decode("#F0F0F0"));
				assignClosedPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
								
				lblAssignment = new JLabel(fileName.replaceFirst("[.][^.]+$", "")); // Strips the .csv extension.
				lblAssignment.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
				lblAssignment.setBounds(50, -3, 350, 70);
				assignClosedPanel.add(lblAssignment);
				
				lblDeadline = new JLabel("Due " + info[2]);
				lblDeadline.setFont(new Font("Segoe UI Regular", Font.PLAIN, 13));
				lblDeadline.setBounds(50, 22, 350, 70);
				lblDeadline.setBackground(Color.BLACK);
				assignClosedPanel.add(lblDeadline);

				// Set y for the next assignment panel.
				i += 90;
			}
			listAssignmentsPanel.add(assignClosedPanel);
			
		}
		
		listAssignmentsPanel.setBounds(62, 145, 765, 250 + i);
		contentPane.add(listAssignmentsPanel);



		
		contentPane.setPreferredSize(new Dimension(900, 100  + listAssignmentsPanel.getHeight()));
		setSize(900, 700);
		setLocationRelativeTo(null);

	}
	
	/**
	 * Return an ArrayList of assignment files from the user's
	 * default project directory. 
	 * 
	 * @return ArrayList of files.
	 */
	public static ArrayList<File> gatherExistingAssignments(){
		
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
