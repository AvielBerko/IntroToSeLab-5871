package geometries;

import primitives.Axis;
import primitives.BoundingBox;
import primitives.Median;
import primitives.Ray;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Bounded Volume Hierarchy
 * Automatically generates a binary tree to create hierarchy of bounding boxes.
 */
public class BVH implements Intersectable {

    private final Intersectable _root;

    /**
     * Constructs the BVH from a given intersectables.
     */
    public BVH(Intersectable... intersectables) {
        _root = generateRoot(Arrays.asList(intersectables), Axis.X);
    }

    /**
     * Generates a binary tree for the BVH by comparing the center of the bounding boxes.
     * Source: https://github.com/eregon/raytracer/blob/master/src/raytracer/BVH.java#L21
     */
    private static Intersectable generateRoot(List<Intersectable> intersectables, Axis axis) {
        if (intersectables.size() == 1) {
            return intersectables.get(0);
        }

        Comparator<Intersectable> comparator = Intersectable.getComparatorByAxis(axis);
        Intersectable median = new Median<>(intersectables, comparator).findMedian();

        // Splits the intersectables around the median
        int maxLeftSize = (intersectables.size() + 1) / 2;
        List<Intersectable> left = new ArrayList<>(maxLeftSize);
        List<Intersectable> right = new ArrayList<>(intersectables.size() - maxLeftSize);
        for(Intersectable intersectable : intersectables) {
            // We need to ensure no less than half go to the right (happens when many are equal)
            if (comparator.compare(intersectable, median) <= 0 && left.size() < maxLeftSize) {
                left.add(intersectable);
            } else {
                right.add(intersectable);
            }
        }

        // Generate the node's children with the next axis
        Axis nextAxis = axis.getNext();
        return new Node(
                generateRoot(left, nextAxis),
                generateRoot(right, nextAxis));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean useBB) {
        if (useBB) {
            BoundingBox bb = getBoundingBox();
            if (bb != null && !bb.isIntersecting(ray)) {
                return null;
            }
        }

        return _root.findGeoIntersections(ray, maxDistance, useBB);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersections(ray, maxDistance, false);
    }


    @Override
    public BoundingBox getBoundingBox() {
        return _root.getBoundingBox();
    }

    /**
     * Helper class for the BVH binary tree.
     */
    private static class Node implements Intersectable {

        private final Intersectable _left, _right;
        private final BoundingBox _boundingBox;

        /**
         * Constructs a new Node with a given children.
         */
        public Node(Intersectable left, Intersectable right) {
            _left = left;
            _right = right;
            _boundingBox = BoundingBox.surround(
                    _left.getBoundingBox(),
                    _right.getBoundingBox());
        }

        @Override
        public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean useBB) {
            if (useBB) {
                BoundingBox bb = getBoundingBox();
                if (bb != null && !bb.isIntersecting(ray)) {
                    return null;
                }
            }

            List<GeoPoint> left = _left.findGeoIntersections(ray, maxDistance, useBB);
            List<GeoPoint> right = _right.findGeoIntersections(ray, maxDistance, useBB);
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }

            // Concatenates the intersections points' lists
            return Stream.concat(left.stream(), right.stream())
                    .collect(Collectors.toList());
        }

        @Override
        public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
            return findGeoIntersections(ray, maxDistance, false);
        }

        @Override
        public BoundingBox getBoundingBox() {
            return _boundingBox;
        }
    }
}
