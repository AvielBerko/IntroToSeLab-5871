package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {
    final Point3D _center;
    final double _radius;

    public Sphere(Point3D center, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }

        _center = center;
        _radius = radius;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
