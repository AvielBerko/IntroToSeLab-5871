package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Gives interface for an object that is instersectable.
 */
public interface Intersectable {
    /**
     * Gives all the points where the given ray is intersecting with the object.
     * @param ray A ray to check if is intersecting with the object.
     * @return  {@code List<Point3D>} of all the points of the intersections or {@code null} if no intersections at all.
     */
    List<Point3D> findIntersections(Ray ray);
}
