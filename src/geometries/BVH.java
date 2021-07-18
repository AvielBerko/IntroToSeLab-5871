package geometries;

import primitives.Axis;
import primitives.BoundingBox;
import primitives.Median;
import primitives.Ray;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BVH implements Intersectable {

    private final Intersectable _root;

    public BVH(Intersectable... intersectables) {
        _root = generateRoot(Arrays.asList(intersectables), Axis.X);
    }

    private static Intersectable generateRoot(List<Intersectable> intersectables, Axis axis) {
        if (intersectables.size() == 1) {
            return intersectables.get(0);
        }

        Comparator<Intersectable> comparator = Intersectable.getComparatorByAxis(axis);
        Intersectable median = new Median<>(intersectables, comparator).findMedian();

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

    private static class Node implements Intersectable {

        private final Intersectable _left, _right;
        private final BoundingBox _boundingBox;

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
