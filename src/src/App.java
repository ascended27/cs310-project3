package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class App {

	private int keySize = 27;
	private int locSize = 27;
	private int altSize = 6;
	private int recordSize = keySize + locSize + altSize;
	private DirectFile df;

	public App(DirectFile df) {
		this.df = df;
	}

	public void start(String filename) {
		loadFile(filename);
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
						key = key.substring(0, 27);
					alt = loc = "";
					char[] record = format(key, loc, alt).toCharArray();
					if (df.findRecord(record)) {
						String tmp = "";
						loc = "";
						for (int i = 0; i < record.length; i++)
							tmp += record[i];
						key = tmp.substring(0, keySize).substring(0, key.length()).trim();
						loc = tmp.substring(keySize, keySize + locSize);
						loc = loc.substring(0, locSize - 1).trim();
						alt = tmp.substring(keySize + locSize, recordSize);

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

	public void loadFile(String fileName) {
		try {
			String path = new File("").getAbsolutePath() +"/src/src/"+fileName;
			path = path.replace("\\", "/");
			BufferedReader br = new BufferedReader(new FileReader(path));

			String line = br.readLine();
			while (line != null) {
				String key = line.substring(0, line.indexOf("#"));
				line = line.substring(line.indexOf("#") + 1, line.length());
				String loc = line.substring(0, line.indexOf("#"));
				line = line.substring(line.indexOf("#") + 1, line.length());
				String alt = line;

				df.insertRecord(format(key,loc,alt).toCharArray());
				line = br.readLine();
			}

			br.close();
			// If an error occurs notify that user.
		} catch (Exception e) {
			System.out.printf("%s does not exist\n\n", fileName);
		}
	}

	private String format(String key, String loc, String alt) {
		for (int i = key.length(); i < keySize; i++)
			key += " ";
		for (int i = loc.length(); i < locSize; i++)
			loc += " ";
		for (int i = alt.length(); i < altSize; i++)
			alt += " ";

		return key.substring(0,keySize) + loc.substring(0, locSize) + alt;
	}

	private void printMenu() {
		System.out.printf("1.Insert Record\n2.Find Record\n3.Quit\n-> ");
	}
}
