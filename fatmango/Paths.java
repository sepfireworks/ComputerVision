package fatmango;

public class Paths {
	/**
	 * Define Paths Class, store the the relative paths of samples and return them according to the input from Main.java.
	 */
	public static String paths1 = "C:\\Users\\Edward\\Desktop\\testing\\testing\\";
	public String path(int p0) {
		String p;String p2;
		p2 = String.valueOf(p0);
		p2 = p2 + ".jpg";
		p = paths1 + p2;
		return p;
	}
}