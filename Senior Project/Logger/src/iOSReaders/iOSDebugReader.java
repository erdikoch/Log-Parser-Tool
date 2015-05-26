package iOSReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class iOSDebugReader extends Thread {

	public static final int DATE_AND_TIME_LENGTH = 19;

	String fileName;
	
	public iOSDebugReader (String fileName) {
		this.fileName = fileName;
	}
	public void run () {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("iOSDebug.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

			if (line.contains("[DEBUG]") || line.contains("[WARNING]")
					|| line.contains("[INFO]") || line.contains("[ERROR]")) {

				writer.write(line + "\n");

				if (((line.contains(": (") || line.contains("to:")) && (line
						.contains("[INFO]")))) {
					line = scanner.nextLine();
					while (!startsWithDate(line)) {
						writer.write(line + "\n");
						line = scanner.nextLine();
					}
				}

				if ((line.contains(": (") && (line.contains("[WARNING]")))) {
					line = scanner.nextLine();
					while (!startsWithDate(line)) {
						writer.write(line + "\n");
						line = scanner.nextLine();
					}
				}
			}

		}
		writer.flush();
		writer.close();
		scanner.close();
	}
	private boolean startsWithDate(String text) {
		return text.length() >= DATE_AND_TIME_LENGTH && text.startsWith("20")
				&& text.charAt(4) == '-' && text.charAt(7) == '-';
	}
}
