package primitives;

import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * A ray - A fundamental object in geometry with initial point and direction.
 */
public class Ray {
	private final Point3D _p0;
	private final Vector _dir;

	/**
	 * Creates a new ray with a given point and vector.
	 * The vector will be normalized.
	 * @param p0 The initial point of the ray.
	 * @param dir The direction of the ray. (Can be a non-normalized vector).
	 */
	public Ray(Point3D p0, Vector dir) {
		_dir = dir.normalized();
		//_p0 = new Point3D(_p0.getX(), _p0.getY(), _p0.getZ());

		// For performance improvement.
		_p0 = p0;
	}

	/**
	 * Returns the initial point of the ray.
     *
	 * @return A shallow copy of the initial point.
	 */
	public Point3D getP0() {
		//return new Point3D(_p0.getX(), _p0.getY(), _p0.getZ());

	    // For performance Improvement.
		return _p0;
	}

	/**
	 * Gets a point on the ray by calculating p0 + t*v.
	 * @param t A scalar to calculate the point.
	 * @return A point on the ray.
	 */
	public Point3D getPoint(double t) {
		return _p0.add(_dir.scale(t));
	}

	/**
	 * Returns the direction vector of the ray.
     *
	 * @return A shallow copy of the direction vector.
	 */
	public Vector getDir() {
	    // return new Vector(_dir.getHead());

		// For performance Improvement.
		return _dir;
	}

	/**
	 * Finds the closest point to the ray
	 * @param points list of points to choose one of
	 * @return the closest point if points is not null, else returns null
	 */
	public Point3D findClosestPoint(List<Point3D> points) {
		if (points == null) return null;

		Point3D result = null;
		double closestDistanceSquared = Double.MAX_VALUE;

		for (Point3D point : points) {
			double distanceSquared = point.distanceSquared(_p0);
			if (distanceSquared < closestDistanceSquared) {
				closestDistanceSquared = distanceSquared;
				result = point;
			}
		}

		return result;
	}

	public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
		if (geoPoints == null) return null;

		GeoPoint result = null;
		double closestDistanceSquared = Double.MAX_VALUE;

		for (GeoPoint geo : geoPoints) {
			double distanceSquared = geo.point.distanceSquared(_p0);
			if (distanceSquared < closestDistanceSquared) {
				closestDistanceSquared = distanceSquared;
				result = geo;
			}
		}

		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ray)) return false;
		Ray ray = (Ray) o;
		return _p0.equals(ray._p0) && _dir.equals(ray._dir);
	}
}
