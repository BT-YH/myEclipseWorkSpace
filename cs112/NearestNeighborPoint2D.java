import java.util.ArrayList;

public class NearestNeighborPoint2D {
	ArrayList<MyPoint2D> points = new ArrayList<MyPoint2D>();
	
	public void add(MyPoint2D point) {
		points.add(point);
	}
	
	public MyPoint2D getNearestNeighbor(MyPoint2D point) {
		if (points.size() == 0) {
			return null;
		}
		double distance = Double.MAX_VALUE;
		int index = -1;
		for (MyPoint2D p : points) {
			if (p.getDistance(point) < distance) {
				distance = p.getDistance(point);
				index = points.indexOf(p);
				System.out.println(index);  // check
			}
		}
		return points.get(index);
	}
	
	public MyPoint2D getNearestNeighbor(double x, double y) {
		if (points.size() == 0) {
			return null;
		}
		double distance = Double.MAX_VALUE;
		int index = -1;
		for (MyPoint2D p : points) {
			if (p.getDistance(x, y) < distance) {
				distance = p.getDistance(x,y);
				index = points.indexOf(p);
				System.out.println(index);  // check
			}
		}
		return points.get(index);
	}
	
//	public static void main(String[] args) {
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		for (int i = 0; i < 10; i++) {
//			list.add(i + 1);
//		}
//		for (int p : list) {
//			System.out.println(list.indexOf(p));
//		}
//	}
//	
}

















