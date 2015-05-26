package parserTool;

import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen gui = new StartScreen();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}