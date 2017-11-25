package controllers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.SwingConstants;

/*
 * Button class used to either release or unrelease 
 * assignments, depending on it's current status.
 */
public class StatusButton extends JButton{
	
	private File assignment;
	public StatusButton(String text, File assignment) {
		super(text);
		this.assignment = assignment;
		
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBounds(640, 26, 100, 35);
		setFocusPainted(false);
		setFont(new Font("Segoe UI", Font.PLAIN, 15));
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStatusInfo(getAssignmentFile());
				
			}
		});
	}
		
	/**
	 * Changes the Released/Unreleased flag string in the corresponding assignment
	 * csv file.
	 * 
     * @param file: The file object which we want to change release status
	 */
	private void updateStatusInfo(File file) {	
		
        try {
        	String newStatus = "";
        	String prevStatus = "";
        	String prevContent = "";
        	String newContent = "";
        	String temptext = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			prevContent = reader.readLine() + '\n';
			prevStatus = prevContent.split(",")[0]; // Gets previous status.
			switch (prevStatus){
				case "Released":
					newStatus = "Unreleased";
					break;
				case "Unreleased":
					newStatus = "Released";
					break;
			}			
			newContent = prevContent.replaceFirst(prevStatus, newStatus);
			
			// Add rest of contents to the new Contents.
			while((temptext = reader.readLine()) != null) {
				newContent += temptext + '\n';
				temptext = "";
			}
			reader.close();

			FileWriter writer = new FileWriter(file);
			BufferedWriter buf = new BufferedWriter(writer);
			buf.write(newContent);
			buf.close();
			writer.close();
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	protected File getAssignmentFile() {return this.assignment;}
}
