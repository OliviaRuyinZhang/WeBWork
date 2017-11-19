package controllers;
import controllers.Authenticator;
import static org.junit.Assert.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class AuthenticatorTest {
	FileWriter fw;
	BufferedWriter bf;
	StringBuilder sb;

	public void setUp() {	
		try {
			fw = new FileWriter("users.csv", true);
			bf = new BufferedWriter(fw);
			sb = new StringBuilder();
			sb.append("TRUE,julian.barker@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Julian	Barker");
			sb.append("FALSE,ruyin.zhang@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Olivia	Zhang,1002205883");
			sb.append("TRUE,tito.ku@mail.utoronto.ca,a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3,Tito Ku");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Test
	public void testisInstructor() {
		String email = "julian.barker@mail.utoronto.ca";
		try {
			bf.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Authenticator.isInstructor(email));
	}
	
	@Test
	public void testIsNotInstructor() {
		String email = "ruyin.zhang@mail.utoronto.ca";
		try {
			bf.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(Authenticator.isInstructor(email));
	}
	
	@Test
	public void testAuthenticate() {
		String email = "ruyin.zhang@mail.utoronto.ca";
		String password = "123";
		assertTrue(Authenticator.authenticate(email, password));
	}
	
	@Test
	public void testFailAuthenticate() {
		String email = "ruyin.zhang@mail.utoronto.ca";
		String password = "1233";
		assertFalse(Authenticator.authenticate(email, password));
	}
	
	@Test
	public void tearDown() {
		
	}
	
	
}
