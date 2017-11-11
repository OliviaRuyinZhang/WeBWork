package controllers;

import java.awt.EventQueue;

import views.AssignmentCompletionGUI;
import views.LoginRegisterGUI;
import views.StudentListingGUI;

public class ApplicationDriver {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				AssignmentCompletionGUI frame = new AssignmentCompletionGUI("Assignment1.csv");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		});
}

}
