package src;

@SuppressWarnings("unused")
public class DirectFile {

	private Disk disk; // disk on which the file will be written
	private char[] buffer; // disk buffer
	private int recordSize; // in characters
	private int keySize; // in characters
	private int recordsPerSector;
	private int firstAllocated; // sector number
	private int bucketsAllocated; // buckets (i.e. sectors) originally allocated
	private int firstOverflow; // sector number
	private int overflowBuckets; // count of overflow buckets in use
	private int sectorSize;

	/**
	 * Default constructor for the direct file
	 * 
	 * @param disk
	 *            Disk that the direct file will write to.
	 * @param recordSize
	 *            The size of each record being written to the disk.
	 * @param keySize
	 *            The size of the key field in each record.
	 * @param firstAllocated
	 *            The first sector to write records to.
	 * @param bucketsAllocated
	 *            The number of non overflow buckets originally allocated.
	 */
	public DirectFile(Disk disk, int recordSize, int keySize, int firstAllocated, int bucketsAllocated) {
		// Initialize variables with passed parameters.
		this.disk = disk;
		this.recordSize = recordSize;
		this.keySize = keySize;
		this.firstAllocated = firstAllocated;
		this.bucketsAllocated = bucketsAllocated;

		// Get the sector size and records per sector in the disk.
		sectorSize = disk.getSectorSize();
		recordsPerSector = disk.getSectorSize() / recordSize;

		// Initialize variables.
		firstOverflow = 0;
		overflowBuckets = 0;
		buffer = new char[disk.getSectorSize()];

		// Read the first sector into the buffer.
		disk.readSector(firstAllocated, buffer);
	}

	/**
	 * Inserts the passed record into the disk.
	 * 
	 * @param record
	 *            The record to insert.
	 * @return True if the operation succeeded, False if it failed
	 */
	public boolean insertRecord(char[] record) {

		// Get the bucket the record should be inserted in
		int bucket = hash(getKey(record)) + firstAllocated;

		// Copy the record to insert and attempt to find it. If it exists then
		// we have a duplicate key error.
		char[] tmp = new char[recordSize];
		copyArr(record, tmp);

		// If the record is found in the disk then we have a duplicate key error
		// so return false.
		if (findRecord(tmp))
			return false;

		// Get the bucket to that is going to be written to.
		disk.readSector(bucket, buffer);

		// Retrieve the first empty record in the buffer. If there are none in
		// this sector then the buffer will become the first sector with an open
		// space and index will be the first open space in that sector.
		int[] indexBucket = getEmptyRecord(bucket);
		int index = indexBucket[0];
		bucket = indexBucket[1];

		// Insert into the sector buffer
		for (int i = 0; i < recordSize; i++) {
			buffer[index++] = record[i];
		}

		// Write the updated sector buffer to the disk.
		disk.writeSector(bucket, buffer);

		// Operation was successful so return true.
		return true;
	}

	/**
	 * Searches the disk for the key field in the passed record. If it is found
	 * then the record is copied into the passed character array.
	 * 
	 * @param record
	 *            The record to find and the array to write the record to if
	 *            found.
	 * @return True if the operation succeeded, False if it failed
	 */
	public boolean findRecord(char[] record) {
		// Get the bucket the record should be inserted in.
		int bucket = hash(getKey(record)) + firstAllocated;

		// Read the sector the record should be into the buffer.
		disk.readSector(bucket, buffer);

		// Search through the buffer for the key.
		int index = -1;
		for (int i = 0; i < sectorSize; i += recordSize) {
			// If the key is in the buffer then set the index to the start of
			// the record.
			if (checkKey(i, record)) {
				index = i;
				break;
			}
		}

		// If the record was found then copy it to the passed char[]
		if (index != -1) {
			for (int i = 0; i < recordSize; i++) {
				record[i] = buffer[index++];
			}
			// Operation was successful so return true. The record was found.
			return true;
		}
		// Otherwise it was not in that sector so move to the next overflow
		// bucket if there are any.
		else {
			// If there are any overflow buckets then we have to look there too.
			if (overflowBuckets != 0) {
				// Loop over the overflow buckets searching for the key.
				int count = 1;
				while (count <= overflowBuckets) {
					disk.readSector(firstAllocated + bucketsAllocated + count, buffer);
					for (int i = 0; i < sectorSize; i += recordSize) {
						// If the key is in the buffer then set the index to the
						// start of the record.
						if (checkKey(i, record)) {
							index = i;
							break;
						}
					}
					// If it is found then copy it to the passed char[].
					if (index != -1) {
						for (int i = 0; i < recordSize; i++) {
							record[i] = buffer[index++];
						}
						// Operation successful so return true. The record was
						// found.
						return true;
					}
					count++;
				}
			}
		}
		// Operation has failed so return false. The record was not found.
		return false;
	}

	/**
	 * Gets the hash value of the passed key.
	 * 
	 * @param key
	 *            The value to hash.
	 * @return The integer hash code of the key.
	 */
	private int hash(char[] key) {
		// Convert the key to a string
		String tmp = "";
		for (char letter : key)
			tmp += letter;
		// Return the hash value of the key.
		return Math.abs(tmp.trim().hashCode()) % (bucketsAllocated);
	}

	/**
	 * Searches through the buffer to find the passed key, starting from the
	 * passed index.
	 * 
	 * @param index
	 *            Index to start the search.
	 * @param key
	 *            Key to find.
	 * @return True if the key is found, False otherwise.
	 */
	private boolean checkKey(int index, char[] key) {
		// Retrieve the key at the passed index in the buffer.
		String keyAtIndex = "";
		for (int i = index; i < index + keySize; i++) {
			keyAtIndex += buffer[i];
		}

		// Convert the char[] passed to a string
		String keyToCheck = "";
		for (int i = 0; i < keyAtIndex.length(); i++)
			keyToCheck += key[i];

		// Return if they are equal.
		return keyAtIndex.trim().equals(keyToCheck.trim());

	}

	/**
	 * Copies the source character array to the destination character array.
	 * 
	 * @param src
	 *            Source of the characters to copy.
	 * @param dest
	 *            Destination of the copied characters.
	 * @return True if the operation was successful, otherwise false.
	 */
	private boolean copyArr(char[] src, char[] dest) {
		// If the src array is smaller than the destination then proceed to
		// copy.
		if (src.length <= dest.length) {
			for (int i = 0; i < src.length; i++)
				dest[i] = src[i];
			// Operation was successful so return true.
			return true;
		}
		// Operation failed so return false.
		return false;
	}

	/**
	 * Retrieves the key of the passed record.
	 * 
	 * @param record
	 *            The record to extract the key from.
	 * @return The key of the record
	 */
	private char[] getKey(char[] record) {
		// Initialize a new array to store the key.
		char[] key = new char[keySize];

		// Loop over the record and extract the key.
		for (int i = 0; i < keySize; i++) {
			if (record[i] == '#')
				break;
			key[i] = record[i];
		}

		// Return the key.
		return key;
	}

	/**
	 * Finds the first empty record in the current bucket, or in the overflow
	 * buckets. Returns an array of the index of the empty record and the sector
	 * it was found in.
	 * 
	 * @param bucket
	 *            Initial bucket to search in.
	 * @return An array of the index of the empty record and the sector it was
	 *         found in.
	 */
	private int[] getEmptyRecord(int bucket) {
		// Array to store the index of empty record and the bucket that has the
		// empty record in it.
		int[] indexBucket = new int[2];

		// If there are overflow buckets and the last character in the current
		// buffer isn't a null character then move the next sector to the buffer
		// this one is full.
		if (overflowBuckets != 0 && buffer[buffer.length - 1] != '\000') {
			bucket = firstAllocated + bucketsAllocated + overflowBuckets++;
			disk.readSector(bucket, buffer);
		}
		// If the current buffer is full and there are no overflow buckets then
		// move the next sector on the disk to the buffer.
		if (buffer[buffer.length - 1] != '\000') {
			while (buffer[buffer.length - 1] != '\000') {
				if (overflowBuckets == 0) {
					overflowBuckets++;
					bucket = firstAllocated + overflowBuckets + bucketsAllocated;
					firstOverflow = bucket;
				} else
					bucket += overflowBuckets++;
				disk.readSector(bucket, buffer);
			}
		}

		// Retrieve an index of the first empty space in the sector buffer.
		int i = 0;
		while (i < sectorSize) {
			if (buffer[i] == '\000') {
				if (i + recordSize - 1 > sectorSize) {
					bucket = firstAllocated + bucketsAllocated + (++overflowBuckets);
					disk.readSector(bucket, buffer);
					return getEmptyRecord(bucket);
				}
				indexBucket[0] = i;
				indexBucket[1] = bucket;
				return indexBucket;
			} else if (i + recordSize - 1 > sectorSize) {
				bucket = firstAllocated + bucketsAllocated + (++overflowBuckets);
				disk.readSector(bucket, buffer);
				return getEmptyRecord(bucket);
			}
			i++;
		}
		// Store the index of the empty record and the bucket it was found in
		// and return them.
		indexBucket[0] = i;
		indexBucket[1] = bucket;
		return indexBucket;
	}

}