package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static java.lang.Math.random;
import static primitives.Util.isZero;

/**
 * Basic implementation of RayTracerBase.
 */
public class BasicRayTracer extends RayTracerBase {
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private int glossinessRays = 10;

    /**
     * Constructs a ray tracer object with a given scene
     *
     * @param scene the scene for ray tracing
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a given ray and returns the color of the hit object
     *
     * @param ray the ray to trace
     * @return the color of the hit object
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? _scene.background : calcColor(closestPoint, ray);
    }

    @Override
    public Color averageColor(LinkedList<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(traceRay(ray));
        }
        return color.reduce(rays.size());
    }

    /**
     * Calculates the color of the intersected object
     * on the intersection point with a given ray.
     * Starts the recursion call to calculate the reflection
     * and the refraction with starting level of
     * {@code MAX_CALC_COLOR_LEVEL} and starting k
     * of {@code INITIAL_K}.
     *
     * @param gp  the intersection point with geometry
     * @param ray the ray that caused the intersection
     * @return the color on the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of the intersected object
     * on the intersection point with a given ray.
     * This is recursive function that calculates also
     * the reflection and refraction.
     *
     * @param gp  the intersection point with geometry
     * @param ray the ray that caused the intersection
     * @return the color on the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, double k) {
        Color color = gp.geometry.getEmission()
                .add(getLocalEffects(gp, ray, k));

        if (level == 1) {
            return color;
        }
        Color global = calcGlobalEffects(gp, ray, level, k);
        return color.add(global);
    }

    /**
     * Calculates the effects of the scene's lighting.
     *
     * @param gp  the intersection point
     * @param ray the ray that caused the intersection
     * @return the color on the intersection point
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
                        calcDiffusive(kd, ln, lightIntensity),
                        calcSpecular(ks, l, n, ln, v, nShininess, lightIntensity)
                );
            }
        }

        return lightsColor.add(gp.geometry.getEmission());
    }

    /**
     * Calculates the color at an intersected point
     * with the effect of the diffusion according
     * to the diffusion parameter, the angle between the
     * ray's direction and the normal at the intersection
     * and the light's intensity.
     *
     * @param kD             the diffusion parameter of the material
     * @param ln             the dot product between the ray's direction
     *                       and the normal at the intersection
     * @param lightIntensity the light's intensity
     * @return the color affected by the diffusion of the material
     */
    private Color calcDiffusive(double kD, double ln, Color lightIntensity) {
        return lightIntensity.scale(kD * Math.abs(ln));
    }


    /**
     * Calculates the color at an intersected point
     * with the effect of the specular according
     * to the specular parameter, the ray's direction,
     * the normal at the intersection, the angle between
     * the two given vectors and the light's intensity.
     *
     * @param kS             the specular parameter of the material
     * @param l              the ray's direction
     * @param n              the normal at the intersection
     * @param ln             the dot product between {@code l} and {@code n}
     * @param lightIntensity the light's intensity
     * @return the color affected by the specular of the material
     */
    private Color calcSpecular(double kS, Vector l, Vector n, double ln, Vector v, double nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(ln * 2));
        double vr = Math.pow(v.scale(-1).dotProduct(r), nShininess);
        return lightIntensity.scale(kS * vr);
    }

    /**
     * Calculates the shadow effect at the intersection point
     * with the effect of the blocking-object's transparency.
     *
     * @param light the light source to check if the given
     *              point is blocked by
     * @param l     the ray's direction
     * @param n     the normal at the intersection
     * @param gp    the intersection point and geometry
     * @return the shadow effect from 0 (fully blocked) to 1 (not blocked at all)
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        double lightDistance = light.getDistance(gp.point);

        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightDistance);

        if (intersections == null) {
            return 1.0;
        }

        double ktr = 1.0;
        for (GeoPoint p : intersections) {
            ktr *= p.geometry.getMaterial().kT;
            if (ktr < MIN_CALC_COLOR_K) {
                return 0.0;
            }
        }
        return ktr;
    }

    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that caused the intersection
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDir();
        Material material = gp.geometry.getMaterial();

        for(Ray reflectedRay : constructReflectedRays(gp.point, v, n, material.kG, glossinessRays)) {
            double kkr = k * material.kR;
            if (kkr > MIN_CALC_COLOR_K) {
                color = color.add(
                        calcGlobalEffect(reflectedRay, level, material.kR, kkr).scale(1d / glossinessRays)
                );
            }
        }

        for (int i = 0; i < glossinessRays; ++i) {
            double kkt = k * material.kT;
            if (kkt > MIN_CALC_COLOR_K) {
                color = color.add(
                        calcGlobalEffect(constructRefractedRay(gp.point, v, n, material.kG), level, material.kT, kkt).scale(1d / rays));
            }
        }

        return color;
    }

    /**
     * Calculates the effect of a reflection or a refraction
     * by the objects of the scene
     *
     * @param ray   the reflection or refraction ray
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param kx    the strength's of the reflection or the refraction by the material
     * @param kkx   the strength's of the reflection or the refraction affected
     *              by the level of the tree
     * @return the color affected by the scene's objects
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ?
                _scene.background :
                calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    /**
     * Constructs the reflection ray at the intersection point
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @return new reflection ray
     */
    private Ray[] constructReflectedRays(Point3D point, Vector v, Vector n, double kG, int numOfRays) {
        Vector vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(vn);
        if (isZero(kG)) {
            return new Ray[]{new Ray(point, r, n)};
        }

        Vector[] randomizedVectors = getRandomVectorsOnUnitHemisphere(n, numOfRays);
        Ray[] rays = new Ray[numOfRays];

        if (isZero(kG - 1)) {
            return (Ray[]) Arrays.stream(randomizedVectors).map(vector -> new Ray(point, vector, n)).toArray();
        }
        return (Ray[]) Arrays.stream(randomizedVectors).map(vector -> new Ray(point, vector.scale(1 - kG).add(r.scale(kG)), n)).toArray();
    }

    /**
     * Constructs the refraction ray at the intersection point
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @return new refraction ray
     */
    private Ray[] constructRefractedRays(Point3D point, Vector v, Vector n, double kG) {
        if (isZero(kG)) {
            return new Ray(point, v, n);
        }

        Vector randomized = getRandomVectorOnUnitHemisphere(n.scale(-1));
        if (isZero(kG - 1)) {
            return new Ray(point, randomized, n);
        }

        randomized = randomized.scale(1 - kG)
                .add(v.scale(kG));
        return new Ray(point, randomized, n);
    }

    private Vector[] getRandomVectorsOnUnitHemisphere(Vector n, int numOfVectors) {
        // glossiness source: https://stackoverflow.com/questions/32077952/ray-tracing-glossy-reflection-sampling-ray-direction
        // source: https://my.eng.utah.edu/~cs6958/slides/pathtrace.pdf

        // pick axis with smallest component in normal
        Vector axis;
        if (Math.abs(n.getX()) < Math.abs(n.getY()) && Math.abs(n.getX()) < Math.abs(n.getZ())) {
            axis = new Vector(1, 0, 0);
        } else if (Math.abs(n.getY()) < Math.abs(n.getZ())) {
            axis = new Vector(0, 1, 0);
        } else {
            axis = new Vector(0, 0, 1);
        }

        // find two vectors orthogonal to the normal
        Vector x = n.crossProduct(axis);
        Vector z = n.crossProduct(x);

        Vector[] randomVectors = new Vector[numOfVectors];
        for (int i = 0; i < numOfVectors; i++) {


            // pick a point on the hemisphere bottom
            double u, v, u_2, v_2;
            do {
                u = random() * 2 - 1;
                v = random() * 2 - 1;
                u_2 = u * u;
                v_2 = v * v;
            } while (u_2 + v_2 >= 1);

            // calculate the height of the point
            double w = Math.sqrt(1 - u_2 - v_2);

            randomVectors[i] = x.scale(u)
                    .add(z.scale(v))
                    .add(n.scale(w));
        }
        return randomVectors;
    }

    /**
     * Finds the closest intersection with the ray to the ray's origin
     *
     * @param ray the ray that caused the intersection
     * @return the closest point to the ray's origin
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }

        return ray.findClosestGeoPoint(intersections);
    }
}
