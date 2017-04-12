package src;

public class Disk {
	private int sectorCount; // sectors on the disk
	private int sectorSize; // characters in a sector
	private char[][] store; // all disk data is stored here

	// for default sectorCount and sectorSize
	public Disk() {
		sectorCount = 10000;
		sectorSize = 512;
		store = new char[sectorCount][sectorSize];
	}

	// for user specified sectorCount and sectorSize
	public Disk(int sectorCount, int sectorSize) {
		this.sectorCount = sectorCount;
		this.sectorSize = sectorSize;
		store = new char[sectorCount][sectorSize];

	}

	// sector to buffer{}
	public void readSector(int sectorNumber, char[] buffer) {
		for(int i = 0; i < sectorSize; i++)
			buffer[i] = store[sectorNumber][i];
	}

	// buffer to sector{}
	public void writeSector(int sectorNumber, char[] buffer) {
		for(int i = 0; i < sectorSize; i++)
			store[sectorNumber][i] = buffer[i];
	} 

	// Returns the sectorCount
	public int getSectorCount() {
		return sectorCount;
	}

	// Returns the sectorSize
	public int getSectorSize() {
		return sectorSize;
	}
}
