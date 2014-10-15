package parserTool;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

@SuppressWarnings("serial")
public class LoadPage extends JDialog {

	public static final int NUMBER_OF_THREADS = 3;

	public LoadPage(final StartScreen screen) {

		super(screen, false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel loadingLabel = new JLabel(
				new ImageIcon(
						"C:\\Users\\erdikoch\\Desktop\\workspace\\ParserTool\\loading.gif"));
		loadingLabel.setBorder(new EmptyBorder(30, 30, 30, 30));
		loadingLabel.setFont(new Font("MS Mincho", Font.BOLD, 12));
		add(loadingLabel);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.getThreadGroup().stop();
				dispose();
			}
		});
		add(cancelButton);
		setLocationRelativeTo(screen);
		pack();
		setVisible(false);

	}

}