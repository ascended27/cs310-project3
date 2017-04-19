package src;

import java.util.Scanner;

public class App {

	private int keySize = 27;
	private int locSize = 27;
	private int altSize = 6;
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
				switch (action) {
				case 1:
					System.out.printf("\nMountain Name: ");
					key = in.nextLine();
					if (key.length() > 27)
						key = key.substring(0, 26);
					System.out.printf("Country: ");
					String loc = in.nextLine();
					if (loc.length() > 27)
						loc = loc.substring(0, 26);
					System.out.printf("Altitude: ");
					String alt = in.nextLine();
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
					char[] record = formatKey(key).toCharArray();
					if (df.findRecord(record)) {
						for (int i = 0; i < record.length; i++) {
							if (record[i] != '#')
								System.out.print(record[i]);
							else
								System.out.print(',');
						}
						System.out.println();
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
	private void loadFile(String fileName) {

	}

	private String formatKey(String key) {
		for (int i = key.length(); i < keySize; i++)
			key += " ";
		return key;

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
