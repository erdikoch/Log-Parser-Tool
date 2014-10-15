package parserTool;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class ColoredTag {

	private String tag;
	private String htmlColorCode;

	public ColoredTag(String tagWord, String color, boolean isError) {
		htmlColorCode = "<span p style=\"color:" + color + "\">";
		if (isError)
			htmlColorCode.replace(" p ", " b ");
		tag = tagWord;
	}

	public ColoredTag(String tagWord) {
		tag = tagWord;
		htmlColorCode = "<span p style=\"color:black\">";
	}

	public String getTag() {
		return tag;
	}

	public String getColor() {
		return htmlColorCode;
	}

}
