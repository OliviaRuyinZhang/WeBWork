package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.Grading;

/*
 * DisplayMarksGUI class
 */
public class DisplayMarksGUI extends JFrame{

	private JPanel contentPane;
	private DefaultListModel<String> model;
	private JTextField txtStudentId;
	
	public DisplayMarksGUI() {
		ImageIcon icon = new ImageIcon("resources/webwork_icon.png");
		setIconImage(icon.getImage());
		model = new DefaultListModel<>();
		setSize(350,400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout());
		searchPanel.setBackground(Color.WHITE);
		
	    txtStudentId = new JTextField();
	    txtStudentId.setUI(new JTextFieldHintUI("Student #", Color.gray));
	    txtStudentId.setPreferredSize(new Dimension(200,30));
	    searchPanel.add(txtStudentId);
	    

	    JButton btnSearch = new JButton("Search");
	    btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnSearch.setBounds(640, 26, 100, 35);
		btnSearch.setBackground(Color.decode("#5D8AA8"));
	    btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 15));
	    btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = getTxtStudentId().getText();
				DefaultListModel<String> listModel = getListModel();
				listModel.clear();
				// If not a valid entry.
				if(id.equals("") || !id.matches(".*\\d.*")) {
					JOptionPane.showMessageDialog(DisplayMarksGUI.this, "Please insert a student number.");
				} else {
					addMarksToList(id, listModel);
				}
				getTxtStudentId().setText("");
			}	
		});
	    searchPanel.add(btnSearch);
	    
	    contentPane.add(searchPanel);
	    
	    JPanel gradesPanel = new JPanel();
	    gradesPanel.setLayout(new FlowLayout());
	    gradesPanel.setBackground(Color.WHITE);
		
	    JList<String> lstGrades = new JList<>(model);
	    
		JScrollPane scroll = new JScrollPane(lstGrades, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(250,250));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		
	    gradesPanel.add(scroll);
	    
	    contentPane.add(gradesPanel);
	    
	    add(contentPane);
	    
	}
	
	/**
	 * Adds all existing marks to the model, otherwise only a message is
	 * added to the model.
	 * @param studentId String representation of the student number
	 * @param model model linked to the JList
	 */
	public void addMarksToList(String studentId, DefaultListModel<String> model) {
		HashMap<Integer,String> assignNumToGrade = Grading.getStudentGrades(studentId);
		
		Set<Integer> keys = assignNumToGrade.keySet();
		Integer[] assignNumArray = keys.toArray(new Integer[keys.size()]);
		
		for(int assignNum: assignNumArray) {
			model.addElement("Assignment" + String.valueOf(assignNum) + "       " + assignNumToGrade.get(assignNum));
		}
		
		if (model.getSize() == 0) {
			model.addElement("This student has no submitted marks.");
		}
	}
	
	/**
	 * Returns the JTextField where the student id is
	 * entered.
	 * @return JTextField
	 */
	public JTextField getTxtStudentId() {
		return txtStudentId;
	}
	
	/**
	 * Returns the list model.
	 * @return DefaultListModel<String>
	 */
	public DefaultListModel<String> getListModel(){
		return model;
	}


}
