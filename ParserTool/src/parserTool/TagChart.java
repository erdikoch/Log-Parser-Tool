package parserTool;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class TagChart extends ApplicationFrame {
	{
		// set a theme using the new shadow generator feature available in
		// 1.0.14 - for backwards compatibility it is not enabled by default
		ChartFactory.setChartTheme(StandardChartTheme.createDarknessTheme());
		
	}
private static String fileName;
	public TagChart(String s,String fileName) throws FileNotFoundException {
		super(s);
		this.fileName = fileName;
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private static CategoryDataset createDataset() throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		String line;
		int verboseCounter = 0;
		int traceCounter = 0;
		int infoCounter = 0;
		int warningCounter = 0;

		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("VERBOSE")) {
				verboseCounter++;
			} else if (line.contains("TRACE")) {
				traceCounter++;
			} else if (line.contains("INFO")) {
				infoCounter++;
			} else if (line.contains("WARNING")) {
				warningCounter++;
			}
		}
		scanner.close();
		
		int total = verboseCounter + traceCounter + infoCounter
				+ warningCounter;
		double verbosePercentage = ((double) verboseCounter / (double) total) * 100;
		double tracePercentage = ((double) traceCounter / (double) total) * 100;
		double infoPercentage = ((double) infoCounter / (double) total) * 100;
		double warningPercentage = ((double) warningCounter / (double) total) * 100;

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(verbosePercentage, "Series 1",
				"VERBOSE (" + verboseCounter + ")");
		defaultcategorydataset.addValue(tracePercentage, "Series 1", "TRACE ("
				+ traceCounter + ")");
		defaultcategorydataset.addValue(infoPercentage, "Series 1", "INFO ("
				+ infoCounter + ")");
		defaultcategorydataset.addValue(warningPercentage, "Series 1",
				"WARNING (" + warningCounter + ")");

		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D("TAG STATISTICS",
				"", "Percentage Value", categorydataset,
				PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.2D));
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setBaseItemLabelsVisible(true);
		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;
		barrenderer.setItemMargin(200D);
		return jfreechart;
	}

	public static JPanel createDemoPanel() throws FileNotFoundException {
		JFreeChart jfreechart = createChart(createDataset());
		setDefaultLookAndFeelDecorated(true);
		return new ChartPanel(jfreechart);
	}

}
