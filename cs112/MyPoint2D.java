import java.lang.Double;

public class MyPoint2D {
	
	String x, y;
	
	MyPoint2D(double x, double y) {
		this.x = x + "";
		this.y = y + "";
	}

	public double getX() {
		return Double.parseDouble(x);
	}

	public double getY() {
		return Double.parseDouble(y);
	}
	
	public double getDistance(double x, double y) {
		double dx = x - this.getX();
		double dy = y - this.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public double getDistance(MyPoint2D other) {
		double dx = other.getX() - this.getX();
		double dy = other.getY() - this.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}

	public String toString() {
		
		return String.format("(%g, %g)", getX(), getY());
	}
	
	public static void main(String[] args) {
		MyPoint2D p = new MyPoint2D(-4.1214790988292175E-9, -4.1214790988292175E-9);
		System.out.println(p);
	}
}



















