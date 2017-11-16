package models;

import java.util.ArrayList;

/**
 * Class to represent an assignment problem.
 */
public class Problem {
	private int problemID;
	private String problemString;
	private ArrayList<String> options;
	private String solution;
	
	public Problem(int problemID, String problemString, ArrayList<String> options, String solution) {
		this.problemID = problemID;
		this.problemString = problemString;
		this.options = options;
		this.solution = solution;
	}
	
	public boolean equals(Object other) {
		if(other instanceof Problem) {
			if(this.problemID == ((Problem) other).getProblemID() && 
					this.problemString.equals(((Problem) other).getProblemString()) && 
			this.options.equals(((Problem) other).options) && this.solution.equals(((Problem) other).solution)){
				return true;
				
			}
		}
		
		return false;
	}
	
	public int getProblemID() {
		return problemID;
	}
	
	public void setProblemID(int problemID) {
		this.problemID = problemID;
	}
	
	public String getProblemString() {
		return problemString;
	}
	
	public void setProblemString(String problemString) {
		this.problemString = problemString;
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}
	
	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}
	
	public String getSolution() {
		return solution;
	}
	
	public void setSolution(String solution) {
		this.solution = solution;
	}
}
