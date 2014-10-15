package parserTool;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class TextEditor implements ActionListener {
	// Size of the editing area
	private static final int EDIT_ROWS = 20;
	private static final int EDIT_COLS = 40;

	// Size of search and replace fields
	private static final int NUM_CHARS = 15;

	// The area where the user does the editing
	private JTextArea editor;

	// Swing components to load and save files
	private JButton loadButton;
	private JButton saveButton;
	private JFileChooser chooser = new JFileChooser();

	// The Swing components dealing with searching
	private JButton searchButton;
	private JTextField searchField;
	private JCheckBox searchCaseSensitiveBox;
	private JCheckBox reverseSearchBox;

	// The Swing components dealing with replace
	private JButton replaceAllButton;
	private JButton replaceSelectionButton;
	private JTextField replaceField;

	/*
	 * Creates the user interface for the editor.
	 */
	public TextEditor() {
		// Create the editing area. Make it scrollable. Give it a border so the
		// user knows what it is for.
		editor = new JTextArea(EDIT_ROWS, EDIT_COLS);
		JScrollPane editorScroller = new JScrollPane(editor);
		editorScroller.setBorder(BorderFactory
				.createTitledBorder("Editing area"));
		JFrame frame = new JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.add(editorScroller, BorderLayout.CENTER);

		// Create the components dealing with search
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		searchField = new JTextField(NUM_CHARS);
		searchCaseSensitiveBox = new JCheckBox("Case sensitive", true);
		reverseSearchBox = new JCheckBox("Reverse search", false);
		JPanel searchPanel = new JPanel();
		searchPanel.add(searchButton);
		searchPanel.add(searchField);
		searchPanel.add(searchCaseSensitiveBox);
		searchPanel.add(reverseSearchBox);

		// Create the components dealing with replace
		replaceAllButton = new JButton("Replace all");
		replaceAllButton.addActionListener(this);
		replaceSelectionButton = new JButton("Replace selection");
		replaceSelectionButton.addActionListener(this);
		replaceField = new JTextField(NUM_CHARS);
		JPanel replacePanel = new JPanel();
		replacePanel.add(replaceAllButton);
		replacePanel.add(replaceSelectionButton);
		replacePanel.add(replaceField);

		// Create the components to deal with files
		loadButton = new JButton("Load file");
		loadButton.addActionListener(this);
		saveButton = new JButton("Save file");
		saveButton.addActionListener(this);
		JPanel filePanel = new JPanel();
		filePanel.add(loadButton);
		filePanel.add(saveButton);

		// Put all the buttons at the bottom of the window
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(searchPanel);
		buttonPanel.add(replacePanel);
		buttonPanel.add(filePanel);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * Handle the button clicks.
	 */
	public void actionPerformed(ActionEvent event) {
		JButton clickedButton = (JButton) event.getSource();
		if (clickedButton == searchButton) {
			search();
		} else if (clickedButton == replaceAllButton) {
			replaceAll(searchField.getText(), replaceField.getText());
		} else if (clickedButton == replaceSelectionButton) {
			replaceSelection();
		} else if (clickedButton == saveButton) {
			saveToFile();
		} else if (clickedButton == loadButton) {
			loadFromFile();
		}
	}

	/*
	 * Read in the contents of a file and display it in the text area.
	 */
	private void loadFromFile() {
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File loadFile = chooser.getSelectedFile();
			try {
				BufferedReader in = new BufferedReader(new FileReader(loadFile));
				editor.setText("");
				String nextLine = in.readLine();
				while (nextLine != null) {
					editor.append(nextLine + "\n");
					nextLine = in.readLine();
				}
				in.close();
			} catch (IOException e) {
				// Happens if the file cannot be read for any reason, such as:
				// - the user might not have read permission on the file
				JOptionPane.showMessageDialog(null, "Could not load the file "
						+ e.getMessage());
			}
		}

	}

	/*
	 * Save the contents of the editing area to a the file named "file.txt".
	 */
	private void saveToFile() {
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File saveFile = chooser.getSelectedFile();
			try {
				PrintWriter out = new PrintWriter(new FileWriter(saveFile));
				out.print(editor.getText());
				out.close();
			} catch (IOException e) {
				// Happens if the file cannot be written to for any reason, such
				// as:
				// - the file might already exist
				// - the disk might be full
				// - the user might not have write permission in the directory
				JOptionPane.showMessageDialog(null, "Could not save the file "
						+ e.getMessage());
			}
		}
	}

	/*
	 * Replace all occurrences of a string with another string. Does nothing if
	 * oldString is empty.
	 */
	private void replaceAll(String oldString, String newString) {
		if (!oldString.equals("")) {
			String editorText = editor.getText();
			editorText = editorText.replaceAll(oldString, newString);
			editor.setText(editorText);
		}
	}

	/*
	 * Replace the selected text in the editing area with the text the user
	 * entered in the replace field. If the replace field is empty, this will
	 * delete the selected text.
	 */
	private void replaceSelection() {
		// Get the text out of the editing area
		String editorText = editor.getText();

		// Find the substrings that appear before and after the selection
		String start = editorText.substring(0, editor.getSelectionStart());
		String end = editorText.substring(editor.getSelectionEnd());

		// Replace the selected portion with the contents of the replace field
		editorText = start + replaceField.getText() + end;
		editor.setText(editorText);
	}

	/*
	 * Find the next occurrence of the string entered in the search field. The
	 * setting of the "case sensitive" box will determine if the search requires
	 * the same capitalization or not. The setting of the "reverse" box will
	 * determine if the search proceeds forward from the current caret position
	 * or backwards. If there are no more occurences of the search string,
	 * nothing happens.
	 */
	private void search() {
		// Get the string from the editing area.
		String editorText = editor.getText();

		// Get the string the user wants to search for
		String searchValue = searchField.getText();

		// If the search should be case insensitive, change the editing text and
		// search string to all lower case
		if (searchCaseSensitiveBox.getSelectedObjects() == null) {
			editorText = editorText.toLowerCase();
			searchValue = searchValue.toLowerCase();
		}

		// Find the next occurrence, searching forward or backward depending on
		// the setting of the reverse box.
		int start;
		if (reverseSearchBox.getSelectedObjects() == null) {
			start = editorText.indexOf(searchValue, editor.getSelectionEnd());
		} else {
			start = editorText.lastIndexOf(searchValue,
					editor.getSelectionStart() - 1);
		}

		// If the string was found, move the selection so that the found string
		// is highlighted.
		// (This is detail about using JTextArea that you don't really need to
		// know. Understanding the
		// String methods is much more important.
		if (start != -1) {
			editor.setCaretPosition(start);
			editor.moveCaretPosition(start + searchValue.length());
			editor.getCaret().setSelectionVisible(true);
		}
	}

}
