package src;

@SuppressWarnings("unused")
public class DirectFile {

	private Disk disk; // disk on which the file will be written
	private char[] buffer; // disk buffer
	private int recordSize; // in characters
	private int keySize; // in characters
	private int recordsPerSector;
	private int recordsInSector;
	private int sectorInUse;
	private int firstAllocated; // sector number
	private int bucketsAllocated; // buckets (i.e. sectors) originally allocated
	private int firstOverflow; // sector number
	private int overflowBuckets; // count of overflow buckets in use
	private int sectorSize;
	
	public DirectFile(Disk disk, int recordSize, int keySize, int firstAllocated, int bucketsAllocated) {
		this.disk = disk;
		this.recordSize = recordSize;
		this.keySize = keySize;
		this.firstAllocated = firstAllocated;
		this.bucketsAllocated = bucketsAllocated;
		this.sectorInUse = firstAllocated;
		sectorSize = disk.getSectorSize();
		recordsPerSector = disk.getSectorSize() / recordSize;
		firstOverflow = 0;
		overflowBuckets = 0;
		buffer = new char[disk.getSectorSize()];
		disk.readSector(firstAllocated, buffer);
	}

	public boolean insertRecord(char[] record) {
		int bucket = hash(getKey(record));
		disk.readSector(bucket + firstAllocated, buffer);

		char[] tmp = new char[recordSize];
		copyArr(record,tmp);
		
		if(findRecord(tmp))
			return false;
		
		int index = getEmptyRecord();
		if(index == sectorSize){
			// Buffer overflowed
		}

		for(int i = 0; i < recordSize; i++){
			buffer[index++] = record[i];
		}
		disk.writeSector(bucket, buffer);
		return true;
	}

	public boolean findRecord(char[] record) {
		if(overflowBuckets != 0){
			// there is a chance the record is in an overflow bucket
		}
		
		int bucket = hash(getKey(record));
		System.out.println(bucket);
		disk.readSector(bucket+firstAllocated, buffer);
		int index=-1;
		for(int i = 0; i < sectorSize; i *= recordSize){
			if(checkKey(i, record)){
				index = i;
				break;
			}
			if(i == 0)
				i = 1;
		}
		
		if(index != -1){
			for(int i = 0; i < recordSize; i++){
				record[i] = buffer[index++];
			}
			return true;
		}
		
		return false;
	}

	private int hash(char[] key) {
		return Math.abs(key.toString().hashCode()) % (bucketsAllocated);
	}

	private boolean checkKey(int index,char[] key){
		for(int i = 0; i < keySize; i++){
			if(buffer[index+i] != key[i])
				return false;
		}
		return true;
	}
	
	// Copy arr1 to arr2
	private boolean copyArr(char[] src, char[] dest) {
		if (src.length <= dest.length) {
			for (int i = 0; i < src.length; i++)
				dest[i] = src[i];
			return true;
		}
		return false;
	}

	private char[] getKey(char[] record) {
		char[] key = new char[keySize];

		for (int i = 0; i < keySize; i++) {
			if (record[i] == '#')
				break;
			key[i] = record[i];
		}

		return key;
	}

	private int getEmptyRecord() {
		int i = 0;
		while (i < sectorSize){
			if (buffer[i] == '\000')
				return i;
			i++;
		}
		return ++i;
	}
}