package parserTool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class TaggedString {

	public static final String HTML_START_TAG = "<html> <font face=\"Courier New\" size=\"3\">";

	private String string;
	private String tag;
	private ArrayList<ColoredTag> tagsToBeParsed;

	public TaggedString(String taggedWord) {
		string = HTML_START_TAG;
		tag = taggedWord;
	}

	public TaggedString(TagParser parser, int tagPosition) {
		string = HTML_START_TAG;
		tag = parser.getTags().get(tagPosition).getTag();
		tagsToBeParsed = new ArrayList<ColoredTag>();
		for (int index = tagPosition; index < parser.getTags().size(); index++) {
			tagsToBeParsed.add(parser.getTags().get(index));
		}
	}

	public String getString() {
		return string;
	}

	public void setString(String s) {
		string = s;
	}

	public TaggedString setTag(String taggedWord) {
		tag = taggedWord;
		return this;
	}

	public String getTag() {
		return tag;
	}

	public ArrayList<ColoredTag> getTagsToBeParsed() {
		return tagsToBeParsed;
	}

	public void writeToFile() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(tag
				+ ".txt")));
		writer.write(string);
		writer.close();
	}

}
