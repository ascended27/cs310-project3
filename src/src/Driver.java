package src;

public class Driver {

	public static void main(String[] args) {
		Disk disk = new Disk();
		DirectFile df = new DirectFile(disk, 60, 27, 0, 2);

		String testString1 = "A-1 Peak#United States#12377";
		String testString2 = "Abbot-Petit Griffon, Mount#United States#13715";

		String key1 = "A-1 Peak";
		String key2 = "Abbot-Petit Griffon, Mount";

		char[] tmp1 = testString1.toCharArray();
		char[] tmp2 = testString2.toCharArray();
		char[] tmp3 = key1.toCharArray();
		char[] tmp4 = key2.toCharArray();
		
		char[] s1 = new char[60];
		char[] s2 = new char[60];
		char[] k1 = new char[60];
		char[] k2 = new char[60];
		
		for(int i = 0; i < tmp1.length; i++)
			s1[i] = tmp1[i];
		for(int i = 0; i < tmp2.length; i++)
			s2[i] = tmp2[i];
		for(int i = 0; i < tmp3.length; i++)
			k1[i] = tmp3[i];
		for(int i = 0; i < tmp4.length; i++)
			k2[i] = tmp4[i];
		
		System.out.println("Insert s1: " + df.insertRecord(s1));
//		System.out.println("Insert s2: " + df.insertRecord(s2));
		System.out.println("Find s1: " + df.findRecord(k1));
//		System.out.println("Find s2: " + df.findRecord(k2));
		
		for(int i = 0; i < k1.length; i++)
			System.out.print(k1[i]);
		System.out.println();
		for(int i = 0; i < k2.length; i++)
			System.out.print(k2[i]);
	}
}
