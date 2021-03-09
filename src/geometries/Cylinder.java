package geometries;

import primitives.Ray;

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
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
