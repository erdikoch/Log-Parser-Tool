package parserTool;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class StartScreen {
	private JFileChooser fileChooser = new JFileChooser();
	private JFrame startScreenFrame;
	private JTextField pathTextField;
	private JPanel downPanel;
	private JButton browseButton;
	private JButton startParserButton;
	private JButton exitButton;
	private JRadioButton androidLogRadioButton;
	private JPanel panel_2;

	public StartScreen() {
		startScreenFrame = new JFrame();
		startScreenFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		startScreenFrame.setBounds(300, 300, 507, 170);

		JPanel panel = new JPanel();
		startScreenFrame.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel filePathLabel = new JLabel("File Path :");
		filePathLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		filePathLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(filePathLabel);

		pathTextField = new JTextField();
		panel.add(pathTextField);
		pathTextField.setColumns(35);

		downPanel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		startScreenFrame.getContentPane().add(downPanel, BorderLayout.CENTER);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(42, 59, 400, 33);
		downPanel.add(panel_1);
		panel_1.setLayout(null);
		androidLogRadioButton = new JRadioButton("Android Log");
		androidLogRadioButton.setBounds(88, 5, 101, 23);
		androidLogRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(androidLogRadioButton);
		androidLogRadioButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		group.add(androidLogRadioButton);

		final JRadioButton iOSLogRadioButton = new JRadioButton("iOS Log");
		iOSLogRadioButton.setBounds(249, 5, 73, 23);
		panel_1.add(iOSLogRadioButton);
		iOSLogRadioButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		group.add(iOSLogRadioButton);
		downPanel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(42, 5, 439, 53);
		downPanel.add(panel_2);
				panel_2.setLayout(null);
		
				browseButton = new JButton("Browse");
				browseButton.setBounds(16, 5, 124, 43);
				panel_2.add(browseButton);
				browseButton
						.setIcon(new ImageIcon(
								"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\browse.gif"));
				
						startParserButton = new JButton("Start Parser");
						startParserButton.setBounds(150, 5, 145, 43);
						panel_2.add(startParserButton);
						startParserButton
								.setIcon(new ImageIcon(
										"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\arrow-right-on-hover-35px.png"));
						
								exitButton = new JButton("Exit");
								exitButton.setBounds(305, 5, 95, 43);
								panel_2.add(exitButton);
								exitButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										System.exit(0);
									}
								});
								exitButton
										.setIcon(new ImageIcon(
												"C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\exit.gif"));
						startParserButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								if (pathTextField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(startScreenFrame,
											"No file selected");
								} else if (androidLogRadioButton.isSelected()) {
									try {
										ParserScreen parser = new ParserScreen(pathTextField.getText());
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else if (iOSLogRadioButton.isSelected()) {
									//ParserScreen parser = new ParserScreen(pathTextField.getText());
								}

							}
						});
				browseButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						int returnVal = fileChooser.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							pathTextField.setText(fileChooser.getSelectedFile()
									.toString());
						}
					}
				});

		startScreenFrame.setVisible(true);

	}
}
