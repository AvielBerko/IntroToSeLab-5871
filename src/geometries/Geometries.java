package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Geometries class is a collection of intersectables and can calculates their intersections.
 * It is using Composite design.
 */
public class Geometries implements Intersectable {
    private final List<Intersectable> _intersectables;

    /**
     * Default constructor.
     * Creates an empty list of intersectables.
     */
    public Geometries() {
        // ArrayList has constant-time access and the class probably will access the list a lot more
        // than add to the list, so we chose to use Array List instead of LinkedList.
        _intersectables = new ArrayList<>();
    }

    /**
     * Creates a list of given intersectables.
     * @param intersectables List of intersectables
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new ArrayList<>(Arrays.asList(intersectables));
    }

    /**
     * Adds a list of given intersectables to the current list.
     * @param intersectables List of intersectables to add
     */
    public void add(Intersectable... intersectables) {
        _intersectables.addAll(Arrays.asList(intersectables));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;

        for (Intersectable intersectable : _intersectables) {
            List<Point3D> intersections = intersectable.findIntersections(ray);
            if (intersections == null) {
                continue;
            }

            if (result == null) {
                result = new ArrayList<>(intersections);
                continue;
            }

            result.addAll(intersections);
        }

        return result;
    }
}
