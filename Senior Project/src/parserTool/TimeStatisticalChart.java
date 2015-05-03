/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * -------------------
 * LineChartDemo6.java
 * -------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: LineChartDemo6.java,v 1.5 2004/04/26 19:11:55 taqua Exp $
 *
 * Changes
 * -------
 * 27-Jan-2004 : Version 1 (DG);
 * 
 */

package parserTool;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using data from an
 * {@link XYDataset}.
 *
 */
public class TimeStatisticalChart extends ApplicationFrame {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     * @throws FileNotFoundException 
     */
    public TimeStatisticalChart(final String title) throws FileNotFoundException {

        super(title);

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

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
   

    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     * @throws FileNotFoundException 
     */
    private XYDataset createDataset() throws FileNotFoundException {
        
        final XYSeries series1 = new XYSeries("Verbose");
        
        File file = new File("Verbose.txt");
		Scanner scanner = new Scanner(file);
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("V/")) {
				series1.add(Integer.parseInt(line.substring(16, 18)),Integer.parseInt(line.substring(3, 5)));

			}
		}
		scanner.close();
		


        final XYSeries series2 = new XYSeries("Debug");
        File file2 = new File("Debug.txt");
		Scanner scanner2 = new Scanner(file2);
		String line2;
		while (scanner2.hasNextLine()) {
			line2 = scanner2.nextLine();
			if (line2.contains("D/")) {
				series2.add(Integer.parseInt(line2.substring(16, 18)),Integer.parseInt(line2.substring(3, 5)));

			}
		}
		scanner2.close();

        final XYSeries series3 = new XYSeries("Info");
        File file3 = new File("Info.txt");
		Scanner scanner3 = new Scanner(file3);
		String line3;
		while (scanner3.hasNextLine()) {
			line3 = scanner3.nextLine();
			if (line3.contains("I/")) {
				series3.add(Integer.parseInt(line3.substring(16, 18)),Integer.parseInt(line3.substring(3, 5)));

			}
		}
		scanner3.close();
        
        final XYSeries series4 = new XYSeries("Warn");
        File file4 = new File("Warn.txt");
		Scanner scanner4 = new Scanner(file4);
		String line4;
		while (scanner4.hasNextLine()) {
			line4 = scanner4.nextLine();
			if (line4.contains("W/")) {
				series4.add(Integer.parseInt(line4.substring(16, 18)),Integer.parseInt(line4.substring(3, 5)));

			}
		}
		scanner4.close();
		
        final XYSeries series5 = new XYSeries("Error");
        File file5 = new File("Error.txt");
		Scanner scanner5 = new Scanner(file5);
		String line5;
		while (scanner5.hasNextLine()) {
			line5 = scanner5.nextLine();
			if (line5.contains("E/")) {
				series5.add(Integer.parseInt(line5.substring(16, 18)),Integer.parseInt(line5.substring(3, 5)));

			}
		}
		scanner5.close();


        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);
                
        return dataset;
        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6",      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     * @throws FileNotFoundException 
     */
    public static void main(final String[] args) throws FileNotFoundException {

        final TimeStatisticalChart demo = new TimeStatisticalChart("Line Chart Demo 6");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}