package parserTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class TagParser extends LogParser {

	private static final String HTML_LINE_BREAK = "<br>";
	private static final String HTML_SPAN_END_TAG = "</span>";
	private static final String HTML_END_TAG = "</font></html>";
	private ArrayList<TaggedString> taggedStrings;
	private ArrayList<ColoredTag> tags;

	public TagParser(File logFile) throws IOException {
		super(logFile);
	}

	public void parse(ArrayList<ColoredTag> tagList) throws IOException {
		tags = tagList;
		setTaggedStrings(tags);
		parse();

	}

	@Override
	public void parse() throws IOException {

		logReader = new BufferedReader(new FileReader(inputFile), READER_SIZE);
		
		// Indicates whether the previous line was tagged
		String currentLine;
		ColoredTag taggedWith = null;
		while ((currentLine = logReader.readLine()) != null) {
			for (TaggedString taggedString : taggedStrings) {
				tagsLoop: for (ColoredTag tag : taggedString
						.getTagsToBeParsed()) {
					if (currentLine.contains(tag.getTag())) {
						setTaggedString(taggedString, currentLine, tag);
						taggedWith = tag;
						break tagsLoop;

					} else if (taggedWith == tag
							&& !startsWithDate(currentLine)) {
						setTaggedString(taggedString, currentLine, tag);
						break tagsLoop;

					} else {
						if (taggedWith == tag)
							taggedWith = null;
					}
				}

			}
		}
		for (TaggedString taggedString : taggedStrings) {
			taggedString.setString(taggedString.getString() + HTML_END_TAG);
		}

		logReader.close();

	}

	public void customParse(TaggedString taggedString, boolean writeToFile)
			throws IOException {

		logReader = new BufferedReader(new FileReader(inputFile), READER_SIZE);
		String currentLine;
		String input = taggedString.getTag();
		String string = taggedString.getString();
		ArrayList<String> keywords = setKeywords(input);
		while ((currentLine = logReader.readLine()) != null) {
			for (String tag : keywords) {
				if (currentLine.contains(tag)) {
					string = string + currentLine + HTML_LINE_BREAK;
					break;
				}
			}
		}
		taggedString.setString(string + HTML_END_TAG);

		logReader.close();
		if (writeToFile)
			taggedString.writeToFile();

	}

	private ArrayList<String> setKeywords(String input) {
		ArrayList<String> keywords = new ArrayList<String>();
		int indexOfSemiColon;
		while (input.contains(";;")) {
			indexOfSemiColon = input.indexOf(";;");
			keywords.add(input.substring(0, indexOfSemiColon));
			input = input.substring(indexOfSemiColon + 2);
		}
		keywords.add(input);
		return keywords;
	}

	private void setTaggedString(TaggedString taggedString, String currentLine,
			ColoredTag coloredTag) throws IOException {
		String string = taggedString.getString() + coloredTag.getColor();

		if (startsWithDate(currentLine)) {
			string = string.concat(currentLine.substring(0,
					DATE_AND_TIME_LENGTH_WITH_MILLISECONDS + 1)
					+ currentLine.substring(currentLine.indexOf(coloredTag.getTag()))
					+ HTML_LINE_BREAK);
		} else {
			string = string.concat(currentLine + HTML_LINE_BREAK);
		}

		taggedString.setString(string + HTML_SPAN_END_TAG);
	}

	private void setTaggedStrings(ArrayList<ColoredTag> tags) {
		taggedStrings = new ArrayList<TaggedString>();
		for (int index = 0; index < tags.size(); index++) {
			taggedStrings.add(new TaggedString(this, index));
		}
	}

	public ArrayList<TaggedString> getTaggedStrings() {
		return taggedStrings;
	}

	public ArrayList<ColoredTag> getTags() {
		return tags;
	}

	public void writeToFiles(JFrame frame) throws IOException {
		for (TaggedString taggedString : taggedStrings) {

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					setFileName(taggedString.getTag()))));
			writer.write(taggedString.getString());
			writer.close();

		}
	}

	public static String setFileName(String tag) {
		return tag + ".txt";
	}

}
