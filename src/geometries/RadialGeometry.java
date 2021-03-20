package geometries;

public abstract class RadialGeometry {
    protected final double _radius;

    public RadialGeometry(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }
        _radius = radius;
    }

    /**
     * Returns the sphere's radius.
     *
     * @return The radius.
     */
    public double getRadius() {
        return _radius;
    }
}
