package src;

public class Driver {

	public static void main(String[] args) {
		
		Disk disk = new Disk(2000, 512);
		DirectFile df = new DirectFile(disk, 60, 27, 1024, 600);

		App app = new App(df);
		app.loadFile("mountains.txt");
		app.start();
	}

}
