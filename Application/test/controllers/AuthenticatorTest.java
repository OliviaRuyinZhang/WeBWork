package controllers;
import controllers.Authenticator;
import static org.junit.Assert.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;


public class AuthenticatorTest {
	static File f;
	static FileWriter fw;
	static StringBuilder sb;
	static BufferedWriter bf;
	


	@BeforeClass
	public static void setUp() throws IOException {
		TestFileHandler.setUp();
		
		f = new File("users.csv");
		fw = new FileWriter(f.getName());
		bf = new BufferedWriter(fw);
		sb = new StringBuilder();
		sb.append("TRUE,julian.barker@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Julian	Barker");
		sb.append("\n");
		sb.append("FALSE,ruyin.zhang@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Olivia	Zhang,1002205883");
		sb.append("\n");
		sb.append("TRUE,tito.ku@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Tito Ku");
		bf.write(sb.toString());
		bf.close();
	}	


	/**
	 * Test whether the user is a instructor or not by given the user's email
	 * return true when the user is an instructor
	 */
	@Test
	public void testisInstructor() {
		String email = "julian.barker@mail.utoronto.ca";
		assertTrue(Authenticator.isInstructor(email));
	}
	
	/**
	 * Test whether the user is a instructor or not by given the user's email
	 * return false when the user is an instructor
	 */
	@Test
	public void testIsNotInstructor() {
		String email = "ruyin.zhang@mail.utoronto.ca";
		
		assertFalse(Authenticator.isInstructor(email));
	}
	
	/**
	 * Test whether the user enters a correct user name and password to login
	 * return true when the user provides correct user name and password
	 */
	@Test
	public void testAuthenticate() {
		String email = "ruyin.zhang@mail.utoronto.ca";
		String password = "123";
		assertTrue(Authenticator.authenticate(email, password));
	}
	
	/**
	 * Test whether the user enters a correct user name and password to login
	 * return false when the user provides incorrect user name and password
	 */
	@Test
	public void testFailAuthenticate() {
		String email = "ruyin.zhang@mail.utoronto.ca";
		String password = "1233";
		assertFalse(Authenticator.authenticate(email, password));
	}
	
	@Test
	public void tearDown() {
		f.delete();		
		TestFileHandler.tearDown();
	}
	
	
}
