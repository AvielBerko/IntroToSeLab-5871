package geometries;

import primitives.Ray;

public class Cylinder extends Tube {
    final double _height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        if (height <= 0) {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        _height = height;
    }

    public double getHeight() {
        return _height;
    }
}
