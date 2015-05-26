package iOSScreen;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

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

/**
 * A simple demonstration application showing how to create a line chart using data from an
 * {@link XYDataset}.
 *
 */
public class iOSTimeChartView {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     * @throws FileNotFoundException 
     */
    public iOSTimeChartView() throws FileNotFoundException {

        super();
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        frame.setContentPane(chartPanel);
        frame.setBounds(200, 200, 500, 270);
        frame.setVisible(true);

    }
	/**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     * @throws FileNotFoundException 
     */
    private XYDataset createDataset() throws FileNotFoundException {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries seriesForVerbose = createSeries("Verbose", 0);
        dataset.addSeries(seriesForVerbose);

        XYSeries seriesForTrace = createSeries("Trace", 1);
        dataset.addSeries(seriesForTrace);
        
        XYSeries seriesForDebug = createSeries("Debug", 2);
        dataset.addSeries(seriesForDebug);
        
        XYSeries seriesForInfo = createSeries("Info", 3);
        dataset.addSeries(seriesForInfo);
        
        XYSeries seriesForWarn = createSeries("Warn", 4);
        dataset.addSeries(seriesForWarn);
        
        XYSeries seriesForError = createSeries("Error", 5);
        dataset.addSeries(seriesForError);
        
        return dataset;
        
    }
    
    private XYSeries createSeries(String nameOfAcivity, int numberOfActivity) throws FileNotFoundException {
    	
    	int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		int seven = 0;
		int eight = 0;
		int nine = 0;
		int ten = 0;
		
        final XYSeries series1 = new XYSeries(nameOfAcivity);
        
        if(numberOfActivity == 0) {
        	File file = new File("iOSVerbose.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("[VERBOSE]")) {
    				if(line.substring(8,10).equals("21")) one++;
    				else if(line.substring(8,10).equals("22")) two++;
    				else if(line.substring(8,10).equals("23")) three++;	
    				else if(line.substring(8,10).equals("24")) four++;
    				else if(line.substring(8,10).equals("25")) five++;		
    				else if(line.substring(8,10).equals("26")) six++;			
    				else if(line.substring(8,10).equals("27")) seven++;
    				else if(line.substring(8,10).equals("28")) eight++;				
    				else if(line.substring(8,10).equals("29")) nine++;
    				else if(line.substring(8,10).equals("30")) ten++;
    			}
    		}
    		scanner.close();
        }
      //read the debug part
        else if (numberOfActivity == 1) {
        	File file = new File("iOSTrace.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("[TRACE]")) {
    				if(line.substring(8,10).equals("21")) one++;
    				else if(line.substring(8,10).equals("22")) two++;
    				else if(line.substring(8,10).equals("23")) three++;	
    				else if(line.substring(8,10).equals("24")) four++;
    				else if(line.substring(8,10).equals("25")) five++;		
    				else if(line.substring(8,10).equals("26")) six++;			
    				else if(line.substring(8,10).equals("27")) seven++;
    				else if(line.substring(8,10).equals("28")) eight++;				
    				else if(line.substring(8,10).equals("29")) nine++;
    				else if(line.substring(8,10).equals("30")) ten++;
    			}
    		}
    		scanner.close();
        }
        //read the debug part
        else if (numberOfActivity == 2) {
        	File file = new File("iOSDebug.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("[DEBUG]")) {
    				if(line.substring(8,10).equals("21")) one++;
    				else if(line.substring(8,10).equals("22")) two++;
    				else if(line.substring(8,10).equals("23")) three++;	
    				else if(line.substring(8,10).equals("24")) four++;
    				else if(line.substring(8,10).equals("25")) five++;		
    				else if(line.substring(8,10).equals("26")) six++;			
    				else if(line.substring(8,10).equals("27")) seven++;
    				else if(line.substring(8,10).equals("28")) eight++;				
    				else if(line.substring(8,10).equals("29")) nine++;
    				else if(line.substring(8,10).equals("30")) ten++;
    			}
    		}
    		scanner.close();
        }
        //read the info part
        else if (numberOfActivity == 3) {
        	File file = new File("iOSInfo.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("[INFO]")) {
    				if(line.substring(8,10).equals("21")) one++;
    				else if(line.substring(8,10).equals("22")) two++;
    				else if(line.substring(8,10).equals("23")) three++;	
    				else if(line.substring(8,10).equals("24")) four++;
    				else if(line.substring(8,10).equals("25")) five++;		
    				else if(line.substring(8,10).equals("26")) six++;			
    				else if(line.substring(8,10).equals("27")) seven++;
    				else if(line.substring(8,10).equals("28")) eight++;				
    				else if(line.substring(8,10).equals("29")) nine++;
    				else if(line.substring(8,10).equals("30")) ten++;
    			}
    		}
    		scanner.close();	
        }
        //read the warn part
        else if (numberOfActivity == 4) {
        	File file = new File("iOSWarning.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("[WARN]")) {
    				if(line.substring(8,10).equals("21")) one++;
    				else if(line.substring(8,10).equals("22")) two++;
    				else if(line.substring(8,10).equals("23")) three++;	
    				else if(line.substring(8,10).equals("24")) four++;
    				else if(line.substring(8,10).equals("25")) five++;		
    				else if(line.substring(8,10).equals("26")) six++;			
    				else if(line.substring(8,10).equals("27")) seven++;
    				else if(line.substring(8,10).equals("28")) eight++;				
    				else if(line.substring(8,10).equals("29")) nine++;
    				else if(line.substring(8,10).equals("30")) ten++;
    			}
    		}
    		scanner.close();
        }
        //read the error part
        else if (numberOfActivity == 5) {
        	File file = new File("iOSError.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("[ERROR]")) {
    				if(line.substring(8,10).equals("21")) one++;
    				else if(line.substring(8,10).equals("22")) two++;
    				else if(line.substring(8,10).equals("23")) three++;	
    				else if(line.substring(8,10).equals("24")) four++;
    				else if(line.substring(8,10).equals("25")) five++;		
    				else if(line.substring(8,10).equals("26")) six++;			
    				else if(line.substring(8,10).equals("27")) seven++;
    				else if(line.substring(8,10).equals("28")) eight++;				
    				else if(line.substring(8,10).equals("29")) nine++;
    				else if(line.substring(8,10).equals("30")) ten++;
    			}
    		}
    		scanner.close();
        }
		
		series1.add(21, one);
		series1.add(22, two);
		series1.add(23, three);
		series1.add(24, four);
		series1.add(25, five);
		series1.add(26, six);
		series1.add(27, seven);
		series1.add(28, eight);
		series1.add(29, nine);
		series1.add(30, ten);

		return series1; 
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
            "Time Statistics",      // chart title
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

}