package renderer;

import elements.LightSource;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * Basic implementation of RayTracerBase.
 */
public class BasicRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;

    /**
     * Constructs a ray tracer object with a given scene
     * @param scene the scene for ray tracing
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a given ray and returns the color of the hit object
     * @param ray the ray to trace
     * @return the color of the hit object
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray)
                    .add(closestPoint.geometry.getEmission())
                    .add(_scene.ambientLight.getIntensity());
        }

        //ray did not intersect any geometrical object
        return _scene.background;
    }

    /**
     * Helper function for calculating the color at a given collision.
     * @param geoPoint the point of the collision
     * @param ray the ray from the camera
     * @return the color at the collision
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Point3D p = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(p);
        double kd = geoPoint.geometry.getMaterial().kD;
        double ks = geoPoint.geometry.getMaterial().kS;
        double nShininess = geoPoint.geometry.getMaterial().nShininess;
        Vector v = ray.getDir();

        Color lightsColor = Color.BLACK;
        for (LightSource ls : _scene.lights) {
            Vector l = ls.getL(p);
            double ln = l.dotProduct(n);
            double vn = v.dotProduct(n);
            if (ln * vn <= 0) {
                continue;
            }

            if (unshaded(ls, l, n, geoPoint)) {
                Vector r = l.subtract(n.scale(2 * ln)).normalize();
                double vr = v.dotProduct(r);
                double lightEffect = kd * Math.abs(ln) + ks * Math.pow(Math.max(0, -vr),nShininess);
                lightsColor = lightsColor.add(ls.getIntensity(p).scale(lightEffect));
            }
        }

        return lightsColor;
    }

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, light.getDistance(gp.point));
/*
        if (intersections != null) {
            _scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        }
        return intersections == null;
*/
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint p : intersections) {
            double temp = p.point.distance(gp.point) - lightDistance;
            if (alignZero(temp) <= 0)
                return false;
        }
        return true;
    }

}
