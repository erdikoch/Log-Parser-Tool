package parserTool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.JRadioButton;

public class ParserScreen {

	private JFrame frame;
	private JTextField textField;
	private Highlighter.HighlightPainter painter;
	final JTextArea textArea = new JTextArea();
	private JTextField customTagTextField;

	public ParserScreen(String fileName) throws FileNotFoundException {
		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(3);
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);

		JButton saveButton = new JButton("Save");
		saveButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\smallsaveicon.png"));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		topPanel.add(saveButton);

		JButton saveAllButton = new JButton("Save all log levels as zip");
		saveAllButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\1417987851_ikon_winzip.png"));
		topPanel.add(saveAllButton);
		saveAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileOutputStream fos = new FileOutputStream("files.zip");
					ZipOutputStream zos = new ZipOutputStream(fos);

					String file1Name = "verbose.txt";
					String file2Name = "debug.txt";
					String file3Name = "info.txt";
					String file4Name = "warn.txt";
					String file5Name = "error.txt";

					addToZipFile(file1Name, zos);
					addToZipFile(file2Name, zos);
					addToZipFile(file3Name, zos);
					addToZipFile(file4Name, zos);
					addToZipFile(file5Name, zos);

					zos.close();
					fos.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton loadFileButton = new JButton("Load File");
		loadFileButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\file-plus.png"));
		topPanel.add(loadFileButton);
		loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadFromFile();
			}
		});

		JButton editFileButton = new JButton("Edit File");
		editFileButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\imgres.jpg"));
		topPanel.add(editFileButton);
		// EDIT FILE
		editFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setEditable(true);
			}
		});

		JButton loggingChartButton = new JButton("Log Capacity Chart");
		loggingChartButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\url.jpg"));
		topPanel.add(loggingChartButton);
		loggingChartButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					LoggingLevelStatisticalChart chart = new LoggingLevelStatisticalChart();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton logStatisticalChart = new JButton("Time Statistical Chart");
		logStatisticalChart
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\21stCHART.jpg"));
		topPanel.add(logStatisticalChart);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);

		final JButton verboseButton = new JButton("Verbose");
		verboseButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\0086822.jpg"));

		JLabel loggingLevelLabel = new JLabel("Log Levels :");
		loggingLevelLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

		final JButton debugButton = new JButton("Debug");
		debugButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\url.png"));
		debugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		final JButton infoButton = new JButton("Info");
		infoButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\icon-info.png"));

		final JButton warnButton = new JButton("Warn");
		warnButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\Sys_Warn.png"));

		final JButton errorButton = new JButton("Error");
		errorButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\red_cross.gif"));

		customTagTextField = new JTextField();
		customTagTextField.setEnabled(false);
		customTagTextField.setColumns(10);

		final JButton customTagButton = new JButton("Custom Tag");
		customTagButton.setEnabled(false);
		customTagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verboseButton.setBackground(null);
				debugButton.setBackground(null);
				infoButton.setBackground(null);
				warnButton.setBackground(null);
				errorButton.setBackground(null);
				customTagButton.setBackground(Color.CYAN);

				textArea.setEditable(true);
				textArea.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Verbose.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						if (line.contains(customTagTextField.getText())) {
							textArea.append(line + "\n");
							textArea.setForeground(Color.BLACK);
						}
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				textArea.setEditable(false);
			}
		});

		final JRadioButton customTagRadioButton = new JRadioButton("Custom Tag");
		customTagRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (customTagRadioButton.isSelected()) {
					customTagTextField.setEnabled(true);
				}else{
					customTagButton.setText("Custom Tag");
					customTagTextField.setEnabled(false);
				}
			}
		});
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customTagButton.setEnabled(true);
				customTagButton.setText(customTagTextField.getText());
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(customTagButton, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(customTagTextField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(loggingLevelLabel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
								.addComponent(verboseButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(debugButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(infoButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(warnButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(errorButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(customTagRadioButton))
							.addContainerGap())
						.addComponent(btnUpdate, Alignment.TRAILING)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(loggingLevelLabel)
					.addGap(5)
					.addComponent(verboseButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(debugButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infoButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(warnButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(customTagButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
					.addComponent(customTagRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(customTagTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnUpdate)
					.addGap(82))
		);
		panel.setLayout(gl_panel);

		JPanel southPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) southPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

		JLabel searchLabel = new JLabel("Enter a word");
		southPanel.add(searchLabel);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		southPanel.add(textField);
		textField.setColumns(40);

		JButton searchButton = new JButton("Search");
		searchButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\search_icon_big.gif"));
		southPanel.add(searchButton);

		JButton upButton = new JButton("Up");
		upButton.setIcon(new ImageIcon(
				"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\upArrow.png"));
		southPanel.add(upButton);
		upButton.setEnabled(false);

		JButton downButton = new JButton("Down");
		downButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\arrow-down-circle.png"));
		southPanel.add(downButton);
		downButton.setEnabled(false);

		JButton markButton = new JButton("Mark All");
		markButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\ecc239b23d8e0da79a1aa90f0a831a21.jpg"));
		southPanel.add(markButton);
		markButton.setEnabled(false);

		JButton exitButton = new JButton("Exit");
		exitButton
				.setIcon(new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\exit.gif"));
		southPanel.add(exitButton);
		// EXIT BUTTON
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(textArea);

		textArea.setEditable(false);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setBounds(0, 0, 1200, 700);
		frame.setVisible(true);

		// SEARCH BUTTON
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Please write a word to search");

				} else if (textArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"There is no data to search in the text area");
				} else {

					Highlighter hilite = textArea.getHighlighter();
					Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
							Color.GREEN);

					String pattern = textField.getText();
					Document doc = textArea.getDocument();
					try {
						String text = doc.getText(0, doc.getLength());
						int pos = 0;

						while ((pos = text.toUpperCase().indexOf(
								pattern.toUpperCase(), pos)) >= 0) {
							hilite.addHighlight(pos, pos + pattern.length(),
									painter);
							pos += pattern.length();
						}

					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// RADIOBUTTON VERBOSE
		verboseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				verboseButton.setBackground(Color.PINK);
				debugButton.setBackground(null);
				infoButton.setBackground(null);
				warnButton.setBackground(null);
				errorButton.setBackground(null);
				try {
					Scanner scanner = new Scanner(new File("Verbose.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();

						textArea.append(line + "\n");
						textArea.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		// RADIOBUTTON DEBUG
		debugButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				verboseButton.setBackground(null);
				debugButton.setBackground(Color.MAGENTA);
				infoButton.setBackground(null);
				warnButton.setBackground(null);
				errorButton.setBackground(null);

				textArea.setEditable(true);

				textArea.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Debug.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						textArea.append(line + "\n");
						textArea.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				textArea.setEditable(true);
			}
		});

		// RADIOBUTTON INFO
		infoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				verboseButton.setBackground(null);
				debugButton.setBackground(null);
				infoButton.setBackground(Color.BLUE);
				warnButton.setBackground(null);
				errorButton.setBackground(null);

				textArea.setEditable(true);
				textArea.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Info.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						textArea.append(line + "\n");
						textArea.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				textArea.setEditable(false);
			}
		});

		// RADIOBUTTON WARN
		warnButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				verboseButton.setBackground(null);
				debugButton.setBackground(null);
				infoButton.setBackground(null);
				warnButton.setBackground(Color.ORANGE);
				errorButton.setBackground(null);

				textArea.setEditable(true);
				textArea.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Warn.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						textArea.append(line + "\n");
						textArea.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				textArea.setEditable(false);
			}
		});

		// RADIOBUTTON ERROR
		errorButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				verboseButton.setBackground(null);
				debugButton.setBackground(null);
				infoButton.setBackground(null);
				warnButton.setBackground(null);
				errorButton.setBackground(Color.RED);

				textArea.setEditable(true);
				textArea.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Error.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						textArea.append(line + "\n");
						textArea.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				textArea.setEditable(false);
			}
		});

		getVerboseTextFile(fileName);
		getDebugTextFile(fileName);
		getInfoTextFile(fileName);
		getWarnTextFile(fileName);
		getErrorTextFile(fileName);
	}

	public void addToZipFile(String fileName, ZipOutputStream zos)
			throws FileNotFoundException, IOException {

		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
		JOptionPane
				.showMessageDialog(null, fileName + " is added to files.zip");
	}

	private void saveToFile() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File saveFile = chooser.getSelectedFile();
			try {
				PrintWriter out = new PrintWriter(new FileWriter(saveFile));
				String textFile = textArea.getText();
				if (textFile.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Empty file!");
				} else {
					JOptionPane.showMessageDialog(null, "Successfully saved");
					out.print(textFile);
				}
				out.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not save the file "
						+ e.getMessage());
			}
		}
	}

	private void loadFromFile() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File loadFile = chooser.getSelectedFile();
			textArea.setEditable(true);
			try {
				BufferedReader in = new BufferedReader(new FileReader(loadFile));
				textArea.setText("");
				String nextLine = in.readLine();
				while (nextLine != null) {
					textArea.append(nextLine + "\n");
					nextLine = in.readLine();
				}
				in.close();
			} catch (IOException e) {
				// Happens if the file cannot be read for any reason, such as:
				// - the user might not have read permission on the file
				JOptionPane.showMessageDialog(null, "Could not load the file "
						+ e.getMessage());
			}
			textArea.setEditable(false);
		}

	}

	private void getErrorTextFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		PrintWriter writer = new PrintWriter("Error.txt");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!line.contains("V/") && !line.contains("D/")
					&& !line.contains("I/") && !line.contains("W/")) {
				writer.write(line);
				writer.println();
			}
		}
		writer.flush();
		scanner.close();
		writer.close();

	}

	private void getWarnTextFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		PrintWriter writer = new PrintWriter("Warn.txt");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!line.contains("V/") && !line.contains("D/")
					&& !line.contains("I/")) {
				writer.write(line);
				writer.println();
			}
		}
		writer.flush();
		scanner.close();
		writer.close();

	}

	private void getInfoTextFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		PrintWriter writer = new PrintWriter("Info.txt");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!line.contains("V/") && !line.contains("D/")) {
				writer.write(line);
				writer.println();
			}
		}
		writer.flush();
		scanner.close();
		writer.close();

	}

	private void getDebugTextFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		PrintWriter writer = new PrintWriter("Debug.txt");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!line.contains("V/")) {
				writer.write(line);
				writer.println();
			}
		}
		writer.flush();
		scanner.close();
		writer.close();

	}

	private void getVerboseTextFile(String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		PrintWriter writer = new PrintWriter("Verbose.txt");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			writer.write(line);
			writer.println();
		}
		writer.flush();
		scanner.close();
		writer.close();
	}
}
