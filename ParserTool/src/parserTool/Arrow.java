package parserTool;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

// Panel that contains an arrow button and a corresponding label.
// Several of these panels are placed in an ArrowLayoutPanel.

@SuppressWarnings("serial")
public class Arrow extends JPanel {

	private ArrowButton arrowButton;

	public Arrow(ArrowButton button) {
		setLayout(new BorderLayout());
		ImageIcon wallIcon = new ImageIcon(
				"C:\\Users\\erdikoch\\Desktop\\workspace\\ParserTool\\label.png");
		JLabel leftWall = setLeftWall(wallIcon);
		JLabel rightWall = setRightWall(wallIcon);
		add(leftWall, BorderLayout.WEST);
		add(rightWall, BorderLayout.EAST);
		arrowButton = button;
		add(arrowButton, BorderLayout.CENTER);
		setAlignmentY(TOP_ALIGNMENT);
	}

	private JLabel setRightWall(ImageIcon wallIcon) {
		JLabel rightWall = new JLabel(wallIcon);
		rightWall.setBorder(new MatteBorder(0, 50, 0, 150,
				AnalysisScreen.BACKGROUND_BLUE));
		return rightWall;
	}

	private JLabel setLeftWall(ImageIcon wallIcon) {
		JLabel leftWall = new JLabel(wallIcon);
		leftWall.setBorder(new MatteBorder(0, 150, 0, 50,
				AnalysisScreen.BACKGROUND_BLUE));
		return leftWall;
	}

	public ArrowButton getArrowButton() {
		return arrowButton;
	}

}
