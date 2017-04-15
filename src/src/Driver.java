package src;

public class Driver {

	public static void main(String[] args) {
		Disk disk = new Disk(2000, 120);
		DirectFile df = new DirectFile(disk, 60, 27, 0, 600);

		String testString1 = "AaAaAa#United States#12377";
		String testString2 = "AaAaBB#United States#13715";
		String testString3 = "AaBBAa#United States#13612";
		String testString4 = "AaBBBB#United States#12344";
		String testString5 = "BBAaAa#United States#12344";

		String key1 = "AaAaAa";
		String key2 = "AaAaBB";
		String key3 = "AaBBAa";
		String key4 = "AaBBBB";
		String key5 = "BBAaAa";

		char[] tmp1 = testString1.toCharArray();
		char[] tmp2 = testString2.toCharArray();
		char[] tmp3 = key1.toCharArray();
		char[] tmp4 = key2.toCharArray();
		char[] tmp5 = testString3.toCharArray();
		char[] tmp6 = key3.toCharArray();
		char[] tmp7 = testString4.toCharArray();
		char[] tmp8 = key4.toCharArray();
		char[] tmp9 = testString5.toCharArray();
		char[] tmp10 = key5.toCharArray();

		char[] s1 = new char[60];
		char[] s2 = new char[60];
		char[] s3 = new char[60];
		char[] s4 = new char[60];
		char[] s5 = new char[60];

		char[] k1 = new char[60];
		char[] k2 = new char[60];
		char[] k3 = new char[60];
		char[] k4 = new char[60];
		char[] k5 = new char[60];

		for (int i = 0; i < tmp1.length; i++)
			s1[i] = tmp1[i];
		for (int i = tmp1.length; i < 60; i++)
			s1[i] = ' ';
		for (int i = 0; i < tmp2.length; i++)
			s2[i] = tmp2[i];
		for (int i = tmp2.length; i < 60; i++)
			s2[i] = ' ';
		for (int i = 0; i < tmp5.length; i++)
			s3[i] = tmp5[i];
		for (int i = tmp5.length; i < 60; i++)
			s3[i] = ' ';
		for (int i = 0; i < tmp7.length; i++)
			s4[i] = tmp7[i];
		for (int i = tmp7.length; i < 60; i++)
			s4[i] = ' ';
		for (int i = 0; i < tmp9.length; i++)
			s5[i] = tmp9[i];
		for (int i = tmp7.length; i < 60; i++)
			s5[i] = ' ';
		for (int i = 0; i < tmp3.length; i++)
			k1[i] = tmp3[i];
		for (int i = 0; i < tmp4.length; i++)
			k2[i] = tmp4[i];
		for (int i = 0; i < tmp6.length; i++)
			k3[i] = tmp6[i];
		for (int i = 0; i < tmp8.length; i++)
			k4[i] = tmp8[i];
		for (int i = 0; i < tmp10.length; i++)
			k5[i] = tmp10[i];

		System.out.println("Insert s1: " + df.insertRecord(s1));
		System.out.println("Insert s2: " + df.insertRecord(s2));
		System.out.println("Insert s3: " + df.insertRecord(s3));
		System.out.println("Insert s4: " + df.insertRecord(s4));
		System.out.println("Insert s5: " + df.insertRecord(s5));
		System.out.println("Find s1: " + df.findRecord(k1));
		System.out.println("Find s2: " + df.findRecord(k2));
		System.out.println("Find s3: " + df.findRecord(k3));
		System.out.println("Find s4: " + df.findRecord(k4));
		System.out.println("Find s5: " + df.findRecord(k5));
		
		for (int i = 0; i < k1.length; i++)
			System.out.print(k1[i]);
		System.out.println();
		for (int i = 0; i < k2.length; i++)
			System.out.print(k2[i]);
		System.out.println();
		for (int i = 0; i < k3.length; i++)
			System.out.print(k3[i]);
		System.out.println();
		for (int i = 0; i < k4.length; i++)
			System.out.print(k4[i]);
		System.out.println();
		for (int i = 0; i < k5.length; i++)
			System.out.print(k5[i]);
	}
}
