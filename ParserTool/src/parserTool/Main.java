package parserTool;

import java.awt.EventQueue;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen gui = new StartScreen();
					gui.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

}
