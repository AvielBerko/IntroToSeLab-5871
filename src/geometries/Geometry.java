package geometries;

import primitives.*;

/**
 * An abstract class to represent a geometry.
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission = Color.BLACK;
    protected Material _material = new Material();
    protected final BoundingBox _boundingBox;

    public Geometry() {
        _boundingBox = calculateBoundingBox();
    }

    /**
     * Returns a normal for a given point on the geometry.
     * @param point A point on the geometry.
     * @return A normal vector.
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * Gets the emission color of the geometry.
     * @return the geometry's emission
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * Chaining method for setting the geometry's emission color.
     * @param emission the new emission color to set
     * @return the current geometry
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * Gets the material of the geometry.
     * @return the geometry's material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * Chaining method for setting the geometry's material.
     * @param material the new material to set
     * @return the current geometry
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return _boundingBox;
    }

    protected BoundingBox calculateBoundingBox() {
        return null;
    }
}
