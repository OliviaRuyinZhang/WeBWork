package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grading {
	
    /**
     * Returns a HashMap of the students grades per assignment number, given
     * the student's id.
     * @param id Student number
     * @return HashMap<Integer, String>
     */
    public static HashMap<Integer, String> getStudentGrades(String id){
            HashMap<Integer, String> assignNumToGrade = new HashMap<>();
          
            for (File f: getExistingAssignments()){
                try {
                    FileReader fr = new FileReader(f.getName());
                    BufferedReader br = new BufferedReader(fr);
                    // Skip first cell
                    String line = br.readLine();
                    // Read all studentIDs
                    while ((line = br.readLine()) != null) {
                            // Get line
                            String[] assignmentLine = line.split(",");
                            // Check if studentID is present
                            if (assignmentLine[0].equals(id)){
                                    // get assignmnet number
                            		String fileName = f.getName();
                            		int assignmentNum = Integer.parseInt(fileName.replaceAll("[^0-9]", ""));
                                    assignNumToGrade.put(assignmentNum, assignmentLine[assignmentLine.length - 1].replace(" ", "") + "%");
                                    break;
                            }
                    }
                    br.close();
                    fr.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
               
            }
            return assignNumToGrade;
        }
        
        private static ArrayList<File> getExistingAssignments(){
		
            ArrayList<File> assignments = new ArrayList<>();
            Pattern pattern= Pattern.compile("Assignment(\\d)+Submission.csv");
            
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
}
