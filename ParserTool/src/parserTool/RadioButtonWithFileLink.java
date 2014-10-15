package parserTool;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

@SuppressWarnings("serial")
public class RadioButtonWithFileLink extends JRadioButton {

	private String textToBeDisplayed;

	private int selectionStart;
	private int selectionEnd;
	private boolean firstClickForFocus = true;

	// Text area to be displayed on the main display when
	// TextLayout is selected
	private JTextPane textArea;

	public RadioButtonWithFileLink(TaggedString taggedFile) {
		String textOnButton = taggedFile.getTag();
		setText(textOnButton);
		textToBeDisplayed = taggedFile.getString();
		setTextArea();
	}

	public RadioButtonWithFileLink(String name) {
		setText(name);
		textToBeDisplayed = "";
		setTextArea();
	}

	private void setTextArea() {
		textArea = new JTextPane();
		textArea.setContentType("text/html");
		textArea.setText(textToBeDisplayed);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
		textArea.setEditable(false);
		textArea.setFocusable(true);
		textArea.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent e) {
				selectionStart = textArea.getSelectionStart();
				selectionEnd = textArea.getSelectionEnd();
			}
		});
		textArea.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				if (firstClickForFocus) {
					firstClickForFocus = false;
				} else {
					setTextAreaSelection();
				}
			}

			public void focusLost(FocusEvent ev) {
				setTextAreaSelection();
			}
		});

	}

	public void setTextAreaSelection() {
		textArea.select(selectionStart, selectionEnd);
		textArea.getCaret().setSelectionVisible(true);
	}

	public String getTextToBeDisplayed() {
		return textToBeDisplayed;
	}

	public void setFile(String inputFile) {
		textToBeDisplayed = inputFile;
		refreshText();
	}

	public JTextPane getTextArea() {
		return textArea;
	}

	private void refreshText() {
		textArea.setText(textToBeDisplayed);
	}

}
