package models;

import java.util.Calendar;
import java.util.Date;

/*
 * Feedback object
 */
public class Feedback {

	String subject;
	String message;
	Calendar date;
	
	public Feedback(String subject, Calendar date) {
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
	
	public Date getDate() {
		return this.date.getTime();
	}
}