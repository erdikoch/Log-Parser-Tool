package iOSScreen;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * 
 * @author Erdi Koc
 */
public class iOSPieChartView {

	public iOSPieChartView() throws FileNotFoundException {
		super();
		JFrame statisticalChart = new JFrame("Log Statistical Chart");
		statisticalChart.setContentPane(createDemoPanel());
		statisticalChart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		statisticalChart.setBounds(200, 200, 500, 400);
		statisticalChart.setVisible(true);
	}


	private static PieDataset createDataset() throws FileNotFoundException {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Verbose", new Double(verboseCounter()));
		dataset.setValue("Trace", new Double(traceCounter()));
		dataset.setValue("Debug", new Double(debugCounter()));
		dataset.setValue("Info", new Double(infoCounter()));
		dataset.setValue("Warn", new Double(warnCounter()));
		dataset.setValue("Error", new Double(errorCounter()));
		return dataset;
	}

	private static Double errorCounter() throws FileNotFoundException {
		File file = new File("iOSError.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double errorCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("[ERROR]")) {
				errorCounter++;
			}
		}
		scanner.close();
		return errorCounter;
	}

	private static Double warnCounter() throws FileNotFoundException {
		File file = new File("iOSWarning.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double warnCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("[WARNING]")) {
				warnCounter++;
			}
		}
		scanner.close();
		return warnCounter;
	}

	private static Double infoCounter() throws FileNotFoundException {
		File file = new File("iOSInfo.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double infoCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("[INFO]")) {
				infoCounter++;
			}
		}
		scanner.close();
		return infoCounter;
	}

	private static Double debugCounter() throws FileNotFoundException {
		File file = new File("iOSDebug.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double debugCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("[DEBUG]")) {
				debugCounter++;
			}
		}
		scanner.close();
		return debugCounter;
	}
	private static Double traceCounter() throws FileNotFoundException {
		File file = new File("iOSTrace.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double debugCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("[TRACE]")) {
				debugCounter++;
			}
		}
		scanner.close();
		return debugCounter;
	}

	private static double verboseCounter() throws FileNotFoundException {
		File file = new File("iOSVerbose.txt");
		Scanner scanner = new Scanner(file);
		String line;
		double verboseCounter = 0.0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("[VERBOSE]")) {
				verboseCounter++;
			}
		}
		scanner.close();
		return verboseCounter;
	}

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

	public static JPanel createDemoPanel() throws FileNotFoundException {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}