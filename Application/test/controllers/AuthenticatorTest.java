package controllers;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import controllers.Authenticator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuthenticatorTest {

	FileReader usersFile;
	
	@BeforeClass
	public static void setup() {
		TestFileHandler.setUp();
		try {
			FileWriter usersFile = new FileWriter("users.csv");
			BufferedWriter usersbw = new BufferedWriter(usersFile);
			usersbw.write("TRUE,instruct@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Tito,Ku\r\n");
			usersbw.write("FALSE,stu@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Julian,Baker,1001521775\r\n");
			usersbw.close();
			usersFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void teardown() {
		File usersFile = new File ("users.csv");
		usersFile.delete();
		TestFileHandler.tearDown();
	}
	
	@Test
	public void testisInstructor() {
		String email = "instruct@mail.utoronto.ca";
		assertTrue(Authenticator.isInstructor(email));
	}
	
	@Test
	public void testisNotInstructor() {
		String  email = "stu@mail.utoronoto.ca";
		assertFalse(Authenticator.isInstructor(email));
	}

	@Test
	public void testAuthenticate() {
		String email = "instruct@mail.utoronto.ca";
		String pass = "123";
		assertTrue(Authenticator.authenticate(email, pass));
	}
}
