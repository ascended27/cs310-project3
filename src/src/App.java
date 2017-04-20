package src;

import java.io.File;
import java.util.Scanner;

public class App {

	private int keySize = 27;
	private int locSize = 27;
	private int altSize = 6;
	private int recordSize = keySize + locSize + altSize;
	private DirectFile df;

	public App(DirectFile df) {
		this.df = df;
		// TODO: Load file
	}

	public void start() {
		boolean sentinel = true;
		Scanner in = new Scanner(System.in);
		int action = -1;
		// Menu loop
		while (sentinel) {
			printMenu();
			try {
				action = Integer.parseInt(in.nextLine());

				String key;
				String loc;
				String alt;
				switch (action) {
				case 1:
					System.out.printf("\nMountain Name: ");
					key = in.nextLine();
					if (key.length() > 27)
						key = key.substring(0, 26);
					System.out.printf("Country: ");
					loc = in.nextLine();
					if (loc.length() > 27)
						loc = loc.substring(0, 26);
					System.out.printf("Altitude: ");
					alt = in.nextLine();
					if (alt.length() > 6)
						alt = alt.substring(0, 5);
					String insert = format(key, loc, alt);
					if (df.insertRecord(insert.toCharArray()))
						System.out.printf("\nRecord inserted successfully\n\n");
					else
						System.out.printf("\nRecord insert failed\n\n");
					break;
				case 2:
					System.out.printf("\nMountain Name: ");
					key = in.nextLine();
					if (key.length() > 27)
						key = key.substring(0, 26);
					alt = loc = "";
					char[] record = format(key,loc,alt).toCharArray();
					if (df.findRecord(record)) {
						String tmp = "";
						loc = "";
						for (int i = 0; i < record.length; i++)
							tmp += record[i];
						key = tmp.substring(0, keySize).substring(0, key.length()).trim();
						loc = tmp.substring(keySize, keySize + locSize);
						loc = loc.substring(0, locSize - 1).trim();
						alt = tmp.substring(keySize + locSize, keySize + locSize + altSize);

						System.out.printf(key + ", " + loc + ", " + alt + "\n\n");
					} else
						System.out.printf("\nRecord not found\n\n");
					break;
				case 3:
					in.close();
					sentinel = false;
					break;
				default:
					System.out.printf("Please enter a valid option\n\n");
				}
			} catch (Exception e) {
				System.out.printf("Please enter a numeric value\n\n");
			}
		}
	}

	// TODO: Load file
	public void loadFile(String fileName) {
		try {
			// Open the file
			File inFile = new File("./"+fileName);

			// Create a new scanner for the inFile
			Scanner in = new Scanner(inFile);

			boolean success = false;

			// Iterate over the file and insert each new line to the document
			while (in.hasNextLine()) {
				String info = in.nextLine();
				int index = info.indexOf("#");
				String key = info.substring(0, index);
				info = info.substring(index+1,info.length());
				index = info.indexOf("#");
				String loc = info.substring(0,index);
				String alt = info.substring(index+1,info.length());
				String insert = format(key, loc, alt); 
				success = df.insertRecord(insert.toCharArray());
			}
			if(!success)
				System.out.printf("Failed to load all mountains!\n\n");
			
			// Close the scanner.
			in.close();

			// If an error occurs notify that user.
		} catch (Exception e) {
			System.out.printf("%s does not exist\n\n", fileName);
		}
	}

	private String format(String key, String loc, String alt) {
		for (int i = key.length(); i < keySize - 1; i++)
			key += " ";
		for (int i = loc.length(); i < locSize - 1; i++)
			loc += " ";
		for (int i = alt.length(); i < altSize; i++)
			alt += " ";

		return key + "#" + loc + "#" + alt;
	}

	private void printMenu() {
		System.out.printf("1.Insert Record\n2.Find Record\n3.Quit\n-> ");
	}
}
