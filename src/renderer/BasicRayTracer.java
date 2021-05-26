package renderer;

import elements.LightSource;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.*;
import _scene.Scene;

import java.util.List;

/**
 * Basic implementation of RayTracerBase.
 */
public class BasicRayTracer extends RayTracerBase {
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? _scene.background : calcColor(closestPoint, ray);
    }

    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(getLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Helper function for calculating the color at a given collision.
     * @param gp the point of the collision
     * @param ray the ray from the camera
     * @return the color at the collision
     */
    private Color getLocalEffects(GeoPoint gp, Ray ray, double k) {
        Point3D p = gp.point;
        Vector n = gp.geometry.getNormal(p);
        double kd = gp.geometry.getMaterial().kD;
        double ks = gp.geometry.getMaterial().kS;
        double nShininess = gp.geometry.getMaterial().nShininess;
        Vector v = ray.getDir();


        Color lightsColor = Color.BLACK;
        for (LightSource ls : _scene.lights) {
            Vector l = ls.getL(p);
            double ln = l.dotProduct(n);
            double vn = v.dotProduct(n);
            if (ln * vn <= 0) {
                continue;
            }

            double ktr = transparency(ls, l, n, gp);
            if (ktr * k > MIN_CALC_COLOR_K) {
                Color lightIntensity = ls.getIntensity(gp.point).scale(ktr);
                lightsColor = lightsColor.add(
                        calcDiffusive(kd,l,n, lightIntensity),
                        calcSpecular( ks,l,n, ln, v, nShininess, lightIntensity));
            }
        }

        return lightsColor.add(gp.geometry.getEmission());
    }


    private Color calcSpecular(double ks, Vector l, Vector n,double nl, Vector v, double nShininess, Color lightIntensity) {
        Vector r=l.subtract(n.scale(nl*2));
        double vr=Math.pow(v.scale(-1).dotProduct(r),nShininess);
        return lightIntensity.scale(ks * vr);
    }


    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln=Math.abs(n.dotProduct(l));
        return lightIntensity.scale(kd*ln);
    }


    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDir();
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K) {
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        }
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(
                    calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kT, kkt));
        }
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    private Ray constructReflectedRay(Point3D point, Vector v, Vector n ) {
        Vector vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(vn);
        // use the constructor with 3 arguments to move the head
        return new Ray(point, r, n);
    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);

        double lightDistance = light.getDistance(gp.point);
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightDistance);
/*
        if (intersections != null) {
            _scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        }
        return intersections == null;
*/
        if (intersections == null) {
            return 1.0;
        }

        double ktr = 1.0;
        for (GeoPoint p : intersections) {
            if (alignZero(gp.point.distance(gp.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }

        return ray.findClosestGeoPoint(intersections);
    }
}
