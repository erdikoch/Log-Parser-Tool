package seniorproject;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class StartScreen {
	
	private JFrame startScreenFrame;
	private JTextField pathTextField;
	private JPanel downPanel;
	private JButton BrowseButton;
	private JButton startParserButton;
	private JButton exitButton;
	
	
	public StartScreen(){
		startScreenFrame = new JFrame();
		startScreenFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
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
		startScreenFrame.getContentPane().add(downPanel, BorderLayout.CENTER);
		
		BrowseButton = new JButton("Browse");
		BrowseButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\browse.gif"));
		BrowseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		downPanel.add(BrowseButton);
		
		startParserButton = new JButton("Start Parser");
		startParserButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\arrow-right-on-hover-35px.png"));
		downPanel.add(startParserButton);
		
		exitButton = new JButton("Exit");
		exitButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\exit.gif"));
		downPanel.add(exitButton);
	}
}
