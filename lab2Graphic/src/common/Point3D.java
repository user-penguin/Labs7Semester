package common;

public class Point3D {
	
	public final double x, y, z, h;
	
	public Point3D(double _x, double _y, double _z, double _h) {
		x = _x; y = _y; z = _z; h = _h;
	}
	
	public Point3D(double[] d) {
		x = d[0]; y = d[1]; z = d[2]; h = d.length == 4 ? d[3] : 1;
	}
	
	@Override
	public String toString() {
		return "{" + x + ", " + y + ", " + z + ", (" + h + ")}";
	}
	
	public double[][] toMatrix() {
		return new double[][] {{x, y, z, h}};
	}
	
	public double x() {
		return x / h;
	}
	
	public double y() {
		return y / h;
	}
	
	public double z() {
		return z / h;
	}
	
}
