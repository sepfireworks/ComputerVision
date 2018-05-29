package noprocess;

public class Parameters {
	/**
	 * Define Parameters Class, store two kinds of parameters (sigma and r) of convolution and return them according to
     * the input from Main.java.
	 */
	//{Sigma,radius}
	public static double parameters[][] = {
			{20,10},    //dog
			{20,20},    //cat
			{10,5},     //motorcycle
			{10,3},     //bicycle
			{13,8},     //plane
			{16,6},     //bird
			{8,5},      //Einstein
			{10,4},     //marilyn
			{10,5},     //submarine
			{12,4}};    //fish
	public double[] Para(int P0) {
		double P[] = new double[2];
		P = parameters[P0];
		return P;
	}
}
