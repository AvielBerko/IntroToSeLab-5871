package geometries;

import primitives.Vector;
import primitives.Point3D;

public interface Geometry extends Intersectable {
    public Vector getNormal(Point3D point);
}
