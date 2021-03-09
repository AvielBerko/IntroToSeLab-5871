package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class represents a sphere in 3D Cartesian coordinate system.
 */
public class Sphere implements Geometry {
    protected final Point3D _center;
    protected final double _radius;

    /**
     * Creates a new sphere by a given center point and radius.
     * @param center The sphere's center.
     * @param radius The sphere's radius.
     * @exception IllegalArgumentException When the radius is equals or less than 0.
     */
    public Sphere(Point3D center, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }

        _center = center;
        _radius = radius;
    }

    /**
     * Returns the sphere's center point.
     * @return A shallow copy of the center point.
     */
    public Point3D getCenter() {
        // return new Point3D(_center.getX(), _center.getY(), _center.getZ());

        // For performance improvement.
        return _center;
    }

    /**
     * Returns the sphere's radius.
     * @return The radius.
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}
