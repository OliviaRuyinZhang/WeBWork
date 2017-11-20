package controllers;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Problem;

/**
 * Class to test the AssignmentCreator.java class
 */
public class AssignmentCreatorTest {
	private String fileName = "test/controllers/Assignment1.csv";
	private String dueDate = "15/12/2017";
	private static String todaysDate;

	/**
	 * Checks a newly initialized assignment file (with no problems) if the released/unreleased
	 * status, due date, and creation date are correct and are the only things that exist in the file.
	 * @param fileName The assignment file name
	 * @param status "Released" or "Unreleased"
	 * @param dueDate The assignment's due date in format dd/mm/yyyy
	 * @return true if the file is correct, false otherwise
	 */
	private boolean checkInitializedFileContents(String fileName, String status, String dueDate) {
		boolean correctContents = false;
		BufferedReader br = null;
		String line = "";

		try {
			int counter = 0;
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				// Use comma as separator
				String[] elements = line.split(",");
				correctContents = (elements[0].equals(status)
						&& elements[1].equals(todaysDate)
						&& elements[2].equals(dueDate)
						&& elements.length == 3);

				counter ++;
			}

			// Check that file only has one line
			correctContents = correctContents && (counter == 1);

		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		return correctContents;
	}
	
	/**
	 * Checks an assignment file to see if every given problem is properly
	 * placed in the file which includes the problem's ID, actual problem string,
	 * solution, and options.
	 * @param problems An ArrayList of Problems that should be in the file
	 * @return true if the file is correct, false otherwise
	 */
	private boolean checkProblemsInFile(ArrayList<Problem> problems) {
		boolean correctContents = false;
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(fileName));
			// Skip over the header row
			line = br.readLine();
			
			int counter = 0;

			while ((line = br.readLine()) != null) {

				// Use comma as separator
				String[] elements = line.split(",");
				String[] options = elements[3].split("\\|");
				correctContents = (elements[0].equals(Integer.toString(problems.get(counter).getProblemID()))
						&& elements[1].equals(problems.get(counter).getProblemString())
						&& elements[2].equals(problems.get(counter).getSolution()));
				
				if (options.length == 4) {
					for (int i = 0; i < 4; i++) {
						correctContents = correctContents && options[i].equals(problems.get(counter).getOptions().get(i));
					}
				} else {
					return false;
				}
				
				counter ++;
			}

			// Check that file only has one line
			correctContents = correctContents && (counter == problems.size());

		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		return correctContents;
	}

	// Sets todays date
	@BeforeClass
	public static void setupTodaysDate() {
		// Format today's date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		todaysDate = dtf.format(localDate);
	}

	// Deletes the dummy testing file
	@After
	public void deleteFile() {
		File file = new File(fileName);

		// Tries to delete the file
		file.delete();
	}

	// Tests creating a fresh new assignment file
	@Test
	public void testInitializeFile_newName() {
		// Create initial assignment file
		AssignmentCreator.initializeFile(fileName, dueDate);
		File file = new File(fileName);

		// Check file exists
		assertTrue(file.exists());

		// Check if contents are correct
		assertTrue(checkInitializedFileContents(fileName, "Unreleased", dueDate));
	}

	// Tests creating an assignment file when one already exists with that name
	@Test
	public void testInitializeFile_existingName() {
		// Create initial assignment file
		AssignmentCreator.initializeFile(fileName, dueDate);

		String newDueDate = "20/12/2017";
		
		// Create new file with same name and different due date
		AssignmentCreator.initializeFile(fileName, newDueDate);
		
		File file = new File(fileName);

		// Check file exists
		assertTrue(file.exists());

		// Check if contents are correct
		assertTrue(checkInitializedFileContents(fileName, "Unreleased", newDueDate));
	}
	
	// Tests toggling an unreleased assignment to released
	@Test
	public void testToggleReleaseStatus_unreleasedToReleased() {
		// Create initial assignment file
		AssignmentCreator.initializeFile(fileName, dueDate);
		
		// Call the method to toggle the status from "Unreleased" to "Released"
		AssignmentCreator.toggleReleaseStatus(fileName);
		
		// Check if contents are correct
		assertTrue(checkInitializedFileContents(fileName, "Released", dueDate));
	}
	
	// Tests toggling a released assignment to unreleased
	@Test
	public void testToggleReleaseStatus_releasedToUnreleased() {
		// Create initial assignment file
		AssignmentCreator.initializeFile(fileName, dueDate);
		
		// Call the method to toggle the status from "Unreleased" to "Released"
		AssignmentCreator.toggleReleaseStatus(fileName);
		
		// Check if contents are correct
		assertTrue(checkInitializedFileContents(fileName, "Released", dueDate));
		
		// Call the method to toggle the status from "Released" to "Unreleased"
		AssignmentCreator.toggleReleaseStatus(fileName);
		
		// Check if contents are correct
		assertTrue(checkInitializedFileContents(fileName, "Unreleased", dueDate));
	}
	
	// Tests checking if an unreleased assignment is released
	@Test
	public void testIsReleased_unreleased() {
		// Create initial assignment file
		AssignmentCreator.initializeFile(fileName, dueDate);
		
		// Check if contents are correct and unchanged after method call
		assertTrue(checkInitializedFileContents(fileName, "Unreleased", dueDate));
		
		// Check that the assignment is unreleased
		assertFalse(AssignmentCreator.isReleased(fileName));
	}
	
	// Tests checking if a released assignment is released
	@Test
	public void testIsReleased_released() {
		// Create initial assignment file
		AssignmentCreator.initializeFile(fileName, dueDate);
		
		// Call the method to toggle the status from "Unreleased" to "Released"
		AssignmentCreator.toggleReleaseStatus(fileName);
		
		// Check that the assignment is released
		assertTrue(AssignmentCreator.isReleased(fileName));
	}
	
	/* Tests adding a single problem to an assignment file that
	 * contains no problems
	 */
	@Test
	public void testAddProblem_singleProblem() {
		// Create dummy problem
		int problemID = 1;
		String problemString = "Testing";
		ArrayList<String> options = new ArrayList<>(Arrays.asList("OptionA", "OptionB", "OptionC", "OptionD"));
		String solution = "OptionB";
		
		Problem problem = new Problem(problemID, problemString, options, solution);
		
		// Initialize file
		AssignmentCreator.initializeFile(fileName, dueDate);
		
		// Add problem to assignment
		AssignmentCreator.addProblem(fileName, problem);
		
		// Add single problem to ArrayList
		ArrayList<Problem> problems = new ArrayList<>(Arrays.asList(problem));
		
		// Check if the problem in the csv file is correct
		assertTrue(checkProblemsInFile(problems));
	}
	
	// Tests adding a multiple problems to an assignment file
	@Test
	public void testAddProblem_multipleProblems() {
		ArrayList<Problem> problems = new ArrayList<>();
		
		// Initialize file
		AssignmentCreator.initializeFile(fileName, dueDate);
				
		// Add four unique problems to ArrayList
		for (int i = 0; i < 4; i++) {
			// Create dummy problem
			int problemID = i + 1;
			String problemString = "Testing" + i;
			ArrayList<String> options = new ArrayList<>(Arrays.asList("OptionA", "OptionB", "OptionC", "OptionD"));
			String solution = "OptionB";
			Problem problem = new Problem(problemID, problemString, options, solution);
			problems.add(problem);
			
			// Add problem to assignment
			AssignmentCreator.addProblem(fileName, problem);
		}
		
		// Check if the problem in the csv file is correct
		assertTrue(checkProblemsInFile(problems));
	}
}
