package controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/*
 * Button class used to export student marks
 * as a spreadsheet to the user's local drive.
 */
public class ExportButton extends JButton {
	
	String fileName;
	
	public ExportButton(String fileName) {
		super();
		this.fileName = fileName;
		setText("Export Marks");
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBounds(620, 26, 120, 35);
		setBackground(new Color(51, 204, 153));
		setFont(new Font("Segoe UI", Font.PLAIN, 15));
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				String fileFound = getFileCurrentDirectory();
				if(fileFound != null) {
					// Start at user/home directory
					fc.setCurrentDirectory(new java.io.File("user.home"));
			        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				} else {
        			JOptionPane.showMessageDialog(null, "No Submissions were made.");
        			return;
				}

		        // Get the absolute path of the user's selected directory and copy submission result to the indicated path.
		        if (fc.showOpenDialog(ExportButton.this) == JFileChooser.APPROVE_OPTION) {
		        		String destinatedDirectory = fc.getSelectedFile().getAbsolutePath() + "/" +
		        					fileName.subSequence(0, fileName.indexOf(".")) + "Submission.csv";
		        		String message = "The document was sucessfully saved in " + fc.getSelectedFile().getName() + ".";
		        		try {
		        			Files.copy(Paths.get(fileFound), Paths.get(destinatedDirectory), StandardCopyOption.REPLACE_EXISTING);
		        			JOptionPane.showMessageDialog(null, message);
		        		} catch (IOException e1) {
		        			e1.printStackTrace();
		        		}
		        }			
			}
			
		});
	}
	
	/**
	 * Matches the submission file to it's corresponding
	 * assignment file's name.
	 * @return String submission file
	 */
	private String getFileCurrentDirectory() {
		
		Pattern pattern = Pattern.compile(fileName.subSequence(0, fileName.indexOf(".")) + "Submission.csv");
		Matcher matcher;
		File[] files = new File(".").listFiles(); // All files in current directory.
		for (File file : files) {
			matcher = pattern.matcher(file.getName());
			if (file.isFile() && matcher.find()) {
				return file.getAbsolutePath();
			}

		}
		return null;
	}

}
