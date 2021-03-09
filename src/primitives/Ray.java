package primitives;

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
	public Point3D getPoint() {
		//return new Point3D(_p0.getX(), _p0.getY(), _p0.getZ());

	    // For performance Improvement.
		return _p0;
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
