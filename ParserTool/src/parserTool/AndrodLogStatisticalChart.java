package parserTool;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * A simple demonstration application showing how to create a pie chart using
 * data from a {@link DefaultPieDataset}.
 * 
 * @author Erdi Koc
 */
public class AndrodLogStatisticalChart extends ApplicationFrame {

	/**
	 * Default constructor.
	 * 
	 * @param title
	 *            the frame title.
	 * @throws FileNotFoundException
	 */
	public AndrodLogStatisticalChart(String title) throws FileNotFoundException {
		super(title);
		setContentPane(createDemoPanel());
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return A sample dataset.
	 * @throws FileNotFoundException
	 */
	private static PieDataset createDataset() throws FileNotFoundException {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Verbose", new Double(verboseCounter()));
		dataset.setValue("Debug", new Double(debugCounter()));
		dataset.setValue("Info", new Double(infoCounter()));
		dataset.setValue("Warn", new Double(warnCounter()));
		dataset.setValue("Error", new Double(errorCounter()));
		dataset.setValue("Assert", new Double(assertCounter()));
		return dataset;
	}

	private static Double assertCounter() throws FileNotFoundException {
		File file = new File("Assert.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double assertCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("E/")) {
				assertCounter++;
			}
		}
		scanner.close();
		return assertCounter;
	}

	private static Double errorCounter() throws FileNotFoundException {
		File file = new File("Error.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double errorCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("E/")) {
				errorCounter++;
			}
		}
		scanner.close();
		return errorCounter;
	}

	private static Double warnCounter() throws FileNotFoundException {
		File file = new File("Warn.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double warnCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("W/")) {
				warnCounter++;
			}
		}
		scanner.close();
		return warnCounter;
	}

	private static Double infoCounter() throws FileNotFoundException {
		File file = new File("Info.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double infoCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("I/")) {
				infoCounter++;
			}
		}
		scanner.close();
		return infoCounter;
	}

	private static Double debugCounter() throws FileNotFoundException {
		File file = new File("Debug.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double debugCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("D/")) {
				debugCounter++;
			}
		}
		scanner.close();
		return debugCounter;
	}

	private static double verboseCounter() throws FileNotFoundException {
		File file = new File("Verbose.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double verboseCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("V/")) {
				verboseCounter++;
			}
		}
		scanner.close();
		return verboseCounter;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return A chart.
	 */
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart("Statistical Data", // chart
				// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("monospaced", Font.ITALIC, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		return chart;

	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 * 
	 * @return A panel.
	 * @throws FileNotFoundException
	 */
	public static JPanel createDemoPanel() throws FileNotFoundException {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}