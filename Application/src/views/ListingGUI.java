package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import controllers.ExtractData;

public abstract class ListingGUI extends JFrame {

	protected static final int INITIAL_SECTION_Y = 55;
	protected static final int ASSIGN_PANEL_GAP = 90;
	protected JPanel contentPane;
	protected JPanel listAssignmentsPanel;
	protected String email;
	protected Timer timer;
	
	public ListingGUI(String email) {
		this.email = email;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("WebWork");
		this.contentPane = new JPanel();
		JScrollPane scroll = new JScrollPane(contentPane, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		this.contentPane.setBackground(Color.WHITE);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(scroll);
		this.contentPane.setLayout(null);
		
		// Welcome label.
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		lblWelcome.setBounds(62, 20, 80, 50);
		lblWelcome.setSize(lblWelcome.getPreferredSize());
		this.contentPane.add(lblWelcome);
	
		
		// User's Name label.
		JLabel lblName = new JLabel(ExtractData.getFirstName(this.email));
		lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 52));
		lblName.setBounds(62, 45, 350, 70);
		lblName.setSize(lblName.getPreferredSize());
		this.contentPane.add(lblName);
		
		// Released Assignments Panel
		this.listAssignmentsPanel = new JPanel();
		this.listAssignmentsPanel.setBackground(Color.WHITE);
		this.contentPane.add(this.listAssignmentsPanel);
		this.listAssignmentsPanel.setLayout(null);
		
		timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetAssignmentListing();
				displayAssignments();
			}
			
		});
						
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
		Pattern pattern = Pattern.compile("Assignment(\\d)*.csv");
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
	
	public void resetAssignmentListing(){
		listAssignmentsPanel.removeAll();
		listAssignmentsPanel.revalidate();
		listAssignmentsPanel.repaint();
	}
	
	protected abstract void displayAssignments();
}
