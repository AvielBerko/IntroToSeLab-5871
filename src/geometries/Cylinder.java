package geometries;

import primitives.BoundingBox;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate system.
 */
public class Cylinder extends Tube {
    protected final double _height;
    protected final Plane bottomCap, topCap;

    /**
     * Creates a new cylinder by a given axis ray, radius and height
     *
     * @param axisRay The cylinder's axis ray
     * @param radius  The cylinder's radius
     * @param height  The cylinder's height
     * @throws IllegalArgumentException When the radius or the height are equals or less than 0
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        // checks if height is negative
        if (height <= 0) {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        _height = height;

        Point3D p0 = _axisRay.getP0();
        Point3D p1 = _axisRay.getPoint(_height);
        bottomCap = new Plane(p0, _axisRay.getDir().scale(-1) /* Sets the normal directed outside of the cylinder */);
        topCap = new Plane(p1, _axisRay.getDir());
    }

    /**
     * Returns the cylinder's height
     *
     * @return The height
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        if (isBetweenCaps(p)) {
            return super.getNormal(p);
        }

        // Checks if the point is on the bottom cap
        Vector v0 = _axisRay.getDir();
        Point3D p0 = _axisRay.getP0();
        if (p.equals(p0) || v0.dotProduct(p.subtract(p0)) == 0) {
            return bottomCap.getNormal(p);
        }

        // If we got to here, the point should be on the top cap,
        // because we don't count the points that aren't on the cylinder.
        return topCap.getNormal(p);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D p0 = _axisRay.getP0();
        Point3D p1 = _axisRay.getPoint(_height);
        List<GeoPoint> result = null;

        // Find the tube's intersections
        List<GeoPoint> tubePoints = super.findGeoIntersections(ray, maxDistance);
        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                // Checks if the intersection points are on the cylinder
                GeoPoint q0 = tubePoints.get(0);
                GeoPoint q1 = tubePoints.get(1);
                boolean q0Intersects = isBetweenCaps(q0.point);
                boolean q1Intersects = isBetweenCaps(q1.point);

                if (q0Intersects && q1Intersects) {
                    return tubePoints;
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(q0);
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(q1);
                }
            }

            if (tubePoints.size() == 1) {
                // Checks if the intersection point is on the cylinder
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersections(ray, maxDistance);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap0Point.get(0);
            if (gp.point.distanceSquared(p0) < _radius * _radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.add(gp);
                if (result.size() == 2) {
                    return result;
                }
            }
        }

        // Finds the top cap's intersections
        List<GeoPoint> cap1Point = topCap.findGeoIntersections(ray, maxDistance);
        if (cap1Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap1Point.get(0);
            if (gp.point.distanceSquared(p1) < _radius * _radius) {
                if (result == null) {
                    return List.of(gp);
                }

                result.add(gp);
            }
        }

        return result;
    }

    /**
     * Helper function that checks if a points is between the two caps (not on them, even on the edge)
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point3D p) {
        Vector v0 = _axisRay.getDir();
        Point3D p0 = _axisRay.getP0();
        Point3D p1 = _axisRay.getPoint(_height);

        // Checks against zero vector...
        if (p.equals(p0) || p.equals(p1)) {
            return false;
        }

        return v0.dotProduct(p.subtract(p0)) > 0 &&
                v0.dotProduct(p.subtract(p1)) < 0;
    }

    @Override
    protected BoundingBox calculateBoundingBox() {
        // source: https://gdalgorithms-list.narkive.com/s2wbl3Cd/axis-aligned-bounding-box-of-cylinder#post8
        Vector dir = _axisRay.getDir();
        double sX = (dir.getX() * _height + 2 * _radius * Math.sqrt(1 - dir.getX() * dir.getX())) / 2;
        double sY = (dir.getY() * _height + 2 * _radius * Math.sqrt(1 - dir.getY() * dir.getY())) / 2;
        double sZ = (dir.getZ() * _height + 2 * _radius * Math.sqrt(1 - dir.getZ() * dir.getZ())) / 2;

        Point3D center = _axisRay.getPoint(_height / 2);

        return new BoundingBox(
                new Point3D(
                        center.getX() - sX,
                        center.getY() - sY,
                        center.getZ() - sZ
                ),
                new Point3D(
                        center.getX() + sX,
                        center.getY() + sY,
                        center.getZ() + sZ
                )
        );
    }



    /*************** Admin *****************/
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
