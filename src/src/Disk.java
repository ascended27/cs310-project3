package src;
public class Disk{
  private int sectorCount;   // sectors on the disk
  private int sectorSize;    // characters in a sector
  private char[][] store;    // all disk data is stored here

  // for default sectorCount and sectorSize
  public Disk(){
      sectorCount = 10000;
      sectorSize = 512;
      store = new char[sectorCount][sectorSize];
  }

  public Disk(int sectorCount, int sectorSize){
    this.sectorCount = sectorCount;
    this.sectorSize = sectorSize;
    store = new char[sectorCount][sectorSize];

  }
  public void readSector(int sectorNumber, char[] buffer){}   // sector to buffer{}
  public void writeSector(int sectorNumber, char[] buffer){} // buffer to sector{}
  public int getSectorCount(){
     return sectorCount;
  }
  public int getSectorSize(){
     return sectorSize;
  }
}
