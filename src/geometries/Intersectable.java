package geometries;

import primitives.Axis;
import primitives.BoundingBox;
import primitives.Point3D;
import primitives.Ray;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gives interface for an object that is instersectable.
 */
public interface Intersectable {
    /**
     * A pair of 3D point and its geometry.
     */
    class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
    }

    /**
     * Gives all the points where the given ray is intersecting with the object.
     * @param ray A ray to check if is intersecting with the object.
     * @return  {@code List<Point3D>} of all the points of the intersections,
     * or {@code null} if no intersections at all.
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * Gives all the geo points where the given ray is intersecting with the object.
     * @param ray A ray to check if is intersecting with the object.
     * @return  {@code List<GeoPoint>} of all the geo points of the intersections,
     * or {@code null} if no intersections at all.
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    default List<GeoPoint> findGeoIntersections(Ray ray, boolean useBB) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY, useBB);
    }

    /**
     * Gives all the geo points where the given ray is intersecting with the object within the given distance.
     * @param ray A ray to check if is intersecting with the object.
     * @param maxDistance the maximum distance of the intersection points
     * @return  {@code List<GeoPoint>} of all the geo points of the intersections,
     * or {@code null} if no intersections at all.
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    default List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean useBB) {
        if (useBB) {
            BoundingBox bb = getBoundingBox();
            if (bb != null && !bb.isIntersecting(ray)) {
                return null;
            }
        }

        return findGeoIntersections(ray, maxDistance);
    }

    /**
     * Gets the boundingBox surrounding the object
     * @return The bounding box
     */
    BoundingBox getBoundingBox();

    default Point3D getBoundingBoxCenter() {
        BoundingBox bb = getBoundingBox();
        if (bb == null) {
            return null;
        }

        return bb.getCenter();
    }

    static Comparator<Intersectable> getComparatorByAxis(Axis axis) {
        return (bb1, bb2) -> {
            Point3D center1 = bb1.getBoundingBoxCenter();
            if (center1 == null) {
                return 0;
            }

            Point3D center2 = bb2.getBoundingBoxCenter();
            if (center2 == null) {
                return 0;
            }

            return Double.compare(center1.get(axis), center2.get(axis));
        };
    }
}
