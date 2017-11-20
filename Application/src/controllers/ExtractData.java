package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtractData {

	/**
	 * Returns ArrayList of ArrayList of Assignment Question data.
	 * @param file
	 * @return
	 */
	public static ArrayList<ArrayList<String>> getAssignmentQData(File file) {

		ArrayList<String> orderedQInfo1 = new ArrayList<>();
		ArrayList<String> orderedQInfo2 = new ArrayList<>();
		ArrayList<String> orderedQInfo3 = new ArrayList<>();
		ArrayList<String> orderedQInfo4 = new ArrayList<>();
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		data.add(orderedQInfo1);
		data.add(orderedQInfo2);
		data.add(orderedQInfo3);
		data.add(orderedQInfo4);
		String line;
		String[] placeHolder = new String[4];

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			line = br.readLine();
			while (line != null) {
				placeHolder = line.split(",");
				data.get(0).add(placeHolder[0]);
				data.get(1).add(placeHolder[1]);
				data.get(2).add(placeHolder[2]);
				data.get(3).add(placeHolder[3]);
				line = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Returns a String array of the assignment information, located in it's
	 * respective assignment csv file.
	 * [(Un)released/Date of creation/Due-date]
	 * @param fileName String name of the assignment's csv file.
	 */
	public static String[] getAssignmentInfo(String fileName) {

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

	
	/**
	 * Returns an ArrayList<String> of all registered instructors.
	 * @return ArrayList of Strings
	 */
	public static ArrayList<String> getInstructorEmails() {
		ArrayList<String> instEmails = new ArrayList<String>();
		try {

			FileReader fr = new FileReader("users.csv");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// Reads every line of the file.
			while((line = br.readLine()) != null) {

				String[] userInfo = line.split(","); // [isInstructor, email, password, firstName, lastName, studentID]
				// If the registered account is an instructor.
				if(Boolean.valueOf(userInfo[0])) {
					instEmails.add(userInfo[1]);
				}
			}
			
			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instEmails;
    }
    
	/**
	 * Returns the student's email given studentID
	 * @return String
	 */
    public static String getStudentEmail(String studentID) {
    		String email = "";
		try {
			FileReader fr = new FileReader("users.csv");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// Reads every line of the file.
			while ((line = br.readLine()) != null) {
				
				String[] user_info = line.split(","); // [isInstructor, email, password, firstName, lastName, studentID]
				// If the email exists in the file.
				if(user_info[0].equalsIgnoreCase("false")) {
					if (user_info[5].equals(studentID)) {
						email = user_info[1];
					}
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return email;
    }
    public static String getFirstName(String email){ 		
    		String firstName = "";
    		try {
    			
    			FileReader fr = new FileReader("users.csv");
    			@SuppressWarnings("resource")
    			BufferedReader br = new BufferedReader(fr);
    			String line = "";
    			// Reads every line of the file.
    			while((line = br.readLine()) != null) {

    				String[] user_info = line.split(","); // [isInstructor, email, password, firstName, lastName, studentID]
    				// If the email exists in the file.
    				if(user_info[1].equals(email)) {
    					firstName = user_info[3];
    				}
    			}
    			
    			br.close();
    			fr.close();
    		} catch (FileNotFoundException fnfe) {
    			fnfe.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

		return firstName;

    }
    
    /**
     * Given an assignment file name and student ID, returns a HashMap containing the final mark,
     * time spent, number of tries, and average mark for that student's submission.
     * @param fileName The assignment file name
     * @param studentID The student's unique ID
     * @return a HashMap of Strings mapped to Strings
     */
    public static HashMap<String, String> getAssignmentSubmissionDetails(String fileName, String studentID) {
    	HashMap<String, String> submissionDetails = new HashMap<String, String>();
 
    	try {
			FileReader fr = new FileReader(fileName.substring(0, fileName.indexOf(".")) + "Submission.csv");

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// Reads every line of the file.
			while ((line = br.readLine()) != null) {
				String[] user_info = line.split(","); // [studentID, question 1 answer, ... question n answer,
														// averageMark, timeSpent, finalMark]
				// If the studentID exists in the file.
				if (user_info[0].equals(studentID)) {
					submissionDetails.put("Final Mark", user_info[user_info.length - 1]);
					submissionDetails.put("Time Spent", user_info[user_info.length - 2]);
					submissionDetails.put("Number of Tries", user_info[user_info.length - 3]);
					submissionDetails.put("Average Mark", user_info[user_info.length - 4]);
				}
			}

			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			return submissionDetails; // There aren't any submissions at all for this assignment
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    	return submissionDetails;
    }
    
    /**
     * Returns a HashMap where keys are the question ids and the values are
     * the submitted answers.
     * @return HashMap of Strings mapped to Strings
     */
    public static HashMap<String,String> getSubmittedAnswers(String fileName, String studentId){
    	HashMap<String,String> answers = new HashMap<String,String>();
    	
    	try {
			FileReader fr = new FileReader(fileName.substring(0, fileName.indexOf(".")) + "Submission.csv");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// Reads every line of the file.
			while((line = br.readLine()) != null) {
				String[] userInfo = line.split(","); // [studentID, question 1 answer, ... question n answer, averageMark, timeSpent, finalMark]
				// If the studentID exists in the file.
				if(userInfo[0].equals(studentId)) {
					int i = 1;
					while(i < userInfo.length - 4) {
						answers.put(String.valueOf(i), userInfo[i]);
						i++;
					}
					break;
				}
			}
			
			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			return answers; // There aren't any submissions at all for this assignment
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return answers;
    }
	
	/**
	 * Searches and returns the student's studentID based on the email
	 * @param email String name of email address
	 */
	public static String getStudentID(String email) {
		String studentID = "";
		try {

			FileReader fr = new FileReader("users.csv");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// Reads every line of the file.
			while ((line = br.readLine()) != null) {
				
				String[] user_info = line.split(","); // [isInstructor, email, password, firstName, lastName, studentID]
				// If the email exists in the file.
				if (user_info[1].equals(email)) {
					studentID = user_info[5];
				}
			}

			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return studentID;
	}
}
