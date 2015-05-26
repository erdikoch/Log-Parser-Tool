package iOSReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class iOSVerboseReader extends Thread {
	
	String fileName;
	
	public iOSVerboseReader(String fileName){
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
			writer = new PrintWriter("iOSVerbose.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			writer.write(line + "\n");
		}
		writer.flush();
		writer.close();
		scanner.close();

	}
}
