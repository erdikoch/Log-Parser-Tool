package seniorproject;



import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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


/*
 * @author Erdi Koç
 * @author Deniz Ýskender
 * 
 * */

public class StartScreen extends JFrame {

	protected static final int TEXT_FILE_EXTENSION_LENGTH = 4;

	private JFileChooser fileChooser = new  JFileChooser();

	private JLabel fileNameLabel;
	private StartScreen startScreenFrame = this;
	private ParserScreen parserScreen;

	private JButton browseButton;
	private JButton startParserButton;
	private JButton exitButton;

	private JTextField pathTextField;

	private JPanel fileNamePanel;
	private JPanel buttonsPanel;

	private JCheckBox androidLogCheckBox;
	private JCheckBox iOSLogCheckBox;

	private JPanel boxPanel;

	public StartScreen() {
		initialize();
	}

	private void initialize() {
		setFrameFeatures();

		pathTextField = new JTextField();
		pathTextField.setColumns(24);

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

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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

		fileChooser.setAcceptAllFileFilterUsed(false);
		buttonOperations(browseButton, startParserButton, exitButton);
		pack();
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

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(pathTextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(startScreenFrame, "No file selected !!");
				}else if(iOSLogCheckBox.isSelected()){
					java.awt.EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							ParserScreen parser = new ParserScreen();
							parser.parseiOSLog(""); // iOS parser log will be added !

						}
					});


				}else if(androidLogCheckBox.isSelected()){
					java.awt.EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							ParserScreen parser = new ParserScreen();
							parser.parseAndroidLog(""); // android parser log will be added !

						}
					});

				}else if((!iOSLogCheckBox.isSelected())&&(!androidLogCheckBox.isSelected())){
					JOptionPane.showMessageDialog(startScreenFrame, "No type selected");
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

	private void setFrameFeatures() {
		setResizable(false);
		setTitle("Log-Tool");
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
