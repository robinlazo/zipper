package ZipCoder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame{
	Main() {
		setTitle("CoderZip");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Board());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
		
	
	}
}
