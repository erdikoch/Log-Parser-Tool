package parserTool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

@SuppressWarnings("serial")
public class ArrowButton extends JButton {

	private static final boolean POINT_RIGHT = false;
	private static final boolean POINT_LEFT = true;
	private static final boolean SENT = POINT_RIGHT;
	private static final boolean RECEIVED = POINT_LEFT;

	private String startDateAndTime;

	// Will be used to mark arrows representing different calls with different
	// colors when there are multiple calls within a single log. This function
	// has not yet been implemented.
	private String sessionData;

	private String eventType;

	// The associated section from the log file, to be displayed in a
	// ToolTip box when the mouse is hovered over the button and in a
	// separate frame when the button is clicked
	private String text;

	private boolean arrowDirection;

	private String toolTipText;

	// The text display frame that is opened when the button is clicked
	private JFrame frame = null;

	// The text area displayed on the frame
	private JTextPane textArea;

	// Indicates that the button is currently highlighted yellow by the finder
	private boolean primarySelection = false;

	// Indicates that the button is currently selected by the finder
	private boolean selected = false;

	public ArrowButton(String associatedText) {
		initializeButton(associatedText);
	}

	private void initializeButton(String associatedText) {
		text = associatedText;
		setSessionID();
		setEventType();
		setArrowDirection();
		startDateAndTime = text.substring(0,
				LogParser.DATE_AND_TIME_LENGTH_WITH_MILLISECONDS);
		setFocusable(false);
		setAlignmentY(TOP_ALIGNMENT);
		initializeArrowFrame();
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

				if (!frame.isVisible()) {
					frame.setVisible(true);
				} else {
					frame.requestFocus();
				}
			}

		});
		drawArrow();
		setDefaultBorder();

		try {
			toolTipText = "<html>" + getStartDateAndTime() + "<br>"
					+ getSessionID().substring(0, 21) + "..." + "<br>"
					+ getEventType() + "</html>";
		} catch (StringIndexOutOfBoundsException e) {
			toolTipText = "<html>" + getStartDateAndTime() + "<br>"
					+ getEventType() + "</html>";
		} catch (NullPointerException ex) {
			toolTipText = "<html>" + getStartDateAndTime() + "<br>"
					+ getEventType() + "</html>";
		}
		setToolTipText(toolTipText);
	}

	private void setArrowDirection() {
		if (text.startsWith(DataTypeParser.RECEIVED)) {
			arrowDirection = RECEIVED;
			text = text.substring(text.indexOf("]") + 1);
		} else if (text.startsWith(DataTypeParser.SENT)) {
			arrowDirection = SENT;
			text = text.substring(text.indexOf("]") + 1);
		}
	}

	private void drawArrow() {
		if (arrowDirection == POINT_LEFT) {
			setIcon(new ImageIcon(
					"C:\\Users\\erdikoch\\Desktop\\workspace\\ParserTool\\sol.png"));
			setText(eventType);
			Font font = new Font("Verdana", Font.ITALIC, 12);
			setFont(font);
			setBackground(Color.WHITE);
		} else if (arrowDirection == POINT_RIGHT) {
			setIcon(new ImageIcon(
					"C:\\Users\\erdikoch\\Desktop\\workspace\\ParserTool\\sag.png"));
			setText(eventType);
			Font font = new Font("Verdana", Font.ITALIC, 12);
			setFont(font);
			setBackground(Color.WHITE);
		}
		setVisible(true);
	}

	public String getStartDateAndTime() {
		return startDateAndTime;
	}

	public String getSessionID() {
		return sessionData;
	}

	private void setSessionID() {
		try {
			int sessionIDStartPosition = text.indexOf("\"",
					text.indexOf("sessionData")) + 1;
			int sessionIDEndPosition = text.indexOf("\"",
					sessionIDStartPosition) - 1;
			sessionData = text.substring(sessionIDStartPosition,
					sessionIDEndPosition + 1);
		} catch (StringIndexOutOfBoundsException e) {
			sessionData = null;
		}
	}

	public String getAssociatedText() {
		return text;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType() {
		int eventTypeStartPosition = text.indexOf("eventType:");
		int eventTypeEndPosition = text.indexOf("\n", eventTypeStartPosition);

		eventType = text
				.substring(eventTypeStartPosition, eventTypeEndPosition);
	}

	/* Invariant: isSelected is true whenever isPrimarySelection is true */
	public void setPrimarySelection(boolean b) {
		primarySelection = b;
		if (primarySelection) {
			selected = true;
			setPrimarySelectionBorder();
		} else {
			if (selected) {
				setSelectedBorder();
			} else {
				setDefaultBorder();
			}
		}
	}

	public boolean isPrimarySelection() {
		return primarySelection;
	}

	private void setSelectedBorder() {
		setBorder(new MatteBorder(10, 10, 10, 10, Color.LIGHT_GRAY));
	}

	private void setPrimarySelectionBorder() {
		setBorder(new MatteBorder(10, 10, 10, 10, Color.YELLOW));
	}

	public void setDefaultBorder() {
		setBorder(new CompoundBorder(new MatteBorder(10, 10, 10, 10,
				AnalysisScreen.BACKGROUND_BLUE), new MatteBorder(1, 1, 1, 1,
				Color.BLACK)));
	}

	public boolean getIsSelected() {
		return selected;
	}

	/* Invariant: isPrimarySelection is false whenever isSelected is false */
	public void unselect() {
		selected = false;
		setDefaultBorder();
		primarySelection = false;
	}

	public void select() {
		if (primarySelection) {
			setPrimarySelectionBorder();
		} else {
			setSelectedBorder();
		}
	}

	public JTextPane getTextArea() {
		return textArea;
	}

	private void initializeArrowFrame() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		textArea = new JTextPane();
		textArea.setText(text);
		textArea.setAutoscrolls(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(1200, 300);
		frame.setResizable(true);
		frame.setVisible(false);
	}
}
