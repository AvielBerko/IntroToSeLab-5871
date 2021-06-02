package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate system.
 */
public class Cylinder extends Tube {
    protected final double _height;
    protected final Plane cap0;
    protected final Plane cap1;

    /**
     * Creates a new cylinder by a given axis ray, radius and height.
     *
     * @param axisRay The cylinder's axis ray.
     * @param radius  The cylinder's radius.
     * @param height  The cylinder's height.
     * @throws IllegalArgumentException When the radius or the height are equals or less than 0.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        if (height <= 0) {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        _height = height;

        Point3D p0 = _axisRay.getP0();
        Point3D p1 = _axisRay.getPoint(_height);
        cap0 = new Plane(p0, getNormal(p0));
        cap1 = new Plane(p1, getNormal(p1));
    }

    /**
     * Returns the cylinder's height.
     *
     * @return The height.
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        // Finding the normal to the side:
        // n = normalize(p - o)
        // t = v + (p - p0)
        // o = p0 + t * v

        Vector v = _axisRay.getDir();
        Point3D p0 = _axisRay.getP0();

        // if p == p0, it means (p - p0) is a zero vector.
        // should return the vector of the base as the normal.
        if (p.equals(p0)) {
            return v.scale(-1);
        }

        double t = v.dotProduct(p.subtract(p0));

        // Checking if the point is on the top or the bottom.
        if (isZero(t)) {
            return v.scale(-1);
        }
        if (isZero(t - _height)) {
            return v;
        }

        Point3D o = p0.add(v.scale(t));

        return p.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Vector v0 = _axisRay.getDir();
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
                boolean q0Intersects = v0.dotProduct(q0.point.subtract(p0)) > 0 &&
                        v0.dotProduct(q0.point.subtract(p1)) < 0;
                boolean q1Intersects = v0.dotProduct(q1.point.subtract(p0)) > 0 &&
                        v0.dotProduct(q1.point.subtract(p1)) < 0;

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
                if (v0.dotProduct(q.point.subtract(p0)) > 0 &&
                        v0.dotProduct(q.point.subtract(p1)) < 0) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = cap0.findGeoIntersections(ray, maxDistance);
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
        List<GeoPoint> cap1Point = cap1.findGeoIntersections(ray, maxDistance);
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

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
