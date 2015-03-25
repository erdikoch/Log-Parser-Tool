package seniorproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

/*
 * @author Erdi Koç
 * @author Deniz Ýskender
 * 
 * */

public class ParserScreen extends javax.swing.JFrame {
	
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(StartScreen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(StartScreen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(StartScreen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(StartScreen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new StartScreen().setVisible(true);
			}
		});
	}
	private JFrame frmLogParserTool;
	private JScrollPane allPanelWindow;
	private JPanel upPanel;
	private JButton upButton;
	private JButton downButton;
	private JButton chartButton;
	private JButton markAllButton;
	private JButton saveAsButton;
	private JPanel loggingLevelsPanel;
	private JButton warnButton;
	private JButton verboseButton;
	private JButton infoButton;
	private JButton traceButton;
	private JButton debugButton;
	private JLabel LoggingLevelsLabel;
	private JButton errorButton;
	private JButton fatalButton;
	private JLabel offLabel;
	private JPanel searchPanel;
	private JLabel enterWordLabel;
	private JTextField textField;
	private JButton searchButton;
	private JButton editFileButton;
	private JButton exitButton;
	private JButton save;
	private JButton loadFileButton;
	private JButton timeChart;
	private JButton maximizeButton;
	private JButton zipButton;
	private JPanel timeBarPanel;
	private JPanel textAreaPanel;
	private JScrollPane scrollPane;


	public ParserScreen() {

		iniComponents();

	}



	private void iniComponents() {
		frmLogParserTool = new JFrame("LOG PARSER TOOL");
		frmLogParserTool.setTitle("LOG PARSER TOOL");
		frmLogParserTool.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLogParserTool.setSize(getWidth(), getHeight());
		frmLogParserTool.setBounds(0, 0, 1250, 592);



		allPanelWindow = new JScrollPane();

		frmLogParserTool.getContentPane().setLayout(new BorderLayout());
		frmLogParserTool.getContentPane().add(allPanelWindow, BorderLayout.CENTER);

		upPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) upPanel.getLayout();
		flowLayout.setHgap(3);
		flowLayout.setAlignment(FlowLayout.LEADING);
		allPanelWindow.setColumnHeaderView(upPanel);

		save = new JButton("Save");
		save.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\smallsaveicon.png"));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		upPanel.add(save);

		saveAsButton = new JButton("Save as");
		saveAsButton.setHorizontalAlignment(SwingConstants.TRAILING);
		saveAsButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\toolbar_saveAs.png"));
		upPanel.add(saveAsButton);

		upButton = new JButton("Up");
		upButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\upArrow.png"));
		upPanel.add(upButton);

		downButton = new JButton("Down");
		downButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\arrow-down-circle.png"));
		upPanel.add(downButton);

		chartButton = new JButton("Log Charts");
		chartButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\url.jpg"));
		upPanel.add(chartButton);

		timeChart = new JButton("Time Statistics");
		timeChart.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\21stCHART.jpg"));
		upPanel.add(timeChart);

		markAllButton = new JButton("Mark All");
		markAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		markAllButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\ecc239b23d8e0da79a1aa90f0a831a21.jpg"));
		upPanel.add(markAllButton);

		loadFileButton = new JButton("Load File");
		loadFileButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\file-plus.png"));
		upPanel.add(loadFileButton);

		zipButton = new JButton("Zip");
		zipButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\1417987851_ikon_winzip.png"));
		upPanel.add(zipButton);

		maximizeButton = new JButton("Maximize");
		maximizeButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\v4-popup.png"));
		upPanel.add(maximizeButton);

		loggingLevelsPanel = new JPanel();
		allPanelWindow.setRowHeaderView(loggingLevelsPanel);
		loggingLevelsPanel.setLayout(new GridLayout(0, 1, 0, 0));

		LoggingLevelsLabel = new JLabel("Logging Levels : ");
		LoggingLevelsLabel.setFont(new Font("Stencil", Font.PLAIN, 16));
		loggingLevelsPanel.add(LoggingLevelsLabel);

		verboseButton = new JButton("Verbose");
		verboseButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\0086822.jpg"));
		loggingLevelsPanel.add(verboseButton);

		traceButton = new JButton("Trace");
		traceButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\makethumb.jpg"));
		loggingLevelsPanel.add(traceButton);

		debugButton = new JButton("Debug");
		debugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		debugButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\url.png"));
		loggingLevelsPanel.add(debugButton);

		infoButton = new JButton("Info");
		infoButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\icon-info.png"));
		loggingLevelsPanel.add(infoButton);

		warnButton = new JButton("Warn");
		warnButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\Sys_Warn.png"));
		warnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		loggingLevelsPanel.add(warnButton);

		errorButton = new JButton("Error");
		errorButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\red_cross.gif"));
		loggingLevelsPanel.add(errorButton);

		fatalButton = new JButton("Fatal");
		fatalButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\322.jpg"));
		loggingLevelsPanel.add(fatalButton);

		offLabel = new JLabel();
		offLabel.setEnabled(false);
		loggingLevelsPanel.add(offLabel);


		JPanel downPanel = new JPanel();
		allPanelWindow.setViewportView(downPanel);
		GridBagLayout gbl_downPanel = new GridBagLayout();
		gbl_downPanel.columnWidths = new int[] {0, 1};
		gbl_downPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3};
		gbl_downPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_downPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		downPanel.setLayout(gbl_downPanel);

		textAreaPanel = new JPanel();
		textAreaPanel.setLayout(null);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 14;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		downPanel.add(textAreaPanel, gbc_panel_2);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 1073, 363);



		JTextPane textPane = new JTextPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textAreaPanel.add(scrollPane);
		scrollPane.setViewportView(textPane);


		timeBarPanel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 14;
		downPanel.add(timeBarPanel, gbc_panel);

		searchPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) searchPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.WEST;
		gbc_panel_4.fill = GridBagConstraints.VERTICAL;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 17;
		downPanel.add(searchPanel, gbc_panel_4);

		enterWordLabel = new JLabel("Enter Word");
		searchPanel.add(enterWordLabel);

		textField = new JTextField();
		searchPanel.add(textField);
		textField.setColumns(85);

		searchButton = new JButton("Search");
		searchButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\search_icon_big.gif"));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchPanel.add(searchButton);

		editFileButton = new JButton("Edit File");
		editFileButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\imgres.jpg"));
		searchPanel.add(editFileButton);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitButton.setIcon(new ImageIcon("C:\\Users\\erdikoch\\Desktop\\workspace\\Senior Project\\exit.gif"));
		searchPanel.add(exitButton);
		
		frmLogParserTool.setResizable(true);
		frmLogParserTool.pack();
		frmLogParserTool.setVisible(true);
	}



	public void parseiOSLog(String fileName) {


	}

	public void parseAndroidLog(String fileName) {

	}
}
