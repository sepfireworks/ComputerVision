package noprocess;

public class Jud {
	/**
	 * Define Jud Class, used to judge the output values from other processing method and ensure that they range from 0-255 (integer)
	 */
	public static int judge(double value)
	{
		if (value<0)
			value = 0;
		else if (value>255)
			value = 255;
		else
			value = (int)value;
		return (int)value;
	}
}
