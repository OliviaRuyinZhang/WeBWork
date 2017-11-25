package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
		
		// If the user is an instructor
		if (ExtractData.getStudentID(email).equals("")) {
			// Rescaled feedback icon to fit the button size 
			ImageIcon feedbackIcon = new ImageIcon("resources/view_feedback_image.png");	        
			Image img = feedbackIcon.getImage() ;  
		    Image newimg = img.getScaledInstance(172, 47, java.awt.Image.SCALE_SMOOTH ) ;  
		    feedbackIcon= new ImageIcon( newimg );
		    // clear the default button boarder and content
		    JButton btnViewFeedback = new JButton();
		    btnViewFeedback.setBorderPainted(false);
		    btnViewFeedback.setFocusPainted(false);
		    btnViewFeedback.setBorder(null);
		    btnViewFeedback.setMargin(new Insets(0, 0, 0, 0));
		    btnViewFeedback.setContentAreaFilled(false);
		    // set the feedback icon as button
		    btnViewFeedback.setIcon(feedbackIcon);
		    btnViewFeedback.setBounds(650, 60, 179, 35);
		    btnViewFeedback.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Creates the GUI to view feedback
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							InstructorFeedbackGUI frame = new InstructorFeedbackGUI();
							frame.setVisible(true);
							frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);									
						}
					});
				}
				
			});
			this.contentPane.add(btnViewFeedback);
		}
		
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
