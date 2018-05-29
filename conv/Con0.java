package conv;

public class Con0 {
	public static void main(String args[])
	{
		// ’‚ «µ•––◊¢ Õ£ø£ø
		System.out.println("¿ÓΩıº«œ„”Õ.");
		int num[][] = new int[32][32];
		int numX[][] = new int[32][32];
		for (int i = 0;i<32;i++)
		{
			for (int j = 0;j<32;j++)
			{
				num[i][j] = 1;
			}
		}
//		//≤‚ ‘
//		for (int i = 0;i<32;i++)
//		{
//			System.out.print(i + "  ");
//		}
//		System.out.println( "  ");
//		for (int i = 0;i<32;i++)
//		{
//			for (int j = 0;j<32;j++)
//			{
//				System.out.print(num[i][j] + "  ");
//			}
//			System.out.print(i + "  ");
//			System.out.println("  ");
//		}// ≤‚ ‘
		
		int r = 1;
		int n = 2*r + 1;
		int kn[][] = new int[n][n];
		int t[][] = new int[n][n];
		for (int i = 0;i<n;i++)
		{
			for (int j = 0;j<n;j++)
			{
				if ((i == r) && (j == r))
					kn[i][j] = n*n;
				else
					kn[i][j] = -1;
			}
		}
//		//≤‚ ‘
//		for (int i = 0;i<n;i++)
//		{
//			System.out.print(i + "  ");
//		}
//		System.out.println( "  ");
//		for (int i = 0;i<n;i++)
//		{
//			for (int j = 0;j<n;j++)
//			{
//				System.out.print(kn[i][j] + "  ");
//			}
//			System.out.print(i + "  ");
//			System.out.println("  ");
//		}// ≤‚ ‘
		for (int i = 0;i<32;i++)
		{
			for (int j = 0;j<32;j++)
			{
				for(int s1=0;s1<n;s1++)
				{
					for(int s2=0;s2<n;s2++)
					{
						if ((i-r+s1<0)||(j-r+s2<0)||(i-r+s1>31)||(j-r+s2>31))
							t[s1][s2] = 0;
						// ±ﬂ‘µºÏ≤‚
						else
							try {
								t[s1][s2] = num[i-r+s1][j-r+s2]*kn[s1][s2];
							}
						catch (Exception e) {
			                e.printStackTrace();
			              }
					}
				}
				int sum = 0;
				for(int p=0;p<n;p++)
				{
					for(int q=0;q<n;q++)
					{
						sum = sum + t[p][q];
					}
				} 
				numX[i][j] = sum;
			}
		}
		//≤‚ ‘
		for (int i = 0;i<32;i++)
		{
			System.out.print(i + "  ");
		}
		System.out.println( "  ");
		for (int i = 0;i<32;i++)
		{
			for (int j = 0;j<32;j++)
			{
				System.out.print(numX[i][j] + "  ");
			}
			System.out.print(i + "  ");
			System.out.println("  ");
		}// ≤‚ ‘
		
		
		
	}
}

