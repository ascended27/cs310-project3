package src;

public class Driver {

	public static void main(String[] args) {
		Disk disk = new Disk(2000, 120);
		DirectFile df = new DirectFile(disk, 60, 27, 572, 1);

		App app = new App(df);
		app.start();
	}
}
