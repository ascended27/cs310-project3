package src;

public class Driver {

	public static void main(String[] args) {

		// Initialize disk, direct file, and app
		Disk disk = new Disk(2000, 512);
		DirectFile df = new DirectFile(disk, 60, 27, 1024, 600);
		App app = new App(df);

		// Start the app with mountains.txt as an initial data source.
		app.start("mountains.txt");
	}

}
