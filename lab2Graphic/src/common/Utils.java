package common;

import java.text.DecimalFormat;

public class Utils {

	private static DecimalFormat format = new DecimalFormat();
	static {
		format.setGroupingUsed(false);
		format.setMinimumFractionDigits(1);
		format.setMaximumFractionDigits(5);
	}

	public static boolean isZero(double d) {
		return Math.abs(d) < 1E-9;
	}

	public static String fd(double d) {
		return fd(d, true);
	}

	public static String fd(double d, boolean left) {
		return fd(d, 10, left);
	}

	public static String fd(double d, int width) {
		return fd(d, width, false);
	}

	public static String fd(double d, int width, boolean left) {
		if(d < 0 && isZero(d)) d = 0;

		String s = format.format(d);
		if(Double.isNaN(d)) s = "NaN";

		return fillSpaces(s, width, left);
	}

	public static String fillSpaces(String s, int width, boolean left) {
		while(s.length() < width)
			s = left ? (" " + s) : (s + " ");
		return s;
	}
	
}
