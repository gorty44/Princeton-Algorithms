
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;

import javax.sound.sampled.Line;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

	private final int x; // x-coordinate of this point
	private final int y; // y-coordinate of this point

	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	@Override
	public int compareTo(Point other) {
		if (other == null) {
			throw new NullPointerException("other point is null");
		}

		int xCompare = Double.compare(x, other.x);
		int yCompare = Double.compare(y, other.y);

		if (xCompare == 0 && yCompare == 0) {
			return 0;
		} else if (yCompare < 0 || (yCompare == 0 && xCompare < 0)) {
			return -1;
		} else {
			return 1;
		}
	}

	public double slopeTo(Point other) {
		if (other == null) {
			throw new NullPointerException("other point is null");
		}

		if (compareTo(other) == 0) {
			return Double.NEGATIVE_INFINITY;
		} else if (x == other.x) {
			return Double.POSITIVE_INFINITY;
		} else if (y == other.y) {
			return +0.0;
		} else {
			return (double) (other.y - y) / (other.x - x);
		}
	}

	public Comparator<Point> slopeOrder() {
		return new PointComparator();
	}

	private class PointComparator implements Comparator<Point> {

		@Override
		public int compare(Point p, Point q) {
			if (p == null) {
				throw new NullPointerException("first point is null");
			} else if (q == null) {
				throw new NullPointerException("second point is null");
			}

			int slopeCompare = Double.compare(slopeTo(p), slopeTo(q));
			if (slopeCompare == 1) {
				return 1;
			} else if (slopeCompare == -1) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}
