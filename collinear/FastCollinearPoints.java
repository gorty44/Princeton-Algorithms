import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

	private ArrayList<LineSegment> ls = new ArrayList<>();
	private Point[] copy;

	public FastCollinearPoints(Point[] points) {
		isDuplicate(points);
		if (points == null)
			throw new NullPointerException();
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new NullPointerException();
		}
		copy = new Point[points.length];
		for (int i = 0; i < copy.length; i++) {
			copy[i] = points[i];
		}
		Arrays.sort(copy);
		sortingSolution();
	}

	public int numberOfSegments() {
		return ls.size();
	}

	private void show() {
		for (Point p : copy) {
			System.out.println(p);
		}
	}

	private void showLineSegment() {
		for (LineSegment l : ls) {
			System.out.println(l);
		}
	}

	private void sortingSolution() {
		
		for (int i = 0; i < copy.length - 3; i++) {
			Arrays.sort(copy);
			Arrays.sort(copy, copy[i].slopeOrder());
			for (int p = 0, first = 1, last = 2; last < copy.length; last++) {
				while ((last < copy.length)
						&& Double.compare(copy[p].slopeTo(copy[first]), copy[p].slopeTo(copy[last])) == 0)
					last++;
				if ((last - first >= 3) && copy[p].compareTo(copy[first]) < 0)
					ls.add(new LineSegment(copy[p], copy[last - 1]));
				first = last;
			}
		}
	}

	public LineSegment[] segments() {
		return ls.toArray(new LineSegment[ls.size()]);
	}

	private void isDuplicate(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0)
					throw new IllegalArgumentException();
			}
		}
	}

	public static void main(String[] args) {
		Point[] points = { new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(1, 4), new Point(2, 2),
				new Point(2, 1), new Point(3, 1), new Point(4, 1) };
		FastCollinearPoints fcp = new FastCollinearPoints(points);
		LineSegment[] ls = fcp.segments();
		for (int i = 0; i < ls.length; i++) {
			System.out.println(ls[i]);
		}
	}
}
