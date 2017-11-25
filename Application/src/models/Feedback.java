package models;

/*
 * Feedback object
 */
public class Feedback {

	String subject;
	String message;
	String date;
	
	public Feedback(String subject, String date) {
		this.subject = subject;
		this.message = "";
		// Gets current day.
		this.date = date; 
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setMessage(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getDate() {
		return this.date;
	}
}