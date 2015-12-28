package sso.io;

import sso.gauss.Point;

public class PointWithDistance implements Comparable<PointWithDistance> {
	public final Point point;
	public final Double distance;
	public PointWithDistance(Point point, Double distance) {
		this.point = point;
		this.distance = distance;
	}
	@Override
	public int compareTo(PointWithDistance o) {
		if (distance > o.distance) return 1;
		if (distance < o.distance) return -1;
		return 0;
	}
}
