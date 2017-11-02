package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import models.Problem;

/**
 * Class to handle creation of assignments as .csv files.
 */
public class AssignmentCreator {

	// Number of multiple choice options per problem
	public static final int numOptions = 4;

	/**
	 * Writes a new problem for the given assignment file into the assignment#.csv file.
	 * @param fileName: The assignment's file name in the format assignment#.csv
	 * @param problemId: The problem's ID
	 * @param problem: The actual assignment problem/question
	 * @param solution: The problem's solution
	 * @param options: A list of multiple choice options for the problem
	 */
	public static boolean addProblem(String fileName, Problem problem) {
		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bf = new BufferedWriter(fw);

			// Add each of the problem's items
			StringBuilder sb = new StringBuilder();
			sb.append(problem.getProblemID());
			sb.append(","); // Next column.
			sb.append(problem.getProblemString());
			sb.append(","); // Next column.
			sb.append(problem.getSolution());
			sb.append(","); // Next column.
			for (int i = 0; i < numOptions; i++) {
				sb.append(problem.getOptions().get(i));
				if (i != numOptions -1) {
					sb.append("|");
				}
			}

			sb.append("\n"); // Next row.
			bf.write(sb.toString());
			bf.close();

			fw.close();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Returns true if the first cell of assignment#.csv is "Released"
	 * @param fileName: The file where release status is to be checked
	 * @return: true if first cell is "Released"
	 */
	public static boolean isReleased(String fileName){
		boolean status = true;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			// Check first cell for unreleased
			String line = br.readLine();
			if (line.charAt(0) == 'U'){
				status = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Toggles first row first col cell between Unreleased and Released
	 * strings.
	 * @param fileName: The file where release status is to be toggled
	 */
	public static void toggleReleaseStatus(String fileName){
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			// Get old string
			String line = br.readLine();

			// Make new string
			String newLine = "";
			// Toggle status for new line
			if (!isReleased(fileName)){
				newLine = "Released";
			} else {
				newLine = "Unreleased";
			}
			// Add rest of old line
			int addFromIndex = line.indexOf(",");
			for(int i = addFromIndex; i < line.length(); i ++){
				newLine = newLine + line.charAt(i);
			}

			br.close();
			fr.close();

			// Write new status
			FileWriter fw = new FileWriter(fileName, false);
			BufferedWriter bf = new BufferedWriter(fw);

			bf.write(newLine + "\n");

			bf.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wipes out the given file and adds the unreleased tag, current date 
	 * due date and as the first row in the assignment csv file.
	 * @param fileName: The file to be initialized
	 * @param dueDate: The assignment's due date
	 */
	public static void initializeFile(String fileName, String dueDate) {
		try {
			FileWriter fw = new FileWriter(fileName, false);
			BufferedWriter bf = new BufferedWriter(fw);

			// Format today's date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.now();

			// Add unreleased tag today's date and the due date 
			// to the file
			bf.write("Unreleased," + dtf.format(localDate) + "," + dueDate + "\n");
			bf.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
