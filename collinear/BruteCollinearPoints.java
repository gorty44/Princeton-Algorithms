import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> ls;

	public BruteCollinearPoints(Point[] points) {
		isDuplicate(points);
		if (points == null)
			throw new NullPointerException();
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new NullPointerException();
		}
		StdDraw.setScale(0, 32736);
		Point[] copy = new Point[points.length];
		for (int i = 0; i < copy.length; i++) {
			copy[i] = points[i];
		}
		Arrays.sort(copy);
		segmentsHelper(copy);
	}

	private void segmentsHelper(Point[] points) {
		ls = new ArrayList<>();
		for (int p = 0; p < points.length - 3; p++)
			for (int q = p + 1; q < points.length - 2; q++)
				for (int r = q + 1; r < points.length - 1; r++)
					for (int s = r + 1; s < points.length; s++)
						if ((points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]))
								&& points[p].slopeTo(points[r]) == points[p].slopeTo(points[s]))
							ls.add((new LineSegment(points[p], points[s])));
	}

	public LineSegment[] segments() {
		return ls.toArray(new LineSegment[ls.size()]);
	}

	public int numberOfSegments() {
		return ls.size();
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
		Point[] p = { new Point(1000, 1000), new Point(2000, 2000), new Point(3000, 3000), new Point(4000, 4000),
				new Point(1000, 2000), new Point(1000, 3000), new Point(1000, 4000), new Point(2000, 3000),
				new Point(3000, 2000), new Point(4000, 1000) };
		BruteCollinearPoints bcp = new BruteCollinearPoints(p);
		LineSegment[] ls = bcp.segments();
		System.out.println(ls.length);

		for (int i = 0; i < ls.length; i++) {
			ls[i].draw();
		}

	}
}
