package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry {
    final Ray _axisRay;
    final double _radius;

    public Tube(Ray axisRay, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }

        _axisRay = axisRay;
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
