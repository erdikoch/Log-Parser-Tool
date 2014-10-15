package parserTool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

@SuppressWarnings("serial")
public class AnalysisScreen extends JFrame {

	private static final int POSITION_OF_FIRST_RADIO_BUTTON = 2;
	public static final Color BACKGROUND_BLUE = Color.getHSBColor(0.6f, 0.25f,
			0.8f);
	public static final Dimension DISPLAY_PANE_SIZE = new Dimension(270, 730);

	private AnalysisScreen analysisScreen = this;

	private boolean writeToFiles;

	private ThreadGroup threadGroup;
	private Thread tagParserThread;
	private Thread dataTypeParserThread;
	private Thread initCompThread;

	private LoadPage loadPage;

	private ReentrantLock errorLock;

	public ArrayList<ColoredTag> tags;

	// Chart that displays the count for each tag
	private TagChart chart;

	// Main display, located on the left hand side of the panel
	private JScrollPane displayPane;

	// Contains the radio buttons for tags
	private JPanel radioButtonPanel;

	// Contains radioButtonPanel, needed to make newly created buttons visible
	// in case radioButtonPanel does not fit within the window
	private JScrollPane radioButtonScrollPane;

	// Contains arrowLayoutButton and textLayoutButton
	private JPanel layoutButtonPanel;

	// Switches to ArrowLayout when pressed
	private JRadioButton arrowLayoutButton;

	// Switches to textLayout when pressed
	private JRadioButton textLayoutButton;

	// Text field to enter text to be found
	private JTextField finderTextField;

	// List of arrows selected by the finder
	private ArrayList<ArrowButton> selectedButtons;

	private JLabel showLabel;
	private JLabel findLabel;
	private JButton findButton;
	private JButton searchButton;
	private JButton quitButton;
	private JButton chartButton;
	private JCheckBox writeToFilesCheckBox;
	private File logFile;

	// Tag parser to use in TextLayout
	TagParser tagParser;

	// Data type parser to use in ArrowLayout
	DataTypeParser dataTypeParser;

	// Variables to set focus on finder on Ctrl+F
	AbstractAction focusOnFinder;
	private final String focus = "focus on finder";
	private final KeyStroke CtrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F,
			Event.CTRL_MASK);

	// Constant to use in finder() to indicate that
	// there are no more instances of the given string
	private static int NO_MORE_INSTANCES = -1;

	// Text area that is displayed on the main display when
	// "TextLayout" is selected
	JTextPane textArea;

	// Panel that is displayed on the main display when
	// "ArrowLayout" is selected
	ArrowLayoutPanel arrowPanel;

	// Indicates position of component on its parent panel
	int componentPositionOnPanel;

	// Holds the starting positions in the text area for each instance of
	// the input string in finder()
	private ArrayList<Integer> textPositions = new ArrayList<Integer>();

	// String that was last entered in the text field for
	// the "find in text" function
	private String existingText = "";

	// Tag for the highlighter that is used to highlight
	// the currently selected string
	// (selection is made with the finder)
	private Object yellowHighlighter = null;

	// Variables for grouping the radio buttons
	private ButtonGroup tagRadioButtonGroup;
	private ArrayList<RadioButtonWithFileLink> tagRadioButtonList;
	// private ButtonGroup dataTypeRadioButtonGroup;
	// private ArrayList<RadioButtonWithFileLink> dataTypeRadioButtonList;

	// Panel to hold the radio buttons for data types in arrow layout
	// JPanel dataTypePanel;

	// Text field to enter custom tag for errors
	JTextField customTagField;

	JLabel customTagLabel;

	// Generates custom radio button with custom file.
	// Gets text from customTagField and is located right next to it.
	JButton customTagUpdateButton;

	// Describes the user how to enter multiple tags at the same time
	JLabel inputDescriptionLabel;

	JPanel customTagPanel;
	JPanel customTagTextPanel;
	JPanel customTagButtonPanel;

	// Custom tag radio button
	RadioButtonWithFileLink customTagButton;

	// Constructor
	public AnalysisScreen(final StartScreen startScreen, File log,
			ThreadGroup group, final boolean writeToFilesIsChecked)
			throws FileNotFoundException {

		errorLock = new ReentrantLock();
		loadPage = startScreen.getLoadPage();
		threadGroup = group;

		tagParserThread(startScreen, writeToFilesIsChecked);
		dataTypeParserThread(startScreen);
		tagChartAndFinderThread(startScreen);

		addTags();
		logFile = log;

		// Put initialization outside initCompThread for synchronization
		radioButtonPanel = new JPanel();

		startThreads();
	}

	private void startThreads() {
		tagParserThread.start();
		dataTypeParserThread.start();
		initCompThread.start();
	}

	private void tagChartAndFinderThread(final StartScreen startScreen) {
		initCompThread = new Thread(threadGroup, new Runnable() {
			public void run() {

				try {
					chart = new TagChart("Tag Statistics",startScreen.getPathTextField());

					// Synchronization needed to set parent frame to error
					// dialogs
					// which can be created in the updateCustomTag method
					synchronized (analysisScreen) {

						tagRadioButtonGroup = new ButtonGroup();
						tagRadioButtonList = new ArrayList<RadioButtonWithFileLink>();

						initComponents();

						// Set focus to finderTextField on Ctrl+F
						focusOnFinder = new AbstractAction() {
							public void actionPerformed(ActionEvent e) {
								finderTextField.requestFocusInWindow();
							}
						};

						getRootPane().getInputMap().put(CtrlF, focus);
						getRootPane().getActionMap().put(focus, focusOnFinder);
						customTagField.getInputMap().put(CtrlF, focus);
						customTagField.getActionMap().put(focus, focusOnFinder);

						synchronized (radioButtonPanel) {
							initializeRadioButtonPanel();
						}

						addActionListeners();
						startScreen.getLoadPage().dispose();
						setVisible(true);
					}
				} catch (Exception e) {

					stopAllThreads(startScreen, initCompThread);
				}
			}
		});
	}

	private void dataTypeParserThread(final StartScreen startScreen) {
		dataTypeParserThread = new Thread(threadGroup, new Runnable() {
			public void run() {

				try {
					synchronized (logFile) {
						dataTypeParser = new DataTypeParser(logFile);
						dataTypeParser.parse();
						arrowPanel = dataTypeParser.getArrowPanel();
					}

				} catch (Exception e) {

					stopAllThreads(startScreen, dataTypeParserThread);

				}

			}
		});
	}

	private void tagParserThread(final StartScreen startScreen,
			final boolean writeToFilesIsChecked) {
		tagParserThread = new Thread(threadGroup, new Runnable() {
			public void run() {

				try {
					synchronized (radioButtonPanel) {
						tagParser = new TagParser(logFile);
						tagParser.parse(tags);
					}

					setTitle(tagParser.startDateAndTime);

					if (writeToFilesIsChecked)
						tagParser.writeToFiles(analysisScreen);

				} catch (Exception e) {

					stopAllThreads(startScreen, tagParserThread);
				}

			}
		});
	}

	private void findButtonActionPerformed(ActionEvent e) {
		if (textLayoutButton.isSelected())
			findInTextArea();
		if (arrowLayoutButton.isSelected())
			findInArrowLayoutPanel();
	}

	private void finderTextFieldActionPerformed(ActionEvent e) {
		if (textLayoutButton.isSelected())
			findInTextArea();
		if (arrowLayoutButton.isSelected())
			findInArrowLayoutPanel();
	}

	// Set the view to ArrowLayout on click
	private void arrowLayoutButtonActionPerformed(ActionEvent e) {
		radioButtonPanel.setVisible(false);
		displayPane.setViewportView(arrowPanel);
	}

	// Set the view to text layout on click
	private void textLayoutButtonActionPerformed(ActionEvent e) {
		radioButtonPanel.setVisible(true);
		for (RadioButtonWithFileLink button : tagRadioButtonList) {
			if (button.isSelected()) {
				setTextArea(button);
			}
		}
	}

	private void setTextArea(RadioButtonWithFileLink button) {
		button.setTextAreaSelection();
		textArea = button.getTextArea();
		displayPane.setViewportView(textArea);
	}

	private void quitButtonActionPerformed(ActionEvent e) {
		System.exit(ABORT);
	}

	private void buttonSearchActionPerformed(ActionEvent e) {
		new TextEditor();
	}

	private void updateCustomTag() {
		String tag = customTagField.getText();
		boolean tagsContainsTag = false;
		for (ColoredTag coloredTag : tags) {
			if (coloredTag.getTag().equals(tag)) {
				tagsContainsTag = true;
				break;
			}
		}
		if (!(customTagField.getText().isEmpty() || tagsContainsTag)) {
			final RadioButtonWithFileLink newButton = customTagButton;
			newButton.setText(tag);
			TaggedString customCreationFile = new TaggedString(tag);
			try {
				tagParser.customParse(customCreationFile, writeToFiles);
				tagParser.getTaggedStrings().add(customCreationFile);
				tags.add(new ColoredTag(tag));
			} catch (IOException e) {
				synchronized (this) {
					JOptionPane.showMessageDialog(
							this,
							"Error in writing to file \" "
									+ TagParser.setFileName(customCreationFile
											.getTag()));
				}
			}
			newButton.setFile(customCreationFile.getString());
			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setTextArea(newButton);
				}
			};
			newButton.addActionListener(actionListener);
			newButton.setEnabled(true);

			createNewCustomTagButton();

		} else if (customTagField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Text field is empty");
		} else if (tagsContainsTag) {
			try {
				File f = new File(TagParser.setFileName(tag));
				Scanner scanner = new Scanner(f);
				scanner.next();
				scanner.close();
				buttonAlreadyExists();
			} catch (FileNotFoundException e) {
				if (writeToFiles) {
					try {
						tagParser.getTaggedStrings().get(tags.indexOf(tag))
								.writeToFile();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					buttonAlreadyExists();
				}
			}

		}
	}

	private void buttonAlreadyExists() {
		JOptionPane.showMessageDialog(this, "Button already exists");
	}

	private void initComponents() {

		displayPane = new JScrollPane();
		showLabel = new JLabel();
		radioButtonScrollPane = new JScrollPane();
		layoutButtonPanel = new JPanel();
		arrowLayoutButton = new JRadioButton();
		textLayoutButton = new JRadioButton();
		findLabel = new JLabel();
		finderTextField = new JTextField();
		findButton = new JButton();
		searchButton = new JButton();
		quitButton = new JButton();
		customTagPanel = new JPanel();
		customTagLabel = new JLabel();
		customTagField = new JTextField();
		customTagUpdateButton = new JButton();
		customTagTextPanel = new JPanel();
		customTagButtonPanel = new JPanel();
		inputDescriptionLabel = new JLabel();
		chartButton = new JButton();
		writeToFilesCheckBox = new JCheckBox();

		// ======== this ========

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] {
				32, 10, 512, 65, 52, 7, 65, 34, 172, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 0,
				6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6,
				0, 6, 0, 6, 0, 6, 0, 6, 0, 6 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] {
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] {
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 1.0 };

		// ======== radioButtonPanel ========
		{
			radioButtonPanel.setFocusable(false);
			radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel,
					BoxLayout.Y_AXIS));
			radioButtonPanel.setAlignmentX(LEFT_ALIGNMENT);
		}

		// ======== radioButtonScrollPane ========
		{
			radioButtonScrollPane.setPreferredSize(new Dimension(190, 450));
			radioButtonScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			radioButtonScrollPane.setViewportView(radioButtonPanel);
		}
		getContentPane().add(
				radioButtonScrollPane,
				new GridBagConstraints(6, 6, 3, 22, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 11, 5), 0, 0));

		// ======== displayPane ========
		{

			displayPane.setPreferredSize(new Dimension(1, 1));
			displayPane.setFocusable(false);
			displayPane.getViewport().setBackground(BACKGROUND_BLUE);
			synchronized (logFile) {
				displayPane.setViewportView(arrowPanel);
			}
		}
		contentPane.add(displayPane, new GridBagConstraints(0, 2, 6, 26, 0.0,
				0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(10, 10, 11, 6), 0, 0));

		// ======== layoutButtonPanel ========
		{
			layoutButtonPanel.setLayout(new BoxLayout(layoutButtonPanel,
					BoxLayout.X_AXIS));

			// Group the radio buttons for the arrow layout and the text layout
			ButtonGroup layoutmenu = new ButtonGroup();
			layoutmenu.add(arrowLayoutButton);
			layoutmenu.add(textLayoutButton);

			// ---- arrowLayoutButton ----
			arrowLayoutButton.setText("ArrowLayout");
			arrowLayoutButton.setSelected(true);
			arrowLayoutButton.setFocusable(false);
			arrowLayoutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					arrowLayoutButtonActionPerformed(e);
				}
			});

			layoutButtonPanel.add(arrowLayoutButton);

			// ---- textLayoutButton ----
			textLayoutButton.setText("TextLayout");
			textLayoutButton.setFocusable(false);
			textLayoutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textLayoutButtonActionPerformed(e);
				}
			});

			layoutButtonPanel.add(textLayoutButton);
		}
		contentPane.add(layoutButtonPanel, new GridBagConstraints(6, 28, 3, 2,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 1, 0), 0, 0));

		// ---- findLabel ----
		findLabel.setText("Find in text:");
		findLabel.setFocusable(false);
		contentPane.add(findLabel, new GridBagConstraints(1, 28, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 1, 5), 0, 0));

		// ---- finderTextField ----

		contentPane.add(finderTextField, new GridBagConstraints(2, 28, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 1, 6), 0, 0));

		// ---- findButton ----
		findButton.setText("Find");
		findButton.setFocusable(false);

		contentPane.add(findButton, new GridBagConstraints(3, 28, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 1, 1), 0, 0));

		// ---- searchButton ----
		searchButton.setText("Search...");

		contentPane.add(searchButton, new GridBagConstraints(4, 28, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 1, 1), 0, 0));

		// ---- quitButton ----
		quitButton.setText("Quit LogTool");
		quitButton.setFocusable(false);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitButtonActionPerformed(e);
			}
		});

		contentPane.add(quitButton, new GridBagConstraints(5, 28, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 1, 6), 0, 0));

		pack();
		setLocationRelativeTo(getOwner());

		// ---- writeToFilesCheckBox ----
		writeToFilesCheckBox.setText("Write custom output text to files");
		writeToFilesCheckBox.setFocusable(false);
		writeToFilesCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				writeToFiles = (e.getStateChange() == ItemEvent.SELECTED);
			}
		});

		customTagLabel.setText("Custom tags:  ");

		customTagField.setEditable(true);

		customTagUpdateButton.setText("Update");
		customTagUpdateButton.setFocusable(false);

		inputDescriptionLabel.setText("(insert \";;\" between tags) ");
		inputDescriptionLabel.setFont(new Font("Courier New", Font.ITALIC, 9));
		inputDescriptionLabel.setFocusable(false);

		customTagTextPanel.setLayout(new BorderLayout());
		customTagTextPanel.setMaximumSize(new Dimension(300, 33));
		customTagTextPanel.setBorder(new EmptyBorder(25, 0, 3, 0));
		customTagTextPanel.add(customTagLabel, BorderLayout.WEST);
		customTagTextPanel.add(customTagField, BorderLayout.CENTER);

		customTagButtonPanel.setLayout(new BorderLayout());
		customTagButtonPanel.setMaximumSize(new Dimension(300, 73));
		customTagButtonPanel.add(inputDescriptionLabel, BorderLayout.NORTH);
		customTagButtonPanel.add(customTagUpdateButton, BorderLayout.EAST);
		customTagButtonPanel.add(writeToFilesCheckBox, BorderLayout.SOUTH);

		customTagPanel
				.setLayout(new BoxLayout(customTagPanel, BoxLayout.Y_AXIS));
		customTagPanel.setAlignmentX(LEFT_ALIGNMENT);
		customTagPanel.add(customTagTextPanel);
		customTagPanel.add(customTagButtonPanel);

		// ---- showLabel ----
		showLabel.setText("Show:");
		showLabel.setFocusable(false);
		showLabel.setBorder(new EmptyBorder(15, 0, 0, 0));

		// ======== chartButton ========
		chartButton.setText("Display Chart");

		// Initialize non-visual attributes
		selectedButtons = new ArrayList<ArrowButton>();

	}

	private void initializeRadioButtonPanel() {

		radioButtonPanel.add(chartButton);
		radioButtonPanel.add(showLabel);

		componentPositionOnPanel = POSITION_OF_FIRST_RADIO_BUTTON;

		for (TaggedString f : tagParser.getTaggedStrings()) {
			RadioButtonWithFileLink button = new RadioButtonWithFileLink(f);
			addButtonToPanel(button);
			button.getTextArea().getInputMap().put(CtrlF, focus);
			button.getTextArea().getActionMap().put(focus, focusOnFinder);
		}

		createNewCustomTagButton();

		radioButtonPanel.add(customTagPanel);

		radioButtonPanel.setVisible(false);

	}

	private void createNewCustomTagButton() {
		customTagButton = new RadioButtonWithFileLink("(custom tag)");
		customTagButton.setEnabled(false);
		addButtonToPanel(customTagButton);
	}

	private void addButtonToPanel(RadioButtonWithFileLink button) {
		button.setFocusable(false);
		button.setAlignmentX(LEFT_ALIGNMENT);
		radioButtonPanel.add(button, componentPositionOnPanel);
		tagRadioButtonGroup.add(button);
		tagRadioButtonList.add(button);
		if (componentPositionOnPanel == POSITION_OF_FIRST_RADIO_BUTTON)
			button.setSelected(true);
		componentPositionOnPanel++;
	}

	/**
	 * Find and highlight every instance of the input string in the text area.
	 * Use caret position to mark the starting position of the currently
	 * selected string and highlight it yellow. Not case sensitive
	 */

	private void findInTextArea() {
		if (textArea.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No text in view");
		} else {
			String newText = finderTextField.getText().toLowerCase();

			// Terminates without any action if input string is empty
			if (newText.equals("")) {
				if (existingText.equals("")) {
					return;
				} else {
					selectNextInstanceOfText();
				}
			} else {

				// Check whether the user is trying to find the next instance of
				// a string that was already searched in the text area or
				// the user is searching for a new string in the text area
				if (!newText.equals(existingText)) {
					find(newText, textArea);
				} else {
					selectNextInstanceOfText();
				}
			}
		}
	}

	private void find(String newText, JTextPane inputTextArea) {
		textPositions = new ArrayList<Integer>();

		// For case insensitivity
		String lowerCaseText = inputTextArea.getText().toLowerCase();

		inputTextArea.getHighlighter().removeAllHighlights();
		existingText = newText;
		int startPositionInTextArea = 0;
		int endPositionInTextArea = 0;

		// Used to highlight first instance of text yellow in TextLayout
		boolean firstPosition = (inputTextArea == textArea);

		// Find all instances of the string in the text area
		// and highlight them
		while (endPositionInTextArea < inputTextArea.getText().length()) {
			startPositionInTextArea = lowerCaseText.indexOf(existingText,
					startPositionInTextArea);
			endPositionInTextArea = startPositionInTextArea
					+ (existingText.length());

			// Break the while loop if there are no more instances of
			// the given string to find
			if (startPositionInTextArea == NO_MORE_INSTANCES) {
				break;
			} else {
				textPositions.add(endPositionInTextArea);
				try {

					// Highlight the first instance of the string yellow
					if (firstPosition) {
						highlightFirstInstanceYellow();
						firstPosition = false;
					}

					// Highlight all instances blue
					inputTextArea.getHighlighter().addHighlight(
							startPositionInTextArea, endPositionInTextArea,
							DefaultHighlighter.DefaultPainter);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				startPositionInTextArea++;
			}
		}
		if (firstPosition) {
			JOptionPane.showMessageDialog(this, "String not found");
		}
	}

	private void highlightFirstInstanceYellow() throws BadLocationException {
		int positionOfFirstInstance = textPositions.get(0);
		try {
			textArea.getHighlighter().changeHighlight(yellowHighlighter,
					positionOfFirstInstance - existingText.length(),
					positionOfFirstInstance);
		} catch (NullPointerException e) {
			yellowHighlighter = textArea.getHighlighter()
					.addHighlight(
							positionOfFirstInstance - existingText.length(),
							positionOfFirstInstance,
							new DefaultHighlighter.DefaultHighlightPainter(
									Color.YELLOW));
		}

		textArea.setCaretPosition(positionOfFirstInstance);

	}

	private void selectNextInstanceOfText() {
		try {
			int selectedTextPosition = textPositions.get((textPositions
					.indexOf(textArea.getCaretPosition())) + 1);
			textArea.setCaretPosition(selectedTextPosition);
			try {
				textArea.getHighlighter().changeHighlight(yellowHighlighter,
						selectedTextPosition - existingText.length(),
						selectedTextPosition);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} catch (IndexOutOfBoundsException e) {
			returnToFirstInstanceOfText();
		}
	}

	private void returnToFirstInstanceOfText() {

		if (textArea.getHighlighter().getHighlights().length == 0) {
			JOptionPane.showMessageDialog(this, "String not found");
		} else {
			endOfFileReached();
			try {
				highlightFirstInstanceYellow();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	private void endOfFileReached() {
		JOptionPane.showMessageDialog(this, "End of file reached");
	}

	private void findInArrowLayoutPanel() {
		String newText = finderTextField.getText().toLowerCase();

		// Terminates without any action if input string is empty
		if (newText.equals("")) {
			if (existingText.equals("")) {
				return;
			} else {
				highlightNextArrowButtonYellow();
			}
		} else {

			// Check whether the user is trying to find the next instance of
			// a string that was already searched in the text area or
			// the user is searching for a new string in the text area
			if (!newText.equals(existingText)) {
				searchForNewStringInArrows(newText);
			} else {
				highlightNextArrowButtonYellow();
			}
		}

	}

	private void addTags() {
		tags = new ArrayList<ColoredTag>();
		tags.add(new ColoredTag("[VERBOSE]", "black", false));
		tags.add(new ColoredTag("[TRACE]", "blue", false));
		tags.add(new ColoredTag("[INFO]", "green", false));
		tags.add(new ColoredTag("[WARNING]", "orange", false));
		tags.add(new ColoredTag("[ERROR]", "red", true));
	}

	private void searchForNewStringInArrows(String newText) {

		boolean isFirstArrow = true;
		for (ArrowButton button : selectedButtons) {
			button.unselect();
		}
		selectedButtons = new ArrayList<ArrowButton>();
		for (Arrow arrow : arrowPanel.getArrows()) {
			if (arrow.getArrowButton().getAssociatedText().toLowerCase()
					.contains(newText.toLowerCase())) {
				selectArrowButton(arrow.getArrowButton(), isFirstArrow, newText);
				isFirstArrow = false;
			}
		}
	}

	private void highlightNextArrowButtonYellow() {
		boolean previousButtonWasSelected = false;
		for (ArrowButton button : selectedButtons) {
			if (button.isPrimarySelection()) {
				button.setPrimarySelection(false);
				previousButtonWasSelected = true;
			} else if (previousButtonWasSelected) {
				button.setPrimarySelection(true);
				previousButtonWasSelected = false;
			}
		}

		// Highlight the first button yellow if the previous button that
		// was highlighted yellow was the last button on the list
		if (previousButtonWasSelected) {
			endOfFileReached();
			selectedButtons.get(0).setPrimarySelection(true);
		}
	}

	private void selectArrowButton(ArrowButton button, boolean isFirstArrow,
			String newText) {
		if (isFirstArrow) {
			button.setPrimarySelection(true);
		} else {
			button.select();
		}
		find(newText, button.getTextArea());
		selectedButtons.add(button);
	}

	private void stopAllThreads(StartScreen startScreen, Thread currentThread) {
		try {
			errorLock.lock();
			Thread[] threads = new Thread[threadGroup.activeCount()];
			threadGroup.enumerate(threads);
			for (Thread thread : threads) {
				if (thread != currentThread)
					thread.interrupt();
			}

			loadPage.dispose();
			JOptionPane.showMessageDialog(startScreen, "Not a valid log file");
			currentThread.interrupt();
			// Lock is not released as multiple threads will be calling
			// this method at the same time. Each click on "Start Parser"
			// creates new threads and a new lock, so this is not a problem.
		} catch (Exception e) {
			return;
		}
	}

	private void addActionListeners() {

		chartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				chart.setSize(600, 600);
				chart.setVisible(true);
			}
		});

		finderTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finderTextFieldActionPerformed(e);
			}
		});

		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findButtonActionPerformed(e);
			}
		});

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonSearchActionPerformed(e);
			}
		});

		customTagField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCustomTag();
			}
		});

		customTagUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCustomTag();
			}
		});

		for (final RadioButtonWithFileLink button : tagRadioButtonList) {
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					setTextArea(button);
				}

			});
		}
	}

}
