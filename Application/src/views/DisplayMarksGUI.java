package views;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class DisplayMarksGUI extends JFrame{

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String id = "1002428037";
					DisplayMarksGUI frame = new DisplayMarksGUI(id);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DisplayMarksGUI(String studentId) {
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,500);
		setLocationRelativeTo(null);
	}
}
