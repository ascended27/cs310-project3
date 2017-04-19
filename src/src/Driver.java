package src;

public class Driver {

	public static void main(String[] args) {
		Disk disk = new Disk();
		DirectFile df = new DirectFile(disk, 60, 27, 0, 2);

		App app = new App(df);
		app.start();
	}
}
