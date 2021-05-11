package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point3D;

/**
 * An interface to represent a geometry.
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission = Color.BLACK;
    protected Material _material = new Material();

    /**
     * Returns a normal for a given point on the geometry.
     * @param point A point on the geometry.
     * @return A normal vector.
     */
    public abstract Vector getNormal(Point3D point);

    public Color getEmission() {
        return _emission;
    }

    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    public Material getMaterial() {
        return _material;
    }

    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }
}
