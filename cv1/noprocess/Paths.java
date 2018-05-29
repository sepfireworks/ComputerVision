package noprocess;

public class Paths {
	/**
	 * Define Paths Class, store the the relative paths of samples and return them according to the input from Main.java.
	 */
	public static String[] paths = {
			"/Samples/dog.jpg",
			"/Samples/cat.jpg",
			"/Samples/motorcycle.jpg",
			"/Samples/bicycle.jpg",
			"/Samples/plane.jpg",
			"/Samples/bird.jpg",
			"/Samples/einstein.jpg",
			"/Samples/marilyn.jpg",
			"/Samples/submarine.jpg",
			"/Samples/fish.jpg"};
	public String path(int p0) {
		String p;
		p = paths[p0];
		return p;
	}
}