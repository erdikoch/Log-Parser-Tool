package androidReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class AndroidWarnReader extends Thread{
	
	String fileName;
	
	public AndroidWarnReader (String fileName) {

		
		this.fileName = fileName;
	}
	
	public void run () {
		File file = new File(fileName);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Warn.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!line.contains("V/") && !line.contains("D/")
					&& !line.contains("I/")) {
				writer.write(line);
				writer.println();
			}
		}
		writer.flush();
		scanner.close();
		writer.close();
	}	
}
