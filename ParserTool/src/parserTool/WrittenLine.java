package parserTool;

/**
 * @author Erdi Koc
 * @author Emre Findik
 */

public class WrittenLine {

	private String associatedLine;
	private boolean isWritten;

	public WrittenLine(String associatedLine, boolean isWritten) {
		this.associatedLine = associatedLine;
		this.isWritten = isWritten;
	}

	public boolean getWritten() {
		return isWritten;
	}

	public String getLine() {
		return associatedLine;
	}

	public void setWritten() {
		isWritten = true;
	}

}
