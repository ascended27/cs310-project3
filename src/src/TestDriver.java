package src;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

public class TestDriver {

	// Initialize disk, direct file, and app
	Disk disk = new Disk(2000, 512);
	DirectFile df = new DirectFile(disk, 60, 27, 1024, 600);
	App app = new App(df);
	ArrayList<String> failures = new ArrayList<String>();

	@Test
	public void test(){
		
		app.loadFile("mountains.txt");
		try {
			String path = new File("").getAbsolutePath() +"/src/src/mountains.txt";
			path = path.replace("\\", "/");
			BufferedReader br = new BufferedReader(new FileReader(path));
			boolean success = false;
			String line = br.readLine();
			while (line != null) {
				String key = line.substring(0, line.indexOf("#"));
				line = line.substring(line.indexOf("#") + 1, line.length());
				String loc = line.substring(0, line.indexOf("#"));
				line = line.substring(line.indexOf("#") + 1, line.length());
				String alt = line;

				
				success = df.findRecord(format(key,loc,alt).toCharArray());
				assertEquals(true,success);
				
				if(!success)
					failures.add(key);
				
				line = br.readLine();
			}
			for(String key : failures)
				System.out.println(key);
			br.close();
			// If an error occurs notify that user.
		} catch (Exception e) {
			System.out.printf("mountains.txt does not exist\n\n");
		}
	}
	
	private String format(String key, String loc, String alt) {
		for (int i = key.length(); i < 27; i++)
			key += " ";
		for (int i = loc.length(); i < 27; i++)
			loc += " ";
		for (int i = alt.length(); i < 6; i++)
			alt += " ";

		return key.substring(0,27) + loc.substring(0, 27) + alt;
	}
	
	@After
	public void printResults(){
		if(!failures.isEmpty())
			for(String key : failures)
				System.out.println(key);
		else
			System.out.println("All records found");
	}
	
}
