package common;

public class Point2D {
	
	public final double x, y;
	
	public Point2D(double _x, double _y) {
		x = _x; y = _y;
	}
	
	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
	}
	
}
