package parserTool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
/*
 * @author Erdi Koc
 */
import org.jfree.ui.RefineryUtilities;

public class AndroidLogParserFrame {
	private String fileName;
	private Highlighter highLighter;
	private HighlightPainter painter;
	private JTextField finderTextField;

	public AndroidLogParserFrame(final String fileName)
			throws FileNotFoundException {
		this.fileName = fileName;

		getVerboseTextFile(fileName);
		getDebugTextFile(fileName);
		getInfoTextFile(fileName);
		getWarnTextFile(fileName);
		getErrorTextFile(fileName);
		getAssertTextFile(fileName);

		JFrame frame = new JFrame("ANDROID LOG PARSER TOOL");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 600);
		frame.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel("Show : ");
		label.setForeground(Color.GRAY);
		JButton chartButton = new JButton("Show Statistics");
		JButton exitButton = new JButton("Exit");
		JButton loadButton = new JButton("Search in File");
	
		panel.setLayout(new GridLayout(16, 1));

		ButtonGroup group = new ButtonGroup();
		JRadioButton verboseRadioButton = new JRadioButton("Verbose");
		verboseRadioButton.setForeground(Color.BLACK);
		JRadioButton debugRadioButton = new JRadioButton("Debug");
		debugRadioButton.setForeground(Color.BLACK);
		JRadioButton infoRadioButton = new JRadioButton("Info");
		infoRadioButton.setForeground(Color.BLACK);
		JRadioButton warnRadioButton = new JRadioButton("Warn");
		warnRadioButton.setForeground(Color.BLACK);
		JRadioButton errorRadioButton = new JRadioButton("Error");
		errorRadioButton.setForeground(Color.BLACK);
		JRadioButton assertRadioButton = new JRadioButton("Assert");
		assertRadioButton.setForeground(Color.BLACK);

		group.add(verboseRadioButton);
		group.add(debugRadioButton);
		group.add(infoRadioButton);
		group.add(warnRadioButton);
		group.add(errorRadioButton);
		group.add(assertRadioButton);
		
		
		panel.add(chartButton);
		panel.add(label);
		panel.add(verboseRadioButton);
		panel.add(debugRadioButton);
		panel.add(infoRadioButton);
		panel.add(warnRadioButton);
		panel.add(errorRadioButton);
		panel.add(assertRadioButton);
		
		

		JPanel finderPanel = new JPanel();
		finderPanel.setLayout(new FlowLayout());
		finderTextField = new JTextField(60);
		JButton findButton = new JButton("Search Word");

		finderPanel.add(finderTextField);
		finderPanel.add(findButton);
		finderPanel.add(loadButton);
		finderPanel.add(exitButton);

		final JTextArea area = new JTextArea();
		final JScrollPane scroll = new JScrollPane(area);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		area.setBackground(Color.WHITE);


		loadButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				TextEditor editor = new TextEditor();

			}
		});

		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		findButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (finderTextField.getText().isEmpty()) {
					return;
				} else {
					Highlighter hilite = area.getHighlighter();
					Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
							Color.GREEN);

					String pattern = finderTextField.getText();
					Document doc = area.getDocument();
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

		verboseRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Scanner scanner = new Scanner(new File("Verbose.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						area.append(line + "\n");
					//	area.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		debugRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				area.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Debug.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						area.append(line + "\n");
					//	area.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		infoRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				area.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Info.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						area.append(line + "\n");
				//		area.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		warnRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				area.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Warn.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						area.append(line + "\n");
					//	area.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		errorRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				area.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Error.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
			     		area.append(line + "\n");
				//		area.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		assertRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				area.setText(null);
				try {
					Scanner scanner = new Scanner(new File("Assert.txt"));
					String line;
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						area.append(line + "\n");
					//	area.setForeground(Color.BLACK);
					}
					scanner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		chartButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					AndrodLogStatisticalChart chart = new AndrodLogStatisticalChart(
							"Statistical Data");
					chart.setDefaultCloseOperation(chart.DISPOSE_ON_CLOSE);
					chart.pack();
					RefineryUtilities.centerFrameOnScreen(chart);
					chart.setVisible(true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		area.setEditable(false);
		area.setFocusable(true);
        
		
		
		frame.add(finderPanel, BorderLayout.SOUTH);
		frame.add(panel, BorderLayout.EAST);
		frame.add(scroll, BorderLayout.CENTER);
		frame.setVisible(true);

	}

	private void getAssertTextFile(String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		PrintWriter writer = new PrintWriter("Assert.txt");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("A/")) {
				writer.write(line);
				writer.println();
			}
		}
		writer.flush();
		scanner.close();
		writer.close();

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
