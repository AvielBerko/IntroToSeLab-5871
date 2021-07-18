package geometries;

import primitives.BoundingBox;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
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
        // LinkedList has constant-time insertion (better than ArrayList linear-time insertion)
        // both have linear-time scanning.
        _intersectables = new LinkedList<>();
    }

    /**
     * Creates a list of given intersectables.
     * @param intersectables List of intersectables
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<>(Arrays.asList(intersectables));
    }

    /**
     * Adds a list of given intersectables to the current list.
     * @param intersectables List of intersectables to add
     */
    public void add(Intersectable... intersectables) {
        _intersectables.addAll(Arrays.asList(intersectables));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersections(ray, maxDistance, false);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean useBB) {
        if (useBB) {
            BoundingBox bb = getBoundingBox();
            if (bb != null && !bb.isIntersecting(ray, maxDistance)) {
                return null;
            }
        }
        // The list of all intersections geoPoints from all geometries
        List<GeoPoint> result = null;

        // Looops on every intersectable in the list of geometries
        for (Intersectable intersectable : _intersectables) {
            List<GeoPoint> intersections = intersectable.findGeoIntersections(ray, maxDistance, useBB);

            // If the current intersectable has no intersections, go to the next intersectable
            if (intersections == null) {
                continue;
            }

            // If this is the first intersectable that has intersections
            if (result == null) {
                result = new LinkedList<>(intersections);
                continue;
            }
            // Add all intersectable's intersections to the result list
            result.addAll(intersections);
        }

        return result;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.surround(
                _intersectables.stream()
                .map(Intersectable::getBoundingBox)
                .toArray(BoundingBox[]::new)
        );
    }
}
