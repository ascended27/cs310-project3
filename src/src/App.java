package src;

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
					char[] record = formatKey(key);
					if (df.findRecord(record)) {
						String tmp = "";
						
						for(int i = 0; i < record.length; i++)
							tmp+=record[i];
						key = tmp.substring(0, keySize).substring(0,key.length()-1).trim();
						loc = tmp.substring(0, locSize).substring(0,loc.length()-1).trim();
						alt = tmp.substring(0, altSize);
						
						System.out.printf(key+"\n\n");
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
				System.out.println(e);
				System.out.printf("Please enter a numeric value\n\n");
			}
		}
	}

	// TODO: Load file
	private void loadFile(String fileName) {

	}

	private char[] formatKey(String key) {
		char[] toReturn = new char[recordSize];

		for (int i = 0; i < key.length(); i++)
			toReturn[i] = key.charAt(i);
		for (int i = toReturn.length; i < recordSize; i++) {
			if (i == keySize - 1)
				toReturn[i] = '#';
			else
				toReturn[i] = ' ';
		}

		return toReturn;
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
