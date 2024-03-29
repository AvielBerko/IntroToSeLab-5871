package geometries;

import primitives.BoundingBox;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class represents a sphere in 3D Cartesian coordinate system.
 */
public class Sphere extends Geometry {
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
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        // source: https://imgur.com/Zh4CSDU

        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();
        // When the ray starts on the center, find one intersection
        if (p0.equals(_center) && alignZero(_radius - maxDistance) <= 0) {
            Point3D p1 = ray.getPoint(_radius);
            return List.of(new GeoPoint(this, p1));
        }

        Vector u = _center.subtract(p0);
        double tm = u.dotProduct(v);
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        // d is greater or equals to r
        if (thSquared <= 0) {
            return null;
        }

        double th = Math.sqrt(thSquared);
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = p0.add(v.scale(t2));
            return List.of(new GeoPoint(this,p1), new GeoPoint(this,p2));
        }

        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point3D p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this,p1));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            Point3D p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p2));
        }

        return null;
    }

    @Override
    protected BoundingBox calculateBoundingBox() {
        return new BoundingBox(
                new Point3D(
                        _center.getX() - _radius,
                        _center.getY() - _radius,
                        _center.getZ() - _radius),
                new Point3D(
                        _center.getX() + _radius,
                        _center.getY() + _radius,
                        _center.getZ() + _radius)
        );
    }


    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}
