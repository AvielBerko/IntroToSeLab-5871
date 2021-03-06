package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {

    final Point3D _q0;
    final Vector _normal;

    public Vector getNormal() {
        return _normal;
    }

    public Plane(Point3D q0, Vector normal) {
        if (normal.equals(new Vector(Point3D.ZERO))) {
            throw new IllegalArgumentException("Normal cannot be a zero vector");
        }

        _q0 = q0;
        _normal = normal;
    }

    public Plane(Point3D a, Point3D b, Point3D c) {
        if (a.equals(b) || a.equals(c) || b.equals(c)) {
            throw new IllegalArgumentException("All points should be different");
        }

        _q0 = a;
        _normal = null; /* TODO: the normal calculation should be implemented next assignment. */
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal; /* TODO: check if point is on the plane */
    }
}
