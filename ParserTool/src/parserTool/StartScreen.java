package parserTool;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

@SuppressWarnings("serial")
public class StartScreen extends JFrame {

	private static final int TEXT_FILE_EXTENSION_LENGTH = 4;
	public StartScreen frame = this;
	private JFileChooser fileChooser = new JFileChooser();
	private JTextField pathTextField;
	private File logFile;
	private JLabel fileNameLabel;
	private JButton browseButton;
	private JButton startParserButton;
	private JButton exitButton;

	private JCheckBox writeToFileCheckBox;
	private boolean writeToFileIsChecked;

	private JPanel fileNamePanel;
	private JPanel buttonsPanel;

	private JCheckBox androidLogCheckBox;
	private JCheckBox iOSLogCheckBox;

	private LoadPage loadPage;

	private JPanel boxPanel;

	private ThreadGroup threadGroup;

	public StartScreen() {
		initialize();
	}

	private void initialize() {
		setFrame();
		setPathTextField();

		fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new FlowLayout());

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());

		fileNameLabel = new JLabel("File name:   ");
		browseButton = new JButton("Browse");
		startParserButton = new JButton("Start Parser");
		exitButton = new JButton("Exit");


		boxPanel = new JPanel();

		ButtonGroup group = new ButtonGroup();
		androidLogCheckBox = new JCheckBox("Android Log");
		iOSLogCheckBox = new JCheckBox("iOS Log");

		group.add(androidLogCheckBox);
		group.add(iOSLogCheckBox);

		boxPanel.add(androidLogCheckBox);
		boxPanel.add(iOSLogCheckBox);

		writeToFileCheckBox = new JCheckBox("Write output text to files");
		writeToFileCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				writeToFileIsChecked = (e.getStateChange() == ItemEvent.SELECTED);
			}
		});

		addButtonsToFrame();

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		acceptOnlyTxtFile();
		fileChooser.setAcceptAllFileFilterUsed(false);

		buttonOperations(browseButton, startParserButton, exitButton);

		pack();

	}

	private void addButtonsToFrame() {
		setBackground(Color.YELLOW);
		fileNamePanel.add(fileNameLabel);
		fileNamePanel.add(pathTextField);
		buttonsPanel.add(browseButton);
		buttonsPanel.add(startParserButton);
		buttonsPanel.add(exitButton);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(fileNamePanel).addComponent(buttonsPanel)
				.addComponent(boxPanel));
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(fileNamePanel).addComponent(buttonsPanel)
				.addComponent(boxPanel));

		getContentPane().setLayout(layout);
	}

	private void setPathTextField() {
		pathTextField = new JTextField();
		pathTextField.setColumns(24);
	}

	private void setFrame() {
		setResizable(false);
		setTitle("LogTool");
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void buttonOperations(JButton browseButton,
			JButton startParserButton, JButton exitButton) {
		browseButtonOperation(browseButton);
		startParserButtonOperation(startParserButton);
		exitButtonOperation(exitButton);
	}

	private void exitButtonOperation(JButton exitButton) {
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

	private void startParserButtonOperation(JButton startParserButton) {
		startParserButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (pathTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "No file selected");
				} else if (iOSLogCheckBox.isSelected()) {
					try {
						logFile = new File(pathTextField.getText());
						threadGroup = new ThreadGroup("threads");
						loadPage = new LoadPage(frame);
						loadPage.setVisible(true);
						new AnalysisScreen(frame, logFile, threadGroup,
								writeToFileIsChecked);
					} catch (Exception e) {
						threadGroup.interrupt();
						JOptionPane.showMessageDialog(frame,
								"Not a valid file name");
					}
				} else if (androidLogCheckBox.isSelected()) {
					try {
						
						AndroidLogParserFrame frame = new AndroidLogParserFrame(
								pathTextField.getText());
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void browseButtonOperation(JButton browseButton) {
		browseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					pathTextField.setText(fileChooser.getSelectedFile()
							.toString());
				}
			}
		});
	}

	private void acceptOnlyTxtFile() {
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				String filename = file.getName();
				return ((filename.length() > TEXT_FILE_EXTENSION_LENGTH && filename
						.substring(
								filename.length() - TEXT_FILE_EXTENSION_LENGTH,
								filename.length()).toUpperCase().equals(".TXT")) || file
						.isDirectory());
			}

			@Override
			public String getDescription() {
				return "Text files (*.txt)";
			}
		});
	}

	public String getPathTextField() {
		return pathTextField.getText();
	}

	public File getLogFile() {
		return logFile;
	}

	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}

	public LoadPage getLoadPage() {
		return loadPage;
	}

}
