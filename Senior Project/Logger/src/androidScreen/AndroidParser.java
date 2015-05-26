package androidScreen;

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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import androidReaders.AndroidDebugReader;
import androidReaders.AndroidErrorReader;
import androidReaders.AndroidInfoReader;
import androidReaders.AndroidVerboseReader;
import androidReaders.AndroidWarnReader;

public class AndroidParser {

	private JFrame frame;
	private JTextField textField;
	private Highlighter.HighlightPainter painter;
	final JTextArea textArea = new JTextArea();
	private JTextField customTagTextField;
	private String existingText = "";
	private ArrayList<Integer> textPositions = new ArrayList<Integer>();
	private Object yellowHighlighter = null;
	private static int NO_MORE_INSTANCES = -1;
	private JTextField replaceFieldText;
	private JTextField oldStringField;
	
	public AndroidParser(String fileName) throws FileNotFoundException {
		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(192, 192, 192));
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(3);
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);

		textArea.setOpaque(true);
		JButton saveButton = new JButton("Save");
		saveButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/smallsaveicon.png")));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		topPanel.add(saveButton);

		JButton saveAllButton = new JButton("Save all log levels as zip");
		saveAllButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/1417987851_ikon_winzip.png")));
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
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/file-plus.png")));
		topPanel.add(loadFileButton);
		loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadFromFile();
			}
		});

		JButton editFileButton = new JButton("Edit File");
		editFileButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/imgres.jpg")));
		topPanel.add(editFileButton);
		// EDIT FILE
		editFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setEditable(true);
			}
		});

		JButton loggingChartButton = new JButton("Log Capacity Chart");
		loggingChartButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/url.jpg")));
		topPanel.add(loggingChartButton);
		loggingChartButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					AndroidPieChartView chart = new AndroidPieChartView();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton logStatisticalChart = new JButton("Time Statistical Chart");
		logStatisticalChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AndroidTimeChartView demo;
				try {
					demo = new AndroidTimeChartView();
					
					  
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		      
			}
		});
		logStatisticalChart
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/21stCHART.jpg")));
		topPanel.add(logStatisticalChart);
		
				JButton exitButton = new JButton("Exit");
				topPanel.add(exitButton);
				exitButton
						.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/exit.gif")));
				// EXIT BUTTON
				exitButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		frame.getContentPane().add(panel, BorderLayout.WEST);

		final JButton verboseButton = new JButton("Verbose");
		verboseButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/0086822.jpg")));

		JLabel loggingLevelLabel = new JLabel("Log Levels :");
		loggingLevelLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

		final JButton debugButton = new JButton("Debug");
		debugButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/url.png")));
		debugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		final JButton infoButton = new JButton("Info");
		infoButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/icon-info.png")));

		final JButton warnButton = new JButton("Warn");
		warnButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/Sys_Warn.png")));

		final JButton errorButton = new JButton("Error");
		errorButton
				.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/red_cross.gif")));

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
		final JButton btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customTagButton.setEnabled(true);
				customTagButton.setText(customTagTextField.getText());
			}
		});

		final JRadioButton customTagRadioButton = new JRadioButton("Custom Tag");
		customTagRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (customTagRadioButton.isSelected()) {
					customTagTextField.setEnabled(true);
					btnUpdate.setEnabled(true);
				} else {
					customTagButton.setText(customTagTextField.getText());
					customTagTextField.setEnabled(false);
					btnUpdate.setEnabled(false);
				}
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						customTagButton,
																						GroupLayout.DEFAULT_SIZE,
																						111,
																						Short.MAX_VALUE)
																				.addComponent(
																						customTagTextField,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						111,
																						Short.MAX_VALUE)
																				.addComponent(
																						loggingLevelLabel,
																						GroupLayout.PREFERRED_SIZE,
																						86,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						verboseButton,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						111,
																						Short.MAX_VALUE)
																				.addComponent(
																						debugButton,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						111,
																						Short.MAX_VALUE)
																				.addComponent(
																						infoButton,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						111,
																						Short.MAX_VALUE)
																				.addComponent(
																						warnButton,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						errorButton,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						111,
																						Short.MAX_VALUE)
																				.addComponent(
																						customTagRadioButton))
																.addContainerGap())
												.addComponent(btnUpdate,
														Alignment.TRAILING))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
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
						.addComponent(customTagButton,
								GroupLayout.PREFERRED_SIZE, 42,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 96,
								Short.MAX_VALUE)
						.addComponent(customTagRadioButton)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(customTagTextField,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnUpdate).addGap(82)));
		panel.setLayout(gl_panel);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(192, 192, 192));
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
						.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/search_icon_big.gif")));
				southPanel.add(searchButton);
				
				JPanel replaceAllPanel = new JPanel();
				replaceAllPanel.setBackground(new Color(255, 255, 0));
				replaceAllPanel.setBorder(UIManager.getBorder("EditorPane.border"));
				replaceAllPanel.setForeground(new Color(51, 204, 0));
				southPanel.add(replaceAllPanel);
				
				JLabel newWordLabel = new JLabel("New Word");
				replaceAllPanel.add(newWordLabel);
				
				replaceFieldText = new JTextField();
				replaceAllPanel.add(replaceFieldText);
				replaceFieldText.setToolTipText("");
				replaceFieldText.setColumns(10);
				
				JLabel lblNewLabel_1 = new JLabel("Old Word");
				replaceAllPanel.add(lblNewLabel_1);
				
				oldStringField = new JTextField();
				replaceAllPanel.add(oldStringField);
				oldStringField.setColumns(10);
				
				JButton btnReplaceAll = new JButton("Replace All");
				replaceAllPanel.add(btnReplaceAll);
				btnReplaceAll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						replaceAll(oldStringField.getText(),replaceFieldText.getText());

					}

					private void replaceAll(String oldString, String newString) {
						if (!oldString.equals("")) {
							String editorText = textArea.getText();
							editorText = editorText.replaceAll(oldString, newString);
							textArea.setText(editorText);
						}
					}
				});
				btnReplaceAll.setIcon(new ImageIcon(AndroidParser.class.getResource("/images/2_twitter_retweet_icon.png")));
				
						// SEARCH BUTTON
						searchButton.addActionListener(new ActionListener() {
				
							public void actionPerformed(ActionEvent arg0) {	
								if (textArea.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null, "No text in view");
								} else {
									String newText = textField.getText().toLowerCase();
				
									// Terminates without any action if input string is empty
									if (newText.equals("")) {
										if (existingText.equals("")) {
											return;
										} else {
											selectNextInstanceOfText();
										}
									} else {
				
										// Check whether the user is trying to find the next
										// instance of
										// a string that was already searched in the text area
										// or
										// the user is searching for a new string in the text
										// area
										if (!newText.equals(existingText)) {
											find(newText, textArea);
										} else {
											selectNextInstanceOfText();
										}
									}
								}
							}
				
							private void find(String newText, JTextArea textArea) {
								textPositions = new ArrayList<Integer>();
				
								// For case insensitivity
								String lowerCaseText = textArea.getText().toLowerCase();
				
								textArea.getHighlighter().removeAllHighlights();
								existingText = newText;
								int startPositionInTextArea = 0;
								int endPositionInTextArea = 0;
				
								// Used to highlight first instance of text yellow in TextLayout
								boolean firstPosition = (textArea == textArea);
				
								// Find all instances of the string in the text area
								// and highlight them
								while (endPositionInTextArea < textArea.getText().length()) {
									startPositionInTextArea = lowerCaseText.indexOf(
											existingText, startPositionInTextArea);
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
											textArea.getHighlighter().addHighlight(
													startPositionInTextArea,
													endPositionInTextArea,
													DefaultHighlighter.DefaultPainter);
										} catch (BadLocationException e) {
											e.printStackTrace();
										}
										startPositionInTextArea++;
									}
								}
								if (firstPosition) {
									JOptionPane.showMessageDialog(null, "String not found");
								}
				
							}
				
							private void highlightFirstInstanceYellow()
									throws BadLocationException {
								int positionOfFirstInstance = textPositions.get(0);
								try {
									textArea.getHighlighter().changeHighlight(
											yellowHighlighter,
											positionOfFirstInstance - existingText.length(),
											positionOfFirstInstance);
								} catch (NullPointerException e) {
									yellowHighlighter = textArea.getHighlighter().addHighlight(
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
										textArea.getHighlighter().changeHighlight(
												yellowHighlighter,
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
									JOptionPane.showMessageDialog(null, "String not found");
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
								JOptionPane.showMessageDialog(null, "End of file reached");
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
					}
					scanner.close();
				} catch (FileNotFoundException e1){
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
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				textArea.setEditable(false);
			}
		});
		
		AndroidVerboseReader verboseReader = new AndroidVerboseReader( fileName);
		AndroidDebugReader debugReader = new AndroidDebugReader( fileName);
		AndroidInfoReader infoReader = new AndroidInfoReader( fileName);
		AndroidWarnReader warnReader = new AndroidWarnReader( fileName);
		AndroidErrorReader errorReader = new AndroidErrorReader(fileName);
		
		verboseReader.run();
		debugReader.run();
		infoReader.run();
		warnReader.run();
		errorReader.run();
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
					textArea.setText(textArea.getText() + nextLine);
					textArea.setText(textArea.getText() + "\n");
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
}