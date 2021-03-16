package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 */
public class Plane implements Geometry {

    protected final Point3D _q0;
    protected final Vector _normal;

    /**
     * Creates a new plane by a point on the plane and the plane's normal.
     * @param q0 A point on the plane.
     * @param normal The plane's normal.
     */
    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
    }

    /**
     * Creates a new plane by three different points on the plane.
     * @param a A point on the plane.
     * @param b A point on the plane.
     * @param c A point on the plane.
     * @exception IllegalArgumentException When two of the given points are equals.
     */
    public Plane(Point3D a, Point3D b, Point3D c) {
        if (a.equals(b) || a.equals(c) || b.equals(c)) {
            throw new IllegalArgumentException("All points should be different");
        }

        _q0 = a;

        Vector v1 = b.subtract(a);
        Vector v2 = c.subtract(a);
        _normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Returns a point on the plane.
     * @return A shallow copy of the point.
     */
    public Point3D getPoint() {
        // return new Point3D(_q0.getX(), _q0.getY(), _q0.getZ());

        // For performance improvement.
        return _q0;
    }

    /**
     * @deprecated  use {@link Plane#getNormal(Point3D)}   with null for parameter value.
     * Returns the plane's normal.
     * @return A shallow copy of the normal.
     */
    @Deprecated
    public Vector getNormal() {
        // return new Vector(_normal.getHead());

        // For performance improvement.
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + _q0 +
                ", normal=" + _normal +
                '}';
    }
}
