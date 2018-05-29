package fatmango;

import java.io.File;

public class checker {
	public static void main(String[] args) {
		int p = 0;
		String m = "tree7";String k = "tree6";
		int[] dcar = new int[10]; int car = 0;
		Paths ppp = new Paths();int u = 0; int u3 = 0;
		for (int i = 0;i<2988;i++)
		{
			k = ppp.path(i);
			
			//File tempFile = new File(k.trim());  
			File tempFile = new File(k);  
//		    String fileName = tempFile.getName();   
		    
//		    int l = fileName.length();
//		    String num = fileName.substring(0,l-4);
//		    int numt = Integer.parseInt(num);
//		    
//		    System.out.println(fileName);
		    //System.out.println(numt);
			boolean yu = true;
			yu = tempFile.exists();
			if (yu)
			{
				System.out.println("电竞中山桥一霸"); u3 = u3 + 1;
			}
			else
			{
				u = u+1; dcar[car]=i; car = car + 1;
			}
		    
		}
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(u);
		System.out.println(" ");
		System.out.println(u3);
		System.out.println(" ");
		for (int i=0;i<u;i++)
		{
			System.out.println(dcar[i]);
		}
	}
}
