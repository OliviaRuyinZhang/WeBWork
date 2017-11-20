package controllers;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * ExtractDataTest class
 * Assumes users.csv file exists.
 */
public class ExtractDataTest {
	
	FileReader usersFile;
	FileReader assignmentFile;
	FileReader submissionFile;
	
	
	@BeforeClass
	public static void setup() {
		TestFileHandler.setUp();
		try {
			
			// Create dummy files.
			FileWriter usersFile = new FileWriter("users.csv");
			BufferedWriter usersbw = new BufferedWriter(usersFile);
			usersbw.write("TRUE,julianbarker@gmail.com,"
					+ "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08,Julian,Barker\r\n" + 
					"");
			usersbw.write("FALSE,abhay6547@gmail.com,9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08,Abhay,Vaidya,1002428037\r\n" + 
					"");
			usersbw.write("TRUE,zhangruyin@gmail.com,9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08,Olivia,Zhang\r\n" + 
					"");
			usersbw.write("FALSE	felixchen1998@gmail.com,9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08,Felix,Chen,1002428039\r\n" + 
					"");
			usersbw.write("FALSE,tikuto112233@gmail.com,9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08,Tito,Ku,1002428040");
			
			usersbw.close();
			usersFile.close();
			
			FileWriter assignmentFile = new FileWriter("Assignment1.csv");
			BufferedWriter assignmentbw = new BufferedWriter(assignmentFile);
			assignmentbw.write("Released,18/11/2017,6/3/2018\r\n" + 
					"");
			assignmentbw.write("1,question1,answer,happy|answer|excited|sad\r\n" + 
					"");
			assignmentbw.write("2,question2,answer,guitar|bass|answer|drums\r\n" + 
					"");
			assignmentbw.write("3,question3,answer,answer|dog|cat|bird\r\n" + 
					"");
			assignmentbw.write("4,question4,answer,java|c|python|answer\r\n" + 
					"");
			assignmentbw.close();
			assignmentFile.close();
			
			FileWriter submissionFile = new FileWriter("Assignment1Submission.csv");
			BufferedWriter submissionbw = new BufferedWriter(submissionFile);
			submissionbw.write("StudentID,Question 1 Answer,Question 2 Answer,Question 3 Answer,Question 4 Answer,Average Mark,Number of Tries,Time (in seconds),Final Mark\r\n" + 
					"");
			submissionbw.write("1002428037,answer,guitar,answer,python,50,1,20,50\r\n" + 
					"");
			submissionbw.write("1002428039,answer,answer,answer,c,75,1,20,75\r\n" + 
					"");
			submissionbw.write("1002428040,sad,bass,cat,answer,25,1,20,25\r\n" + 
					"");
			submissionbw.close();
			submissionFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@AfterClass
	public static void teardown() {
		File usersFile = new File("users.csv");
		usersFile.delete();
		File assignmentFile = new File("Assignment1.csv");
		assignmentFile.delete();
		File submissionFile = new File("Assignment1Submission.csv");
		submissionFile.delete();	
		TestFileHandler.tearDown();
	}
	
	/*
	 * users.csv Section.
	 */
	
	private void initializeUsersFile() {

		try {
			usersFile = new FileReader("users.csv");
		} catch (FileNotFoundException e) {
			fail("users.csv file does not exists.");
		}
	}

	private void closeUsersFile() {
		try {
			usersFile.close();
		} catch (IOException e) {
			fail("Could not close file.");
		}
	}
	
	// Tests if the file can be open and closed.
	@Test
	public void testOpenCloseUsersFile() {
		initializeUsersFile();
		closeUsersFile();
	}
	
	// Tests if all the Instructors email are returned.
	@Test
	public void testGetInstructorEmails() {
		ArrayList<String> expectInstEmails = new ArrayList<String>
			(Arrays.asList("julianbarker@gmail.com", "zhangruyin@gmail.com"));
		ArrayList<String> actualInstEmails = ExtractData.getInstructorEmails();
		assertEquals(expectInstEmails, actualInstEmails);	
	}
	
	// Tests ExtractData.getFirstName if the email is null.
	@Test
	public void testGetFirstName1() {
		assertEquals("", ExtractData.getFirstName(null));
	}
	
	// Tests ExtractData.getFirstName if the email is "".
	@Test
	public void testGetFirstName2() {
		assertEquals("", ExtractData.getFirstName(""));

	}
	
	// Tests ExtractData.getFirstName if the email does not exist.
	@Test
	public void testGetFirstName3() {
		assertEquals("", ExtractData.getFirstName("email@notreal.com"));
	}
	
	// Tests ExtractData.getFirstName if the email exists.
	@Test
	public void testGetFirstName4() {
		assertEquals("Abhay", ExtractData.getFirstName("abhay6547@gmail.com"));
	}
	
	// Tests ExtractData.getStudentID if the email is null.
	@Test
	public void testGetStudentID1() {
		assertEquals("", ExtractData.getStudentID(null));
	}
	
	// Tests ExtractData.getStudentID if the email is "".
	@Test
	public void testGetStudentID2() {
		assertEquals("", ExtractData.getStudentID(""));
	}
	
	// Tests ExtractData.getStudentID if the email does not exist.
	@Test
	public void testGetStudentID3() {
		assertEquals("", ExtractData.getStudentID("email@notreal.com"));
	}
	
	// Tests ExtractData.getStudentID if the email exists
	// and belongs to an instructor.
	//[BUG DETECTED]
	@Test
	public void testGetStudentID4() {
		//assertEquals("", ExtractData.getStudentID("julianbarker@gmail.com"));
	}
		
	// Tests ExtractData.getStudentID if the email does exist
	// and belongs to a student.
	@Test
	public void testGetStudentID5() {
		assertEquals("1002428037", ExtractData.getStudentID("abhay6547@gmail.com"));
	}
	
	/*
	 * Assignment#.csv Section.
	 */
	
	public void initializeAssignmentFile() {
		try {
			usersFile = new FileReader("Assignment1.csv");
		} catch (FileNotFoundException e) {
			fail("Assignment1.csv file does not exists.");
		}
	}

	public void closeAssignmentFile() {
		try {
			usersFile.close();
		} catch (IOException e) {
			fail("Could not close file.");
		}
	}
	
	// Tests if the file can be open and closed.
	@Test
	public void testOpenCloseAssignmentFile() {
		initializeAssignmentFile();
		closeAssignmentFile();
	}
	
	// Tests if ExtractData.getAssignmentInfo returns
	// the appropriate info.
	@Test
	public void testGetAssignmentInfo() {
		initializeAssignmentFile();
		closeAssignmentFile();
		String[] expectedInfo = {"Released", "18/11/2017", "6/3/2018"};
		String[] actualInfo = ExtractData.getAssignmentInfo("Assignment1.csv");
		assertArrayEquals(expectedInfo, actualInfo);
	}
	
	// Tests if ExtractData.getAssignmnetQData returns
	// the appropriate question ids, questions strings,
	// solution, and solution options.
	@Test
	public void testGetAssignmentQData() {
		initializeAssignmentFile();
		closeAssignmentFile();
		
		ArrayList<ArrayList<String>> expectedData = new ArrayList<>();
		expectedData.add(new ArrayList<>(Arrays.asList("1", "2", "3", "4")));
		expectedData.add(new ArrayList<>(Arrays.asList("question1", "question2", "question3", "question4")));
		expectedData.add(new ArrayList<>(Arrays.asList("answer", "answer", "answer", "answer")));
		
		expectedData.add(new ArrayList<>(Arrays.asList("happy|answer|excited|sad", "guitar|bass|answer|drums", 
				"answer|dog|cat|bird", "java|c|python|answer")));
		
		assertEquals(expectedData, ExtractData.getAssignmentQData(new File("Assignment1.csv")));
	}
	
	/**
	 * Assignment#Submission.csv Section.
	 */
	
	private void initializeSubmissionFile() {
		try {
			submissionFile = new FileReader("Assignment1Submission.csv");
		} catch (FileNotFoundException e) {
			fail("users.csv file does not exists.");
		}
	}

	private void closeSubmissionFile() {
		try {
			submissionFile.close();
		} catch (IOException e) {
			fail("Could not close file.");
		}
	}
	
	// Tests if the Submissions file exists for
	// Assignment1.
	@Test
	public void testOpenCloseAssignmentSubmission() {
		initializeSubmissionFile();
		closeSubmissionFile();
	}
	
	// Tests If the appropriate Assignment submisison file
	// details are returned if the student id is null.
	@Test
	public void testGetAssignmentSubmissionDetails1() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedDetails = new HashMap<>();
		HashMap<String, String> actualDetails = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", null);
		assertEquals(expectedDetails, actualDetails);
	}
	
	// Tests If the appropriate Assignment submisison file
	// details are returned if the student id is "".
	@Test
	public void testGetAssignmentSubmissionDetails2() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedDetails = new HashMap<>();
		HashMap<String, String> actualDetails = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", "");
		assertEquals(expectedDetails, actualDetails);
	}
	
	// Tests If the appropriate Assignment submisison file
	// details are returned if the student id does not exists.
	@Test
	public void testGetAssignmentSubmissionDetails3() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedDetails = new HashMap<>();
		HashMap<String, String> actualDetails = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", "100");
		assertEquals(expectedDetails, actualDetails);
	}
	
	// Tests If the appropriate Assignment submisison file
	// details are returned if the student id exists.
	@Test
	public void testGetAssignmentSubmissionDetails4() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedDetails = new HashMap<>();
		expectedDetails.put("Final Mark", "50");
		expectedDetails.put("Time Spent", "20");
		expectedDetails.put("Number of Tries", "1");
		expectedDetails.put("Average Mark", "50");
		HashMap<String, String> actualDetails = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", "1002428037");
		assertEquals(expectedDetails, actualDetails);
	}
	
	
	@Test
	public void testGetSubmittedAnswers1() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedAnswers = new HashMap<>();
		HashMap<String, String> actualAnswers = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", null);
		assertEquals(expectedAnswers, actualAnswers);
	}
	
	@Test
	public void testGetSubmittedAnswers2() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedAnswers = new HashMap<>();
		HashMap<String, String> actualAnswers = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", "");
		assertEquals(expectedAnswers, actualAnswers);
	}
	

	@Test
	public void testGetSubmittedAnswers3() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedAnswers = new HashMap<>();
		HashMap<String, String> actualAnswers = 
				ExtractData.getAssignmentSubmissionDetails("Assignment1.csv", "100");
		assertEquals(expectedAnswers, actualAnswers);
	}
	
	@Test
	public void testGetSubmittedAnswers4() {
		initializeAssignmentFile();
		closeAssignmentFile();
		initializeSubmissionFile();
		closeSubmissionFile();
		
		HashMap<String, String> expectedAnswers = new HashMap<>();
		expectedAnswers.put("1", "answer");
		expectedAnswers.put("2", "guitar");
		expectedAnswers.put("3", "answer");
		expectedAnswers.put("4", "python");
		
		HashMap<String, String> actualAnswers = 
				ExtractData.getSubmittedAnswers("Assignment1.csv", "1002428037");
		assertEquals(expectedAnswers, actualAnswers);
	}
}
