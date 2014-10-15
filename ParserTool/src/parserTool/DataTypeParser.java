package parserTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class DataTypeParser extends LogParser {

	public static final String SENT_STARTED = "[SENT_START]";
	public static final String SENT_FINISHED = "[SENT_END]";
	public static final String RECEIVED_STARTED = "[RECEIVED_START]";
	public static final String RECEIVED_FINISHED = "[RECEIVED_END]";
	public static final String SENT = "[sent]";
	public static final String RECEIVED = "[received]";
	public static final String ERROR = " -- ERROR";
	public static final String LOCAL_SDP_SEND = "Local SDP will be sent to server for call:";
	private static String[] eventTypes = new String[] { "CallEnd", "Call",
			"CallAnswer", "CallCancel" };
	
	private File outputFile;
	private ArrowLayoutPanel arrowPanel;
	private String userName;

	public DataTypeParser(File logFile) throws IOException {
		super(logFile);
	}

	public void parse() throws IOException {
		logReader = new BufferedReader(new FileReader(inputFile));

		boolean isWritingReceived = false;
		boolean isWritingSent = false;
		boolean isWritingSentSDP = false;
		boolean previousLineIsBracket = false;

		String eventTypeBeingSent = null;
		String eventTypeSentTime = null;
		String line;

		WrittenLine currentLine;

		outputFile = new File("Output.txt");

		writer = new BufferedWriter(new FileWriter(outputFile));

		while ((line = logReader.readLine()) != null) {
			currentLine = new WrittenLine(line, false);

			if (userName == null && line.contains("@spidr.com")) {
				int index = line.indexOf("@spidr.com");
				userName = line.substring(line.lastIndexOf("/", index) + 1,
						index);
			}

			if (isWritingReceived) {
				if (previousLineIsBracket && startsWithDate(line)) {
					writeToFile(RECEIVED_FINISHED);
					isWritingReceived = false;
					previousLineIsBracket = false;
				} else {
					writeToFile(currentLine);
					if (line.startsWith("}"))
						previousLineIsBracket = true;
				}
			} else if (line.contains("eventType:")
					&& !line.contains("presenceWatcher")) {
				isWritingReceived = true;
				writeToFile(RECEIVED_STARTED);
				writeToFile(currentLine);
			}
			if (isWritingSent) {
				if (findEventTypeResponseHandler(line) != null) {
					if (eventTypeBeingSent == null) {
						eventTypeBeingSent = findEventTypeResponseHandler(line);
						eventTypeSentTime = line.substring(0,
								DATE_AND_TIME_LENGTH_WITH_MILLISECONDS);
					} else {
						writeToFile(eventTypeSentTime
								+ " eventType: "
								+ eventTypeBeingSent.substring(0, 1)
										.toLowerCase()
								+ eventTypeBeingSent.substring(1) + ERROR);
						writeToFile(SENT_FINISHED);
						eventTypeBeingSent = findEventTypeResponseHandler(line);
						writeToFile(SENT_STARTED);
					}
				} else if (containsResponseHandlerFinished(line,
						eventTypeBeingSent)) {
					writeToFile(eventTypeSentTime + " eventType: "
							+ eventTypeBeingSent.substring(0, 1).toLowerCase()
							+ eventTypeBeingSent.substring(1));
					writeToFile(SENT_FINISHED);
					eventTypeBeingSent = null;
					isWritingSent = false;
				}
			} else if (findEventTypeResponseHandler(line) != null) {
				eventTypeBeingSent = findEventTypeResponseHandler(line);
				isWritingSent = true;
				writeToFile(SENT_STARTED);
			}
			if (isWritingSentSDP) {
				if (startsWithDate(line)) {
					isWritingSentSDP = false;
				} else {
					writeToFile(currentLine);
				}
			} else if (line.contains(LOCAL_SDP_SEND)) {
				isWritingSentSDP = true;
				isWritingSent = true;
				writeToFile(SENT_STARTED);
				writeToFile(line.substring(0,
						DATE_AND_TIME_LENGTH_WITH_MILLISECONDS));
				writeToFile("sessionData = \""
						+ line.substring(line.indexOf(LOCAL_SDP_SEND)
								+ LOCAL_SDP_SEND.length()));
			}
		}

		writer.flush();
		logReader.close();
		writer.close();
		setArrowPanel();

	}

	private void writeToFile(String currentLine) throws IOException {
		if (startsWithDate(currentLine) && currentLine.contains(">")) {
			writer.write(currentLine.substring(0,
					DATE_AND_TIME_LENGTH_WITH_MILLISECONDS)
					+ currentLine.substring(currentLine.indexOf(">") + 2));
		} else {
			writer.write(currentLine);
		}
		writer.newLine();
	}

	private void writeToFile(WrittenLine currentLine) throws IOException {
		if (!currentLine.getWritten()) {
			writeToFile(currentLine.getLine());
			currentLine.setWritten();
		}

	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setArrowPanel() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					outputFile));
			String line;
			String portionOfText = "";
			boolean isAddingSent = false;
			boolean isInInnerPortion = false;
			String innerPortionOfText = "";
			ArrayList<String> portions = new ArrayList<String>();
			ArrayList<String> innerPortions = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if (line.contains(SENT_STARTED)) {
					portionOfText = SENT;
					isAddingSent = true;
				} else if (line.contains(RECEIVED_STARTED)) {
					if (isAddingSent) {
						innerPortionOfText = RECEIVED;
						isInInnerPortion = true;
					} else {
						portionOfText = RECEIVED;
					}
				} else if (line.contains(RECEIVED_FINISHED)) {
					if (isInInnerPortion) {
						if (innerPortionOfText != "")
							innerPortions.add(innerPortionOfText);
						isInInnerPortion = false;
					} else {
						if (portionOfText != "")
							portions.add(portionOfText);
					}
				} else if (line.contains(SENT_FINISHED)) {
					if (portionOfText != "")
						portions.add(portionOfText);
					for (String innerPortion : innerPortions) {
						portions.add(innerPortion);
					}
					innerPortions = new ArrayList<String>();
					isAddingSent = false;
				} else if (isInInnerPortion) {
					innerPortionOfText = innerPortionOfText + line + "\n";
				} else {
					portionOfText = portionOfText + line + "\n";
				}
			}

			reader.close();
			ArrayList<ArrowButton> buttons = new ArrayList<ArrowButton>();
			for (String portion : portions) {
				buttons.add(new ArrowButton(portion));
			}
			ArrayList<Arrow> arrowList = new ArrayList<Arrow>();
			for (ArrowButton button : buttons) {
				arrowList.add(new Arrow(button));
			}
			arrowPanel = new ArrowLayoutPanel(arrowList, userName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrowLayoutPanel getArrowPanel() {
		return arrowPanel;
	}

	private String findEventTypeResponseHandler(String currentLine) {
		for (String eventType : eventTypes) {
			if (currentLine.contains(eventType
					+ "ResponseHandler-onFinish:>: started")) {
				return eventType;
			}
		}
		return null;
	}

	private boolean containsResponseHandlerFinished(String currentLine,
			String eventType) {
		return currentLine.contains(eventType
				+ "ResponseHandler-onFinish:>: finished");
	}

}
