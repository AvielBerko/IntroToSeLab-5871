package geometries;

import primitives.Vector;
import primitives.Point3D;

/**
 * An interface to represent a geometry.
 */
public interface Geometry {
    /**
     * Returns a normal for a given point on the geometry.
     * @param point A point on the geometry.
     * @return A normal vector.
     */
    public Vector getNormal(Point3D point);
}
