package models;

/**
 * Class to store information regarding a single feedback.
 */
public class Feedback {

	String subject;
	String message;
	String date;
	
	public Feedback(String subject, String message, String date) {
		this.subject = subject;
		this.message = message;
		this.date = date; 
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getDate() {
		return this.date;
	}
}