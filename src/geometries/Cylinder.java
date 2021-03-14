package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate system.
 */
public class Cylinder extends Tube {
    protected final double _height;

    /**
     * Creates a new cylinder by a given axis ray, radius and height.
     * @param axisRay The cylinder's axis ray.
     * @param radius The cylinder's radius.
     * @param height The cylinder's height.
     * @exception IllegalArgumentException When the radius or the height are equals or less than 0.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        if (height <= 0) {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        _height = height;
    }

    /**
     * Returns the cylinder's height.
     * @return The height.
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        // Finding the normal to the side:
        // n = normalize(p - o)
        // t = v + (p - p0)
        // o = p0 + t * v

        Vector v = _axisRay.getDir();
        Point3D p0 = _axisRay.getPoint();

        // if p == p0, it means (p - p0) is a zero vector.
        // should return the vector of the base as the normal.
        if (p.equals(p0)) {
            return v.scale(-1);
        }

        double t = v.dotProduct(p.subtract(p0));

        // Checking if the point is on the top or the bottom.
        if (isZero(t)) {
            return v.scale(-1);
        }
        if (isZero(t - _height)) {
            return v;
        }

        Point3D o = p0.add(v.scale(t));

        return p.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
