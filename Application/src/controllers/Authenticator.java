package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Class to handle authentication of users.
 */
public class Authenticator {

	// csv file name.
	public static final String userscsv = "users.csv";

	/**
	 * This is a register method for instructors Writes the user's email and
	 * password and whether they are an instructor or not into the users.csv file.
	 * 
	 * @param is_instructor:
	 *            true if the user is an instructor, false if they are a student
	 * @param email:
	 *            user's email address
	 * @param password:
	 *            user's password
	 */
	public static boolean register(boolean is_instructor, String email, String password, String firstName,
			String lastName)

	{
		try {

			FileWriter fw = new FileWriter(userscsv, true);
			if (!userExists(email)) {

				String hashVal = hashPassword(password);
				BufferedWriter bf = new BufferedWriter(fw);

				StringBuilder sb = new StringBuilder();
				sb.append(is_instructor);
				sb.append(","); // Next column.
				sb.append(email);
				sb.append(","); // Next column.
				sb.append(hashVal.toString());
				sb.append(",");
				sb.append(firstName);
				sb.append(",");
				sb.append(lastName);
				sb.append("\n"); // Next row.
				bf.write(sb.toString());
				bf.close();

				return true;
			}
			fw.close();
			return false;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * This is a register method for students Writes the user's email and password
	 * and whether they are an instructor or not into the users.csv file.
	 * 
	 * @param is_instructor:
	 *            true if the user is an instructor, false if they are a student
	 * @param email:
	 *            user's email address
	 * @param password:
	 *            user's password
	 */
	public static boolean register(boolean is_instructor, String email, String password, String firstName,
			String lastName, String studentID) {
		{
			try {

				FileWriter fw = new FileWriter(userscsv, true);
				if (!userExists(email)) {

					String hashVal = hashPassword(password);
					BufferedWriter bf = new BufferedWriter(fw);

					StringBuilder sb = new StringBuilder();
					sb.append(is_instructor);
					sb.append(","); // Next column.
					sb.append(email);
					sb.append(","); // Next column.
					sb.append(hashVal.toString());
					sb.append(",");
					sb.append(firstName);
					sb.append(",");
					sb.append(lastName);
					sb.append(",");
					sb.append(studentID);
					sb.append("\n"); // Next row.
					bf.write(sb.toString());
					bf.close();
					return true;
				}
				fw.close();
				return false;

			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			return false;
		}
	}

	/**
	 * Compares the email and password combination to the email and password
	 * existing in users.csv file. Returns true if the email matches the same
	 * password in the file.
	 * 
	 * @param email:
	 *            user's email address
	 * @param password:
	 *            user's password
	 * @return: true if the authentication was successful, false otherwise
	 */
	public static boolean authenticate(String email, String password) {

		try {
			FileReader fr = new FileReader(userscsv);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// Reads every line of the file.
			while ((line = br.readLine()) != null) {

				String[] user_info = line.split(","); // [isInstructor, email, password]
				// If the email exists in the file.
				if (user_info[1].equals(email)) {
					String hash_val = hashPassword(password);

					/*
					 * If password argument hashed is the same as the hashed password in the file.
					 */
					if (user_info[2].equals(hash_val)) {
						return true;
					} else {
						return false;
					}
				}
			}

			br.close();
			fr.close();
			return false;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Helper function that returns true if an account with the inputted email
	 * already exists.
	 * 
	 * @param email:
	 *            user's email address
	 * @return: true if email is already in the users.csv file, false otherwise
	 * @throws IOException
	 */
	private static boolean userExists(String email) throws IOException {
		FileReader fr = new FileReader(userscsv);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		// Reads every line of the file.
		while ((line = br.readLine()) != null) {

			String[] user_info = line.split(","); // [isInstuctor, email, password]

			// If the email exists in the file.
			if (user_info[1].equals(email)) {
				return true;
			}
		}

		br.close();
		fr.close();
		return false;
	}

	/**
	 * Hashes password String using SHA-256 and returns the String representation of
	 * the array of bytes digestion.
	 * 
	 * @param password:
	 *            user's password
	 * @return: hashed value of password
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private static String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		/*
		 * Courtesy of Stack Exchange:
		 * "https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java"
		 */
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		byte[] digest = md.digest();
		md.reset();
		return String.format("%064x", new java.math.BigInteger(1, digest));
	}

	/**
	 * Checks if the corresponding login email is an instructor or not by checking
	 * information in the corresponding userscsv file.
	 * 
	 * @param email:
	 *            user's email
	 * @return: a boolean (true/false) if login email is an instructor
	 * @throws IOException
	 */
	public static boolean isInstructor(String email) {
		try {
			boolean isInstruc = false;
			FileReader fr = new FileReader(userscsv);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] user_info = line.split(",");
				if (user_info[1].equals(email)) {
					if (user_info[0].equalsIgnoreCase("true")) {
						isInstruc = true;
					}
				}
			}

			br.close();
			fr.close();
			return isInstruc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
