package seniorproject;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JEditorPane;

public class ParserScreen {
	private JFrame frame;
	private JTextField textField;
	
	
	public ParserScreen(){
		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(3);
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JButton saveButton = new JButton("Save");
		saveButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\smallsaveicon.png"));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		topPanel.add(saveButton);
		
		JButton saveAs = new JButton("Save As");
		saveAs.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\toolbar_saveAs.png"));
		topPanel.add(saveAs);
		
		JButton saveAllButton = new JButton("Save all");
		saveAllButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\1417987851_ikon_winzip.png"));
		topPanel.add(saveAllButton);
		
		JButton loadFileButton = new JButton("Load File");
		loadFileButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\file-plus.png"));
		topPanel.add(loadFileButton);
		
		JButton editFileButton = new JButton("Edit File");
		editFileButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\imgres.jpg"));
		topPanel.add(editFileButton);
		
		JButton loggingChartButton = new JButton("Log Capacity Chart");
		loggingChartButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\url.jpg"));
		topPanel.add(loggingChartButton);
		
		JButton logStatisticalChart = new JButton("Time Statistical Chart");
		logStatisticalChart.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\21stCHART.jpg"));
		topPanel.add(logStatisticalChart);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
		JButton verboseButton = new JButton("Verbose");
		verboseButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\0086822.jpg"));
		
		JButton traceButton = new JButton("Trace");
		traceButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\makethumb.jpg"));
		
		JLabel loggingLevelLabel = new JLabel("Log Levels :");
		
		JButton debugButton = new JButton("Debug");
		debugButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\icon-bed-bug.png"));
		debugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton infoButton = new JButton("Info");
		infoButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\icon-info.png"));
		
		JButton warnButton = new JButton("Warn");
		warnButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\Sys_Warn.png"));
		
		JButton errorButton = new JButton("Error");
		errorButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\red_cross.gif"));
		
		JButton fatalButton = new JButton("Fatal");
		fatalButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\322.jpg"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(verboseButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(traceButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(debugButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(infoButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(warnButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(errorButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(fatalButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(loggingLevelLabel))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(loggingLevelLabel)
					.addGap(5)
					.addComponent(verboseButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(traceButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(debugButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infoButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(warnButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fatalButton)
					.addContainerGap(16, Short.MAX_VALUE))
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
		searchButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\search_icon_big.gif"));
		southPanel.add(searchButton);
		
		JButton upButton = new JButton("Up");
		upButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\upArrow.png"));
		southPanel.add(upButton);
		
		JButton downButton = new JButton("Down");
		downButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\arrow-down-circle.png"));
		southPanel.add(downButton);
		
		JButton markButton = new JButton("Mark All");
		markButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\ecc239b23d8e0da79a1aa90f0a831a21.jpg"));
		southPanel.add(markButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\exit.gif"));
		southPanel.add(exitButton);
		
		JScrollPane scrollPane = new JScrollPane();
		JTextPane textPane = new JTextPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(textPane);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
}
