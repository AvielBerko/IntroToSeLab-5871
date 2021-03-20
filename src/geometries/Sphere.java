package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public Sphere(double radius, Point3D center) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }

        _radius = radius;
        _center = center;
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
        // n = normalize(P - O)
        return point.subtract(_center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
