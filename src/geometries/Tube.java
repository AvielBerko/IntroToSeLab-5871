package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

/**
 * Tube class represents a tube in 3D Cartesian coordinate system.
 */
public class Tube implements Geometry {
    protected final Ray _axisRay;
    protected final double _radius;

    /**
     * Creates a new tube by a given axis ray and radius.
     * @param axisRay The tube's axis ray.
     * @param radius The tube's radius.
     * @exception IllegalArgumentException When the radius is equals or less than 0.
     */
    public Tube(Ray axisRay, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }

        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * Returns the tube's axis ray.
     * @return A shallow copy of the axis ray.
     */
    public Ray getAxisRay() {
        //return new Ray(_axisRay.getPoint(), _axisRay.getDir());

        // For performance improvement.
        return _axisRay;
    }

    /**
     * Returns the tube's radius.
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
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
