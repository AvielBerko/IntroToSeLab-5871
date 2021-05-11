package renderer;

import elements.LightSource;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
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
            return calcColor(closestPoint, ray);
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
            if (ln * vn < 0) {
                continue;
            }

            Vector r = l.subtract(n.scale(2 * ln)).normalize();
            double vr = v.dotProduct(r);
            double lightEffect = kd * Math.abs(ln) + ks * Math.pow(Math.max(0, -vr),nShininess);
            lightsColor = lightsColor.add(ls.getIntensity(p).scale(lightEffect));
        }
        Color emissionColor = geoPoint.geometry.getEmission();
        Color ambientLightColor = _scene.ambientLight.getIntensity();

        lightsColor = lightsColor.add(emissionColor)
                .add(ambientLightColor);
        return lightsColor;
    }
}
