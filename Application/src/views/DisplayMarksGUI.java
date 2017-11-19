package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class DisplayMarksGUI extends JFrame{

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String id = "1002428037";
					DisplayMarksGUI frame = new DisplayMarksGUI(id);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DisplayMarksGUI(String studentId) {
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,500);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.CENTER;
	    gbc.weighty = 1;
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    
	    JList<String> lstStudentList = new JList<String>();
	    
	    
	    contentPane.add(lstStudentList);
	    
	    add(contentPane);
	}
}
