

package androidScreen;

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
public class AndroidTimeChartView {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     * @throws FileNotFoundException 
     */
    public AndroidTimeChartView() throws FileNotFoundException {

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

        XYSeries seriesForDebug = createSeries("Debug", 1);
        dataset.addSeries(seriesForDebug);
        
        XYSeries seriesForInfo = createSeries("Info", 2);
        dataset.addSeries(seriesForInfo);
        
        XYSeries seriesForWarn = createSeries("Warn", 3);
        dataset.addSeries(seriesForWarn);
        
        XYSeries seriesForError = createSeries("Error", 4);
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
		int eleven = 0;
		int twelve = 0;
		int thirteen = 0;
		int fourteen = 0;
		int fifteen = 0;
		
        final XYSeries series1 = new XYSeries(nameOfAcivity);
        
        if(numberOfActivity == 0) {
        	File file = new File("Verbose.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("V/")) {
    				if(line.substring(3,5).equals("01")) one++;
    				else if(line.substring(3,5).equals("02")) two++;
    				else if(line.substring(3,5).equals("03")) three++;	
    				else if(line.substring(3,5).equals("04")) four++;
    				else if(line.substring(3,5).equals("05")) five++;		
    				else if(line.substring(3,5).equals("06")) six++;			
    				else if(line.substring(3,5).equals("07")) seven++;
    				else if(line.substring(3,5).equals("08")) eight++;				
    				else if(line.substring(3,5).equals("09")) nine++;
    				else if(line.substring(3,5).equals("10")) ten++;
    				else if(line.substring(3,5).equals("11")) eleven++;
    				else if(line.substring(3,5).equals("12")) twelve++;
    				else if(line.substring(3,5).equals("13")) thirteen++;
    				else if(line.substring(3,5).equals("14")) fourteen++;
    				else if(line.substring(3,5).equals("15")) fifteen++;
    			}
    		}
    		scanner.close();
        }
        //read the debug part
        else if (numberOfActivity == 1) {
        	File file = new File("Debug.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("D/")) {
    				if(line.substring(3,5).equals("01")) one++;
    				else if(line.substring(3,5).equals("02")) two++;
    				else if(line.substring(3,5).equals("03")) three++;	
    				else if(line.substring(3,5).equals("04")) four++;
    				else if(line.substring(3,5).equals("05")) five++;		
    				else if(line.substring(3,5).equals("06")) six++;			
    				else if(line.substring(3,5).equals("07")) seven++;
    				else if(line.substring(3,5).equals("08")) eight++;				
    				else if(line.substring(3,5).equals("09")) nine++;
    				else if(line.substring(3,5).equals("10")) ten++;
    				else if(line.substring(3,5).equals("11")) eleven++;
    				else if(line.substring(3,5).equals("12")) twelve++;
    				else if(line.substring(3,5).equals("13")) thirteen++;
    				else if(line.substring(3,5).equals("14")) fourteen++;
    				else if(line.substring(3,5).equals("15")) fifteen++;
    			}
    		}
    		scanner.close();
        }
        //read the info part
        else if (numberOfActivity == 2) {
        	File file = new File("Info.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("I/")) {
    				if(line.substring(3,5).equals("01")) one++;
    				else if(line.substring(3,5).equals("02")) two++;
    				else if(line.substring(3,5).equals("03")) three++;	
    				else if(line.substring(3,5).equals("04")) four++;
    				else if(line.substring(3,5).equals("05")) five++;		
    				else if(line.substring(3,5).equals("06")) six++;			
    				else if(line.substring(3,5).equals("07")) seven++;
    				else if(line.substring(3,5).equals("08")) eight++;				
    				else if(line.substring(3,5).equals("09")) nine++;
    				else if(line.substring(3,5).equals("10")) ten++;
    				else if(line.substring(3,5).equals("11")) eleven++;
    				else if(line.substring(3,5).equals("12")) twelve++;
    				else if(line.substring(3,5).equals("13")) thirteen++;
    				else if(line.substring(3,5).equals("14")) fourteen++;
    				else if(line.substring(3,5).equals("15")) fifteen++;
    			}
    		}
    		scanner.close();	
        }
        //read the warn part
        else if (numberOfActivity == 3) {
        	File file = new File("Warn.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("W/")) {
    				if(line.substring(3,5).equals("01")) one++;
    				else if(line.substring(3,5).equals("02")) two++;
    				else if(line.substring(3,5).equals("03")) three++;	
    				else if(line.substring(3,5).equals("04")) four++;
    				else if(line.substring(3,5).equals("05")) five++;		
    				else if(line.substring(3,5).equals("06")) six++;			
    				else if(line.substring(3,5).equals("07")) seven++;
    				else if(line.substring(3,5).equals("08")) eight++;				
    				else if(line.substring(3,5).equals("09")) nine++;
    				else if(line.substring(3,5).equals("10")) ten++;
    				else if(line.substring(3,5).equals("11")) eleven++;
    				else if(line.substring(3,5).equals("12")) twelve++;
    				else if(line.substring(3,5).equals("13")) thirteen++;
    				else if(line.substring(3,5).equals("14")) fourteen++;
    				else if(line.substring(3,5).equals("15")) fifteen++;
    			}
    		}
    		scanner.close();
        }
        //read the error part
        else if (numberOfActivity == 4) {
        	File file = new File("Error.txt");
            Scanner scanner = new Scanner(file);
    		String line;
    		while (scanner.hasNextLine()) {
    			line = scanner.nextLine();
    			if (line.contains("V/")) {
    				if(line.substring(3,5).equals("01")) one++;
    				else if(line.substring(3,5).equals("02")) two++;
    				else if(line.substring(3,5).equals("03")) three++;	
    				else if(line.substring(3,5).equals("04")) four++;
    				else if(line.substring(3,5).equals("05")) five++;		
    				else if(line.substring(3,5).equals("06")) six++;			
    				else if(line.substring(3,5).equals("07")) seven++;
    				else if(line.substring(3,5).equals("08")) eight++;				
    				else if(line.substring(3,5).equals("09")) nine++;
    				else if(line.substring(3,5).equals("10")) ten++;
    				else if(line.substring(3,5).equals("11")) eleven++;
    				else if(line.substring(3,5).equals("12")) twelve++;
    				else if(line.substring(3,5).equals("13")) thirteen++;
    				else if(line.substring(3,5).equals("14")) fourteen++;
    				else if(line.substring(3,5).equals("15")) fifteen++;
    			}
    		}
    		scanner.close();
        }
		
		series1.add(1, one);
		series1.add(2, two);
		series1.add(3, three);
		series1.add(4, four);
		series1.add(5, five);
		series1.add(6, six);
		series1.add(7, seven);
		series1.add(8, eight);
		series1.add(9, nine);
		series1.add(10, ten);
		series1.add(11, eleven);
		series1.add(12, twelve);
		series1.add(13, thirteen);
		series1.add(14, fourteen);
		series1.add(15, fifteen);

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