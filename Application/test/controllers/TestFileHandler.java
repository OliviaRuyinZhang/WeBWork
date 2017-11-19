/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Me
 */
public class TestFileHandler {
    public static void setUp(){
        // Rename users file
        if (new File("users.csv").exists()){
            new File("users.csv").renameTo(new File("usersOriginal.csv"));
        }
        
        // Rename assignment files
        ArrayList<File> existingAssignments = getExistingAssignments(false, false);
        for (File f: existingAssignments){
            String name = getFileName(f.getName());
            new File(name + ".csv").renameTo(new File(name + "Original.csv"));
        }
        
        // Rename submission files
        ArrayList<File> existingAssignmentSubmissions = getExistingAssignments(true, false);
        for (File f: existingAssignmentSubmissions){
            String name = getFileName(f.getName());
            new File(name + ".csv").renameTo(new File(name + "Original.csv"));
        }
    }
    
    
    public static void tearDown(){
        // Rename users file
        if (new File("usersOriginal.csv").exists()){
            new File("usersOriginal.csv").renameTo(new File("users.csv"));
        }
        
        // Rename assignment files
        ArrayList<File> existingAssignments = getExistingAssignments(false, true);
        for (File f: existingAssignments){
            String name = removeOriginal(f.getName());
            new File(name + "Original.csv").renameTo(new File(name + ".csv"));
        }
        
        // Rename submission files
        ArrayList<File> existingAssignmentSubmissions = getExistingAssignments(true, true);
        for (File f: existingAssignmentSubmissions){
            String name = removeOriginal(f.getName());
            new File(name + "Original.csv").renameTo(new File(name + ".csv"));
        }
    }
    
    private static ArrayList<File> getExistingAssignments(Boolean submission, Boolean original){
		
            ArrayList<File> assignments = new ArrayList<>();
            Pattern pattern;
            
            if(!submission && !original){
                pattern = Pattern.compile("Assignment+(\\d)*.csv");
            }else if (submission && !original){
                pattern = Pattern.compile("Assignment+(\\d)*+Submission.csv");
            }else if (submission && original){
                pattern = Pattern.compile("Assignment+(\\d)*+SubmissionOriginal.csv");
            }else{
                pattern = Pattern.compile("Assignment+(\\d)*+Original.csv");
            }
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
    
    private static String getFileName(String fileWithExtension){
        String result = "";
        for (int i = 0; i < fileWithExtension.indexOf(".csv"); i++){
            result += fileWithExtension.charAt(i);
        }
        return result;
    }
    
    private static String removeOriginal(String fileName){
        String result = "";
        for (int i = 0; i < fileName.indexOf("Original"); i++){
            result += fileName.charAt(i);
        }
        return result;
    }
}
