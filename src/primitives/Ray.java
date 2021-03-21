package primitives;

/**
 * A ray - A fundamental object in geometry with initial point and direction.
 */
public class Ray {
	final Point3D _p0;
	final Vector _dir;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ray)) return false;
		Ray ray = (Ray) o;
		return _p0.equals(ray._p0) && _dir.equals(ray._dir);
	}
}
