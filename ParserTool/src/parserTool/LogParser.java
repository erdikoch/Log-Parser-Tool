package parserTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class LogParser {

	// Length of the strings for date and time in each line of the log
	public static final int DATE_AND_TIME_LENGTH_WITH_MILLISECONDS = 23;
	public static final int DATE_AND_TIME_LENGTH = 19;

	// Buffer size of the reader
	protected final int READER_SIZE = 262144;

	// The start date and time of the log(needed for title of the GUI window)
	protected String startDateAndTime = "";

	protected File inputFile;
	protected BufferedReader logReader;
	protected BufferedWriter writer;

	/**
	 * Constructs a LogParser object and sets its startDateandTime attribute by
	 * reading the log file directly.
	 */
	public LogParser(File logFile) throws IOException {
		inputFile = logFile;
		logReader = new BufferedReader(new FileReader(inputFile));
		if (startDateAndTime == "") {

			// Check for the first line that starts with a date
			boolean temp = true;
			while (temp) {

				String line = logReader.readLine();
				if (line.equals(null))
					throw new IOException();
				if (startsWithDate(line)) {
					startDateAndTime = line.substring(0, DATE_AND_TIME_LENGTH);
					temp = false;
				}
			}
		}
		logReader.close();
	}

	public static boolean startsWithDate(String text) {
		return text.length() >= DATE_AND_TIME_LENGTH && text.startsWith("20")
				&& text.charAt(4) == '-' && text.charAt(7) == '-';
	}

	/** Overridden in subclasses */
	public void parse() throws IOException {
		return;
	}

}
