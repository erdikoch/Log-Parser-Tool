package iOSReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class iOSErrorReader extends Thread {
	
	String fileName;
	
	public iOSErrorReader(String fileName) {
		this.fileName = fileName;
	}
	
	public void run () {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("iOSError.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

			if (line.contains("[ERROR]")) {
				writer.write(line + "\n");
			}

		}
		writer.flush();
		writer.close();
		scanner.close();
		
	}

}
