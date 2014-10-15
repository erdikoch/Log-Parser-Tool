package parserTool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.border.EmptyBorder;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

@SuppressWarnings("serial")
public class ArrowLayoutPanel extends JPanel implements Scrollable {

	private Dimension viewportSize;
	private ArrayList<Arrow> arrows;

	public ArrowLayoutPanel(ArrayList<Arrow> arrowList, String userName) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		arrows = arrowList;

		int height = 0;
		int width = 0;
		JPanel titlePanel = setTitlePanel();
		JLabel userNameLabel = setUserNameLabel(userName);
		titlePanel.add(userNameLabel, BorderLayout.WEST);
		JLabel spidrLabel = setSpidrLabel();
		titlePanel.add(spidrLabel, BorderLayout.EAST);
		add(titlePanel);

		for (Arrow arrow : arrows) {
			add(arrow);
			height = height + arrow.getHeight();
			if (width == 0)
				width = arrow.getWidth();
		}
		if (height < AnalysisScreen.DISPLAY_PANE_SIZE.height) {
			viewportSize = new Dimension(width, height);
		} else {
			viewportSize = new Dimension(width,
					AnalysisScreen.DISPLAY_PANE_SIZE.height);
		}
	}

	private JLabel setSpidrLabel() {
		JLabel spidrLabel = new JLabel("SPiDR");
		spidrLabel.setBackground(AnalysisScreen.BACKGROUND_BLUE);
		spidrLabel.setBorder(new EmptyBorder(15, 0, 10, 135));
		return spidrLabel;
	}

	private JLabel setUserNameLabel(String userName) {
		JLabel userNameLabel = new JLabel(userName);
		userNameLabel.setBackground(AnalysisScreen.BACKGROUND_BLUE);
		userNameLabel.setBorder(new EmptyBorder(15, 130, 10, 0));
		return userNameLabel;
	}

	private JPanel setTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBackground(AnalysisScreen.BACKGROUND_BLUE);
		return titlePanel;
	}

	public ArrayList<Arrow> getArrows() {
		return arrows;
	}

	public Dimension getPreferredScrollableViewportSize() {
		return viewportSize;
	}

	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

}
