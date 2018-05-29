package noprocess;

public class Template {
	/**
	 * Define Template Class, generate weighting coeffcients of the kernel matrix according to the input parameters from
     * Parameters.java
	 */
	public double[][] Tpl(int r, double sigma) {
		double index = 0;double sum = 0;
		int n = 2*r + 1;
		double kn[][] = new double[n][n];
		double kn0[][] = new double[n][n];
		for (int i = 0;i<n;i++)
		{
			for (int j = 0;j<n;j++)
			{
				index = (-1)*((i-r)*(i-r) + (j-r)*(j-r))/(2*sigma*sigma);
				kn[i][j] = 1/(2*(Math.PI)*sigma*sigma)*Math.pow(Math.E,index);
				sum = sum + kn[i][j];
			}
		}
		// normalize the kernel matrix
		for (int i = 0;i<n;i++)
		{
			for (int j = 0;j<n;j++)
			{
				kn[i][j] = kn[i][j]/sum;
			}
		}
		for (int i = 0;i<n;i++)
		{
			for (int j = 0;j<n;j++)
			{
				kn0[i][j] = kn[n-1-i][n-1-j];
			}
		}
		return kn0;
	}
}
