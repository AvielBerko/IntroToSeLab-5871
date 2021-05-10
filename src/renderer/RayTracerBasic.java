package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {
    /**
     * Constructs a ray tracer object with a given scene
     * @param scene the scene for ray tracing
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a given ray and returns the color of the hit object
     * @param ray the ray to trace
     * @return the color of the hit object
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = _scene.geometries.findIntersections(ray);
        if (intersections != null) {
            Point3D closestPoint = ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }

        //ray did not intersect any geometrical object
        return _scene.background;
    }

    private Color calcColor(Point3D point) {
        return _scene.ambientLight.getIntensity();
    }
}
