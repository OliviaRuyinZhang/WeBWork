package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

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
	public static boolean addProblem(String fileName, int problemId, String problem, String solution, ArrayList<String> options) {
		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bf = new BufferedWriter(fw);

			// Add each of the problem's items
			StringBuilder sb = new StringBuilder();
			sb.append(problemId);
			sb.append(","); // Next column.
			sb.append(problem);
			sb.append(","); // Next column.
			sb.append(solution);
			sb.append(","); // Next column.
			for (int i = 0; i < numOptions; i++) {
				sb.append(options.get(i));
				if (i != numOptions -1) {
					sb.append("|");
				}
			}

			sb.append("\n"); // Next row.
			bf.write(sb.toString());
			bf.close();

			System.out.println("Problem added successfully");
			fw.close();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Wipes out the given file and adds the current date and due date as
	 * the first row in the assignment csv file.
	 * @param fileName: The file to be initialized
	 * @param dueDate: The assignment's due date
	 */
	private static void initializeFile(String fileName, String dueDate) {
		try {
			FileWriter fw = new FileWriter(fileName, false);
			BufferedWriter bf = new BufferedWriter(fw);
			
			// Format today's date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.now();
			
			// Add today's date and the due date to the file
			bf.write(dtf.format(localDate) + "," + dueDate + "\n");
			bf.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String argvs[]) {
		String input;
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("CREATING A NEW ASSIGNMENT");
		System.out.println("=========================");
		System.out.print("What is the assignment number?: ");
		input = reader.nextLine();

		// csv file name
		String assignmentcsv = "assignment" + input + ".csv";

		File assignment = new File(assignmentcsv);

		// Loop until the user gives permission to either wipe old assignment file or start a new one
		while (assignment.exists()) {
			System.out.print("Warning: An assignment already exists with this number.\n"
					+ "If you proceed, the existing assignment will be wiped. Continue? [yes] [no]: ");
			input = reader.nextLine();
			if (input.equalsIgnoreCase("yes")) {
				break;
			} else {
				System.out.print("What assignment # is this?: ");
				input = reader.nextLine();
				// csv file name.
				assignmentcsv = "assignment" + input + ".csv";
				assignment = new File(assignmentcsv);
			}
		}
		
		System.out.print("When is the assignment due? [dd/mm/yyyy]: ");
		input = reader.nextLine();
		
		initializeFile(assignmentcsv, input);

		System.out.print("Add new problem? [yes] [no]: ");
		input = reader.nextLine();
		int problemId = 1;

		while(input.equalsIgnoreCase("yes")) {
			System.out.print("Enter the actual problem: ");
			String problem = reader.nextLine();
			System.out.print("Enter the problem's solution: ");
			String solution = reader.nextLine();
			ArrayList<String> options = new ArrayList<>();
			for (int i = 0; i < numOptions; i++) {
				System.out.print("Enter a multiple choice option: ");
				options.add(reader.nextLine());
			}

			addProblem(assignmentcsv, problemId, problem, solution, options);

			System.out.print("Add new problem? [yes] [no]: ");
			input = reader.nextLine();
			problemId++;
		}

		// Once finished
		reader.close();
	}
}
