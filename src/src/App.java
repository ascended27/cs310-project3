package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class App {

	// Initialize record and record component sizes
	private final int keySize = 27;
	private final int locSize = 27;
	private final int altSize = 6;
	private final int recordSize = keySize + locSize + altSize;

	// Declare a Direct file that will be used to store records
	private DirectFile df;

	// Declare record components
	private String key;
	private String loc;
	private String alt;

	// Default constructor for the App
	public App(DirectFile df) {
		this.df = df;
	}

	/**
	 * Starts the application with the passed filename which stores the initial
	 * mountain data.
	 * 
	 * @param filename
	 *            File that stores the initial file data.
	 */
	public void start(String filename) {
		// Load the passed file.
		loadFile(filename);

		// Control variables for menu loop
		boolean sentinel = true;
		Scanner in = new Scanner(System.in);
		int action = -1;

		// Menu loop
		while (sentinel) {
			// Display the app menu.
			printMenu();

			// Attempt to get user input.
			try {
				// Get user input.
				action = Integer.parseInt(in.nextLine());

				// Switch to the function that the user selected.
				switch (action) {
				// Case 1 - Insert a mountain.
				case 1:
					// Get the mountain name
					System.out.printf("\nMountain Name: ");
					key = in.nextLine();
					if (key.length() > keySize)
						key = key.substring(0, keySize);
					// Get the mountain location
					System.out.printf("Country: ");
					loc = in.nextLine();
					if (loc.length() > locSize)
						loc = loc.substring(0, locSize);
					// Get the mountain altitude
					System.out.printf("Altitude: ");
					alt = in.nextLine();
					if (alt.length() > altSize)
						alt = alt.substring(0, altSize);
					// Get the combined string of all the record components
					String insert = format(key, loc, alt);
					// Insert the record, and if it is successful then let the user know it was.
					if (df.insertRecord(insert.toCharArray()))
						System.out.printf("\nRecord inserted successfully\n\n");
					// Otherwise notify them that it failed
					else
						System.out.printf("\nRecord insert failed\n\n");
					break;
				// Case 2 - Find a mountain.
				case 2:
					// Get the key to find
					System.out.printf("\nMountain Name: ");
					key = in.nextLine();
					// If the key is greater than 27 truncate it down.
					if (key.length() > keySize)
						key = key.substring(0, keySize);
					// Initialize the altitude and location strings to the empty
					// string
					alt = loc = "";

					// Format all key components and set the character array to
					// record
					char[] record = format(key, loc, alt).toCharArray();
					// Search for the record, if it is successful then print out
					// the record
					if (df.findRecord(record)) {
						// Initialize a string to hold the string value of the
						// record
						String tmp = "";
						// Copy the record found to tmp
						for (int i = 0; i < record.length; i++)
							tmp += record[i];
						// Extract the record components to the proper variables
						key = tmp.substring(0, keySize).substring(0, key.length()).trim();
						loc = tmp.substring(keySize, keySize + locSize);
						loc = loc.substring(0, locSize - 1).trim();
						alt = tmp.substring(keySize + locSize, recordSize);

						// Output the record for the user
						System.out.printf(key + ", " + loc + ", " + alt + "\n\n");
					}
					// Otherwise the record is not on the disk
					else
						System.out.printf("\nRecord not found\n\n");
					break;
				// Case 3 - Quit
				case 3:
					// Close the scanner
					in.close();
					// Set the menu sentinel to false so that the menu loop ends
					sentinel = false;
					break;
				// User did not enter a valid option
				default:
					System.out.printf("Please enter a valid option\n\n");
				}
			} catch (Exception e) {
				System.out.printf("Please enter a numeric value\n\n");
			}
		}
	}

	//TODO: Finish Commenting
	public void loadFile(String fileName) {
		try {
			String path = new File("").getAbsolutePath() + "/src/src/" + fileName;
			path = path.replace("\\", "/");
			BufferedReader br = new BufferedReader(new FileReader(path));

			String line = br.readLine();
			while (line != null) {
				String key = line.substring(0, line.indexOf("#"));
				line = line.substring(line.indexOf("#") + 1, line.length());
				String loc = line.substring(0, line.indexOf("#"));
				line = line.substring(line.indexOf("#") + 1, line.length());
				String alt = line;

				df.insertRecord(format(key, loc, alt).toCharArray());
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

		return key.substring(0, keySize) + loc.substring(0, locSize) + alt;
	}

	private void printMenu() {
		System.out.printf("1.Insert Record\n2.Find Record\n3.Quit\n-> ");
	}
}
