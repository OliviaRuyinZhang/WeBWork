package controllers;

import javax.swing.JLabel;
import javax.swing.JTextField;

public interface RegisterValidation {
	public void register();
	public void validateNameInput(JLabel label, JTextField name, String labelText);
	public boolean isAlphabetical(String name);
	public void clearTextFields();
}
